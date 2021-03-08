package units.shooter_developers.MenuAPI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class _Submenu_component extends GridPane {

    protected SimpleBooleanProperty all_set = new SimpleBooleanProperty(false);

    protected _Submenu_component(double width_scale, double height_scale)
    {
        var width = Menu.getMenuWidth()*width_scale;
        var height = Menu.getMenuHeight()*height_scale;

        this.setTranslateY(10);
        this.setTranslateX(10);

        this.setMinSize(width, height);
        this.setPrefSize(width, height);
        this.setMaxSize(width, height);

    }

    public SimpleBooleanProperty get_AllSetProperty() {
        return all_set;
    }
}
