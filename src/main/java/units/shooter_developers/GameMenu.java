package units.shooter_developers;

import javafx.stage.*;
import javafx.scene.*;

public class GameMenu extends Menu{
    Simulation _gameInstance;
    boolean _game_running;

    public GameMenu(){
        super();
        _game_running = false;
    }

    public GameMenu(Menu other_menu){
        super(other_menu);
        _game_running = false;
    }

    public GameMenu(Simulation game_instance){
        super();
        _gameInstance = game_instance;
        _game_running = true;
    }

    @Override
    public void start(Stage menu_stage){
        setStage(menu_stage);
        setStageDimensions(getStageWidth(), getStageHeight());

        if(_game_running) {
            this.addItem("CONTINUE");
        } else {
            this.addUnanimatedItem("CONTINUE");
        }

        this.addItem("NEW GAME");
        this.addItem("NEW LAN-GAME");
        this.addItem("OPTIONS");
        this.addItem("EXIT");
        Scene scene = new Scene(this.getRoot());
        menu_stage.setTitle("VIDEO GAME");
        setTitle("C A M P A I G N");
        menu_stage.setScene(scene);
        menu_stage.show();

        for(var item:_menu_items)
        {
            if(item instanceof MenuItem) {
                var item_casted = (MenuItem)item;
                item.setOnMouseReleased(event -> {
                    if (item_casted.getName() == "NEW GAME") {
                        menu_stage.close();
                        if (_game_running) {
                            _gameInstance.stop();
                            _gameInstance = new Simulation();
                        }
                        _game_running = true;
                        // START GAME HERE
                    }

                    if (item_casted.getName() == "CONTINUE") {
                        if (_game_running)
                            menu_stage.close();
                        //  RESUME SIMULATION
                    }

                    if (item_casted.getName() == "EXIT") {
                        //stop game instance
                        _game_running = false;
                        menu_stage.close();
                    }

                    if (item_casted.getName() == "OPTIONS") {
                        //OptionsMenu options_menu = new OptionsMenu(getStageWidth(), getStageHeight());
                        OptionsMenu options_menu = new OptionsMenu(this);
                        options_menu.start(menu_stage);
                    }


                });
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}