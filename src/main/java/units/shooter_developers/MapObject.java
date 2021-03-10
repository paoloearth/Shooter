package units.shooter_developers;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


public abstract class MapObject extends Pane {

    private  int _width, _height;
    private  Pair<Double,Double> _scalingFactors;

    /* Constructors */
    MapObject(Pair<Double,Double> scaling_factors) { set_scalingFactors(scaling_factors); }
    MapObject(int width, int height) { setDimensions(width,height);}

    void moveTo(Coordinates coordinates) { relocate(coordinates.getX(),coordinates.getY()); }

    public double getCurrentXPosition() { return getLayoutX(); }
    public double getCurrentYPosition() { return getLayoutY(); }
    public Coordinates getCurrentPosition() {return  new Coordinates(getLayoutX(),getLayoutY());};

    public void addNodes(Node ... nodes) { getChildren().addAll(nodes); }

    public void setDimensions(int width, int height) { set_width(width); set_height(height); }

    /* Getters */
    public int get_width() { return _width; }
    public int get_height() { return _height; }
    public Pair<Double, Double> get_scalingFactors() { return _scalingFactors; }

    /* Setters */
    public void set_width(int width) { _width = width; }
    public void set_height(int height) { _height = height; }
    public void set_scalingFactors(Pair<Double, Double> scalingFactors) { _scalingFactors =scalingFactors; }
}
