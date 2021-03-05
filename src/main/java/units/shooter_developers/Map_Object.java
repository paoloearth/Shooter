package units.shooter_developers;

import javafx.scene.layout.Pane;
import javafx.util.Pair;


public abstract class Map_Object extends Pane {

    private  int _width, _height;
    private  Pair<Double,Double> _scaling_factors;


    /* Constructors */
    Map_Object() { }
    Map_Object(Pair<Double,Double> scaling_factors) { set_scaling_factors(scaling_factors); }

    void move_to(Coordinates coordinates)
    {
        this.relocate(coordinates.getX(),coordinates.getY());
    }

    public double get_current_X_position() { return this.getLayoutX(); }

    public double get_current_Y_position() { return this.getLayoutY(); }


    /* Getters */
    public int get_width() {
        return _width;
    }
    public int get_height() {
        return _height;
    }
    public Pair<Double, Double> get_scaling_factors() {
        return _scaling_factors;
    }

    /* Getters */
    public void set_width(int _width) {
        this._width = _width;
    }

    public void set_height(int _height) {
        this._height = _height;
    }

    public void set_scaling_factors(Pair<Double, Double> _scaling_factors) {
        this._scaling_factors = _scaling_factors;
    }
}
