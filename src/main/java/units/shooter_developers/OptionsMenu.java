package units.shooter_developers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.*;
import javafx.scene.*;

public class OptionsMenu extends Menu{
    Simulation _gameInstance;
    boolean _game_running;

    public OptionsMenu(){
        super();
        _game_running = false;
    }

    public OptionsMenu(double width, double height){
        super(width, height);
        _game_running = false;
    }

    public OptionsMenu(Simulation game_instance){
        this();
        _gameInstance = game_instance;
        _game_running = true;
    }

    @Override
    public void start(Stage menu_stage){
        setTitle("O P T I O N S");
        this.addItem("RESOLUTION");
        this.addSelectableItem("INTERFACE MODE", "light", "dark");
        this.addSelectableItem("RESOLUTION",
                "640x360 (widescreen)",
                "800x600",
                "1024x768",
                "1280x720 (widescreen)",
                "1536x864 (widescreen)",
                "1600x900 (widescreen)",
                "1920x1080 (widescreen)");
        this.addItem("APPLY");
        this.addItem("BACK");

        Scene scene = new Scene(this.getRoot());
        menu_stage.setTitle("VIDEO GAME");
        menu_stage.setScene(scene);
        menu_stage.show();

        Stage game_stage = new Stage();


        for(var item:_menu_items)
        {
            item.setOnMouseReleased(event -> {
                var item_casted = (MenuItem)item;
                if(item_casted.getName() == "BACK") {
                    GameMenu main_menu = new GameMenu(getStageWidth(), getStageHeight());
                    main_menu.start(menu_stage);
                } else if(item_casted.getName() == "APPLY") {
                    applyCurrentSettings();
                    //insert here other possible settings updating
                }
            });



        }

    }



    private void updateResolution(){
        String width_string = "";
        String height_string = "";

        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher("1600x900 (widescreen)");
        matcher.find();
        width_string = matcher.group();
        matcher.find();
        height_string = matcher.group();

        int width = Integer.parseInt(width_string);
        int height = Integer.parseInt(height_string);

        setStageDimensions(width, height);
    }

    private void applyCurrentSettings(){
        updateResolution();
    }
}
