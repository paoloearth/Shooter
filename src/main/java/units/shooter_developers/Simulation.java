package units.shooter_developers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Simulation extends Application{

    /* Size of the windows */
    private int _width;
    private int _height;

    /* Status of the game */
    private boolean _close,_pause;

    /* Main component on which we add elements */
    private final Pane root = new Pane();



    public Simulation(){
       _width  = 1920;
       _height = 1080;
       _close    = false;
       _pause    = false;
    }

    public Simulation(int width, int height){
        this._width = width;
        this._height = height;
        _close    = false;
        _pause    = false;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage game_stage) throws Exception {

        /* Add windows title */
        game_stage.setTitle("Shooter 2D GAME");


        /* Initialize the window */
        create_content();


        Scene scene = new Scene(root);



        game_stage.setScene(scene);


        // Put here the loop for pressed keys

        // Put here the loop for pressed keys


        final long[] lastNanoTime = {System.nanoTime()};

        //ciclo del render della mappa

        var reference_to_game_instance = this;


        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                /*
                    try {
                        GameMenu pause_menu = new GameMenu(reference_to_game_instance);
                        Stage secondary_stage = new Stage();
                        _pause = true;
                        pause_menu.start(secondary_stage);
                        _pause = false;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                */

                if(_close) {
                    game_stage.close();
                }
                while(_pause){};
            }

                //update objects here!

                //check collisions and illegal movements here!

        }.start();

        game_stage.show();
    }

    private void create_content() {
        create_frame();
    }

    private void create_frame() {
        /* Compute the bounds of the screen to set the dimension of the window */
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        this._height = (int) screenBounds.getMaxY()  -  200;
        this._width =  (int) screenBounds.getMaxX()  -  200;

        /* Set the dimension of the window */
        root.setPrefSize(_width, _height);
    }

    public void stop(){
        this._close = true;
    }
}
