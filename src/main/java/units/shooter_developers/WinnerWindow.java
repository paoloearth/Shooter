package units.shooter_developers;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class WinnerWindow extends Menu{
    Sprite _player;

    WinnerWindow(double width, double height, Sprite player ){
        super(width, height);
        _player = player;
    }

    @Override
    public void start(Stage stage){
        setStage(stage);
        getStage().centerOnScreen();

        var winner_image = retrieve_image(_player.get_url(),4,1);
        var fireworks = retrieve_image("fireworks.png", 1,1);
        addCentralImageView(fireworks, 0.9, 0.9);
        addCentralImageView(winner_image, 0.9, 0.9);
        addSecondaryTitle("The winner is "+ _player._player_name);
        addFlashDisclaimer("<press a key to continue>", 0.32, 0.93);
        show();

        //time_before_read_input(stage, getSceneFromStage());

        getSceneFromStage().addEventHandler(KeyEvent.KEY_PRESSED, ke -> {

            GameMenu new_menu = new GameMenu(this);
            new_menu.start(stage);

        });


    }

    private void time_before_read_input(Stage stage, Scene scene) {
        Timer timer = new Timer();

        TimerTask task_2 = new TimerTask()
        {
            public void run()
            {

                    scene.addEventHandler(KeyEvent.KEY_PRESSED, ke -> {

                    GameMenu new_menu = new GameMenu();
                    new_menu.start(stage);

                });
            }
        };

        timer.schedule(task_2,2000);
    }

    private ImageView retrieve_image(String URL, int n_rows, int n_cols)
    {
        var I = new Image(URL);
        var IM =  new ImageView(I);
        IM.setViewport(new Rectangle2D( 0, 0, I.getWidth()/n_cols, I.getHeight()/n_rows));
        return IM;
    }
}