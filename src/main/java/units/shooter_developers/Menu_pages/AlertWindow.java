package units.shooter_developers.Menu_pages;

import javafx.stage.Stage;
import units.shooter_developers.MenuAPI.Menu;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;

public class AlertWindow extends Menu {
    double _candidate_width;
    double _candidate_height;

    AlertWindow(Menu other_menu, double candidate_width, double candidate_height){
        super(other_menu);
        _candidate_width = candidate_width;
        _candidate_height = candidate_height;
    }

    @Override
    public void start(Stage stage){
        setStage(stage);
        getStage().centerOnScreen();

        var alert_image = Menu.retrieveImage("alert.png", 1,1);

        addCentralImageView(alert_image, 0.7, 0.7);
        addSecondaryTitle("CAUTION!");
        addFreeItem("BACK", 0.05, 0.2);
        addFreeItem("CONTINUE", 0.76, 0.2);
        addFlashDisclaimer("Game will be reset. Do you want to confirm?", 0.185, 0.93);
        show();

        /* THIS LOOP CAN MAKE USE OF STREAMS?
        menu_items.forEach(item -> etc etc)
         */

        var menu_items = getItems();
        for(var item:menu_items)
        {
            item.setOnMouseReleased(event -> {
                if (item.getName().equals("BACK")) {
                    OptionsMenu options_menu = new OptionsMenu(this);
                    options_menu.start(stage);
                }
                if (item.getName().equals("CONTINUE"))
                {
                    stage.setMaximized(false);
                    setStageDimensions(_candidate_width, _candidate_height);
                    writeModifyingSettings();
                    OptionsMenu options_menu = new OptionsMenu();
                    options_menu.start(stage);
                }
            });
        }


    }

    private void writeModifyingSettings() {
        Properties config = new Properties();

        File configFile = new File("config.ini");
        try{
            FileWriter writer = new FileWriter(configFile);
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            config.put("WIDTH", String.valueOf(getStageWidth()));
            config.put("HEIGHT", String.valueOf(getStageHeight()));
            config.store(writer, "Game settings");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}