package units.shooter_developers;

import java.io.*;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.*;
import javafx.util.Pair;

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
        getStage().centerOnScreen();

        setTitle("O P T I O N S");
        this.addSelectorItem("INTERFACE MODE", "light", "dark");

        this.addSelectorItem("RESOLUTION",
                (int) getStageWidth() + "x" + (int) getStageHeight() + " (current)",
                ((int) getScreenWidth()) + "x" + (int) getScreenHeight() + " (native)",
                "640x360 (widescreen)",
                "1000x600",
                "1024x768",
                "1280x720 (widescreen)",
                "1536x864 (widescreen)",
                "1600x900 (widescreen)",
                "1920x1080 (widescreen)");
        this.addItem("APPLY");
        this.addItem("BACK");

        menu_stage.setTitle("VIDEO GAME");
        show();

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



    private Pair<Double, Double> ParseSelectedResolution(){
        String width_string;
        String height_string;

        try {
            String regex = "\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(getSelectableItem("RESOLUTION").getText());
            matcher.find();
            width_string = matcher.group();
            matcher.find();
            height_string = matcher.group();

            double width = Integer.parseInt(width_string);
            double height = Integer.parseInt(height_string);

            return new Pair<Double, Double>(width, height);
        }catch(Exception e){
            return new Pair<Double, Double>(null, null);
        }

    }

    private void askConfirmChanges(double width_candidate, double height_candidate){
        AlertWindow alert_window = new AlertWindow(this, width_candidate, height_candidate);
        //getStage().close();
        alert_window.start(getStage());
    }

    private void applyCurrentSettings(){
        writeSettings();
        var selected_resolution = ParseSelectedResolution();
        var current_resolution = new Pair<Double, Double>(getStageWidth(), getStageHeight());

        if(!selected_resolution.equals(current_resolution))
            if(isGameRunning())
                askConfirmChanges(selected_resolution.getKey(), selected_resolution.getValue());
            else{
                setStageDimensions(selected_resolution.getKey(), selected_resolution.getValue());
                writeSettings();
                OptionsMenu options_menu = new OptionsMenu(this);
                options_menu.start(getStage());
            }
    }

    private void writeSettings() {
        Properties config = new Properties();
        config.setProperty("INTERFACE MODE", getSelectableItem("INTERFACE MODE").getText());
        config.setProperty("WIDTH", String.valueOf(getStageWidth()));
        config.setProperty("HEIGHT", String.valueOf(getStageHeight()));

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
