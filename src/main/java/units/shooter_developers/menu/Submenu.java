package units.shooter_developers.menu;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Submenu extends Menu{
    Submenu(Menu other_menu){
        super(other_menu);
    }

    public void start(Stage stage){
        setStage(stage);

        SubmenuObject content = new SubmenuObject(getMenuWidth(), getMenuHeight());

        addGenericNode(content);
        removeTitle();
        removeMenuBox();
        Scene scene = new Scene(getRoot());
        getStage().setScene(scene);
        getStage().close();
        getStage().show();
        getStage().setAlwaysOnTop(true);
    }

    private class SubmenuObject extends VBox {

        double _width, _height;
        Player_selection_menu P_menu;
        Map_selection_menu P_map;

        SubmenuObject(double width, double height) {
            this._width = width;
            this._height = height;

            this.setPrefSize(width, height);

            P_menu = new Player_selection_menu(_width, _height / 2);
            P_map = new Map_selection_menu(_width * .8, _height / 2);

            P_map.setAlignment(Pos.CENTER);


            // La parte che segue sarà cambiata per maggiore modularità

            HBox H = new HBox(P_map);


            Button LAUNCH_BUTTON = new Button();
            LAUNCH_BUTTON.setText("LAUNCH SIMULATION");
            H.getChildren().add(LAUNCH_BUTTON);
            H.setAlignment(Pos.CENTER_LEFT);


            LAUNCH_BUTTON.disableProperty().bind(P_map.all_setProperty().or(P_menu.all_setProperty()));


            LAUNCH_BUTTON.setOnAction((EventHandler<ActionEvent>) event ->
                    System.out.println(P_menu.get_players_data() + "" + P_map.get_map_data()));

            getChildren().add(P_menu);
            getChildren().add(H);
        }
    }
}
