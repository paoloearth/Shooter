//JOSE: classe visitata.
package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class PicturedObject extends MapObject {

    private final String _url;
    private final ImageView _view;
    private int _n_rows_spritesheet;
    private int _n_cols_spritesheet;
    private double _scale;              // Scale to make the loaded image of desired size
    //JOSE: -può esserci ridundanza fra _view e _url + _n_rows + _n_cols?
    //      -forse un nome più intuitivo, anche _image


    private final BooleanProperty _isDead = new SimpleBooleanProperty(false);
    //JOSE: penso di aver capito che questo oggetto blocca l'aggiornamento di un oggetto
    //      data una certa iterazione se è true. Forse è meglio cambiare il nome a _isBlocked o
    //      _blockedProperty.

    /* Constructors  */
    public PicturedObject(Pair<Double,Double> scalingFactors, String url )
    {
        super(scalingFactors);

        _n_rows_spritesheet = 1;
        _n_cols_spritesheet = 1;

        _url = url;
        Image _picture = retrieveImage(_url);
        setDimensions( _picture.getWidth(), _picture.getHeight());
        _view = new ImageView(_picture);
    }

    public PicturedObject(Pair<Double,Double> scalingFactors, String url, int n_rows, int n_cols )
    {
        this(scalingFactors,url);

        _n_rows_spritesheet = n_rows;
        _n_cols_spritesheet = n_cols;

        setDimensions(get_width()/ _n_cols_spritesheet,get_height()/ _n_rows_spritesheet);
    }

    /* Image management */
    protected static Image retrieveImage(String URL) { return new Image(URL); }
    //JOSE: -Ci sono molti metodi che fanno questo lavoro. Bisognerebbe unificarlo
    //      -Inoltre bisognerebbe considerare sicurezza: che succede se l'immagine non si trova?

    protected final void updateView() {
        this._view.setFitWidth( _scale * getResolutionScalingFactors().getKey()  * get_width());
        this._view.setFitHeight(_scale * getResolutionScalingFactors().getValue() * get_height());
        this._view.setPreserveRatio(false);
    }
    //JOSE: sostituire commento per un nome più chiaro, p.s. update_image_size
    //      cmq, forse potrebbe essere interessante usare in questo caso un bind
    //      invece di ridimensionare manualmente

    protected  final double getActualHeight() { return _view.getFitHeight(); }

    protected  final double getActualWidth() { return   _view.getFitWidth(); }


    /* Collision handling */
    protected Box getHitbox(){ return new Box(getCurrentYPosition(), getCurrentXPosition(),  getActualWidth() , getActualHeight() );}

    protected final boolean intersect(PicturedObject P2) { return getHitbox().intersect(P2.getHitbox()); }


    protected void action(Sprite S){}
    //JOSE: E questi metodi? penso che bisognerebbe renderli abstract oppure implementare un'interfaccia.

    /* Getters */
    public final String getURL() { return _url; }

    public final ImageView getView() { return _view; }
    //JOSE: se si cambia il nome di _view, cambiare il nome di questo metodo.

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
