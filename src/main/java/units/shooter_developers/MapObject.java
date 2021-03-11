package units.shooter_developers;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


public abstract class MapObject extends Pane {

    private double _width, _height;
    private   Pair<Double,Double> _resolutionScalingFactors;

    /* Constructors */
    MapObject(Pair<Double,Double> resolutionScalingFactors) { setResolutionScalingFactors(resolutionScalingFactors); }

    MapObject(double width, double height) { setDimensions(width,height);}

    /* Movement & action management */
    protected final void moveTo(Coordinates coordinates) { relocate(coordinates.getX(),coordinates.getY()); }

    public final double getCurrentXPosition() { return getLayoutX(); }

    public final double getCurrentYPosition() { return getLayoutY(); }

    public final Coordinates getCurrentPosition() {return  new Coordinates(getLayoutX(),getLayoutY());}

    /* Utils */
    protected final void addNodes(Node ... nodes) { getChildren().addAll(nodes); }

    public final void setDimensions(double width, double height) { set_width(width); set_height(height); }

    /* Getters */
    public final double get_width() { return _width; }

    public final double get_height() { return _height; }

    public final Pair<Double, Double> getResolutionScalingFactors() { return _resolutionScalingFactors; }

    /* Setters */
    public final void set_width(double width) { _width = width; }

    public final void set_height(double height) { _height = height; }

    public final void setResolutionScalingFactors(Pair<Double, Double> resolutionScalingFactors) { _resolutionScalingFactors = resolutionScalingFactors; }
}
