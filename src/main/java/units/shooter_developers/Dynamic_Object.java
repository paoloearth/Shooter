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

    //Compute future x-component of the position
    double get_future_x()
    {
        return this.get_current_X_position() + _deltaX;
    }

    //Compute future y-component of the position
    double get_future_y()
    {
        return this.get_current_Y_position() + _deltaY;
    }

    protected abstract  boolean illegal_move(Map M);

    //Check if an element of the map is out of it
    protected boolean is_out_of_map(Map M) {
        return  this.get_future_x() <= 0 ||
                this.get_future_y() <= 0 ||
                this.get_future_x()  + get_actual_width() >= M.get_width() ||
                this.get_future_y()  + get_actual_height() >= M.get_height();

    }


}
