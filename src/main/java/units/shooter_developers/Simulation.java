package units.shooter_developers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;
import units.shooter_developers.Menu_pages.GameMenu;
import units.shooter_developers.Menu_pages.WinnerWindow;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;




public class Simulation extends Application {

    /* Create a new Pane */
    private final Pane root = new Pane();
    private  Stage _stage;

    /* Sprites */
    private Sprite Player_1 ;
    private Sprite Player_2;

    /* Timers and scene */
    private Scene _scene;
    private AnimationTimer _timer;

    /* Window dimensions */
    private double HEIGHT;
    private double WIDTH;

    /* Scaling */
    private Pair<Double,Double> scaling_factors;

    /* Map */
   private GameMap gamemap;

    /* Map */
    private final List<String> _players_names;
    private final List<String> _players_urls_sprite;
    private final List<String> _map_url;


    public Simulation(List<String> players_names ,  List<String> players_urls_sprite,List<String> map_url )
    {
        this._players_names = players_names;
        this._players_urls_sprite = players_urls_sprite;
        this._map_url = map_url;
    }

    public String get_i_player_name(int index)
    {
        return _players_names.get(index);
    }
    public String get_i_urls_sprite(int index)
    {
        return _players_urls_sprite.get(index);
    }
    public String get_map_url()
    {
        return _map_url.get(0);
    }


    private void createContent(){
        create_frame();
        create_map();
        create_players();
        create_teleports();
        create_bonus();
    }

    private void create_bonus(){
        new Bonus(root, gamemap, CustomSettings.URL_HEART,1,10,10, scaling_factors);
    }

    private void create_teleports() {

        var T1  = new Teleport(root,  CustomSettings.URL_TELEPORT, gamemap, scaling_factors, "" + CustomSettings.TELEPORT_CODE + '0');
        var T2  = new Teleport(root,  CustomSettings.URL_TELEPORT, gamemap, scaling_factors, "" + CustomSettings.TELEPORT_CODE + '1');


        T1.setDestination(T2);
        T2.setDestination(T1);

    }

    private void create_map() {
        var MR = new MapReader();
        gamemap = MR.makeMapFromFileContent(get_map_url(), WIDTH,HEIGHT);
        root.getChildren().add(gamemap.getCells());

    }


    private void create_players() {
    Player_1 = new Sprite(root, gamemap, scaling_factors, get_i_urls_sprite(0),4, 1 ,  "" + CustomSettings.PLAYER_CODE + '0', Direction.RIGHT, get_i_player_name(0));
    Player_2 = new Sprite(root, gamemap, scaling_factors, get_i_urls_sprite(1),    4, 1,   "" + CustomSettings.PLAYER_CODE + '1', Direction.LEFT, get_i_player_name(1));
    }

    private void create_frame() {


        WIDTH =  _stage.getWidth();
        HEIGHT = _stage.getHeight();


        /* Compute the scaling factor that will be used to update some parameters at RUNTIME*/
        scaling_factors = new Pair<>(  WIDTH / CustomSettings.DEFAULT_X,  HEIGHT / CustomSettings.DEFAULT_Y);


    }

    public Scene getScene(){
        return _scene;
    }


    /* ---------------------------------- FIRST THINGS EXECUTED ---------------------------------- */
    public void start(Stage stage) throws  IOException{
        this._stage = stage;
        stage.centerOnScreen();

        /* Set the stage title of the game */
        _stage.setTitle("Shooter");
        _stage.setResizable(false);

        /* Create a new scene & fill it with necessary material */
        createContent();

        /* Add root to the scene */
        _scene = new Scene(root);

        /* Start the game */
        GAME();

        /* Set the listeners to capture the movements of the player */
        addKeyHandler_PRESS(_scene,    Player_1, Player_2);
        addKeyHandler_RELEASED(_scene, Player_1,Player_2);

        /* Output the scene */
        _stage.setScene(_scene);
        _stage.show();
    }

