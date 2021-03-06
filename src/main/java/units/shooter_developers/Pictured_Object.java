package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class Pictured_Object extends Map_Object {


    private final String _url;
    private final ImageView _view;

    private int _n_rows;                // Number of rows  of the sprite-sheet
    private int _n_cols;                // Number of columns of the sprite-sheet


    private double _scale;             // Scale to make the loaded image of desired size
    private String _type;               // Will store the type of the object

    private final BooleanProperty _isDead = new SimpleBooleanProperty(false);


    public Pictured_Object(Pair<Double,Double> scaling_factors, String url )
    {
        super(scaling_factors);

        this._n_rows = 1;
        this._n_cols = 1;

        this._url = url;
        Image _picture = retrieve_image(_url);
        set_dimensions((int) _picture.getWidth(),(int) _picture.getHeight());

        this._view = new ImageView(_picture);
    }

    /* Custom constructor for SpriteSheet with multiple views*/
    public Pictured_Object(Pair<Double,Double> scaling_factors, String url, int n_rows, int n_cols )
    {
        this(scaling_factors,url);

        this._n_rows = n_rows;
        this._n_cols = n_cols;

        set_dimensions(get_width()/_n_cols,get_height()/_n_rows);

    }

    // Create an image given an URL
    Image retrieve_image(String URL)
    {
        return new Image(URL);
    }

    // Scaling on x-axis and y-axis of images according to the resolution of the window
    void update_view() {
        this._view.setFitWidth( _scale * get_scaling_factors().getKey()  * get_width());
        this._view.setFitHeight(_scale * get_scaling_factors().getValue() * get_height());
        this._view.setPreserveRatio(false);
    }

    protected  int get_actual_height()
    {
        return (int) this._view.getFitHeight();
    }
    protected  int get_actual_width()
    {
        return (int) this._view.getFitWidth();
    }


    public Box get_hitbox(){ return new Box(get_current_Y_position(), get_current_X_position(),  get_actual_width() ,get_actual_height() );}

    public boolean intersect(Pictured_Object P2)
    {
        return this.get_hitbox().intersect(P2.get_hitbox());
    }


    public void default_movement(GameMap M){};
    public  void update(Sprite S){};


    public String get_url() {
        return _url;
    }

    public ImageView get_view() { return _view; }

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
    public String get_type() {
        return _type;
    }

    /* Setters */
    public void set_type(String _type) {
        this._type = _type;
    }
    public void set_scale(double _scale) {
        this._scale = _scale;
    }
    public void set_is_dead_property(boolean _isDead) {
        this._isDead.set(_isDead);
    }
}
