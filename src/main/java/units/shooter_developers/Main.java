package units.shooter_developers;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import units.shooter_developers.menu_pages.GameMenu;

public class Main extends Application {

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception{

        stage.initStyle(StageStyle.UNDECORATED);
        GameMenu M = new GameMenu();

        M.readProperties();
        M.start(stage);




    }
}

