package units.shooter_developers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

interface Map_object_renderizable{
    void render();
}

interface Map_object_dynamic{
    void update(double t);
    public void setCoordinates(Pair<Integer, Integer> coordinates);
    public Pair<Integer, Integer> getCoordinates();
}

public class Map_object{
    private Pair<Integer, Integer> _coordinates;
    private ImageView _sprite;
    private Rectangle _hitbox;
    private int _width;
    private int _height;

    /********************************************************************************/
    /* CONSTRUCTORS                                                                 */
    /********************************************************************************/

    public Map_object(){
        this.setCoordinates(0, 0);
        this._sprite = null;
        this._hitbox = null;
        this._width = 800;
        this._width = 600;
    }

    public Map_object(int width, int height) {
        this();
        this._width = width;
        this._height = height;
    }

    public Map_object(Map_object map_object){
        _coordinates = map_object._coordinates;
        _sprite = map_object._sprite;
        _hitbox = map_object._hitbox;
        _width = map_object._width;
        _height = map_object._height;
    }

    /********************************************************************************/
    /* SET/GET METHODS                                                              */
    /********************************************************************************/

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

    public int getWidth(){
        return _width;
    }

    public int getHeight(){
        return _height;
    }
}
