package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class PicturedObject extends MapObject {

    private final String _url;
    private final ImageView _view;

    private int _n_rows;                // Number of rows  of the sprite-sheet
    private int _n_cols;                // Number of columns of the sprite-sheet

    private double _scale;              // Scale to make the loaded image of desired size


    private final BooleanProperty _isDead = new SimpleBooleanProperty(false);

    public PicturedObject(Pair<Double,Double> scalingFactors, String url )
    {
        super(scalingFactors);

        _n_rows = 1;
        _n_cols = 1;

        _url = url;
        Image _picture = retrieveImage(_url);
        setDimensions((int) _picture.getWidth(),(int) _picture.getHeight());

        _view = new ImageView(_picture);
    }

    public PicturedObject(Pair<Double,Double> scalingFactors, String url, int n_rows, int n_cols )
    {
        this(scalingFactors,url);

        _n_rows = n_rows;
        _n_cols = n_cols;

        setDimensions(get_width()/_n_cols,get_height()/_n_rows);
    }

    /* Image management */
    protected static Image retrieveImage(String URL) { return new Image(URL); }
    protected final void update_view() {
        this._view.setFitWidth( _scale * getScalingFactors().getKey()  * get_width());
        this._view.setFitHeight(_scale * getScalingFactors().getValue() * get_height());
        this._view.setPreserveRatio(false);
    }
    protected  final int getActualHeight() { return (int)  _view.getFitHeight(); }
    protected  final int getActualWidth() { return  (int)  _view.getFitWidth(); }

    /* Collision handling */
    protected Box getHitbox(){ return new Box(getCurrentYPosition(), getCurrentXPosition(),  getActualWidth() , getActualHeight() );}
    protected final boolean intersect(PicturedObject P2) { return getHitbox().intersect(P2.getHitbox()); }

    /* Movement management */
    protected void defaultMovement(GameMap M){};
    protected void update(Sprite S){};

    /* Getters */
    public final String getURL() { return _url; }
    public final ImageView getView() { return _view; }
    public final boolean isDead() {
        return _isDead.get();
    }
    public final BooleanProperty getIsDeadProperty() {
        return _isDead;
    }

    /* Setters */
    public final void setScale(double scale) {
        _scale = scale;
    }
    public final void setIsDeadProperty(boolean isDead) {
        _isDead.set(isDead);
    }
}
