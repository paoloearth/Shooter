package units.shooter_developers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;


public class Sprite extends Dynamic_Object {
    public String _id;                                                      // Player ID
    IntegerProperty _frame  = new SimpleIntegerProperty(0);        // Frame property to update the moving sprite
    HealthBar H;
    private boolean goNorth, goSouth, goEast, goWest;
    BooleanProperty _can_shoot = new SimpleBooleanProperty(true);
    public String _player_name;



    Timeline shooting_cooldown = new Timeline(
            new KeyFrame(Duration.ZERO, event -> _can_shoot.setValue(false)),
            new KeyFrame(Duration.seconds(0.5), event -> _can_shoot.setValue(true))
    );


    public Sprite(Pane root, Map M, Pair<Double, Double> scaling_factor, String url, int _n_rows, int _n_cols, String id, Direction D, String player_name)
    {
        super(scaling_factor, url, _n_rows, _n_cols);

        this._player_name = player_name;
        this._id = id;

        set_speed(Custom_Settings.PLAYER_SPEED);

        this._scale = Custom_Settings.PLAYER_SCALE;
        this._type = "SPRITE";

        update_view();

        /* Add a triggered event to changes of locations, set the default direction*/
        ChangeListener<Object> updateImage = getListener();
        _current_direction.addListener(updateImage);
        _current_direction.setValue(D);


        H = getHealthBar();

        this._isDead.bind(H.Health.lessThanOrEqualTo(0));

        move_to(M.get_player_pixel_position(id));


        this.getChildren().addAll(H, _view);
        root.getChildren().add(this);
    }

    private HealthBar getHealthBar() {
        return new HealthBar(this);
    }

    //Change the picture of the Sprite in the SpriteSheet according to the direction
    private ChangeListener<Object> getListener() {

        return (ov, o, o2) -> this._view.setViewport(new Rectangle2D( _frame.get()*_width, _current_direction.get().getOffset() * _height, _width, _height));

    }

    //Update the movement in the right direction
    public void update_delta() {
        _deltaX = _deltaY = 0;
        if (goNorth) _deltaY -= _speed;
        if (goSouth) _deltaY += _speed;
        if (goEast)  _deltaX += _speed;
        if (goWest)  _deltaX -= _speed;
    }

    // HERE I TRY TO INFER THE DIRECTION SO THAT I CAN PICK THE RIGHT SPRITE
    public void update_get_direction(double destination_x, double destination_y)
    {
        Direction D;

        if(Math.abs(_deltaX) >0 || Math.abs(_deltaY)  > 0)
        {
            if (Math.abs(_deltaX) > Math.abs(_deltaY))      // NEED TO CHOOSE BETWEEN RIGHT OR LEFT
            {
                if (destination_x  < get_current_X_position()) D=Direction.LEFT;
                else                                           D=Direction.RIGHT;

            } else {                  // NEED TO CHOOSE BETWEEN UP OR DOWN
                if (destination_y  < get_current_Y_position()) D = Direction.UP;
                else                                           D= Direction.DOWN;
            }

            _current_direction.set(D);
        }
    }



    public void move(Map M) {
        update_delta();

        if (this._deltaX == 0 && this._deltaY == 0) return;

        double future_x = get_current_X_position() + _deltaX;
        double future_y = get_current_Y_position() + _deltaY;

        update_get_direction(future_x, future_y);

        if(!(is_out_of_map(M) || illegal_move(M))) move_to(new Coordinates(future_x, future_y));

    }

    @Override
    protected  boolean illegal_move(Map M) {
        /* Compute the collision box*/
        var collision_box =  get_full_collision_box();

        /* Reduce impact area of the object*/
        collision_box.shrink_height_by(2.00/3.00);

        /* Get tiles  */
        collision_box.compute_tiles_bounds(M);

        return collision_box.performs_check(M,this._type);

    }

    //Return the bounds of the player used to check collision
    @Override
    public Rectangle2D get_bounds()
    {
        return new Rectangle2D(get_current_X_position(),
                get_current_Y_position() +get_actual_height() * 0.15 ,
                get_actual_width(),
                get_actual_height() * 3.0/4);
    }

    @Override
    public void update(Map M, Sprite S) {
        move(M);
    }

    public void shoot(Pane root){

        if(_can_shoot.getValue())
        {
            root.getChildren().add(new Projectile( _scaling_factors, Custom_Settings.URL_PROJECTILE,this));
            shooting_cooldown.play();
        }
    }

    /* Set the boolean attributes according to the key pressed*/
    public void setGoNorth(boolean goNorth) {
        this.goNorth = goNorth;
    }

    public void setGoSouth(boolean goSouth) {
        this.goSouth = goSouth;
    }

    public void setGoEast(boolean goEast) {
        this.goEast = goEast;
    }

    public void setGoWest(boolean goWest) {
        this.goWest = goWest;
    }



}
