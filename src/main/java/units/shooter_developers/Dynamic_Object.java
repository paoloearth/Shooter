package units.shooter_developers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Pair;

public abstract class Dynamic_Object extends Pictured_Object {
    ObjectProperty<Direction> _current_direction = new SimpleObjectProperty<>();

    int _speed;
    int _deltaX, _deltaY;

    //Custom Constructor
    public Dynamic_Object(Pair<Double,Double> scaling_factors, String url)
    {
        super(scaling_factors, url);

    }

    //Custom Constructor
    public Dynamic_Object( Pair<Double,Double> scaling_factors, String url, int _n_rows, int _n_cols)
    {
        super(scaling_factors, url, _n_rows, _n_cols);
    }

}
