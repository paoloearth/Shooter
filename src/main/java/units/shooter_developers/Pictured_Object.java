package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public abstract class Pictured_Object extends Map_Object {
    protected String _url;                // URL path to locate the spritesheet containing the pictures
    protected Image _picture;             // Store reference file for the image to render
    protected ImageView _view;            // Will store the current view

    protected int _n_rows;                // Number of rows  of the sprite-sheet
    protected int _n_cols;                // Number of columns of the sprite-sheet


    protected double _scale;              // Scale to make the loaded image of desired size

    // Will store the type of the object
    protected String _type;

    // Boolean dead;
    BooleanProperty _isDead = new SimpleBooleanProperty(false);

    //Custom constructor
    public Pictured_Object(Pair<Double,Double> scaling_factors, String url )
    {
        super(scaling_factors);

        // Setting the number of rows and columns
        this._n_rows = 1;
        this._n_cols = 1;

        // Setting the URL  of the image
        this._url = url;

        // Read the picture into the variable
        this._picture = retrieve_image(_url);

        // Set the view
        this._view = new ImageView(_picture);

        this._width   = (int) _picture.getWidth();
        this._height =  (int) _picture.getHeight();


    }

    /*
    Custom constructor for SpriteSheet with multiple views
      */
    public Pictured_Object(Pair<Double,Double> scaling_factors, String url, int n_rows, int n_cols )
    {
        this(scaling_factors,url);

        this._n_rows = n_rows;
        this._n_cols = n_cols;

        _width   = _width /  _n_cols;
        _height =  _height/ _n_rows;
    }

    // Create an image given an URL
    Image retrieve_image(String URL)
    {
        return new Image(URL);
    }

    // Scaling on x-axis and y-axis of images according to the resolution of the window
    void update_view() {
        this._view.setFitWidth( _scale * _scaling_factors.getKey()  * _width);
        this._view.setFitHeight(_scale * _scaling_factors.getValue() * _height);
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

    //Abstract method to be implemented that defines the bounds of a map object used to check collision
    public abstract Rectangle2D get_bounds();


}
