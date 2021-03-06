package units.shooter_developers.MenuAPI;

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
        var effective_item_width = item_width_ratio;
        var effective_item_height = item_height_ratio;
        if(item_width_ratio < 0){
            effective_item_width = 0.19;
        }
        if(item_height_ratio < 0){
            effective_item_height = 0.05;
        }

        /* COLORS SHOULD BE PUT in a different file, eg Custom_colors */

        Color text_color = Menu.getColorPalette().basic_primary_color;
        Color item_clicked_color = Menu.getColorPalette().clicked_background_color;
        Color item_selected_color_lateral = Menu.getColorPalette().selected_secondary_color;
        Color item_background_color = Menu.getColorPalette().basic_secondary_color;
        Color text_selected_color = Menu.getColorPalette().selected_primary_color;

        _name = name;

        this.setMaxWidth(effective_item_width* Menu.getMenuWidth());
        this.setMaxHeight(effective_item_height* Menu.getMenuHeight());

        LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, item_selected_color_lateral),
                new Stop(0.1, item_background_color),
                new Stop(0.9, item_background_color),
                new Stop(1, item_selected_color_lateral));

        Rectangle box = new Rectangle(effective_item_width* Menu.getMenuWidth(),effective_item_height* Menu.getMenuHeight());
        box.setOpacity(0.4);

        Text text = new Text(name);
        text.setFill(text_color);
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
            text.setFill(text_selected_color);
        });

        setOnMouseExited(event -> {
            box.setFill(item_background_color);
            text.setFill(text_color);
        });

        setOnMousePressed(event -> {
            box.setFill(item_clicked_color);
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
