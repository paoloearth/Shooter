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
import javafx.util.Pair;

import java.util.ArrayList;

public class Simulation extends Application{


    /* Main component on which we add elements */
    private final Pane root = new Pane();

    /* Size of the windows */
    int HEIGHT;
    int WIDTH;


    /* Status of the game */
    private boolean _close,_pause;

    Pair<Double,Double> scaling_factors;





    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage){

        /* Add windows title */
        stage.setTitle("Shooter 2D GAME");

        Scene scene = new Scene(root);
        stage.setScene(scene);


        stage.show();
    }




    public void stop(){
        this._close = true;
    }
}
