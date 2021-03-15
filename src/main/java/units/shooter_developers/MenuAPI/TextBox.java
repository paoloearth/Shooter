package units.shooter_developers.MenuAPI;

import units.shooter_developers.CustomCheckedException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class TextBox extends VBox {

    TextField _textField;
    String _name;
    String _defaultTextfieldContent;

    protected TextBox(String name, String imageUrl, int rowWithinSpritesheet, double imageScale, String defaultMessage, String defaultContent)
    {
        super();
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);
        setFillWidth(false);

        _name = name;
        _defaultTextfieldContent = defaultContent;

        _textField = createCustomizedTextField();
        _textField.setPromptText(defaultMessage);

        ImageView image;

        try {
            image = Menu.retrieveImage(imageUrl, rowWithinSpritesheet, 1);
        }catch (CustomCheckedException.FileManagementException e){
            System.out.println(e.getMessage() + " TextBox's image image not found. Using alternative one. Continuing");
            image = new ImageView(new Rectangle(10, 10).snapshot(null, null));
        }

        image.setPreserveRatio(true);
        image.setFitHeight(0.2* imageScale *Menu.getMenuHeight());
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
        if(_textField.getText().equals(""))
            return _defaultTextfieldContent;
        else
            return _textField.getText();
    }

    protected String getName(){
        return _name;
    }
}
