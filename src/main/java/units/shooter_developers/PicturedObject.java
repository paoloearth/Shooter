//JOSE: classe visitata.
package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class PicturedObject extends MapObject {

    private final ImageView _picture;
    private int _n_rows_spritesheet;
    private int _n_cols_spritesheet;
    private double _customScale;              // Scale to make the loaded image of desired size
    //JOSE: -può esserci ridundanza fra _view e _url + _n_rows + _n_cols?

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

        Image _picture = retrieveImage(url);
        setDimensions( _picture.getWidth(), _picture.getHeight());
        this._picture = new ImageView(_picture);
    }

    public PicturedObject(Pair<Double,Double> resolutionScalingFactors, String url, int n_rows, int n_cols )
    {
        this(resolutionScalingFactors,url);

        _n_rows_spritesheet = n_rows;
        _n_cols_spritesheet = n_cols;

        setDimensions(get_width()/ _n_cols_spritesheet,get_height()/ _n_rows_spritesheet);
    }

    /* Image management */
    protected static Image retrieveImage(String URL) { return new Image(URL); }
    //JOSE: -Ci sono molti metodi che fanno questo lavoro. Bisognerebbe unificarlo
    //      -Inoltre bisognerebbe considerare sicurezza: che succede se l'immagine non si trova?

    protected final void scalePicture() {
        this._picture.setFitWidth( _customScale * getResolutionScalingFactors().getKey()  * get_width());
        this._picture.setFitHeight(_customScale * getResolutionScalingFactors().getValue() * get_height());
        this._picture.setPreserveRatio(false);
    }
    //JOSE: sostituire commento per un nome più chiaro, p.s. update_image_size
    //      cmq, forse potrebbe essere interessante usare in questo caso un bind
    //      invece di ridimensionare manualmente

    protected  final double getScaledHeight() { return _picture.getFitHeight(); }

    protected  final double getScaledWidth() { return   _picture.getFitWidth(); }


    /* Collision handling */
    protected Box getHitbox(){ return new Box(getCurrentYPosition(), getCurrentXPosition(),  getScaledWidth() , getScaledHeight() );}

    protected final boolean intersect(PicturedObject P2) { return getHitbox().intersect(P2.getHitbox()); }


    protected void action(Sprite S){}
    //JOSE: E questi metodi? penso che bisognerebbe renderli abstract oppure implementare un'interfaccia.

    /* Getters */
    public final ImageView getImageView() { return _picture; }
    //JOSE: se si cambia il nome di _view, cambiare il nome di questo metodo.

    public final boolean isDead() {
        return _isDead.get();
    }

    public final BooleanProperty getIsDeadProperty() {
        return _isDead;
    }

    /* Setters */
    public final void setScale(double scale) {
        _customScale = scale;
    }

    public final void setIsDeadProperty(boolean isDead) {
        _isDead.set(isDead);
    }
}
