package units.shooter_developers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Submenu extends Menu{
    Submenu(Menu other_menu){
        super(other_menu);
    }

    public void start(Stage stage) throws IOException {
        setStage(stage);

        SubmenuObject content = new SubmenuObject(getMenuWidth(), getMenuHeight(), this);

        addGenericNode(content);
        removeTitle();
        removeMenuBox();
        Scene scene = new Scene(getRoot());
        getStage().setScene(scene);
        getStage().close();
        getStage().show();
        getStage().setAlwaysOnTop(true);
    }

    private  class SubmenuObject extends VBox {

        double _width, _height;
        Player_selection_menu P_menu;
        Map_selection_menu P_map;
        Submenu M;

        SubmenuObject(double width, double height, Submenu M) throws IOException {

            this.M = M;
            this._width = width;
            this._height = height;

            this.setPrefSize(width, height);

            P_menu = new Player_selection_menu(_width, _height / 2);
            P_map = new Map_selection_menu(_width * .8, _height / 2);

            P_map.setAlignment(Pos.CENTER);


            /* La parte che segue sarà cambiata per maggiore modularità */
            HBox H = new HBox(P_map);


            Button LAUNCH_BUTTON = new Button();
            LAUNCH_BUTTON.setText("LAUNCH SIMULATION");
            H.getChildren().add(LAUNCH_BUTTON);
            H.setAlignment(Pos.CENTER_LEFT);


            LAUNCH_BUTTON.disableProperty().bind(P_map.all_setProperty().or(P_menu.all_setProperty()));

            getChildren().add(P_menu);
            getChildren().add(H);

            H.setAlignment(Pos.CENTER_LEFT);


            LAUNCH_BUTTON.setOnAction(event -> launch_simulation(M));


            /* COMMENT HERE TO  CANCEL THE DEFAULT BUTTON */
            Button DEFAULT = new Button();
            DEFAULT.setText("LAUNCH with DEFAULT parameters");
            H.getChildren().add(DEFAULT);

            DEFAULT.setOnAction(event -> launch_default(M));


        }


        private void launch_default(Submenu M) {

            var FAKE_NAMES = new ArrayList<String>();
            FAKE_NAMES.add("ROBERTUCCIO");
            FAKE_NAMES.add("FILIBERTA");

            var FAKE_URLS = new ArrayList<String>();
            FAKE_URLS.add("warrior.png");
            FAKE_URLS.add("astrologer.png");

            var FAKE_MAP = new ArrayList<String>();
            FAKE_MAP.add("map_islands.csv");


            M.setGameInstance(new Simulation(FAKE_NAMES,FAKE_URLS, FAKE_MAP));
            try {
                M.getStage().close();
                getGameInstance().start(getStage());
                getStage().setAlwaysOnTop(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        private void launch_simulation(Submenu M) {
            M.setGameInstance(new Simulation(P_menu.get_players_names(),P_menu.get_players_URL(), P_map.get_map_data()));
            try {
                M.getStage().close();
                getGameInstance().start(getStage());
                getStage().setAlwaysOnTop(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
