package units.shooter_developers;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


public abstract class MapObject extends Pane {

    private  int _width, _height;
    private  Pair<Double,Double> _scaling_factors;

    /* Constructors */
    MapObject(Pair<Double,Double> scaling_factors) { set_scaling_factors(scaling_factors); }
    MapObject(int width, int height) { setDimensions(width,height);}

    void moveTo(Coordinates coordinates) { this.relocate(coordinates.getX(),coordinates.getY()); }

    public double getCurrentXPosition() { return this.getLayoutX(); }
    public double getCurrentYPosition() { return this.getLayoutY(); }
    public Coordinates getCurrentPosition() {return  new Coordinates(getLayoutX(),getLayoutY());};

    public void addNodes(Node ... nodes) { this.getChildren().addAll(nodes); }

    public void setDimensions(int width, int height)
    {
        this.set_width(width); this.set_height(height);
    }

    /* Getters */
    public int get_width() { return _width; }
    public int get_height() { return _height; }
    public Pair<Double, Double> getScalingFactors() { return _scaling_factors; }

    /* Setters */
    public void set_width(int _width) { this._width = _width; }
    public void set_height(int _height) { this._height = _height; }
    public void set_scaling_factors(Pair<Double, Double> _scaling_factors) { this._scaling_factors = _scaling_factors; }
}
