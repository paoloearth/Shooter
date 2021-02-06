package units.shooter_developers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;

import java.util.ArrayList;

public class Simulation extends Application{
    private int _width = 1920;
    private int _height = 1080;
    private boolean _close;
    private boolean _pause;

    public Simulation(){
        _width = 1920;
        _height = 1080;
        _close = false;
        _pause = false;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage game_stage) throws Exception {
        game_stage.setTitle("Shooter 2D GAME");

        Group root = new Group();
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
                // calculate time since last update.
                double t = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                    try {
                        GameMenu pause_menu = new GameMenu(reference_to_game_instance);
                        Stage secondary_stage = new Stage();
                        _pause = true;
                        pause_menu.start(secondary_stage);
                        _pause = false;

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                if(_close) {
                    game_stage.close();
                }
            }

                //update objects here!

                //check collisions and illegal movements here!

        }.start();

        game_stage.show();
    }

    public void stop(){
        this._close = true;
    }
}
