package units.shooter_developers.Menu_pages;

import units.shooter_developers.CustomCheckedException;
import units.shooter_developers.MenuAPI.Menu;
import units.shooter_developers.Simulation;

public class GameMenu extends Menu {


    public GameMenu(){
        super();
    }

    public GameMenu(Menu otherMenu){
        super(otherMenu);
    }

    public GameMenu(Simulation gameInstance){
        super();
        setSimulationInstance(gameInstance);

    }

    @Override
    public void createContent(){

        if(isSimulationRunning()) {
            this.addItem("CONTINUE");
        } else {
            this.addNonAnimatedItem("CONTINUE");
        }

        this.addItem("NEW GAME");
        this.addItem("OPTIONS");
        this.addItem("EXIT");
        getStage().setTitle("VIDEO GAME");
        setTitle("C A M P A I G N");


        if(isSimulationRunning()) {
            try {
                getItem("CONTINUE").setOnMouseReleased(event -> {
                    getStage().close();
                    getStage().setScene(getSimulationInstance().getScene());
                    getStage().show();
                    getStage().toFront();
                });
            } catch (CustomCheckedException.MissingMenuComponentException e){
                System.out.println(e.getMessage() + " Fatal error. Closing application");
                Runtime.getRuntime().exit(1);
            }
        }

        try {
            getItem("NEW GAME").setOnMouseReleased(event -> {
                Submenu submenuLaunchGame = new Submenu(this);
                submenuLaunchGame.start(getStage());
            });
            getItem("EXIT").setOnMouseReleased(event -> {
                getStage().close();
            });
            getItem("OPTIONS").setOnMouseReleased(event -> {
                OptionsMenu optionsMenu = new OptionsMenu(this);
                optionsMenu.start(getStage());
            });
        } catch (CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.getMessage() + " Fatal error. Closing application");
            Runtime.getRuntime().exit(1);
        }


    }

    @Override
    public void setMenuScale(double width_scale, double height_scale){
        super.setMenuScale(width_scale, height_scale);
        GameMenu newMenu = new GameMenu(this);
        newMenu.start(getStage());
    }

    @Override
    public void setScaledPosition(double scaledPositionX, double scaledPositionY){
        super.setScaledPosition(scaledPositionX, scaledPositionY);
        GameMenu newMenu = new GameMenu(this);
        newMenu.start(getStage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}