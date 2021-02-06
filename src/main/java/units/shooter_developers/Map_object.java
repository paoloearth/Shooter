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
     void set_coordinates(Pair<Integer, Integer> coordinates);
    Pair<Integer, Integer> getCoordinates();
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

    public Map_object(String URL, double SCALE,
                      int WIDTH, int HEIGHT,
                      Pair<Integer, Integer> COORDINATES)
    {

        this._url = URL;

        this._height=HEIGHT;
        this._width = WIDTH;
        this._scale = SCALE;

        /* Read the picture*/
        this._picture = retrieve_image(URL, SCALE, HEIGHT ,WIDTH);

        /* Setting the initial coordinates of the mapobject */
        set_coordinates(COORDINATES);

        this._view = new ImageView(this._picture);

    }

    public Map_object(String URL, double SCALE,
                      int ROW_SHEET, int COLUMNS_SHEET,
                      int WIDTH, int HEIGHT,
                      Pair<Integer, Integer> COORDINATES)
    {
        this(URL,SCALE,WIDTH,HEIGHT,COORDINATES);

        /* Setting the parameters of the image */
        this._row_sheet = ROW_SHEET;
        this._columns_sheet = COLUMNS_SHEET;

    }

    public abstract void render();




    /********************************************************************************/
    /* FUNCTIONS                                                             */
    /********************************************************************************/
    Image retrieve_image(String URL, double SCALE, int HEIGHT, int WIDTH)
    {
        return new Image(URL,  WIDTH*SCALE,  HEIGHT*SCALE,  true,  false);
    }


    protected  void set_coordinates(Pair<Integer, Integer> COORDINATES)
    {
        this._coordinates = COORDINATES;

        if(this.getHitbox() != null) {
            this._hitbox.setX(this.getX());
            this._hitbox.setY(this.getY());
        }

        this.setLayoutX(COORDINATES.getKey());
        this.setLayoutY(COORDINATES.getValue());

    }

    protected  void set_coordinates(int x, int y)
    {
        this.set_coordinates(new Pair<>(x, y));
    }


    /********************************************************************************/
    /* SET/GET METHODS                                                              */
    /********************************************************************************/



    public Pair<Integer, Integer> getCoordinates(){
        return new Pair<>(getX(),  getY());
    }

    public int getX(){
        return (int) this.getLayoutX();
    }

    public int getY(){
        return (int) this.getLayoutY();
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
