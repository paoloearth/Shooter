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

/************************ TITLE ****************************************/
class Title extends StackPane {
    public Title(String name) {
        Rectangle box = new Rectangle(0.357 * Menu.getMenuWidth(), 0.1 * Menu.getMenuHeight());
        box.setStroke(Menu.getColorPalette().basic_primary_color);
        box.setStrokeWidth(2);
        box.setFill(null);

        Text text = new Text(name);
        text.setFill(Menu.getColorPalette().basic_primary_color);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0833 * Menu.getMenuHeight()));

        setAlignment(Pos.CENTER);

        //Text is transformed into an image and dimensioned
        var params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        var textImage = new ImageView(text.snapshot(params, null));
        if (textImage.getBoundsInLocal().getWidth() > box.getWidth())
            textImage.setFitWidth(box.getWidth());

        getChildren().addAll(box, textImage);
    }
}
