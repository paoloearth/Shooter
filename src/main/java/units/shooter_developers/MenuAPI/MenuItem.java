package units.shooter_developers.MenuAPI;
// Visited
// Remove this when it is not necessary
import javafx.geometry.Pos;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/************************ MENU ITEM ****************************************/

public class MenuItem extends StackPane {
    String _name;

    public MenuItem(String name) {
        this(name, -1, -1);
    }

    public MenuItem(String name, double item_width_ratio, double item_height_ratio) {
        final var effective_item_width = item_width_ratio<0? 0.19 : item_width_ratio;
        final var effective_item_height = item_height_ratio<0? 0.05 : item_height_ratio;

        _name = name;

        this.setMaxWidth(effective_item_width * Menu.getMenuWidth());
        this.setMaxHeight(effective_item_height * Menu.getMenuHeight());

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, Menu.getColorPalette().selected_secondary_color),
                new Stop(0.1, Menu.getColorPalette().basic_secondary_color),
                new Stop(0.9, Menu.getColorPalette().basic_secondary_color),
                new Stop(1, Menu.getColorPalette().selected_secondary_color));

        Rectangle box = new Rectangle(effective_item_width* Menu.getMenuWidth(),effective_item_height* Menu.getMenuHeight());
        box.setOpacity(0.4);

        Text text = new Text(name);
        text.setFill(Menu.getColorPalette().basic_primary_color);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,0.0333* Menu.getMenuHeight()));

        //Text is transformed into an image and resized
        var params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        var textImage = new ImageView(text.snapshot(params, null));
        if(textImage.getBoundsInLocal().getWidth() > box.getWidth()) {
            textImage.setFitWidth(box.getWidth());
        }

        setAlignment(Pos.CENTER_LEFT);

        setOnMouseEntered(event -> {
            box.setFill(gradient);
            text.setFill(Menu.getColorPalette().selected_primary_color);
        });

        setOnMouseExited(event -> {
            box.setFill(Menu.getColorPalette().basic_secondary_color);
            text.setFill(Menu.getColorPalette().basic_primary_color);
        });

        setOnMousePressed(event -> {
            box.setFill(Menu.getColorPalette().clicked_background_color);
        });

        setOnMouseReleased(event -> {
            box.setFill(gradient);
        });

        getChildren().addAll(box, textImage);

    }

    public String getName() {
        return _name;
    }
}
