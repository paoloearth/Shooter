package units.shooter_developers;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Simulation extends Application{




    /* Main component on which we add elements */
     final Pane _root = new Pane();
     Stage _stage = new Stage();
     Scene _scene;




    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{

        /* Set the stage title of the game & to not resizable*/
        stage.setTitle("Shooter");
        stage.setResizable(false);


        /* Create a new scene & fill it with necessary material */
        createContent();







        stage.show();
    }

    private void createContent() {
        create_frame();
    }

    private void create_frame() {

    }


    public void stop(){
      boolean _close = true;
    }

}
