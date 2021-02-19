package units.shooter_developers;

import com.sun.scenario.Settings;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class Simulation extends Application{

    /* Default Resolution */
    private static  int WIDTH;
    private static  int HEIGHT ;

    /* Main component on which we add elements */
     final Pane _root = new Pane();
     Stage _stage = new Stage();
     Scene _scene;






    @Override
    public void start(Stage stage) throws Exception{

        /* Set the stage title of the game & to not resizable*/
        stage.setTitle("Shooter");
        stage.setResizable(false);

        /* Create a new scene & fill it with necessary material */
        createContent();

        /* Add root to the scene */
        Scene scene = new Scene(_root);

        /* Output the scene */
        stage.setScene(scene);
        stage.show();
    }

    private void createContent() {
        var required_full_screen = true;
        create_frame(required_full_screen);
    }

    private void create_frame(boolean required_full_screen)
    {

        /* Compute the bounds of the screen to set the dimension of the window */
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        System.out.println("BOUNDS:" + screenBounds );

        /* Set the window dimension accordingly to the boolean variable full_screen*/
        WIDTH =  required_full_screen?   (int) screenBounds.getWidth()  : Custom_Settings.DEFAULT_X;
        HEIGHT = required_full_screen?   (int) screenBounds.getHeight() : Custom_Settings.DEFAULT_Y;

        _root.setMinSize(WIDTH, HEIGHT);
        _root.setMaxSize(WIDTH, HEIGHT);
    }




    public void stop(){
      boolean _close = true;
    }




    public static void main(String[] args)
    {
        launch(args);
    }

}
