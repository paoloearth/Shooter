package units.shooter_developers.MenuAPI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class _Submenu_component extends GridPane {

    protected _Submenu_component(double width_scale, double height_scale)
    {
        var width = Menu.getMenuWidth()*width_scale;
        var height = Menu.getMenuHeight()*height_scale;

        this.setTranslateY(0.017*height);
        this.setTranslateX(0.01*width);

        this.setMinSize(width, height);
        this.setPrefSize(width, height);
        this.setMaxSize(width, height);

    }
}
