package units.shooter_developers;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

public class WinnerWindow extends Application{
    WinnerScreenObject _content;

    WinnerWindow(double width, double height, Sprite player ){
        _content = new WinnerScreenObject(width, height, player);
    }

    public void start(Stage stage){
        Pane root = new Pane();
        root.getChildren().add(_content);
        Scene scene = new Scene(root);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, ke -> {
            GameMenu new_menu = new GameMenu();
            new_menu.start(stage);
        });


        stage.setScene(scene);
        stage.close();
        stage.show();
    }
}

class WinnerScreenObject extends BorderPane {

    double _width, _height;

    WinnerScreenObject(double width, double height, Sprite P)
    {
        this._width = width;
        this._height = height;
        this.setPrefSize(width,height);


        var winner_image = retrieve_image(P.get_url(),4,1);
        var fireworks = retrieve_image("fireworks.png", 1,1);

        addBackgroundImage(this, "menu.jpeg");
        addCentralComposition(fireworks,winner_image);
        addTitle(P._player_name);
        addDisclaimer();
    }

    private void addTitle(String winner){
        Text top = new Text("The winner is "+ winner);
        top.setFont(Font.font("Times New Roman", FontWeight.BOLD,_width*0.06));
        top.setFill(Color.SILVER);
        setAlignment(top,Pos.TOP_CENTER);
        setTop(top);
    }

    /*
        private void addDisclaimer(StackPane sp){
            Text bottom = new Text("<press a key to continue>");
            bottom.setFont(Font.font("Times New Roman", FontWeight.BOLD,_width*0.02));
            bottom.setFill(Color.WHITE);
            textAnimation(bottom);
            setAlignment(bottom,Pos.TOP_CENTER);
            bottom.setTranslateY(_height*0.4);
            sp.getChildren().add(bottom);
        }

       */
    private void addDisclaimer(){
        Text bottom = new Text("<press a key to continue>");
        bottom.setFont(Font.font("Times New Roman", FontWeight.BOLD,_width*0.025));
        bottom.setFill(Color.SILVER);
        textAnimation(bottom);
        setAlignment(bottom,Pos.TOP_CENTER);
        setBottom(bottom);
    }

    private void textAnimation(Text bottom) {
        FadeTransition textTransition = new FadeTransition(Duration.seconds(1.0), bottom);
        textTransition.setAutoReverse(true);
        textTransition.setFromValue(0);
        textTransition.setToValue(1);
        textTransition.setCycleCount(Transition.INDEFINITE);
        textTransition.play();
    }

    private ImageView retrieve_image(String URL, int n_rows, int n_cols)
    {
        var I = new Image(URL);
        var IM =  new ImageView(I);
        IM.setViewport(new Rectangle2D( 0, 0, I.getWidth()/n_cols, I.getHeight()/n_rows));
        return IM;
    }

    private void addCentralComposition(ImageView background_sprite, ImageView player_sprite)
    {
        StackPane sp = new StackPane();
        sp.setMinSize(_width/2, _height/2);
        addProportionalImageToStackPane(background_sprite,sp);
        addProportionalImageToStackPane(player_sprite,sp);
        // addDisclaimer(sp);
        setCenter(sp);
    }

    private void addBackgroundImage(BorderPane bp, String image_url){
        ImageView background = new ImageView(image_url);
        background.setFitWidth(_width);
        background.setFitHeight(_height);
        background.resize(_width, _height);
        bp.getChildren().add(background);
    }
    private void addProportionalImageToStackPane(ImageView image, StackPane sp){
        image.fitHeightProperty().bind(sp.heightProperty());
        image.setPreserveRatio(true);
        sp.getChildren().add(image);
        sp.setAlignment(Pos.CENTER);
    }



}