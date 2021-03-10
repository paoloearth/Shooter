//JOSE: classe visitata
//  -Mi sembra che non siano state implementate le colissioni fra diversi DynamicObject, infatti, i personaggi si possono superporre.
//  -Il ruolo di _speed e di _deltaX e _deltaY è confuso. Sembrerebbe che la presenza di questi due oggetti introduca ridundanza
//   dato che _deltaX e _deltaY si possono ricavare univocamente dalla velocità e viceversa se partiamo di movimento rettilineo
//   uniforme, come effettivamente è il caso.
//  -Mi sembra che _deltaX e _deltaY non siano scalate con la risoluzione.

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

    protected final Coordinates getDestination() { return new Coordinates(getFutureX(), getFutureY()); }
    //JOSE: forse meglio get_future_coordinates


    /* Collision handling */
    public abstract  boolean getPropertyToCheck(Tile t);

    protected final Box getDefaultMoveBox(){ return new Box( getFutureY() , getFutureX(), getActualWidth() , getActualHeight()); }

    protected Box getMoveBox(){ return getDefaultMoveBox();}

    protected final boolean illegalMove(GameMap M) {

       if(getDefaultMoveBox().isOutOfMap(M)) return true;

        var collision_box = getMoveBox();
        collision_box.compute_tiles_bounds(M);

        //JOSE: E le colissioni fra personaggi o altri eventuali DynamicObject?

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

    public final Direction getCurrentDirection() { return _currentDirection.get(); }

    public final ObjectProperty<Direction> _currentDirectionProperty() { return _currentDirection; }





}
