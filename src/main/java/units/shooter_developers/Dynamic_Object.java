package units.shooter_developers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Pair;

public abstract class Dynamic_Object extends Pictured_Object {

    private final ObjectProperty<Direction> _current_direction = new SimpleObjectProperty<>();

    private int _speed;
    private int _deltaX, _deltaY;

    //Custom Constructor
    public Dynamic_Object(Pair<Double,Double> scaling_factors, String url)
    {
        super(scaling_factors, url);
    }
    public Dynamic_Object( Pair<Double,Double> scaling_factors, String url, int _n_rows, int _n_cols){ super(scaling_factors, url, _n_rows, _n_cols); }

    public  void set_speed(double speed) { this._speed = (int) (speed*get_scaling_factors().getKey()); }   // Set characteristics of the projectile

    double get_future_x(){ return this.get_current_X_position() + _deltaX; }
    double get_future_y(){ return this.get_current_Y_position() + _deltaY; }


    public Box get_move_box(){ return new Box( get_future_y() ,get_future_x(), get_actual_width() ,get_actual_height()); }

    protected  boolean illegal_move(GameMap M) {

       /* Compute the collision box*/
       var collision_box =  get_move_box();

       if(collision_box.is_out_of_map(M)) return true;

       /* Get tiles  */
       collision_box.compute_tiles_bounds(M);

       return collision_box.performs_check(M,this);

   }

    public abstract  boolean get_property_to_check(Tile t);

    /* Setters */
    public void set_deltaX(int _deltaX) { this._deltaX = _deltaX; }
    public void set_deltaY(int _deltaY) { this._deltaY = _deltaY; }
    public void set_current_direction(Direction _current_direction) { this._current_direction.set(_current_direction); }

    /* Getters */
    public int get_speed() { return _speed; }
    public int get_deltaX() { return _deltaX; }
    public int get_deltaY() { return _deltaY; }
    public Direction get_current_direction() { return _current_direction.get(); }
    public ObjectProperty<Direction> _current_directionProperty() { return _current_direction; }





}
