package units.shooter_developers;

import javafx.stage.*;
import javafx.scene.*;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class GameMenu extends Menu{


    public GameMenu(){
        super();
        setGameRunning(false);
    }

    public GameMenu(Menu other_menu){
        super(other_menu);
    }

    public GameMenu(Simulation game_instance){
        super();
        setGameInstance(game_instance);
        setGameRunning(true);
    }

    @Override
    public void start(Stage menu_stage){
        setStage(menu_stage);
        readSettings();

        if(isGameRunning()) {
            this.addItem("CONTINUE");
        } else {
            this.addUnanimatedItem("CONTINUE");
        }

        this.addItem("NEW GAME");
        this.addItem("NEW LAN-GAME");
        this.addItem("OPTIONS");
        this.addItem("EXIT");
        menu_stage.setTitle("VIDEO GAME");
        setTitle("C A M P A I G N");
        show();

        var menu_items = getItems();
        for(var item:menu_items)
        {
            item.setOnMouseReleased(event -> {
                if (item.getName().equals("NEW GAME")) {

                    Submenu submenu_launch_game = new Submenu(this);
                    try {
                        submenu_launch_game.start(menu_stage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (item.getName().equals("CONTINUE")) {
                    menu_stage.close();
                    menu_stage.setScene(getGameInstance().getScene());
                    menu_stage.show();
                    menu_stage.toFront();
                }
                if (item.getName().equals("EXIT")) {
                    menu_stage.close();
                }
                if (item.getName().equals("OPTIONS")) {
                    OptionsMenu options_menu = new OptionsMenu(this);
                    options_menu.start(menu_stage);
                }
            });
        }
    }

    private void readSettings(){
        File configFile = new File("config.ini");
        Properties config = new Properties();

        try{
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            double width = Double.parseDouble(config.getProperty("WIDTH"));
            double height = Double.parseDouble(config.getProperty("HEIGHT"));
            setStageDimensions(width, height);

        } catch (IOException e) {
            return;
        }
    }

    @Override
    public void resize(double width_ratio, double height_ratio){
        super.resize(width_ratio, height_ratio);
        GameMenu new_menu = new GameMenu(this);
        new_menu.start(getStage());
    }

    @Override
    public void setPositionRatio(double position_width_ratio, double position_height_ratio){
        super.setPositionRatio(position_width_ratio, position_height_ratio);
        GameMenu new_menu = new GameMenu(this);
        new_menu.start(getStage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}