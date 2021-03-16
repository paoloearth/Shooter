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
    public void createContent() throws CustomCheckedException.MissingMenuComponentException {
        addTextBox("Player_textbox_1", 0, 0, CustomSettings.URL_COMMANDS_P1, 1, CustomSettings.WASD_SCALE,   "Who is Player 1?", "Fizz");
        addTextBox("Player_textbox_2", 0, 1, CustomSettings.URL_COMMANDS_P2, 1, CustomSettings.ARROWS_SCALE, "Who is Player 2?", "Buzz");

        Map<String, String> nameUrl = generatePlayersUrl();
        addSpritesChoiceBoxes(nameUrl);

        Map<String, String> mapURL = generateMapsUrl();
        addChoiceBox("Map_selection", 2, 0,mapURL, CustomSettings.MAP_SCALE, 1);

        addFreeItem("START", 0.6, 0.8);

        getItem("START").setOnMouseReleased(event -> {
            var playerNames = new ArrayList<String>();

            addPlayerNamesFromTextBox(playerNames);

            ArrayList<String> playersUrlList = getSelectedSpritesUrls(nameUrl);
            Map<String, String> mapCsv = generateMapDataUrlDictionary();
            ArrayList<String> mapData = new ArrayList<>();

            try {
                mapData.add(mapCsv.get(getChoiceBoxValue("Map_selection")));
            } catch (CustomCheckedException.MissingMenuComponentException e) {
                e.printStackTrace();
            }

            setSimulationInstance(new Simulation(playerNames, playersUrlList, mapData));
            getStage().close();
            getSimulationInstance().start(getStage());
            getStage().setAlwaysOnTop(true);
        });
    }

    private void addPlayerNamesFromTextBox(ArrayList<String> playerNames) {
        try {
            playerNames.add(getTextBoxValue("Player_textbox_1"));
            playerNames.add(getTextBoxValue("Player_textbox_2"));
        }catch(CustomCheckedException.MissingMenuComponentException e){
            throw new RuntimeException(e);
        }
    }

    private ArrayList<String> getSelectedSpritesUrls(Map<String, String> nameUrl) {
        ArrayList<String> playersUrlList = new ArrayList<>();

        try {
            playersUrlList.add(nameUrl.get(getChoiceBoxValue("Player_selection_1")));
        } catch (CustomCheckedException.MissingMenuComponentException e) {
            throw new RuntimeException(e);
        }

        try {
            playersUrlList.add(nameUrl.get(getChoiceBoxValue("Player_selection_2")));
        } catch (CustomCheckedException.MissingMenuComponentException e) {
            throw new RuntimeException(e);
        }
        return playersUrlList;
    }

    private void addSpritesChoiceBoxes(Map<String, String> nameUrl) {
        try {
            addChoiceBox("Player_selection_1", 1, 0, nameUrl, 1, 4, 0);
            addChoiceBox("Player_selection_2", 1, 1, nameUrl, 1, 4, 1);
        }catch(CustomCheckedException.IndexOutOfRangeException e){
            System.out.println(e.toString() + " Using default indexing. Continuing.");
            addChoiceBox("Player_selection_1", 1, 0, nameUrl, 1, 4);
            addChoiceBox("Player_selection_2", 1, 1, nameUrl, 1, 4);
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
