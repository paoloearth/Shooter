package units.shooter_developers.Menu_pages;

import units.shooter_developers.CustomCheckedException;
import units.shooter_developers.CustomSettings;
import units.shooter_developers.MenuAPI.Menu;
import units.shooter_developers.Sprite;

import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
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
        ImageView winnerImage, fireworks;
        try {
            winnerImage = Menu.retrieveImage(_player.getPicture().getImage().getUrl(), 4, 1);
        }catch (CustomCheckedException.FileManagementException e){
            System.out.println(e.toString() + " Winner sprite image not found. Using alternative one. Continuing");
            winnerImage = new ImageView(new Rectangle(10, 10).snapshot(null, null));
        }
        try {
            fireworks = Menu.retrieveImage(CustomSettings.URL_FIREWORKS, 1, 1);
        }catch (CustomCheckedException.FileManagementException e){
            System.out.println(e.toString() + " Fireworks image image not found. Using alternative one. Continuing");
            fireworks = new ImageView(new Rectangle(10, 10).snapshot(null, null));
        }

        addCentralImageView(fireworks, 0.9, 0.9);
        addCentralImageView(winnerImage, 0.9, 0.9);

        addSecondaryTitle("The winner is "+ _player.getPlayerName());
        addFlashDisclaimer("<press a key to continue>");


        waitAndPressToContinue(1);
    }

    private void waitAndPressToContinue(double seconds) {
        Timer timer = new Timer();

        TimerTask task2 = new TimerTask()
        {
            public void run()
            {
                    getSceneFromStage().addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
                    GameMenu new_menu = new GameMenu();
                    new_menu.start(getStage());
                });
            }
        };

        timer.schedule(task2,(long)(1000*seconds));
    }
}