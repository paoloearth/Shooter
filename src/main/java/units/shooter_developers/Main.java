package units.shooter_developers;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage theStage) throws Exception{
        GameMenu menu = new GameMenu();

        menu.start(theStage);
    }
}
