package units.shooter_developers.MenuAPI;

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

    public NonAnimatedItem(String name) {
        this(name, -1, -1);
    }

    protected NonAnimatedItem(String name, double item_width_ratio, double item_height_ratio) {
        var effective_width_ratio = 0.19;
        var effective_height_ratio = 0.05;
        if (item_width_ratio >= 0) {
            effective_width_ratio = item_width_ratio;
        }
        if (item_height_ratio >= 0) {
            effective_height_ratio = item_height_ratio;
        }

        Color text_color = Menu.getColorPalette().separator_color;
        Color background_color = Menu.getColorPalette().base_background_color;

        Rectangle box = new Rectangle(effective_width_ratio * Menu.getMenuWidth(), effective_height_ratio * Menu.getMenuHeight());
        box.setOpacity(0.3);
        box.setFill(background_color);

        Text text = new Text(name);
        text.setFill(text_color);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0333 * Menu.getMenuHeight()));

        //Text is transformed into an image and resized
        var params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        var textImage = new ImageView(text.snapshot(params, null));
        if (textImage.getBoundsInLocal().getWidth() > box.getWidth())
            textImage.setFitWidth(box.getWidth());

        setAlignment(Pos.CENTER_LEFT);
        getChildren().addAll(box, textImage);
    }
}
