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

        var central_image = new CentralComposition(getMenuWidth(), getMenuHeight(), _player);
        //this.addGenericNode(central_image);
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

class CentralComposition extends BorderPane {

     double  _width, _height;

    CentralComposition(double width, double height, Sprite P)
    {
        _width = width;
        _height = height;
        this.setPrefSize(width,height);


        var winner_image = retrieve_image(P.get_url(),4,1);
        var fireworks = retrieve_image("fireworks.png", 1,1);

        addCentralComposition(fireworks,winner_image);

        addCustomTitle(P._player_name);
    }

    private void addCustomTitle(String winner){
        Text top = new Text("The winner is "+ winner);
        top.setFont(Font.font("Times New Roman", FontWeight.BOLD,_width*0.06));
        top.setFill(Color.SILVER);
        setAlignment(top,Pos.TOP_CENTER);
        setTop(top);
    }

    private void addCentralComposition(ImageView background_sprite, ImageView player_sprite)
    {
        StackPane sp = new StackPane();
        sp.setMinSize(_width/2, _height/2);
        addProportionalImageToStackPane(background_sprite,sp);
        addProportionalImageToStackPane(player_sprite,sp);
        setCenter(sp);
    }

    private void addProportionalImageToStackPane(ImageView image, StackPane sp){
        image.fitHeightProperty().bind(sp.heightProperty());
        image.setPreserveRatio(true);
        sp.getChildren().add(image);
        sp.setAlignment(Pos.CENTER);
    }

    private ImageView retrieve_image(String URL, int n_rows, int n_cols)
    {
        var I = new Image(URL);
        var IM =  new ImageView(I);
        IM.setViewport(new Rectangle2D( 0, 0, I.getWidth()/n_cols, I.getHeight()/n_rows));
        return IM;
    }

}