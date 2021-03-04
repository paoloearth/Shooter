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

    public  void set_speed(double speed)
    {
        this._speed = (int) (speed*_scaling_factors.getKey());    // Set characteristics of the projectile
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

    public Box get_move_box(){
        return new Box( get_future_y() ,get_future_x(), get_actual_width() ,get_actual_height());
    }





   // protected abstract  boolean illegal_move(Map M);
   protected  boolean illegal_move(Map M, double multiplier) {

       /* Compute the collision box*/
       var collision_box =  get_move_box();

       if(collision_box.is_out_of_map(M)) return true;

       /* Reduce impact area of the object*/
       collision_box.shrink_height_by(multiplier);

       /* Get tiles  */
       collision_box.compute_tiles_bounds(M);

       return collision_box.performs_check(M,_type);

   }



}
