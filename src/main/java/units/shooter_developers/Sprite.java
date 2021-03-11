//JOSE: classe visitata

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
    private final HealthBar healthBar;
    private boolean goNorth, goSouth, goEast, goWest;
    private final BooleanProperty canShoot = new SimpleBooleanProperty(true);
    private final String playerName;

    public Sprite(Pane simulation_root, GameMap M, Pair<Double, Double> scalingFactor, String url, int n_rows, int n_cols, String id, Direction D, String playerName)
    {
        super(scalingFactor, url, n_rows, n_cols);
        this.playerName = playerName;
        this._id = id;

        setSpeed(CustomSettings.PLAYER_SPEED);
        setScale(CustomSettings.PLAYER_SCALE);

        updateView();

        /* Add a triggered event to change the view accordingly to the direction of the sprite */
        ChangeListener<Object> updateImage = getListener();
        _currentDirectionProperty().addListener(updateImage);
        _currentDirectionProperty().setValue(D);

        healthBar = new HealthBar(this);
        getIsDeadProperty().bind(healthBar.isRemainingLifeZero());

        moveTo(M.get_position_of(id));

        addNodes(healthBar, getView());
        simulation_root.getChildren().add(this);
    }


    /* Movement & action management */
    public void defaultMovement(GameMap M){ move(M);}
    @Override
    public Box getHitbox(){ return new Box(getCurrentYPosition() , getCurrentXPosition() + getActualWidth() * 0.15,
                                     getActualWidth() - getActualWidth() * 0.15 , getActualHeight()*.9 ); }
    @Override
    public Box getMoveBox(){ return new Box( getFutureY() + (getActualHeight() * 2.0/3.0), getFutureX(),
                                                  getActualWidth() , getActualHeight()* 1.0/3.0); }

    private void move(GameMap M) {

        updateMovement();

        if (hasMoved()) {
            var destination = getDestination();

            updateDirection(destination);

            if (!(illegalMove(M))) moveTo(destination);
        }

    }
    protected void shoot(Pane root){
        if(canShoot.getValue())
        {
            root.getChildren().add(new Projectile( getResolutionScalingFactors(), CustomSettings.URL_PROJECTILE,this));
            shootingCooldown.play();
        }
    }

    /* Utils */

    public boolean checkIfPassable(Tile t)
    {
        return t.is_passable;
    }

    private boolean hasMoved() {
        return (Math.abs(get_deltaX()) > 0 || Math.abs(get_deltaY()) > 0);
    }

    private ChangeListener<Object> getListener() { return (obs, ov, nv) ->
                                                  getView().setViewport(new Rectangle2D( _frame.get()*get_width(),
                                                                                 getCurrentDirection().getOffset() * get_height(),
                                                                                       get_width(), get_height())); }
    //JOSE: questo metodo è un salame, meglio se si organizza un po' più visualmente, anche mettere i parametri in varie righe.

    private void updateMovement() {
        set_deltaX(0);set_deltaY(0);
        if (goNorth) set_deltaY(get_deltaY()- get_speed());
        if (goSouth) set_deltaY(get_deltaY()+get_speed());
        if (goEast)  set_deltaX(get_deltaX()+ get_speed());
        if (goWest)  set_deltaX(get_deltaX()-get_speed());
    }

    private void updateDirection(Coordinates destination)
    {
        Direction D;
        if (Math.abs(get_deltaX()) > Math.abs(get_deltaY()))
            D = (destination.getX()  < getCurrentXPosition())? Direction.LEFT : Direction.RIGHT;
        else
            D = (destination.getY()  < getCurrentYPosition())? Direction.UP:Direction.DOWN;

        set_currentDirection(D);
    }

    /* Animations */
    private final  Timeline shootingCooldown = new Timeline(
            new KeyFrame(Duration.ZERO, event -> canShoot.setValue(false)),
            new KeyFrame(Duration.seconds(CustomSettings.SHOOTING_COOLDOWN), event -> canShoot.setValue(true))
    );

    /* Setters */
    public final void setGoNorth(boolean goNorth) {
        this.goNorth = goNorth;
    }
    public final void setGoSouth(boolean goSouth) {
        this.goSouth = goSouth;
    }
    public final void setGoEast(boolean goEast) {
        this.goEast = goEast;
    }
    public final void setGoWest(boolean goWest) {
        this.goWest = goWest;
    }

    /* Getters */
    public final String get_id() {
        return _id;
    }
    public final HealthBar getHBar() {
        return healthBar;
    }
    public final String getPlayerName() {
        return playerName;
    }


}
