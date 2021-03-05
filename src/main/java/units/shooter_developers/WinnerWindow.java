package units.shooter_developers;

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import units.shooter_developers.Menus.Menu;

import java.util.Timer;
import java.util.TimerTask;

public class WinnerWindow extends Menu {
    Sprite _player;

    WinnerWindow(double width, double height, Sprite player ){
        super(width, height);
        _player = player;
    }

    @Override
    public void start(Stage stage){
        setStage(stage);
        getStage().centerOnScreen();

        var winner_image = Menu.retrieveImage(_player.get_url(),4,1);
        var fireworks = Menu.retrieveImage("fireworks.png", 1,1);
        addCentralImageView(fireworks, 0.9, 0.9);
        addCentralImageView(winner_image, 0.9, 0.9);
        addSecondaryTitle("The winner is "+ _player._player_name);
        addFlashDisclaimer("<press a key to continue>", 0.32, 0.93);
        show();

        waitAndPressToContinue(1);
    }

    private void waitAndPressToContinue(double seconds) {
        Timer timer = new Timer();

        TimerTask task_2 = new TimerTask()
        {
            public void run()
            {
                    getSceneFromStage().addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
                    GameMenu new_menu = new GameMenu();
                    new_menu.start(getStage());
                });
            }
        };
        timer.schedule(task_2,1000*(int)seconds);
    }
}