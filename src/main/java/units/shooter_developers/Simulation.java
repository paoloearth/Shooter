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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;




public class Simulation extends Application {

    /* Create a new Pane */
    private final Pane root = new Pane();
    private  Stage _stage;

    private Sprite Player_1 ;
    private Sprite Player_2;

    private Scene _scene;
    private AnimationTimer _timer;

    private double _height;
    private double _width;

    private Pair<Double,Double> scaling_factors;

   private GameMap _gameMap;

    private final List<String> _playersNames;
    private final List<String> _playersUrlsSprite;
    private final List<String> _mapUrl;

    public Simulation(List<String> players_names ,  List<String> players_urls_sprite,List<String> map_url )
    {
        this._playersNames = players_names;
        this._playersUrlsSprite = players_urls_sprite;
        this._mapUrl = map_url;
    }


    private void createContent(){
        create_frame();
        create_map();
        create_players();
        create_teleports();
        create_bonus();
    }

    private void create_bonus(){
        new Bonus(root, _gameMap, CustomSettings.URL_HEART,1,10,10, scaling_factors);
    }

    private void create_teleports() {
        var T1  = new Teleport(root,  CustomSettings.URL_TELEPORT, _gameMap, scaling_factors, "" + CustomSettings.TELEPORT_CODE + '0');
        var T2  = new Teleport(root,  CustomSettings.URL_TELEPORT, _gameMap, scaling_factors, "" + CustomSettings.TELEPORT_CODE + '1');

        T1.setDestination(T2);
        T2.setDestination(T1);

    }

    private void create_map() {
        var MR = new MapReader();
        _gameMap = MR.makeMapFromFileContent(getMapUrl(), _width, _height);
        root.getChildren().add(_gameMap.getCells()); }

    private void create_players() {
    Player_1 = new Sprite(root, _gameMap, scaling_factors, getIUrlsSprite(0),4, 1 ,  "" + CustomSettings.PLAYER_CODE + '0', Direction.RIGHT, getIPlayerName(0));
    Player_2 = new Sprite(root, _gameMap, scaling_factors, getIUrlsSprite(1),    4, 1,   "" + CustomSettings.PLAYER_CODE + '1', Direction.LEFT, getIPlayerName(1));
    }

    private void create_frame() {
        _width =  _stage.getWidth();
        _height = _stage.getHeight();

        /* Compute the scaling factor that will be used to update some parameters at RUNTIME*/
        scaling_factors = new Pair<>(  _width / CustomSettings.DEFAULT_X,  _height / CustomSettings.DEFAULT_Y);
    }

    public Scene getScene(){
        return _scene;
    }


    /* ---------------------------------- FIRST THINGS EXECUTED ---------------------------------- */
    public void start(Stage stage){
        _stage = stage;
        stage.centerOnScreen();

        _stage.setTitle(CustomSettings.WINDOW_NAME);
        _stage.setResizable(false);

        createContent();
        _scene = new Scene(root);

        GAME();

        addKeyHandler_PRESS(_scene,    Player_1, Player_2);
        addKeyHandler_RELEASED(_scene, Player_1,Player_2);

        _stage.setScene(_scene);
        _stage.show();
    }

    /* Game Loop and Core Functions */
    private void GAME() {
        _timer = new AnimationTimer() {
            private long last_update = 0;
            @Override
            public void handle(long now) {
                if(now-last_update >= 4_000_000) {
                    all_sprites().forEach(
                            s -> {
                                if(s instanceof DynamicObject) ((DynamicObject) s).defaultMovement(_gameMap);
                                all_players().forEach(s::action);
                                });
                    cleanDeadObjectsAndCheckForVictory();
                    last_update = now;
                }
            }
        };

        startSimulation();
    }

    private void cleanDeadObjectsAndCheckForVictory() {
        root.getChildren().removeIf(node -> (node instanceof PicturedObject) && ((PicturedObject) node).hasToBeRemoved());
        if(all_players().size()==1)  launch_winner_window(all_players().iterator().next());
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
        var win_screen = new WinnerWindow(winner);
        stopSimulation();
        win_screen.start(_stage);
    }





    /* Handle Player Movements */
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
                        handleEscape();
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

    private void handleEscape() {
        var game_menu = new GameMenu(this);
        stopSimulation();
        try {
            game_menu.readProperties();
        } catch (CustomCheckedException.FileManagementException e) {
            System.out.println(e.getMessage() + " Using default settings.");
        }
        game_menu.start(_stage);
        startSimulation();
    }


    public static void main(String[] args) {
        launch(args);
    }


    /* Getters */
    public double get_height() {
        return _height;
    }
    public double get_width() {
        return _width;
    }
    public Pane getRoot() { return root; }
    public Sprite getPlayer_1() { return Player_1; }
    public Sprite getPlayer_2() { return Player_2; }
    public GameMap get_gameMap(){return _gameMap;}
    public String getIPlayerName(int index) { return _playersNames.get(index); }
    public String getIUrlsSprite(int index) { return _playersUrlsSprite.get(index); }
    public String getMapUrl() { return _mapUrl.get(0); }

    public void startSimulation(){ _timer.start(); }
    public void stopSimulation(){ _timer.stop(); }



}




