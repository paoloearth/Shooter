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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Timer;
import java.util.TimerTask;

public class AlertWindow extends Menu{
    AlertObject _content;

    AlertWindow(Menu other_menu){
        super(other_menu);
        _content = new AlertObject(getMenuWidth(), getMenuHeight());
    }

    @Override
    public void start(Stage stage){
        setStage(stage);
        this.addGenericNode(_content);
        show();
        getStage().toFront();

        // time_before_read_input(stage, scene);


        getSceneFromStage().addEventHandler(KeyEvent.KEY_PRESSED, ke -> {

            OptionsMenu new_menu = new OptionsMenu(this);
            new_menu.start(stage);

        });


    }

    private class AlertObject extends BorderPane {

        double  _width, _height;

        AlertObject(double width, double height)
        {
            _width = width;
            _height = height;
            this.setPrefSize(width,height);


            var fireworks = retrieve_image("alert.png", 1,1);

            addCentralComposition(fireworks);
            addCustomTitle("¡ALERTA!");
            addDisclaimer("soy un texto inocente");
        }

        private void addCustomTitle(String title_text){
            Text top = new Text(title_text);
            top.setFont(Font.font("Times New Roman", FontWeight.BOLD,_width*0.06));
            top.setFill(Color.SILVER);
            setAlignment(top,Pos.TOP_CENTER);
            setTop(top);
        }

        public  void addDisclaimer(String disclaimer_text){
            Text bottom = new Text(disclaimer_text);
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

        private void addCentralComposition(ImageView background_sprite)
        {
            StackPane sp = new StackPane();
            sp.setMinSize(_width/2, _height/2);
            addProportionalImageToStackPane(background_sprite,sp);
            setCenter(sp);
        }


        private void addProportionalImageToStackPane(ImageView image, StackPane sp){
            image.fitHeightProperty().bind(sp.heightProperty());
            image.setPreserveRatio(true);
            sp.getChildren().add(image);
            sp.setAlignment(Pos.CENTER);
        }
    }
}
