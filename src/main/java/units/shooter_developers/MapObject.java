package units.shooter_developers;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


public abstract class MapObject extends Pane {

    private  int _width, _height;
    private  Pair<Double,Double> _scaling_factors;

    /* Constructors */
    MapObject(Pair<Double,Double> scaling_factors) { set_scaling_factors(scaling_factors); }
    MapObject(int width, int height) { set_dimensions(width,height);}

    void move_to(Coordinates coordinates) { this.relocate(coordinates.getX(),coordinates.getY()); }

    public double get_current_X_position() { return this.getLayoutX(); }
    public double get_current_Y_position() { return this.getLayoutY(); }
    public Coordinates get_current_position() {return  new Coordinates(getLayoutX(),getLayoutY());};

    public void add_nodes(Node ... nodes) { this.getChildren().addAll(nodes); }

    public void set_dimensions(int width, int height)
    {
        this.set_width(width); this.set_height(height);
    }

    /* Getters */
    public int get_width() { return _width; }
    public int get_height() { return _height; }
    public Pair<Double, Double> get_scaling_factors() { return _scaling_factors; }

    /* Setters */
    public void set_width(int _width) { this._width = _width; }
    public void set_height(int _height) { this._height = _height; }
    public void set_scaling_factors(Pair<Double, Double> _scaling_factors) { this._scaling_factors = _scaling_factors; }
}
