package units.shooter_developers;

import javafx.scene.layout.Pane;
import javafx.util.Pair;


public class Map_Object extends Pane {

    int _width, _height;                          // Width & Height of the sprite
    Pair<Double,Double> _scaling_factors;         // Scale to adapt to the screen resolution
    Pair<Integer, Integer> _coordinates;          // Location on which to put the object

    Map_Object()
    {

    }

    void move_to(Pair<Integer, Integer> coordinates)
    {
        this.relocate(coordinates.getKey(),coordinates.getValue());
    }

    public int get_current_X_position() { return (int)this.getLayoutX(); }

    public int get_current_Y_position() { return (int)this.getLayoutY(); }

}