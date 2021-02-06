package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

interface Map_object_renderizable{
    void render();
}

interface Map_object_dynamic{
    void update(double delta_t);
    public void setCoordinates(Pair<Integer, Integer> coordinates);
    public Pair<Integer, Integer> getCoordinates();
}

public abstract class Map_object extends Pane {

    // Contains the PATH to the Image
    protected String _url;

    // Will store reference file for the image to render
    protected Image _picture;

    // Will store the current view
    protected ImageView _view;

    // Will store the map-object's WIDTH & HEIGHT
    protected  int _row_sheet;
    protected  int _columns_sheet;
    protected double _scale;
    protected int _width;
    protected int _height;

    // Will store the coordinates
    protected Pair<Integer, Integer> _coordinates;

    // Will store the type of the object
    protected String type;

    // Boolean dead;
    BooleanProperty dead = new SimpleBooleanProperty(false);

    private Rectangle _hitbox;


    /********************************************************************************/
    /* CONSTRUCTORS                                                                 */
    /********************************************************************************/

    public Map_object(String URL, double SCALE, int ROW_SHEET, 
                     int COLUMNS_SHEET, int WIDTH, int HEIGHT, 
                     Pair<Integer, Integer> COORDINATES)
    {
        this._height=HEIGHT;
        this._width = WIDTH;
        
        this._scale = SCALE;

        /* Read the picture*/
        this._picture = retrieve_image(URL, SCALE, HEIGHT ,WIDTH);

        /* Setting the parameters of the image */
        this._row_sheet = ROW_SHEET;
        this._columns_sheet = COLUMNS_SHEET;

        /* Setting the initial coordinates of the mapobject */
        set_coordinates(COORDINATES);

        this._view = new ImageView(this._picture);
    }

    protected  void set_coordinates(Pair<Integer, Integer> COORDINATES)
    {
        this.setLayoutX(COORDINATES.getKey());
        this.setLayoutY(COORDINATES.getValue());
    };

    public Map_object(){
        this.setCoordinates(0, 0);
        this._view = null;
        this._hitbox = null;
        this._width = 800;
        this._width = 600;
    }

    public Map_object(int width, int height) {
        this();
        this._width = width;
        this._height = height;
    }

    /********************************************************************************/
    /* FUNCTIONS                                                             */
    /********************************************************************************/
    Image retrieve_image(String URL, double SCALE, int HEIGHT, int WIDTH)
    {
        return new Image(URL,  WIDTH*SCALE,  HEIGHT*SCALE,  true,  false);
    }


    /********************************************************************************/
    /* SET/GET METHODS                                                              */
    /********************************************************************************/
    public void copyFrom(Map_object map_object){
        this._width = map_object._width;
        this._height = map_object._height;
        this._hitbox = map_object._hitbox;
        this._view = map_object._view;
        this._coordinates = map_object._coordinates;
    }

    public void setCoordinates(int x, int y) throws IllegalArgumentException {
        this.setCoordinates(new Pair<Integer, Integer>(x, y));
    }

    public void setCoordinates(Pair<Integer, Integer> coordinates){
        this._coordinates = coordinates;
        if(this.getHitbox() != null) {
            this._hitbox.setX(this.getX());
            this._hitbox.setY(this.getY());
        }
    }

    public Pair<Integer, Integer> getCoordinates(){
        return new Pair<Integer, Integer>(0,  0);
    }

    public int getX(){
        return _coordinates.getKey();
    }

    public int getY(){
        return _coordinates.getValue();
    }

    void setHitbox(Rectangle hitbox){
        hitbox.setX(this.getX());
        hitbox.setY(this.getY());
        this._hitbox = hitbox;
    }

    public Rectangle getHitbox(){
        return this._hitbox;
    }


}
