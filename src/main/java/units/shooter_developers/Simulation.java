package units.shooter_developers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
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
    double HEIGHT;
    double WIDTH;

    /* Scaling */
    Pair<Double,Double> scaling_factors;

    /* Map */
    Map R;

    /* Map */
    List<String> _players_names;
    List<String> _players_urls_sprite;
    List<String> _map_url;


    public Simulation(List<String> players_names ,  List<String> players_urls_sprite,List<String> map_url )
    {
        this._players_names = players_names;
        this._players_urls_sprite = players_urls_sprite;
        this._map_url = map_url;
     //   System.out.println(get_i_player_name(0) + get_i_player_name(1) + get_map_url() );
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


    private void createContent() throws IOException{
        create_frame();
        create_map();
        create_players();
        create_teleports();
        create_bonus();
    }

    private void create_bonus(){
        new Bonus_Generator(root,R, Custom_Settings.URL_HEART,1,10, scaling_factors);
    }

    private void create_teleports() {

        var T1  = new Teleport(root,  Custom_Settings.URL_TELEPORT,  R, scaling_factors, "T1");
        var T2  = new Teleport(root,  Custom_Settings.URL_TELEPORT,  R, scaling_factors, "T2");


        T1.setDestination(T2);
        T2.setDestination(T1);

    }

    private void create_map() throws IOException {
        R = new Map(root, get_map_url(), WIDTH,HEIGHT);
    }



    private void create_players() {
        Player_1 = new Sprite(root,R , scaling_factors, get_i_urls_sprite(0),4, 1 , "P1", Direction.RIGHT, get_i_player_name(0));
        Player_2 = new Sprite(root,R, scaling_factors, get_i_urls_sprite(1),    4, 1,   "P2", Direction.LEFT, get_i_player_name(1));
    }

    private void create_frame() {


        WIDTH =  _stage.getWidth();
        HEIGHT = _stage.getHeight();


        /* Compute the scaling factor that will be used to update some parameters at RUNTIME*/
        scaling_factors = new Pair<>(  WIDTH / Custom_Settings.DEFAULT_X,  HEIGHT / Custom_Settings.DEFAULT_Y);


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
        AnimationTimer timer = new AnimationTimer() {
            private long last_update = 0;
            @Override
            public void handle(long now) {

                if(now-last_update >= 4_000_000) {

                    all_sprites().forEach(
                            s -> {
                                switch (s._type) {
                                    case "SPRITE" -> {

                                        ((Sprite) s).move(R);
                                    }

                                    case "PROJECTILE" -> {

                                        var p = (Projectile) s;

                                        p.translate(R);

                                        if (p.intersect(Player_1)) p.update(R, Player_1);
                                        if (p.intersect(Player_2)) p.update(R, Player_2);
                                    }

                                    case "TELEPORT" -> {
                                        var t = (Teleport) s;

                                        if (t.intersect(Player_1)) t.update(R, Player_1);
                                        if (t.intersect(Player_2)) t.update(R, Player_2);
                                    }


                                    case "BONUS" -> {

                                        var b = (Bonus_Generator) s;

                                        if (b.intersect(Player_1)) b.update(R, Player_1);
                                        if (b.intersect(Player_2)) b.update(R, Player_2);

                                    }


                                }
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

        _timer = timer;

        startSimulation();
    }

    public void startSimulation(){
        _timer.start();
    }

    public void stopSimulation(){
        _timer.stop();
    }



    private void remove_dead_objects() {
        root.getChildren().removeIf(node -> (node instanceof Pictured_Object) && ((Pictured_Object)node)._isDead.getValue());

        if (Player_1._isDead.getValue() || Player_2._isDead.getValue())
        {

            var win_screen =  Player_2._isDead.getValue() ?
                    new WinnerWindow(WIDTH, HEIGHT,  Player_1) :
                    new WinnerWindow(WIDTH, HEIGHT, Player_2);


            stopSimulation();
            win_screen.start(_stage);


        }
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


    private List<Pictured_Object> all_sprites()
    {
        return root.getChildren().stream().parallel().filter(i -> i instanceof Pictured_Object).map(n->(Pictured_Object)n).collect(Collectors.toList());
    }



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

}




