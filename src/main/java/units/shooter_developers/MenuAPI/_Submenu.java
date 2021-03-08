package units.shooter_developers.MenuAPI;

import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import units.shooter_developers.*;

import java.util.ArrayList;

public class _Submenu extends Menu {

    public _Submenu(Menu other_menu){
        super(other_menu);
    }

    @Override
    public void createContent(){
        SubmenuObject content = new SubmenuObject(this);
        addGenericNode(content);
    }

    private  class SubmenuObject extends FlowPane {

        _Player_selection_menu player_section;
        _Map_selection_menu map_section;
        _Button_selection_menu buttons_section;
        _Submenu M; // Necessary in order to change the scene

        SubmenuObject(_Submenu M)  {

            this.M = M;

            this.setPrefSize(getMenuWidth(), getMenuHeight());

            player_section = new _Player_selection_menu( 1,             0.5);    // THE TOP WILL OCCUPY HALF THE HEIGHT
            map_section      = new _Map_selection_menu(.7,       0.5);    // THE BOTTOM HALF WILL BE SPLIT 70% for MAP & 25%
            buttons_section = new _Button_selection_menu(.25,   0.5);

            map_section.setAlignment(Pos.TOP_CENTER);
            buttons_section.setAlignment(Pos.CENTER_LEFT);

            getChildren().addAll(player_section,map_section, buttons_section);

            BooleanBinding property = map_section.get_AllSetProperty().or(player_section.get_AllSetProperty());

            disable_button_until_property_is_verified(property);

            add_actions_to_buttons(M);
        }

        private void add_actions_to_buttons(_Submenu M) {
            buttons_section.getMainLaunchButton().setOnAction(event -> launch_simulation(M));
            buttons_section.getDefaultLaunchButton().setOnAction(event -> launch_default(M));
        }

        private void disable_button_until_property_is_verified(BooleanBinding property) {
            buttons_section.getMainLaunchButton().disableProperty().bind(property);
        }

        private void launch_default(_Submenu M) {

            var FAKE_NAMES = new ArrayList<String>();
            FAKE_NAMES.add("FIZZ");
            FAKE_NAMES.add("BUZZ");

            var FAKE_URLS = new ArrayList<String>();
            FAKE_URLS.add(Custom_Settings.URL_ARTIST);
            FAKE_URLS.add(Custom_Settings.URL_WARRIOR);

            var FAKE_MAP = new ArrayList<String>();
            FAKE_MAP.add(Custom_Settings.URL_MAP_ISLAND_CSV);


            M.setSimulationInstance(new Simulation(FAKE_NAMES,FAKE_URLS, FAKE_MAP));
            try {
                getSimulationInstance().start(getStage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



        private void launch_simulation(_Submenu M) {
            M.setSimulationInstance(new Simulation(player_section.get_players_names(), player_section.get_players_URL(), map_section.get_map_data()));
            try {
                M.getStage().close();
                getSimulationInstance().start(getStage());
                getStage().setAlwaysOnTop(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
}
