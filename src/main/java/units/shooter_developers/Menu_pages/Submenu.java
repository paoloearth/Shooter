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
        addTextBox("Player_textbox_1", 0, 0, CustomSettings.URL_COMMANDS_P1, 1, CustomSettings.WASD_SCALE, "Who is Player 1?", "Rajoy");
        addTextBox("Player_textbox_2", 0, 1, CustomSettings.URL_COMMANDS_P2, 1, CustomSettings.ARROWS_SCALE, "Who is Player 2?", "Zapatero");

        Map<String, String> Name_URL = generatePlayersUrl();
        addChoiceBox("Player_selection_1", 1, 0, Name_URL, 1,4);
        addChoiceBox("Player_selection_2", 1, 1, Name_URL, 1,4);

        Map<String, String> Map_URL = generateMapsUrl();
        addChoiceBox("Map_selection", 2, 0,Map_URL, CustomSettings.MAP_SCALE, 1);

        addFreeItem("Play!", 0.6, 0.8);

        getItem("Play!").setOnMouseReleased(event -> {
            var player_names = new ArrayList<String>();
            player_names.add(getTextBoxValue("Player_textbox_1"));
            player_names.add(getTextBoxValue("Player_textbox_2"));

            ArrayList<String> Players_URL = new ArrayList<String>();
            Players_URL.add(Name_URL.get(getChoiceBoxValue("Player_selection_1")));
            Players_URL.add(Name_URL.get(getChoiceBoxValue("Player_selection_2")));

            Map<String, String> Map_CSV = generateMapDataUrlDictionary();
            ArrayList<String> map_data = new ArrayList<String>();
            map_data.add(Map_CSV.get(getChoiceBoxValue("Map_selection")));

            setSimulationInstance(new Simulation(player_names, Players_URL, map_data));
            try {
                getStage().close();
                getSimulationInstance().start(getStage());
                getStage().setAlwaysOnTop(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private Map<String, String> generateMapDataUrlDictionary() {
        Map<String, String> Map_CSV;
        Map_CSV = new Hashtable<>();
        Map_CSV.put(CustomSettings.ISLAND, CustomSettings.URL_MAP_ISLAND_CSV);
        Map_CSV.put(CustomSettings.DESERT, CustomSettings.URL_MAP_DESERT_CSV);
        return Map_CSV;
    }

    private Map<String, String> generateMapsUrl() {
        Map<String, String> Map_URL = new Hashtable<>();
        Map_URL.put(CustomSettings.ISLAND, CustomSettings.URL_MAP_ISLAND_PNG);
        Map_URL.put(CustomSettings.DESERT, CustomSettings.URL_MAP_DESERT_PNG);
        return Map_URL;
    }

    private Map<String, String> generatePlayersUrl() {
        Map<String, String> Name_URL = new Hashtable<>();
        Name_URL.put(CustomSettings.ARTIST, CustomSettings.URL_ARTIST);
        Name_URL.put(CustomSettings.ASTROLOGER, CustomSettings.URL_ASTROLOGER);
        Name_URL.put(CustomSettings.WARRIOR, CustomSettings.URL_WARRIOR);
        return Name_URL;
    }
}
