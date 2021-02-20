package units.shooter_developers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Pair;

public class Dynamic_Object {
    ObjectProperty<Direction> _current_direction = new SimpleObjectProperty<>();

    int _speed;
    int _deltaX, _deltaY;

}
