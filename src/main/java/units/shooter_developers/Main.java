package units.shooter_developers;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception{

        boolean required_full_screen = false;

        /* Compute the bounds of the screen to set the dimension of the window */
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        /* Set the window dimension accordingly to the boolean variable full_screen*/
        int WIDTH =  required_full_screen ? (int) screenBounds.getWidth() : Custom_Settings.DEFAULT_X;
        int HEIGHT = required_full_screen ? (int) screenBounds.getHeight() : Custom_Settings.DEFAULT_Y;

        var my_stage = new Stage();

        my_stage.setWidth(WIDTH);
        my_stage.setHeight(HEIGHT);

        my_stage.initStyle(StageStyle.TRANSPARENT);

        Simulation S = new Simulation();
        S.start(my_stage);
    }
}

