package units.shooter_developers;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.util.Pair;

public abstract class DynamicObject extends PicturedObject {

    private final ObjectProperty<Direction> _currentDirection = new SimpleObjectProperty<>();
    private int _speed;
    private int _deltaX, _deltaY;

    /* Constructors */
    public DynamicObject(Pair<Double,Double> scalingFactors, String url)
    {
        super(scalingFactors, url);
    }

    public DynamicObject(Pair<Double,Double> scalingFactors, String url, int n_rows, int n_cols){ super(scalingFactors, url, n_rows, n_cols); }

    /* Movement management */
    protected void setSpeed(double speed) { _speed = (int) (speed* getScalingFactors().getKey()); }

    protected final double getFutureX(){ return getCurrentXPosition() + get_deltaX(); }

    protected final double getFutureY() { return getCurrentYPosition() + get_deltaY(); }

    protected final Coordinates get_destination() { return new Coordinates(getFutureX(), getFutureY()); }


    /* Collision handling */
    public abstract  boolean getPropertyToCheck(Tile t);

    protected final Box getDefaultMoveBox(){ return new Box( getFutureY() , getFutureX(), getActualWidth() , getActualHeight()); }

    protected Box getMoveBox(){ return getDefaultMoveBox();}

    protected final boolean illegal_move(GameMap M) {

       if(getDefaultMoveBox().isOutOfMap(M)) return true;

        var collision_box = getMoveBox();
        collision_box.compute_tiles_bounds(M);

       return collision_box.performs_check(M,this);

   }



    /* Setters */
    public final void set_deltaX(int deltaX) { _deltaX = deltaX; }

    public final void set_deltaY(int deltaY) { _deltaY = deltaY; }

    public final void set_currentDirection(Direction currentDirection) { _currentDirection.set(currentDirection); }

    /* Getters */
    public final int get_speed() { return _speed; }

    public final int get_deltaX() { return _deltaX; }

    public final int get_deltaY() { return _deltaY; }

    public final Direction get_currentDirection() { return _currentDirection.get(); }

    public final ObjectProperty<Direction> _currentDirectionProperty() { return _currentDirection; }





}
