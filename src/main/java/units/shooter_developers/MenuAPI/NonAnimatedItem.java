package units.shooter_developers.MenuAPI;

//VISITED
import javafx.beans.property.StringPropertyBase;
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/************************ NON-ANIMATED ITEM ****************************************/

class NonAnimatedItem extends StackPane {
    StringPropertyBase _name_property;

    public NonAnimatedItem(String name) {
        this(name, -1, -1);
    }

    protected NonAnimatedItem(String name, double item_width_ratio, double item_height_ratio) {
        _name_property = new StringPropertyBase() {
            @Override
            public Object getBean() { return null; }
            @Override
            public String getName() { return null; }};

        var effective_width_ratio = 0.19;
        var effective_height_ratio = 0.05;
        if (item_width_ratio >= 0) effective_width_ratio = item_width_ratio;
        if (item_height_ratio >= 0) effective_height_ratio = item_height_ratio;

        Color text_color = Menu.getColorPalette().dead_color;
        Color background_color = Menu.getColorPalette().basic_secondary_color;

        Rectangle box = new Rectangle(effective_width_ratio * Menu.getMenuWidth(), effective_height_ratio * Menu.getMenuHeight());
        box.setOpacity(0.3);
        box.setFill(background_color);

        Text text = new Text(_name_property.getValue());
        text.setFill(text_color);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0333 * Menu.getMenuHeight()));
        var params = new SnapshotParameters();

        getChildren().add(box);

        _name_property.addListener((observable,  oldValue,  selected) -> {
            getChildren().removeIf(e -> e instanceof ImageView);
            Text new_text = new Text(_name_property.getValue());
            new_text.setFill(text_color);
            new_text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0333 * Menu.getMenuHeight()));

            //Text is transformed into an image and resized
            params.setFill(Color.TRANSPARENT);
            var textImage = new ImageView(new_text.snapshot(params, null));
            if (textImage.getBoundsInLocal().getWidth() > box.getWidth())
                textImage.setFitWidth(box.getWidth());

            setAlignment(Pos.CENTER_LEFT);
            getChildren().add(textImage);
        });

        _name_property.setValue(name);


    }

    protected void setName(String name){
        _name_property.setValue(name);
    }

    // Dead code?
    protected StringPropertyBase getNameAsProperty(){
        return _name_property;
    }


}
