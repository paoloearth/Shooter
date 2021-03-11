
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
    private final HealthBar _healthBar;
    private boolean _goNorth, _goSouth, _goEast, _goWest;
    private final BooleanProperty canShoot = new SimpleBooleanProperty(true);
    private final String _playerName;

    public Sprite(Pane simulationRoot, GameMap M, Pair<Double, Double> scalingFactor, String url, int n_rows, int n_cols, String id, Direction D, String playerName)
    {
        super(scalingFactor, url, n_rows, n_cols);
        this._playerName = playerName;
        this._id = id;

        setSpeed(CustomSettings.PLAYER_SPEED);
        applyCustomScaleToObject(CustomSettings.PLAYER_SCALE);

        /* Add a triggered event to change the view accordingly to the direction of the sprite */
        ChangeListener<Object> updateImage = getListener();
        getCurrentDirectionProperty().addListener(updateImage);
        getCurrentDirectionProperty().setValue(D);

        _healthBar = new HealthBar(this);
        geToBeRemovedProperty().bind(_healthBar.isRemainingLifeZero());

        moveTo(M.get_position_of(id));

        addNodes(_healthBar, getPicture());
        simulationRoot.getChildren().add(this);
    }


    /* Movement & action management */
    @Override
    public void defaultMovement(GameMap M){ move(M);}

    @Override
    public HitBox getHitbox(){ return new HitBox(getCurrentYPosition() , getCurrentXPosition() + getScaledWidth() * 0.15,
                                     getScaledWidth() - getScaledWidth() * 0.15 , getScaledHeight()*.9 ); }
    @Override
    protected void action(Sprite S) { }

    @Override
    public HitBox getMoveBox(){ return new HitBox( getFutureY() + (getScaledHeight() * 2.0/3.0), getFutureX(),
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
        return t.isPassableForPlayer();
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
        if (_goNorth) setDeltaY(- getSpeed());
        if (_goSouth) setDeltaY(getSpeed());
        if (_goEast)  setDeltaX(getSpeed());
        if (_goWest)  setDeltaX(-getSpeed());
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
    public final void setGoNorth(boolean _goNorth) {
        this._goNorth = _goNorth;
    }
    public final void setGoSouth(boolean _goSouth) {
        this._goSouth = _goSouth;
    }
    public final void setGoEast(boolean _goEast) {
        this._goEast = _goEast;
    }
    public final void setGoWest(boolean _goWest) {
        this._goWest = _goWest;
    }

    /* Getters */
    public final String get_id() {
        return _id;
    }
    public final HealthBar getHBar() {
        return _healthBar;
    }
    public final String get_playerName() {
        return _playerName;
    }


}
