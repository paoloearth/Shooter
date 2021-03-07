package units.shooter_developers.Menu_pages;

import javafx.scene.input.KeyEvent;
import units.shooter_developers.MenuAPI.Menu;
import units.shooter_developers.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class WinnerWindow extends Menu {
    Sprite _player;

    public WinnerWindow(double width, double height, Sprite player ){
        super();
        _player = player;
    }

    @Override
    public void createContent(){

        var winner_image = Menu.retrieveImage(_player.get_url(),4,1);
        var fireworks = Menu.retrieveImage("fireworks.png", 1,1);
        addCentralImageView(fireworks, 0.9, 0.9);
        addCentralImageView(winner_image, 0.9, 0.9);

        addSecondaryTitle("The winner is "+ _player.get_player_name());
        addFlashDisclaimer("<press a key to continue>");

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