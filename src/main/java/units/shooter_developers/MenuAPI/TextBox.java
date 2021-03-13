package units.shooter_developers.MenuAPI;
// Visited
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

class TextBox extends VBox {

    TextField _textField;
    int _nrows;
    double _custom_scale;
    String _name;
    String _default_textfield_content;

    protected TextBox(String name, String commands_url, int nrows, double scale, String default_message, String default_content)
    {
        super();
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);
        setFillWidth(false);

        _nrows = nrows;
        _custom_scale = scale;
        _name = name;
        _default_textfield_content = default_content;

        _textField = createCustomizedTextField();
        _textField.setPromptText(default_message);

        var image = Menu.retrieveImage(commands_url, _nrows, 1);
        image.setPreserveRatio(true);
        image.setFitHeight(0.2*_custom_scale*Menu.getMenuHeight());
        DropShadow ds = new DropShadow( 50, Color.WHITE );
        image.setEffect(ds);

        getChildren().add(_textField);
        getChildren().add(image);
    }

    private TextField createCustomizedTextField() {
        TextField text_field = new TextField();
        var background_color      = Menu.getColorPalette().dead_color;
        var default_text_color    = Menu.getColorPalette().selected_primary_color;
        var introduced_text_color = Menu.getColorPalette().clicked_background_color;
        text_field.setStyle("-fx-control-inner-background: #" + background_color.toString().substring(2) + ";" +
                "-fx-prompt-text-fill: #"+default_text_color.toString().substring(2) + ";" +
                "-fx-text-fill: #"+introduced_text_color.toString().substring(2) + ";");

        return text_field;
    }

    protected String getValue()
    {
        if(_textField.getText() == "")
            return _default_textfield_content;
        else
            return _textField.getText();
    }

    protected String getName(){
        return _name;
    }
}
