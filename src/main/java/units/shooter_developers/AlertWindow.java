package units.shooter_developers;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
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

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class AlertWindow extends Menu{
    AlertObject _content;
    double _candidate_width;
    double _candidate_height;

    AlertWindow(Menu other_menu, double candidate_width, double candidate_height){
        super(other_menu);
        _content = new AlertObject(getMenuWidth(), getMenuHeight());
        _candidate_width = candidate_width;
        _candidate_height = candidate_height;
    }

    @Override
    public void start(Stage stage){
        setStage(stage);
        getStage().centerOnScreen();

        this.addGenericNode(_content);
        addFreeItem("BACK", 0.05, 0.2);
        addFreeItem("CONTINUE", 0.76, 0.2);
        addFlashDisclaimer("Game will be reset. Do you want to confirm?", 0.185, 0.93);
        show();

        var menu_items = getItems();
        for(var item:menu_items)
        {
            item.setOnMouseReleased(event -> {
                if (item.getName().equals("BACK")) {
                    OptionsMenu options_menu = new OptionsMenu(this);
                    options_menu.start(stage);
                }
                if (item.getName().equals("CONTINUE"))
                {
                    stage.setMaximized(false);
                    setStageDimensions(_candidate_width, _candidate_height);
                    writeModifyingSettings();
                    OptionsMenu options_menu = new OptionsMenu();
                    options_menu.start(stage);
                }
            });
        }


    }

    private void writeModifyingSettings() {
        Properties config = new Properties();

        File configFile = new File("config.ini");
        try{
            FileWriter writer = new FileWriter(configFile);
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            config.put("WIDTH", String.valueOf(getStageWidth()));
            config.put("HEIGHT", String.valueOf(getStageHeight()));
            config.store(writer, "Game settings");
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AlertObject extends BorderPane {

        double  _width, _height;

        AlertObject(double width, double height)
        {
            _width = width;
            _height = height;
            this.setPrefSize(width,height);

            var alert_image = retrieveImage("alert.png", 1,1);
            //WORKING HERE!
            /*
            alert_image.resize(getMenuWidth()/2, getMenuHeight()/2);
            //setAlignment(alert_image, Pos.TOP_CENTER);
            alert_image.setTranslateX(getMenuWidth()/2.-alert_image.getFitWidth());
            getChildren().add(alert_image);
             */

            addCentralComposition(alert_image);
            addCustomTitle("WARNING!");
        }

        private void addCustomTitle(String title_text){
            Text top = new Text(title_text);
            top.setFont(Font.font("Times New Roman", FontWeight.BOLD,_width*0.06));
            top.setFill(Color.SILVER);
            setAlignment(top,Pos.TOP_CENTER);
            setTop(top);
        }

        private ImageView retrieveImage(String URL, int n_rows, int n_cols)
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
