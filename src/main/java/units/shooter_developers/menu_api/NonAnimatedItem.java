package units.shooter_developers.menu_api;

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
    StringPropertyBase _nameProperty;

    public NonAnimatedItem(String name) {
        this(name, -1, -1);
    }

    protected NonAnimatedItem(String name, double itemWidthRatio, double itemHeightRatio) {
        _nameProperty = new StringPropertyBase() {
            @Override
            public Object getBean() { return null; }
            @Override
            public String getName() { return null; }};

        var effectiveWidthRatio = itemWidthRatio<0? 0.19 : itemWidthRatio;
        var effectiveHeightRatio = itemHeightRatio<0? 0.05 : itemHeightRatio;

        Color textColor = Menu.getColorPalette().dead_color;
        Color backgroundColor = Menu.getColorPalette().basic_secondary_color;

        Rectangle box = new Rectangle(effectiveWidthRatio * Menu.getMenuWidth(), effectiveHeightRatio * Menu.getMenuHeight());
        box.setOpacity(0.3);
        box.setFill(backgroundColor);

        var params = new SnapshotParameters();

        getChildren().add(box);

        _nameProperty.addListener((observable, oldValue, selected) -> {
            getChildren().removeIf(e -> e instanceof ImageView);
            Text newText = new Text(_nameProperty.getValue());
            newText.setFill(textColor);
            newText.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0333 * Menu.getMenuHeight()));

            //Text is transformed into an image and resized
            params.setFill(Color.TRANSPARENT);
            var textImage = new ImageView(newText.snapshot(params, null));
            if (textImage.getBoundsInLocal().getWidth() > box.getWidth())
                textImage.setFitWidth(box.getWidth());

            setAlignment(Pos.CENTER_LEFT);
            getChildren().add(textImage);
        });

        _nameProperty.setValue(name);


    }

    protected StringPropertyBase getNameAsProperty(){
        return _nameProperty;
    }


}
