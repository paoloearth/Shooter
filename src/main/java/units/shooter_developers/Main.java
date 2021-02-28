package units.shooter_developers;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import units.shooter_developers.menu.GameMenu;

public class Main extends Application {
    Stage _stage;
    Simulation _sim = new Simulation();

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage) throws Exception{

        stage.initStyle(StageStyle.UNDECORATED);
        GameMenu M = new GameMenu();
        M.start(stage);

        /* Compute the bounds of the screen to set the dimension of the window
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        this._stage = stage;

        this._stage.setWidth(screenBounds.getWidth());
        this._stage.setHeight(screenBounds.getHeight());

        stage.initStyle(StageStyle.TRANSPARENT);

        this._sim = new Simulation();

        _sim.start(stage);*/


        //stage.initStyle(StageStyle.TRANSPARENT);
        //Simulation S = new Simulation();
        //S.start(stage);


    }
}

