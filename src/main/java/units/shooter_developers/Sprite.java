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


    public Sprite(Pane root, GameMap M, Pair<Double, Double> scaling_factor, String url, int _n_rows, int _n_cols, String id, Direction D, String player_name)
    {
        super(scaling_factor, url, _n_rows, _n_cols);

        this._player_name = player_name;
        this._id = id;

        set_speed(Custom_Settings.PLAYER_SPEED);

        set_scale(Custom_Settings.PLAYER_SCALE);
        set_type(Custom_Settings.SPRITE);

        update_view();

        /* Add a triggered event to changes of locations, set the default direction*/
        ChangeListener<Object> updateImage = getListener();
        _current_directionProperty().addListener(updateImage);
        _current_directionProperty().setValue(D);



        H = getHealthBar();
        get_is_dead_property().bind(H.is_remaining_life_zero());

        move_to(M.get_position_of(id));


        this.getChildren().addAll(H, get_view());
        root.getChildren().add(this);
    }

    private HealthBar getHealthBar() {
        return new HealthBar(this);
    }

    //Change the picture of the Sprite in the SpriteSheet according to the direction
    private ChangeListener<Object> getListener() {

        return (ov, o, o2) -> get_view().setViewport(new Rectangle2D( _frame.get()*get_width(), get_current_direction().getOffset() * get_height(), get_width(), get_height()));

    }

    //Update the movement in the right direction
    public void update_speed() {
        set_deltaX(0);set_deltaY(0);
        if (goNorth) set_deltaY(get_deltaY()- get_speed());
        if (goSouth) set_deltaY(get_deltaY()+get_speed());
        if (goEast)  set_deltaX(get_deltaX()+ get_speed());
        if (goWest)  set_deltaX(get_deltaX()-get_speed());
    }

    // HERE I TRY TO INFER THE DIRECTION SO THAT I CAN PICK THE RIGHT SPRITE
    public void update_get_direction(Coordinates destination)
    {
        Direction D;
        if (Math.abs(get_deltaX()) > Math.abs(get_deltaY()))
                D = (destination.getX()  < get_current_X_position())? Direction.LEFT : Direction.RIGHT;
        else
                D = (destination.getY()  < get_current_Y_position())? Direction.UP:Direction.DOWN;

        set_current_direction(D);
    }




    public void move(GameMap M) {
        update_speed();

        if (has_moved()) {

            var destination = get_destination();

            update_get_direction(destination);

            if (!(illegal_move(M, 2.0 / 3.0, this))) move_to(destination);
        }

    }

    public boolean get_property_to_check(Tile t)
    {
        return t.is_passable;
    }

    private boolean has_moved() {
        return (Math.abs(get_deltaX()) > 0 || Math.abs(get_deltaY()) > 0);
    }

    Coordinates get_destination()
    {
        double future_x = get_current_X_position() + get_deltaX();
        double future_y = get_current_Y_position() + get_deltaY();
        return new Coordinates(future_x, future_y);

    }

    @Override
    public Box get_hitbox(){
        return new Box(get_current_Y_position() , get_current_X_position() +get_actual_width() * 0.15,  get_actual_width() ,get_actual_height()*.75 );
    }

    @Override
    public void update(GameMap M, Sprite S) {
        move(M);
    }

    public void shoot(Pane root){

        if(_can_shoot.getValue())
        {
            root.getChildren().add(new Projectile( get_scaling_factors(), Custom_Settings.URL_PROJECTILE,this));
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

    public boolean isGoNorth() {
        return goNorth;
    }

    public boolean isGoSouth() {
        return goSouth;
    }

    public boolean isGoEast() {
        return goEast;
    }

    public boolean isGoWest() {
        return goWest;
    }
}
