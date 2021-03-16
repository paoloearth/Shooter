package units.shooter_developers.Menu_pages;
import units.shooter_developers.*;
import units.shooter_developers.MenuAPI.Menu;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Submenu extends Menu {

    public Submenu(Menu otherMenu){
        super(otherMenu);
    }

    @Override
    public void createContent(){
        addTextBox("Player_textbox_1", 0, 0, CustomSettings.URL_COMMANDS_P1, 1, CustomSettings.WASD_SCALE,   "Who is Player 1?", "Fizz");
        addTextBox("Player_textbox_2", 0, 1, CustomSettings.URL_COMMANDS_P2, 1, CustomSettings.ARROWS_SCALE, "Who is Player 2?", "Buzz");

        Map<String, String> nameUrl = generatePlayersUrl();
        try {
            addChoiceBox("Player_selection_1", 1, 0, nameUrl, 1, 4, 0);
            addChoiceBox("Player_selection_2", 1, 1, nameUrl, 1, 4, 1);
        }catch(CustomCheckedException.IndexOutOfRangeException e){
            System.out.println(e.toString() + " Using default index. Continuing.");
            addChoiceBox("Player_selection_1", 1, 0, nameUrl, 1, 4);
            addChoiceBox("Player_selection_2", 1, 1, nameUrl, 1, 4);

        }

        Map<String, String> mapURL = generateMapsUrl();
        addChoiceBox("Map_selection", 2, 0,mapURL, CustomSettings.MAP_SCALE, 1);

        addFreeItem("START", 0.6, 0.8);

        try {
            getItem("START").setOnMouseReleased(event -> {
                var playerNames = new ArrayList<String>();
                playerNames.add(getTextBoxValue("Player_textbox_1"));
                playerNames.add(getTextBoxValue("Player_textbox_2"));

                ArrayList<String> playersUrl = new ArrayList<>();
                playersUrl.add(nameUrl.get(getChoiceBoxValue("Player_selection_1")));
                playersUrl.add(nameUrl.get(getChoiceBoxValue("Player_selection_2")));

                Map<String, String> mapCsv = generateMapDataUrlDictionary();
                ArrayList<String> mapData = new ArrayList<>();
                mapData.add(mapCsv.get(getChoiceBoxValue("Map_selection")));

                setSimulationInstance(new Simulation(playerNames, playersUrl, mapData));
                getStage().close();
                getSimulationInstance().start(getStage());
                getStage().setAlwaysOnTop(true);
            });
        }catch (CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.toString() + " Fatal error. Closing application.");
            Runtime.getRuntime().exit(1);
        }
    }

    private Map<String, String> generateMapDataUrlDictionary() {
        Map<String, String> mapCsv;
        mapCsv = new Hashtable<>();
        mapCsv.put(CustomSettings.ISLAND, CustomSettings.URL_MAP_ISLAND_CSV);
        mapCsv.put(CustomSettings.DESERT, CustomSettings.URL_MAP_DESERT_CSV);
        return mapCsv;
    }

    private Map<String, String> generateMapsUrl() {
        Map<String, String> mapUrl = new Hashtable<>();
        mapUrl.put(CustomSettings.ISLAND, CustomSettings.URL_MAP_ISLAND_PNG);
        mapUrl.put(CustomSettings.DESERT, CustomSettings.URL_MAP_DESERT_PNG);
        return mapUrl;
    }

    private Map<String, String> generatePlayersUrl() {
        Map<String, String> nameUrl = new Hashtable<>();
        nameUrl.put(CustomSettings.ARTIST, CustomSettings.URL_ARTIST);
        nameUrl.put(CustomSettings.ASTROLOGER, CustomSettings.URL_ASTROLOGER);
        nameUrl.put(CustomSettings.WARRIOR, CustomSettings.URL_WARRIOR);
        return nameUrl;
    }
}
