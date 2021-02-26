package units.shooter_developers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;




public class Simulation extends Application {

    /* Create a new Pane */
    private final Pane root = new Pane();
    private  Stage _stage;


    /* Create a rectangle */
    private Sprite Player_1 ;
    private Sprite Player_2;

    private Scene _scene;
    private AnimationTimer _timer;



    int HEIGHT;
    int WIDTH;

    Pair<Double,Double> scaling_factors;
    Map R;



    private void createContent() throws IOException{
        create_frame(false);
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
        var T2  = new Teleport(root,  Custom_Settings.URL_TELEPORT,  R, scaling_factors,"T2");


        T1.setDestination(T2);
        T2.setDestination(T1);

    }

    private void create_map() throws IOException {
        R = new Map(root, "map_islands.csv", WIDTH,HEIGHT);
    }



    private void create_players() {
        Player_1 = new Sprite(root,R , scaling_factors, "astrologer.png",4, 1 , "P1", Direction.RIGHT);
        Player_2 = new Sprite(root,R, scaling_factors, "artist.png",    4, 1,  "P2", Direction.LEFT);
    }

    private void create_frame(boolean full_screen) {

        /* Compute the bounds of the screen to set the dimension of the window */
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        System.out.println("BOUNDS:" + screenBounds );

        /* Set the window dimension accordingly to the boolean variable full_screen*/
        WIDTH =  full_screen?   (int) screenBounds.getWidth() : Custom_Settings.DEFAULT_X;
        HEIGHT = full_screen?   (int) screenBounds.getHeight() : Custom_Settings.DEFAULT_Y;

        WIDTH = (int) _stage.getWidth();
        HEIGHT = (int) _stage.getHeight();


        /* Compute the scaling factor that will be used to update some parameters at RUNTIME*/
        scaling_factors = new Pair<>( (double) WIDTH / Custom_Settings.DEFAULT_X, (double) HEIGHT / Custom_Settings.DEFAULT_Y);


    }

    public Scene getScene(){
        return _scene;
    }


    /* ---------------------------------- FIRST THINGS EXECUTED ---------------------------------- */
    public void start(Stage stage) throws  IOException{
        this._stage = stage;;



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
            @Override
            public void handle(long now) {

                all_sprites().forEach(
                        s ->{
                            switch (s._type)
                            {
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

                                    if(t.intersect(Player_1)) t.update(R,Player_1);
                                    if(t.intersect(Player_2)) t.update(R,Player_2);
                                }


                                case "BONUS" -> {

                                    var b = (Bonus_Generator) s;

                                    if(b.intersect(Player_1)) b.update(R,Player_1);
                                    if(b.intersect(Player_2)) b.update(R,Player_2);

                                }


                            }
                        }
                );
                try {
                    remove_dead_objects();
                } catch (Exception e) {
                    e.printStackTrace();
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

        if (Player_1._isDead.getValue() || Player_1._isDead.getValue())
        {
            System.out.println("GIOCO FINITO!");
            // System.out.println("One of the player is dead");
            // LANCIA UN'ALTRA "SCENA"
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
                    case UP    ->  s.setGoNorth(true);
                    case DOWN  ->  s.setGoSouth(true);
                    case LEFT  ->  s.setGoWest(true);
                    case RIGHT ->  s.setGoEast(true);
                    case ENTER ->  s.shoot(root);

                    case W    ->  p.setGoNorth(true);
                    case S    ->  p.setGoSouth(true);
                    case A    ->  p.setGoWest(true);
                    case D    ->  p.setGoEast(true);
                    case SPACE -> p.shoot(root);

                    case ESCAPE -> {
                        var game_menu = new GameMenu(this);
                        // PAUSE HERE THE GAME TIMER!!
                        stopSimulation();
                        game_menu.start(_stage);
                        startSimulation();
                    }
                    /********** temporal controls added to check the effects of the simulation stopping */
                    /*          remove where are not necessary any more                                 */
                    case P -> {
                        var game_menu = new GameMenu(this);
                    }
                    case R -> {
                        var game_menu = new GameMenu(this);
                        startSimulation();
                    }
                    /************************************************************************************/
                }
            }
        });}


    private List<Pictured_Object> all_sprites()
    {
        return root.getChildren().stream().parallel().filter(i -> i instanceof Pictured_Object).map(n->(Pictured_Object)n).collect(Collectors.toList());
    }



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

}




