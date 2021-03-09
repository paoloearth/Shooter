package units.shooter_developers.Menu_pages;

import units.shooter_developers.MenuAPI.Menu;
import units.shooter_developers.Simulation;

public class GameMenu extends Menu {


    public GameMenu(){
        super();
    }

    public GameMenu(Menu other_menu){
        super(other_menu);
    }

    public GameMenu(Simulation game_instance){
        super();
        setSimulationInstance(game_instance);
    }

    @Override
    public void createContent(){

        if(isSimulationRunning()) {
            this.addItem("CONTINUE");
        } else {
            this.addNonAnimatedItem("CONTINUE");
        }

        this.addItem("NEW GAME");
        this.addItem("NEW LAN-GAME");
        this.addItem("OPTIONS");
        this.addItem("EXIT");
        getStage().setTitle("VIDEO GAME");
        setTitle("C A M P A I G N");

        var menu_items = getItems();
        for(var item:menu_items)
        {
            item.setOnMouseReleased(event -> {
                if (item.getName().equals("NEW GAME")) {
                    Submenu submenu_launch_game = new Submenu(this);
                    submenu_launch_game.start(getStage());
                }
                if (item.getName().equals("CONTINUE")) {
                    getStage().close();
                    getStage().setScene(getSimulationInstance().getScene());
                    getStage().show();
                    getStage().toFront();
                }
                if (item.getName().equals("EXIT")) {
                    getStage().close();
                }
                if (item.getName().equals("OPTIONS")) {
                    OptionsMenu options_menu = new OptionsMenu(this);
                    options_menu.start(getStage());
                }
            });
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