package units.shooter_developers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

interface Map_object_renderizable{
    void render();
}

interface Map_object_dynamic{
    void update(double t);
}

public class Map_object {
    private Pair<Integer, Integer> _coordinates;
    private ImageView _sprite;
    private Shape _hitbox;
    private int _width;
    private int _height;
    private double _sprite_width_ratio;
    private double _sprite_height_ratio;

    public Map_object(int width, int height) {
        this._width = width;
        this._height = height;
        this.setCoordinates(0, 0);
        this._sprite = null;
        this.setHitbox(null);
    }

    public void setCoordinates(int x, int y) throws IllegalArgumentException {
        this.setCoordinates(new Pair<Integer, Integer>(x, y));
    }

    public void setCoordinates(Pair<Integer, Integer> coordinates) throws IllegalArgumentException{
        int x = coordinates.getKey();
        int y = coordinates.getValue();

        if(x<0 || x>=_width){
            throw new IllegalArgumentException("X coordinate (" + Integer.toString(x) + ") must be positive and lower than width (" + Integer.toString(_width) + ")");
        } else if (y<0 || y>=_height){
            throw new IllegalArgumentException("Y coordinate (" + Integer.toString(y) + ") must be positive and lower than height (" + Integer.toString(_height) + ")");
        }

        this._coordinates = coordinates;
    }

    public Pair<Integer, Integer> get_coordinates(){
        return new Pair<Integer, Integer>(0,  0);
    }

    public int getX(){
        return _coordinates.getKey();
    }

    public int getY(){
        return _coordinates.getValue();
    }

    void setHitbox(Shape hitbox){
        this._hitbox = hitbox;
    }

    public Shape getHitbox(){
        return this._hitbox;
    }

    public void setSprite(String path){
        Image image = new Image(path);
        this._sprite = new ImageView(image);
    }

    public void setSprite(String path, double sprite_width_ratio, double sprite_height_ratio){
        Image image = new Image(path);
        this._sprite = new ImageView(image);
        this.resizeSprite(sprite_width_ratio, sprite_height_ratio);
    }

    public void resizeSprite(double sprite_width_ratio, double sprite_height_ratio){
        _sprite.resize(sprite_width_ratio*_width, sprite_height_ratio*_height);
        return;
    }

    public int getWidth(){
        return _width;
    }

    public int getHeight(){
        return _height;
    }

    public ImageView getSprite(){
        return this._sprite;
    }
}
