package units.shooter_developers.Menu_pages.submenu;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;

public class Submenu_component extends GridPane {


    private double _width;
    private double _height;

    protected SimpleBooleanProperty all_set = new SimpleBooleanProperty(false);

    protected Submenu_component(double width, double height)
    {
        /* Functions that deals with the aspects of the menu*/
        set_height(height);
        set_width(width);
        set_padding();
        fix_submenu_size_to_width_and_height();

    }

    protected void fix_submenu_size_to_width_and_height() {
        this.setMinSize(get_width(), get_height());
        this.setPrefSize(get_width(), get_height());
        this.setMaxSize(get_width(), get_height());
    }


    public void set_width(double _width)   { this._width = _width; }
    public void set_height(double _height) { this._height = _height; }

    private void set_padding() {
        setHgap(10);                                                 // horizontal gap in pixels
        setVgap(10);                                                 // vertical gap in pixels
        setPadding(new Insets(10, 10, 10, 10));   // margins around the whole grid
    }

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
