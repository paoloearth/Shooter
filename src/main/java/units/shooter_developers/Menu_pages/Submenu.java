package units.shooter_developers.Menu_pages;

import units.shooter_developers.*;
import units.shooter_developers.MenuAPI.Menu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Submenu extends Menu {

    public Submenu(Menu other_menu){
        super(other_menu);
    }

    @Override
    public void createContent(){
        addTextBox("Player_textbox_1", 0, 0, Custom_Settings.URL_COMMANDS_P1, 1, Custom_Settings.WASD_SCALE, "Who is Player 1?");
        addTextBox("Player_textbox_2", 0, 1, Custom_Settings.URL_COMMANDS_P2, 1, Custom_Settings.ARROWS_SCALE, "Who is Player 2?");

        Map<String, String> Name_URL = new Hashtable<>();
        Name_URL.put(Custom_Settings.ARTIST,Custom_Settings.URL_ARTIST);
        Name_URL.put(Custom_Settings.ASTROLOGER,Custom_Settings.URL_ASTROLOGER);
        Name_URL.put(Custom_Settings.WARRIOR,Custom_Settings.URL_WARRIOR);
        addChoiceBox("Player_selection_1", 1, 0, Name_URL, 1,4);
        addChoiceBox("Player_selection_2", 1, 1, Name_URL, 1,4);

        Map<String, String> Map_URL = new Hashtable<>();
        Map_URL.put(Custom_Settings.ISLAND,Custom_Settings.URL_MAP_ISLAND_PNG);
        Map_URL.put(Custom_Settings.DESERT,Custom_Settings.URL_MAP_DESERT_PNG);
        addChoiceBox("Map_selection", 2, 0,Map_URL, Custom_Settings.MAP_SCALE, 1);

        addFreeItem("Play!", 0.6, 0.8);


        for(var item:getItems())
        {
            item.setOnMouseReleased(event -> {
                if (item.getName().equals("Play!")) {
                    var player_names = new ArrayList<String>();
                    player_names.add(getTextBox("Player_textbox_1").get_value());
                    player_names.add(getTextBox("Player_textbox_2").get_value());

                    ArrayList<String> Players_URL = new ArrayList<String>();
                    Players_URL.add(Name_URL.get(getChoiceBox("Player_selection_1").get_value()));
                    Players_URL.add(Name_URL.get(getChoiceBox("Player_selection_2").get_value()));

                    Map<String, String> Map_CSV;
                    Map_CSV = new Hashtable<>();
                    Map_CSV.put(Custom_Settings.ISLAND,Custom_Settings.URL_MAP_ISLAND_CSV);
                    Map_CSV.put(Custom_Settings.DESERT,Custom_Settings.URL_MAP_DESERT_CSV);
                    ArrayList<String> map_data = new ArrayList<String>();
                    map_data.add(Map_CSV.get(getChoiceBox("Map_selection").get_value()));

                    setSimulationInstance(new Simulation(player_names, Players_URL, map_data));
                    try {
                        getStage().close();
                        getSimulationInstance().start(getStage());
                        getStage().setAlwaysOnTop(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
}
