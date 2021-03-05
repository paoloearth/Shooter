package units.shooter_developers;

import javafx.stage.*;

import java.io.File;
import java.io.FileReader;
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
        getStage().centerOnScreen();
        readDimensions();
        menu_stage.centerOnScreen();

        if(isGameRunning()) {
            this.addItem("CONTINUE");
        } else {
            this.addNonAnimatedItem("CONTINUE");
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
                    submenu_launch_game.start(menu_stage);
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

    private void readDimensions(){
        File configFile = new File("config.ini");
        Properties config = new Properties();

        try{
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            reader.close();
            double width = Double.parseDouble(config.getProperty("WIDTH"));
            double height = Double.parseDouble(config.getProperty("HEIGHT"));
            setStageDimensions(width, height);
        } catch (Exception e) {
            return;
        }
    }

    @Override
    public void scaleMenu(double width_scale, double height_scale){
        super.scaleMenu(width_scale, height_scale);
        GameMenu new_menu = new GameMenu(this);
        new_menu.start(getStage());
    }

    @Override
    public void setScaledPosition(double scaled_position_X, double scaled_position_Y){
        super.setScaledPosition(scaled_position_X, scaled_position_Y);
        GameMenu new_menu = new GameMenu(this);
        new_menu.start(getStage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}