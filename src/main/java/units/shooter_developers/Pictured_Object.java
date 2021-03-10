//JOSE: classe visitata.
//  -Bisognerebbe unificare la lettura di immagini da file.
//  -La gestione della hitbox è poco chiara: si genera insitu quando viene chiamato il metodo con la grandezza dello
//   sprite se ho capito bene. Immaigno che il riscalamento della hitbox accada in delle classi figlie. Se questo è
//   così penso che bisognerebbe spingere in su quel comportamento a questa classe: la hitbox è un elemento intrinsecamente
//   legato ad ogni Pictured_Object, quindi, questa classe deve essere in grado di autogestionarla. Qua mi sembra che ci
//   sia "Refused bequest".

package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class Pictured_Object extends MapObject {

    //JOSE: suggerimento, attaccare le variabili interne senza lasciare spazi, si vede più chiaro
    private final String _url;
    private final ImageView _view;  //JOSE: -può esserci ridundanza fra questi due oggetti?
                                    //      -forse un nome più intuitivo, anche _image

    private int _n_rows;                // Number of rows  of the sprite-sheet
    private int _n_cols;                // Number of columns of the sprite-sheet
    //JOSE: io in queste variabili farei riferimento alla spritesheet, per esempio _n_rows_spritesheet


    private double _scale;             // Scale to make the loaded image of desired size


    private final BooleanProperty _isDead = new SimpleBooleanProperty(false);
    //JOSE: cosa vuol dire morto? che non si dipinge? fa riferimento al perfonaggio del giocatore?
    // se un oggetto non si dipinge più non dobrevve desallocarsi? forse basta mettere un nome più chiaro.


    public Pictured_Object(Pair<Double,Double> scaling_factors, String url )
    {
        super(scaling_factors);

        this._n_rows = 1;
        this._n_cols = 1;

        this._url = url;
        Image _picture = retrieve_image(_url);
        setDimensions((int) _picture.getWidth(),(int) _picture.getHeight());
        //JOSE: se si cambiano gli argomenti a double si può rimuovere il cast.

        this._view = new ImageView(_picture);
    }

    /* Custom constructor for SpriteSheet with multiple views*/
    public Pictured_Object(Pair<Double,Double> scaling_factors, String url, int n_rows, int n_cols )
    {
        this(scaling_factors,url);

        this._n_rows = n_rows;
        this._n_cols = n_cols;

        setDimensions(get_width()/_n_cols,get_height()/_n_rows);

    }

    // Create an image given an URL
    Image retrieve_image(String URL)
    {
        return new Image(URL);
    }
    //JOSE: -Ci sono molti metodi che fanno questo lavoro. Bisognerebbe unificarlo
    //      -Inoltre bisognerebbe considerare sicurezza: che succede se l'immagine non si trova?

    // Scaling on x-axis and y-axis of images according to the resolution of the window
    void update_view() {
        this._view.setFitWidth( _scale * getScalingFactors().getKey()  * get_width());
        this._view.setFitHeight(_scale * getScalingFactors().getValue() * get_height());
        this._view.setPreserveRatio(false);
    }
    //JOSE: sostituire commento per un nome più chiaro, p.s. update_image_size
    //      cmq, forse potrebbe essere interessante usare in questo caso un bind
    //      invece di ridimensionare manualmente

    protected  int getActualHeight()
    {
        return (int) this._view.getFitHeight();
    }
    protected  int getActualWidth()
    {
        return (int) this._view.getFitWidth();
    }


    public Box get_hitbox(){ return new Box(getCurrentYPosition(), getCurrentXPosition(),  getActualWidth() , getActualHeight() );}

    public boolean intersect(Pictured_Object P2)
    {
        return this.get_hitbox().intersect(P2.get_hitbox());
    }


    public void default_movement(GameMap M){};
    public  void update(Sprite S){};
    //JOSE: E questi metodi? penso che bisognerebbe renderli abstract oppure implementare un'interfaccia.


    public String get_url() {
        return _url;
    }

    public ImageView get_view() { return _view; }
    //JOSE: se si cambia il nome di _view, cambiare il nome di questo metodo.

    /* Getters */
    public double get_scale() {
        return _scale;
    }
    public boolean is_dead() {
        return _isDead.get();
    }
    public BooleanProperty get_is_dead_property() {
        return _isDead;
    }



    public void set_scale(double _scale) {
        this._scale = _scale;
    }
    public void set_is_dead_property(boolean _isDead) {
        this._isDead.set(_isDead);
    }
    //JOSE: riterrei la parola "property" per far riferimento agli oggetti property di javafx.
}
