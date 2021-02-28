package units.shooter_developers.menu;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.*;
import javafx.scene.*;
import units.shooter_developers.Simulation;

public class OptionsMenu extends Menu{

    public OptionsMenu(){
        super();
        setGameRunning(false);
    }

    public OptionsMenu(Menu other_menu){
        super(other_menu);
    }

    public OptionsMenu(Simulation game_instance){
        this();
        setGameInstance(game_instance);
        setGameRunning(true);
    }

    @Override
    public void start(Stage menu_stage){
        setStage(menu_stage);
        setStageDimensions(getStageWidth(), getStageHeight());

        setTitle("O P T I O N S");
        this.addSelectableItem("INTERFACE MODE", "light", "dark");

        if(!isGameRunning()) {
            this.addSelectableItem("RESOLUTION",
                    (int) getStageWidth() + "x" + (int) getStageHeight() + " (current)",
                    ((int) getScreenWidth()) + "x" + (int) getScreenHeight() + " (native)",
                    "640x360 (widescreen)",
                    "1000x600",
                    "1024x768",
                    "1280x720 (widescreen)",
                    "1536x864 (widescreen)",
                    "1600x900 (widescreen)",
                    "1920x1080 (widescreen)");
        } else {
            this.addUnanimatedItem("RESOLUTION");
        }
        this.addItem("APPLY");
        this.addItem("BACK");

        Scene scene = new Scene(this.getRoot());
        menu_stage.setTitle("VIDEO GAME");
        menu_stage.setScene(scene);
        menu_stage.centerOnScreen();
        menu_stage.show();

        var menu_items = getItems();
        for(var item:menu_items)
        {
            item.setOnMouseReleased(event -> {
                var item_casted = (MenuItem)item;

                if(item_casted.getName() == "BACK") {
                    GameMenu main_menu = new GameMenu(this);
                    main_menu.start(menu_stage);

                } else if(item_casted.getName() == "APPLY") {
                    applyCurrentSettings();
                    //insert here other possible settings updating
                }
            });



        }

    }



    private void updateResolution(){
        String width_string;
        String height_string;

        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(getSelectableItem("RESOLUTION").getText());
        matcher.find();
        width_string = matcher.group();
        matcher.find();
        height_string = matcher.group();

        double width = Integer.parseInt(width_string);
        double height = Integer.parseInt(height_string);

        var stage = getStage();

        stage.setMaximized(false);
        setStageDimensions(width, height);

        OptionsMenu options_menu = new OptionsMenu(this);
        options_menu.start(stage);

    }

    private void applyCurrentSettings(){
        updateResolution();
        writeSettings();
    }

    private void writeSettings() {
        Properties config = new Properties();
        config.setProperty("WIDTH", String.valueOf(getStageWidth()));
        config.setProperty("HEIGHT", String.valueOf(getStageHeight()));
        config.setProperty("INTERFACE MODE", getSelectableItem("INTERFACE MODE").getText());

        File configFile = new File("config.ini");
        try{
            FileWriter writer = new FileWriter(configFile);
            config.store(writer, "Game settings");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
