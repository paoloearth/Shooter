package units.shooter_developers;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Iterator;

public class Main extends Application{
    private int width = 1920;
    private int height = 1080;

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage theStage)
    {
        theStage.setTitle("Call of Pouty: Tergeste Warfare");

        Group root = new Group();
        Scene scene = new Scene(root);
        theStage.setScene(scene);

        Canvas canvas = new Canvas(width, height);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image default_background = new Image("default_bg.jpg", width, height, false, false);

        ArrayList<String> input_keys = new ArrayList<String>();

        // Put here the loop for pressed keys

        // Put here the loop for pressed keys

        final long[] lastNanoTime = {System.nanoTime()};
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double t = (currentNanoTime - lastNanoTime[0]) / 1000000000.0;
                lastNanoTime[0] = currentNanoTime;

                // game logic
                if (input_keys.contains("LEFT")) {
                    //implement some response here!
                }

                //update objects here!

                //check collisions and illegal movements here!

                gc.drawImage(default_background, 0, 0);
            }
        }.start();


        theStage.show();
    }
}
