package units.shooter_developers;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


public abstract class MapObject extends Pane {

    private   int _width, _height;
    private   Pair<Double,Double> _scalingFactors;

    /* Constructors */
    MapObject(Pair<Double,Double> scaling_factors) { set_scalingFactors(scaling_factors); }

    MapObject(int width, int height) { setDimensions(width,height);}

    /* Movement & action management */
    protected final void moveTo(Coordinates coordinates) { relocate(coordinates.getX(),coordinates.getY()); }

    public final double getCurrentXPosition() { return getLayoutX(); }

    public final double getCurrentYPosition() { return getLayoutY(); }

    public final Coordinates getCurrentPosition() {return  new Coordinates(getLayoutX(),getLayoutY());};

    /* Utils */
    protected final void addNodes(Node ... nodes) { getChildren().addAll(nodes); }

    public final void setDimensions(int width, int height) { set_width(width); set_height(height); }

    /* Getters */
    public final int get_width() { return _width; }

    public final int get_height() { return _height; }

    public final Pair<Double, Double> getScalingFactors() { return _scalingFactors; }

    /* Setters */
    public final void set_width(int width) { _width = width; }

    public final void set_height(int height) { _height = height; }

    public final void set_scalingFactors(Pair<Double, Double> scalingFactors) { _scalingFactors = scalingFactors; }
}
