package units.shooter_developers.menu_pages;

import units.shooter_developers.customs.CustomCheckedException;
import units.shooter_developers.menu_api.Menu;
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
    public void createContent() throws CustomCheckedException.MissingMenuComponentException {

        if(isSimulationRunning()) {
            this.addItem("CONTINUE");
        } else {
            this.addNonAnimatedItem("CONTINUE");
        }

        this.addItem("NEW GAME");
        this.addItem("OPTIONS");
        this.addItem("EXIT");
        getStage().setTitle("VIDEO GAME");
        setTitle("E V E R S C A P E");


        if(isSimulationRunning()) {
            getItem("CONTINUE").setOnMouseReleased(event -> {
                getStage().close();
                getStage().setScene(getSimulationInstance().getScene());
                getStage().show();
                getStage().toFront();
            });
        }

        getItem("NEW GAME").setOnMouseReleased(event -> {
            Submenu submenuLaunchGame = new Submenu(this);
            tryToStart(submenuLaunchGame);
        });
        getItem("EXIT").setOnMouseReleased(event -> getStage().close());
        getItem("OPTIONS").setOnMouseReleased(event -> {
            OptionsMenu optionsMenu = new OptionsMenu(this);
            tryToStart(optionsMenu);
        });


    }

    @Override
    public void setMenuScale(double widthScale, double heightScale) throws CustomCheckedException.MissingMenuComponentException {
        super.setMenuScale(widthScale, heightScale);
        GameMenu newMenu = new GameMenu(this);
        newMenu.start(getStage());
    }

    @Override
    public void setScaledPosition(double scaledPositionX, double scaledPositionY) throws CustomCheckedException.MissingMenuComponentException {
        super.setScaledPosition(scaledPositionX, scaledPositionY);
        GameMenu newMenu = new GameMenu(this);
        newMenu.start(getStage());
    }

    public static void main(String[] args) {
        launch(args);
    }
}