package units.shooter_developers.MenuAPI;

import javafx.beans.binding.BooleanBinding;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import units.shooter_developers.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class _Submenu extends Menu {

    public _Submenu(Menu other_menu){
        super(other_menu);
    }

    @Override
    public void createContent(){
        SubmenuObject content = new SubmenuObject(this);

        //addGenericNode(content);

        Map<String, String> Name_URL = new Hashtable<>();
        Name_URL.put(Custom_Settings.ARTIST,Custom_Settings.URL_ARTIST);
        Name_URL.put(Custom_Settings.ASTROLOGER,Custom_Settings.URL_ASTROLOGER);
        Name_URL.put(Custom_Settings.WARRIOR,Custom_Settings.URL_WARRIOR);
        setChoiceBox("Player_selection_1", 1, 0, Name_URL, 1,4);
        setChoiceBox("Player_selection_2", 1, 1, Name_URL, 1,4);

        //addFreeItem("Start!", 0.7, 0.8);




        for(var item:getItems())
        {
            item.setOnMouseReleased(event -> {
                if (item.getName().equals("Start!")) {
                    launch_default();
                }
            });
        }
    }

    private void launch_default() {

        var FAKE_NAMES = new ArrayList<String>();
        FAKE_NAMES.add("FIZZ");
        FAKE_NAMES.add("BUZZ");

        var FAKE_URLS = new ArrayList<String>();
        FAKE_URLS.add(Custom_Settings.URL_ARTIST);
        FAKE_URLS.add(Custom_Settings.URL_WARRIOR);

        var FAKE_MAP = new ArrayList<String>();
        FAKE_MAP.add(Custom_Settings.URL_MAP_ISLAND_CSV);


        setSimulationInstance(new Simulation(FAKE_NAMES,FAKE_URLS, FAKE_MAP));
        try {
            getSimulationInstance().start(getStage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private  class SubmenuObject extends FlowPane {

        _Player_selection_menu player_section;
        _Map_selection_menu map_section;
        _Button_selection_menu buttons_section;
        _Submenu M; // Necessary in order to change the scene
        Map<String, String> Name_URL;
        Map<String, String> Map_URL;

        SubmenuObject(_Submenu M)  {

            this.M = M;

            this.setPrefSize(getMenuWidth(), getMenuHeight());


            Name_URL = new Hashtable<>();
            Name_URL.put(Custom_Settings.ARTIST,Custom_Settings.URL_ARTIST);
            Name_URL.put(Custom_Settings.ASTROLOGER,Custom_Settings.URL_ASTROLOGER);
            Name_URL.put(Custom_Settings.WARRIOR,Custom_Settings.URL_WARRIOR);
            player_section = new _Player_selection_menu( 1,0.5, Name_URL);    // THE TOP WILL OCCUPY HALF THE HEIGHT


            Map_URL = new Hashtable<>();
            Map_URL.put(Custom_Settings.ISLAND,Custom_Settings.URL_MAP_ISLAND_PNG);
            Map_URL.put(Custom_Settings.DESERT,Custom_Settings.URL_MAP_DESERT_PNG);
            map_section      = new _Map_selection_menu(.7,       0.5, Map_URL);    // THE BOTTOM HALF WILL BE SPLIT 70% for MAP & 25%
            //buttons_section = new _Button_selection_menu(.25,   0.5);

            map_section.setAlignment(Pos.TOP_CENTER);
            //buttons_section.setAlignment(Pos.CENTER_LEFT);

            getChildren().addAll(player_section,map_section);
            //getChildren().addAll(player_section,map_section, buttons_section);

            //BooleanBinding property = map_section.get_AllSetProperty().or(player_section.get_AllSetProperty());

            //disable_button_until_property_is_verified(property);

            //add_actions_to_buttons(M);
        }

        private void add_actions_to_buttons(_Submenu M) {
            buttons_section.getMainLaunchButton().setOnAction(event -> launch_simulation(M));
            //buttons_section.getDefaultLaunchButton().setOnAction(event -> launch_default(M));
        }

        private void disable_button_until_property_is_verified(BooleanBinding property) {
            buttons_section.getMainLaunchButton().disableProperty().bind(property);
        }



        private void launch_simulation(_Submenu M) {
            Map<String, String> Map_CSV;
            Map_CSV = new Hashtable<>();
            Map_CSV.put(Custom_Settings.ISLAND,Custom_Settings.URL_MAP_ISLAND_CSV);
            Map_CSV.put(Custom_Settings.DESERT,Custom_Settings.URL_MAP_DESERT_CSV);
            ArrayList<String> map_data = new ArrayList<String>();
            map_data.add(Map_CSV.get(map_section.getValue()));

            var player_names = new ArrayList<String>();
            player_names.add(player_section.TB_P1.get_value());
            player_names.add(player_section.TB_P2.get_value());
            ArrayList<String> Players_URL = new ArrayList<String>();
            Players_URL.add(Name_URL.get(player_names.get(0)));
            Players_URL.add(Name_URL.get(player_names.get(1)));

            M.setSimulationInstance(new Simulation(player_names, Players_URL, map_data));
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
