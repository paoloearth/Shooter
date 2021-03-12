package units.shooter_developers.Menu_pages;

/* VISITED */

import javafx.scene.input.KeyEvent;
import units.shooter_developers.MenuAPI.Menu;
import units.shooter_developers.Sprite;

import java.util.Timer;
import java.util.TimerTask;

public class WinnerWindow extends Menu {
    Sprite _player;

    /* Are these parameters useless? We should remove them if width and height are
    *  now taken from the stage */
    public WinnerWindow(double width, double height, Sprite player ){
        super();
        _player = player;
    }

    @Override
    public void createContent(){

        /* Maybe fireworks.png url should go in custom settings as well */
        var winner_image = Menu.retrieveImage(_player.getPicture().getImage().getUrl(),4,1);
        var fireworks = Menu.retrieveImage("fireworks.png", 1,1);
        addCentralImageView(fireworks, 0.9, 0.9);
        addCentralImageView(winner_image, 0.9, 0.9);

        addSecondaryTitle("The winner is "+ _player.getPlayerName());
        addFlashDisclaimer("<press a key to continue>");


        waitAndPressToContinue(1);
    }

    /* Some trouble with casts -> if you want a double just pass them
    *  1.0, otherwise change the type in the function to int */
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
        /* Here also explicit long casting should be applied*/
        timer.schedule(task_2,1000*(int)seconds);
    }
}