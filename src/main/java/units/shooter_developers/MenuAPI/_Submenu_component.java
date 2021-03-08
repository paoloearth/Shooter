package units.shooter_developers.MenuAPI;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class _Submenu_component extends GridPane {


    private double _width;
    private double _height;

    protected SimpleBooleanProperty all_set = new SimpleBooleanProperty(false);

    protected _Submenu_component(double width_scale, double height_scale)
    {
        var width = Menu.getMenuWidth()*width_scale;
        var height = Menu.getMenuHeight()*height_scale;

        /* Functions that deals with the aspects of the menu*/
        set_height(height);
        set_width(width);

        setHgap(10);                                                 // horizontal gap in pixels
        setVgap(10);                                                 // vertical gap in pixels
        setPadding(new Insets(10, 10, 10, 10));   // margins around the whole grid

        this.setMinSize(width, height);
        this.setPrefSize(width, height);
        this.setMaxSize(width, height);

    }


    public void set_width(double _width)   { this._width = _width; }
    public void set_height(double _height) { this._height = _height; }

    public double get_width() {
        return _width;
    }

    public double get_height() {
        return _height;
    }

    public SimpleBooleanProperty get_AllSetProperty() {
        return all_set;
    }
}
