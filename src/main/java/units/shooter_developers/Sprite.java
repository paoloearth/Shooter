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

        scalePicture();

        /* Add a triggered event to change the view accordingly to the direction of the sprite */
        ChangeListener<Object> updateImage = getListener();
        getCurrentDirectionProperty().addListener(updateImage);
        getCurrentDirectionProperty().setValue(D);

        healthBar = new HealthBar(this);
        getRemovedProperty().bind(healthBar.isRemainingLifeZero());

        moveTo(M.get_position_of(id));

        addNodes(healthBar, getPicture());
        simulation_root.getChildren().add(this);
    }


    /* Movement & action management */
    @Override
    public void defaultMovement(GameMap M){ move(M);}

    @Override
    public Box getHitbox(){ return new Box(getCurrentYPosition() , getCurrentXPosition() + getScaledWidth() * 0.15,
                                     getScaledWidth() - getScaledWidth() * 0.15 , getScaledHeight()*.9 ); }
    @Override
    protected void action(Sprite S) { }

    @Override
    public Box getMoveBox(){ return new Box( getFutureY() + (getScaledHeight() * 2.0/3.0), getFutureX(),
                                                  getScaledWidth() , getScaledHeight() /3.0); }

    private void move(GameMap M) {

        updateMovement();

        if (hasMoved()) {
            var destination = getFutureCoordinates();

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
        return (Math.abs(getDeltaX()) > 0 || Math.abs(getDeltaY()) > 0);
    }

    private ChangeListener<Object> getListener() { return (obs, ov, nv) ->
                                                  getPicture().setViewport(new Rectangle2D( _frame.get()*get_width(),
                                                                                 getCurrentDirection().getOffset() * get_height(),
                                                                                       get_width(), get_height())); }

    private void updateMovement() {
        setDeltaX(0);
        setDeltaY(0);
        if (goNorth) setDeltaY(- getSpeed());
        if (goSouth) setDeltaY(getSpeed());
        if (goEast)  setDeltaX(getSpeed());
        if (goWest)  setDeltaX(-getSpeed());
    }

    private void updateDirection(Coordinates destination)
    {
        Direction D;
        if (Math.abs(getDeltaX()) > Math.abs(getDeltaY()))
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
