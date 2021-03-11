//JOSE: classe visitata.
package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class PicturedObject extends MapObject {

    private final ImageView _picture;
    private double _customScale;              // Scale to make the loaded image of desired size

    private final BooleanProperty _toBeRemoved = new SimpleBooleanProperty(false);

    /* Constructors  */
    public PicturedObject(Pair<Double,Double> scalingFactors, String url )
    {
        super(scalingFactors);


        Image image = retrieveImage(url);
        setDimensions( image.getWidth(), image.getHeight());
        _picture = new ImageView(image);
    }

    public PicturedObject(Pair<Double,Double> resolutionScalingFactors, String url, int n_rows, int n_cols )
    {
        this(resolutionScalingFactors,url);
        setDimensions(get_width()/ n_cols,get_height()/ n_rows);
    }

    /* Image management */
    protected static Image retrieveImage(String URL) { return new Image(URL); }


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
        return _toBeRemoved.get();
    }

    public final BooleanProperty getIsDeadProperty() {
        return _toBeRemoved;
    }

    /* Setters */
    public final void setScale(double scale) {
        _customScale = scale;
    }

    public final void setIsDeadProperty(boolean isDead) {
        _toBeRemoved.set(isDead);
    }
}
