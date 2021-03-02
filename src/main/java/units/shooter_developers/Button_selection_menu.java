package units.shooter_developers;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;


public class Button_selection_menu extends StackPane {

    double _width, _height;
    Button launch_simulation;
    Button launch_default;

    public Button_selection_menu(double width, double height)
    {
        this._width = width;
        this._height =height;

        setMinSize(_width,_height);
        setPrefSize(_width,_height);
        setMaxSize(_width,_height);


        setAlignment(Pos.CENTER_LEFT);

        VBox V = new VBox();
        V.setFillWidth(true);
        V.setAlignment(Pos.CENTER_LEFT);
        V.setSpacing(10);

        launch_simulation = new Button();
        launch_simulation.setText("LAUNCH SIMULATION");
        launch_simulation.setAlignment(Pos.TOP_LEFT);

        launch_default = new Button();
        launch_default.setText("DEFAULT");
       launch_default.setAlignment(Pos.TOP_LEFT);

        V.getChildren().add(launch_simulation);
        V.getChildren().add(launch_default);

        getChildren().add(V);


    }


    public Button getLaunch_simulation() {
        return launch_simulation;
    }

    public Button getLaunch_default() {
        return launch_default;
    }
}
