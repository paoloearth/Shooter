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


public class Sprite extends DynamicObject {

    private final String _id;
    private final IntegerProperty _frame  = new SimpleIntegerProperty(0);
    private final HealthBar Sprite_Hbar;
    private boolean goNorth, goSouth, goEast, goWest;
    private final BooleanProperty _can_shoot = new SimpleBooleanProperty(true);
    private final String _player_name;

    private final  Timeline shooting_cooldown = new Timeline(
            new KeyFrame(Duration.ZERO, event -> _can_shoot.setValue(false)),
            new KeyFrame(Duration.seconds(CustomSettings.SHOOTING_COOLDOWN), event -> _can_shoot.setValue(true))
    );


    public Sprite(Pane root, GameMap M, Pair<Double, Double> scaling_factor, String url, int _n_rows, int _n_cols, String id, Direction D, String player_name)
    {
        super(scaling_factor, url, _n_rows, _n_cols);
        this._player_name = player_name;
        this._id = id;

        setSpeed(CustomSettings.PLAYER_SPEED);
        setScale(CustomSettings.PLAYER_SCALE);

        update_view();

        /* Add a triggered event to change the view accordingly to the direction of the sprite */
        ChangeListener<Object> updateImage = getListener();
        _currentDirectionProperty().addListener(updateImage);
        _currentDirectionProperty().setValue(D);

        Sprite_Hbar = getHealthBar();
        getIsDeadProperty().bind(Sprite_Hbar.isRemainingLifeZero());

        moveTo(M.get_position_of(id));
        addNodes(Sprite_Hbar, getView());

        root.getChildren().add(this);
    }

    @Override
    public void defaultMovement(GameMap M){
        move(M);
    };
    @Override
    public Box getHitbox(){ return new Box(getCurrentYPosition() , getCurrentXPosition() + getActualWidth() * 0.15,  getActualWidth() - getActualWidth() * 0.15 , getActualHeight()*.9 ); }
    @Override
    public Box getMoveBox(){ return new Box( getFutureY() + (getActualHeight() * 2.0/3.0), getFutureX(), getActualWidth() , getActualHeight()* 1.0/3.0); }


    private HealthBar getHealthBar() {
        return new HealthBar(this);
    }

    private ChangeListener<Object> getListener() { return (ov, o, o2) -> getView().setViewport(new Rectangle2D( _frame.get()*get_width(), getCurrentDirection().getOffset() * get_height(), get_width(), get_height())); }

    //Update the movement in the right direction
    private void update_speed() {
        set_deltaX(0);set_deltaY(0);
        if (goNorth) set_deltaY(get_deltaY()- get_speed());
        if (goSouth) set_deltaY(get_deltaY()+get_speed());
        if (goEast)  set_deltaX(get_deltaX()+ get_speed());
        if (goWest)  set_deltaX(get_deltaX()-get_speed());
    }


    private void update_get_direction(Coordinates destination)
    {
        Direction D;
        if (Math.abs(get_deltaX()) > Math.abs(get_deltaY()))
                D = (destination.getX()  < getCurrentXPosition())? Direction.LEFT : Direction.RIGHT;
        else
                D = (destination.getY()  < getCurrentYPosition())? Direction.UP:Direction.DOWN;

        set_currentDirection(D);
    }



    private void move(GameMap M) {

        update_speed();

        if (has_moved()) {

            var destination = get_destination();

            update_get_direction(destination);

            if (!(illegal_move(M))) moveTo(destination);
        }

    }

    public boolean getPropertyToCheck(Tile t)
    {
        return t.is_passable;
    }

    private boolean has_moved() {
        return (Math.abs(get_deltaX()) > 0 || Math.abs(get_deltaY()) > 0);
    }


    public void shoot(Pane root){

        if(_can_shoot.getValue())
        {
            root.getChildren().add(new Projectile( getScalingFactors(), CustomSettings.URL_PROJECTILE,this));
            shooting_cooldown.play();
        }
    }

    /* Setters */
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

    /* Getters */

    public String get_id() {
        return _id;
    }
    public int get_frame() {
        return _frame.get();
    }
    public IntegerProperty _frameProperty() {
        return _frame;
    }
    public HealthBar getHBar() {
        return Sprite_Hbar;
    }
    public boolean is_can_shoot() {
        return _can_shoot.get();
    }
    public BooleanProperty _can_shootProperty() {
        return _can_shoot;
    }
    public String get_player_name() {
        return _player_name;
    }
}
