package units.shooter_developers.MenuAPI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Text_Box extends VBox {

    TextField _textField;
    int _nrows;
    double _custom_scale;

    public Text_Box(String commands_url, int nrows, double scale)
    {
        super();

        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);

        _nrows = nrows;
        _custom_scale = scale;

        setFillWidth(false);

        _textField = new TextField();
        var background_color = Menu.getColorPalette().dead_color;
        var default_text_color = Menu.getColorPalette().selected_primary_color;
        var introduced_text_color = Menu.getColorPalette().clicked_background_color;
        _textField.setStyle("-fx-control-inner-background: #" + background_color.toString().substring(2) + ";" +
                "-fx-prompt-text-fill: #"+default_text_color.toString().substring(2) + ";" +
                "-fx-text-fill: #"+introduced_text_color.toString().substring(2) + ";");
        _textField.setPromptText("Who are you?.");

        getChildren().add(_textField);

        HBox H = createCustomHbox();
        var I = Menu.retrieveImage(commands_url, _nrows, 1);
        I.setPreserveRatio(true);

        DropShadow ds = new DropShadow( 50, Color.WHITE );
        I.setEffect(ds);

        scale_image_to_fit_box(H, I);
        H.getChildren().add(I);

        getChildren().add(H);


    }

    protected void scale_image_to_fit_box(HBox H, ImageView I) {
        I.fitHeightProperty().bind(H.heightProperty());
        I.setScaleY(_custom_scale);
        I.setScaleX(_custom_scale);
    }

    protected static HBox createCustomHbox() {
        HBox H = new HBox();
        H.setMinHeight(0);
        H.setAlignment(Pos.BOTTOM_CENTER);
        return H;
    }

    public String get_value()
    {
        return _textField.getText();
    }



        /*
           To go back to previous configuration goes to
           Extends HBOX
           HBox.setHgrow(textField, Priority.NEVER);
        */







}
