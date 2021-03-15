package units.shooter_developers.Menu_pages;

/* VISITED */

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import units.shooter_developers.CustomCheckedException;
import units.shooter_developers.CustomSettings;
import units.shooter_developers.MenuAPI.Menu;
import units.shooter_developers.Sprite;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class WinnerWindow extends Menu {
    Sprite _player;

    public WinnerWindow(Sprite player){
        super();
        _player = player;
    }

    @Override
    public void createContent(){
        ImageView winner_image, fireworks;
        try {
            winner_image = Menu.retrieveImage(_player.getPicture().getImage().getUrl(), 4, 1);
        }catch (CustomCheckedException.FileManagementException e){
            System.out.println(e.getMessage() + " Winner sprite image not found. Using alternative one. Continuing");
            winner_image = new ImageView(new Rectangle(10, 10).snapshot(null, null));
        }
        try {
            fireworks = Menu.retrieveImage(CustomSettings.URL_FIREWORKS, 1, 1);
        }catch (CustomCheckedException.FileManagementException e){
            System.out.println(e.getMessage() + " Fireworks image image not found. Using alternative one. Continuing");
            fireworks = new ImageView(new Rectangle(10, 10).snapshot(null, null));
        }

        addCentralImageView(fireworks, 0.9, 0.9);
        addCentralImageView(winner_image, 0.9, 0.9);

        addSecondaryTitle("The winner is "+ _player.getPlayerName());
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
        /* Here also explicit long casting should be applied*/
        timer.schedule(task_2,(long)(1000*seconds));
    }
}