package units.shooter_developers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class Simulation extends Application{

    /* Default Resolution */
    private static  int WIDTH;
    private static  int HEIGHT ;

    /* Map component */
    private Map _map;

    /* Main component on which we add elements */
     final Pane _root = new Pane();
     Stage _stage = new Stage();
     Scene _scene;

    /* Create Players */
    private Sprite Player_1 ;
    private Sprite Player_2;

    /*Scale the objects on the map according to the resolution*/
    Pair<Double,Double> scaling_factors;




    @Override
    public void start(Stage stage) throws Exception{

        /* Set the stage title of the game & to not resizable*/
        stage.setTitle("Shooter");
        stage.setResizable(false);

        /* Create a new scene & fill it with necessary material */
        createContent();

        /* Add root to the scene */
        Scene scene = new Scene(_root);

        /* Start the game */
        GAME();

        /* Set the listeners to capture the movements of the player */
        addKeyHandler_PRESS(scene,    Player_1, Player_2);
        addKeyHandler_RELEASED(scene, Player_1,Player_2);

        /* Output the scene */
        stage.setScene(scene);
        stage.show();
    }

    private void createContent() throws IOException {

        /* These variables will be passed by the menu to here in some way */
        var required_full_screen = false;
        var map_url = "map_islands.csv";

        /* Create the window */
        create_frame(required_full_screen);

        /* Load map from file */
        create_map(map_url);

        /* Load the players and locate them on the map*/
        create_players();

        /* Load the players and locate them on the map*/
        create_teleports();
    }

    private void create_frame(boolean required_full_screen)  {

        /* Compute the bounds of the screen to set the dimension of the window */
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

       // System.out.println("BOUNDS:" + screenBounds );

        /* Set the window dimension accordingly to the boolean variable full_screen*/
        WIDTH =  required_full_screen?   (int) screenBounds.getWidth()  : Custom_Settings.DEFAULT_X;
        HEIGHT = required_full_screen?   (int) screenBounds.getHeight() : Custom_Settings.DEFAULT_Y;

        _root.setMaxSize(WIDTH, HEIGHT);
        /* Compute the scaling factor that will be used to update some parameters at RUNTIME*/
        scaling_factors = new Pair<>( (double) WIDTH / Custom_Settings.DEFAULT_X, (double) HEIGHT / Custom_Settings.DEFAULT_Y);




    }

    private void create_map(String map_url) throws IOException {
        _map = new Map(_root, map_url, WIDTH,HEIGHT);
    }

    private void create_players() {
        Player_1 = new Sprite(_root,_map , scaling_factors, "astrologer.png",4, 1 , "P1", Direction.RIGHT);
        Player_2 = new Sprite(_root,_map, scaling_factors, "artist.png",     4, 1,  "P2", Direction.LEFT);
    }

    private void create_teleports() {

        var T1  = new Teleport(_root,  Custom_Settings.URL_TELEPORT,  _map, scaling_factors, "T1");
        var T2  = new Teleport(_root,  Custom_Settings.URL_TELEPORT, _map, scaling_factors,"T2");

        T1.setDestination(T2);
        T2.setDestination(T1);

    }



    public void stop(){
      boolean _close = true;
    }

    /* ---------------------------------- GAME LOOP ---------------------------------- */
    private void GAME() {


        /* Create timer */
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                all_sprites().forEach(
                        s ->{
                            switch (s._type)
                            {
                                case "SPRITE" -> ((Sprite) s).move(_map);

                                case "TELEPORT" -> {

                                    var t = (Teleport) s;
                                    if(t.get_bounds().intersects(Player_1.get_bounds()))
                                    {
                                        Player_1.move_to(t.destination);
                                    }
                                    if(t.get_bounds().intersects(Player_2.get_bounds()))
                                    {
                                        Player_2.move_to(t.destination);
                                    }

                                }






                            }

                        }

                );

            }
        };


        timer.start();
    }




    public static void main(String[] args)
    {
        launch(args);
    }

    /* ---------------------------------- HANDLE PLAYERS MOVEMENTS ---------------------------------- */

    private void addKeyHandler_PRESS(Scene scene, Sprite s, Sprite p)
    {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
            {
                switch (ke.getCode()) {
                    case UP    ->  s.setGoNorth(true);
                    case DOWN  ->  s.setGoSouth(true);
                    case LEFT  ->  s.setGoWest(true);
                    case RIGHT ->  s.setGoEast(true);

                    case W    ->  p.setGoNorth(true);
                    case S    ->  p.setGoSouth(true);
                    case A    ->  p.setGoWest(true);
                    case D    ->  p.setGoEast(true);
                }
            }
        });}


    private void addKeyHandler_RELEASED(Scene scene, Sprite s, Sprite p)
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

    //List of pictured object on the map
    private List<Pictured_Object> all_sprites()
    {
        return _root.getChildren().stream().parallel().filter(i -> i instanceof Pictured_Object).map(n->(Pictured_Object)n).collect(Collectors.toList());
    }



}