    /* ---------------------------------- GAME LOOP ---------------------------------- */
    private void GAME() {


        /* Create timer */

        _timer = new AnimationTimer() {
            private long last_update = 0;
            @Override
            public void handle(long now) {

                if(now-last_update >= 4_000_000) {

                    all_sprites().forEach(
                            s -> {
                                if(s instanceof DynamicObject) ((DynamicObject) s).defaultMovement(gamemap);
                                all_players().forEach(s::action);
                                }
                    );
                    try {
                        remove_dead_objects();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    last_update = now;
                }

            }
        };

        startSimulation();
    }

    public void startSimulation(){
        _timer.start();
    }

    public void stopSimulation(){
        _timer.stop();
    }



    private void remove_dead_objects() {

     clean_dead_objects();
     if(all_players().size()==1)  launch_winner_window(all_players().iterator().next());
    }

    private void clean_dead_objects() {
        root.getChildren().removeIf(node -> (node instanceof PicturedObject) && ((PicturedObject) node).hasToBeRemoved());
    }

    protected List<PicturedObject> all_sprites()
    {
        return root.getChildren().stream().parallel().filter(i -> i instanceof PicturedObject).map(n->(PicturedObject)n).collect(Collectors.toList());
    }

    protected Set<Sprite> all_players()
    {
        return all_sprites().stream().filter(i -> i instanceof Sprite).map(n->(Sprite)n).collect(Collectors.toSet());
    }


    private void launch_winner_window(Sprite winner) {
        var win_screen = new WinnerWindow(WIDTH, HEIGHT, winner);
        stopSimulation();
        win_screen.start(_stage);
    }


    /* ---------------------------------- MAIN ---------------------------------- */
    public static void main(String[] args) {
        launch(args);
    }


    /* ---------------------------------- HANDLE PLAYERS MOVEMENTS ---------------------------------- */

    private void addKeyHandler_PRESS(Scene scene, Sprite s, Sprite p)
    {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
            {
                switch (ke.getCode()) {
                    case UP    ->  p.setGoNorth(true);
                    case DOWN  ->  p.setGoSouth(true);
                    case LEFT  ->  p.setGoWest(true);
                    case RIGHT ->  p.setGoEast(true);
                    case ENTER ->  p.shoot(root);

                    case W    ->  s.setGoNorth(true);
                    case S    ->  s.setGoSouth(true);
                    case A    ->  s.setGoWest(true);
                    case D    ->  s.setGoEast(true);
                    case SPACE -> s.shoot(root);

                    case ESCAPE -> {
                        var game_menu = new GameMenu(this);
                        stopSimulation();
                        game_menu.readProperties();
                        game_menu.start(_stage);
                        startSimulation();
                    }
                    /*  remove where are not necessary any more                                 */
                    case P -> {
                        var game_menu = new GameMenu(this);
                    }
                    case R -> {
                        var game_menu = new GameMenu(this);
                        startSimulation();
                    }
                }
            }
        });}








    private void addKeyHandler_RELEASED(Scene scene, Sprite p, Sprite s)
    {
        scene.addEventHandler(KeyEvent.KEY_RELEASED, ke -> {
            {
                switch (ke.getCode()) {
                    case UP    ->  s.setGoNorth(false);
                    case DOWN  ->  s.setGoSouth(false);
                    case LEFT  ->  s.setGoWest(false);
                    case RIGHT ->  s.setGoEast(false);

                    case W ->  p.setGoNorth(false);
                    case S ->  p.setGoSouth(false);
                    case A ->  p.setGoWest(false);
                    case D ->  p.setGoEast(false);
                }
            }
        });}

    public double getHEIGHT() {
        return HEIGHT;
    }
    public double getWIDTH() {
        return WIDTH;
    }
    public Pane getRoot() { return root; }
    public Sprite getPlayer_1() { return Player_1; }
    public Sprite getPlayer_2() { return Player_2; }
    public GameMap getGamemap(){return gamemap;}
}




