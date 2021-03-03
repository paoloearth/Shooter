package units.shooter_developers;

import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
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
        getStage().close();
        show();
        getStage().setAlwaysOnTop(true);
    }

    private  class SubmenuObject extends FlowPane {

        double _width, _height;
        Player_selection_menu P_menu;
        Map_selection_menu P_map;
        Button_selection_menu P_buttons;
        Submenu M;

        SubmenuObject(double width, double height, Submenu M)  {

            this.M = M;
            this._width = width;
            this._height = height;

            this.setPrefSize(width, height);

            P_menu     = new Player_selection_menu( _width,         _height / 2);    // THE TOP WILL OCCUPY HALF THE HEIGHT
            P_map      = new Map_selection_menu(_width * .7,   _height / 2);   // THE BOTTOM HALF WILL BE SPLIT 70% for MAP & 25% w
            P_buttons  = new Button_selection_menu(_width * .25,   _height / 2);

            P_map.setAlignment(Pos.TOP_CENTER);
            P_buttons.setAlignment(Pos.CENTER_LEFT);

            getChildren().add(P_menu);
            getChildren().add(P_map);
            getChildren().add(P_buttons);

            BooleanBinding property = P_map.get_AllSetProperty().or(P_menu.get_AllSetProperty());

            disable_button_until_property_is_verified(property);

            add_actions_to_buttons(M);
        }

        private void add_actions_to_buttons(Submenu M) {
            P_buttons.getLaunch_simulation().setOnAction(event -> launch_simulation(M));
            P_buttons.getLaunch_default().setOnAction(event -> launch_default(M));
        }

        private void disable_button_until_property_is_verified(BooleanBinding property) {
            P_buttons.getLaunch_simulation().disableProperty().bind(property);
        }

        private void launch_default(Submenu M) {

            var FAKE_NAMES = new ArrayList<String>();
            FAKE_NAMES.add("FIZZ");
            FAKE_NAMES.add("BUZZ");

            var FAKE_URLS = new ArrayList<String>();
            FAKE_URLS.add(Custom_Settings.URL_ARTIST);
            FAKE_URLS.add(Custom_Settings.URL_WARRIOR);

            var FAKE_MAP = new ArrayList<String>();
            FAKE_MAP.add(Custom_Settings.URL_MAP_ISLAND_CSV);


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
