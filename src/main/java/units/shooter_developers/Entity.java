package units.shooter_developers;

import com.sun.javafx.geom.Area;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

import java.util.ArrayList;

import static java.lang.Math.floor;

public class Entity extends Map_object implements Map_object_renderizable, Map_object_dynamic{
    private Pair<Integer, Integer> _block_dimensions;
    private Room _room;
    private Pair<Integer, Integer> _velocity;
    private double _t;

    Entity(int width, int height){
        super(width, height);
        _block_dimensions = null;
        _room = null;
        _t = 0;
    }

    Entity(int width, int height, Pair<Integer, Integer> block_dimensions){
        this(width, height);
        _block_dimensions = block_dimensions;
    }

    Entity(int width, int height, Room room){
        this(width, height);
        this.setRoom(room);
    }

    Entity(int width, int height, Pair<Integer, Integer> block_dimensions, Room room){
        this(width, height, room);
        _block_dimensions = block_dimensions;
    }

    Entity(Entity object){
        super(object);
        _block_dimensions = object._block_dimensions;
        _room = object._room;
        _t = object._t;
        _velocity = object._velocity;
    }

    public void render(){
        return;
    }

    public void update(double t){
        boolean legal_movement_X = true;
        boolean legal_movement_Y = true;

        Entity copy = new Entity(this);
        int old_X = copy.getX();
        int old_Y = copy.getY();
        copy.move(t);
        int new_X = copy.getX();
        int new_Y = copy.getY();

        var a = this.getSurroundingBlocks();
        for(Block block:this.getSurroundingBlocks()){
            if(!block.isPassable()){

                copy.setCoordinates(new_X, old_Y);
                if(copy.checkCollision(block)){
                    legal_movement_X = false;
                }
                copy.setCoordinates(old_X, new_Y);
                if(copy.checkCollision(block)){
                    legal_movement_Y = false;
                }

            } else {
                var dynamic_objects_list = block.getDynamicObjectList();
                for(var dynamic_object : dynamic_objects_list){
                    if((legal_movement_X || legal_movement_Y) && !this.equals(dynamic_object)){
                        copy.setCoordinates(new_X, old_Y);
                        if(copy.checkCollision(dynamic_object)){
                            legal_movement_X = false;
                        }
                        copy.setCoordinates(old_X, new_Y);
                        if(copy.checkCollision(dynamic_object)){
                            legal_movement_Y = false;
                        }
                    }
                }
            }
        }

        if(!legal_movement_X) new_X = old_X;
        if(!legal_movement_Y) new_Y = old_Y;
        this.setCoordinates(new_X, new_Y);
        this._t = copy._t;
    }

    public void setVelocity(int velocityX, int velocityY){
        Pair<Integer, Integer> velocity = new Pair<Integer, Integer>(velocityX, velocityY);
        this.setVelocity(velocity);
    }

    public void setVelocity(Pair<Integer, Integer> velocity){
        _velocity = velocity;
    }

    public int getVelocityX(){
        return _velocity.getKey();
    }

    public int getVelocityY(){
        return _velocity.getValue();
    }

    public Pair<Integer, Integer> getVelocity(){
        return _velocity;
    }

    public void setRoom(Room room){
        _room = room;
    }

    public Room getRoom(){
        return _room;
    }

    public Pair<Integer, Integer> getRoomCoordinates(){
        int coord_x = (int) floor(this.getX()/this.getRoom().getBlockWidth());
        int coord_y = (int) floor(this.getY()/this.getRoom().getBlockHeight());

        var coordinates = new Pair<Integer, Integer>(coord_x, coord_y);
        return coordinates;
    }

    public ArrayList<Block> getSurroundingBlocks(){
        ArrayList<Block> surrounding_blocks= new ArrayList<Block>();

        var my_coordinates = this.getRoomCoordinates();
        int my_x = my_coordinates.getKey();
        int my_y = my_coordinates.getValue();

        for(int i=-1; i<=1; i++){
            for(int j=-1; j<=1; j++){
                int neighbor_x = my_x+i;
                int neighbor_y = my_y+j;
                if(neighbor_x >= 0 && neighbor_x < this.getRoom().getNumberOfRows()){
                    if(neighbor_y >= 0 && neighbor_y < this.getRoom().getNumberOfColumns()){
                        surrounding_blocks.add(this.getRoom().getBlock(neighbor_x, neighbor_y));
                    }
                }
            }
        }

        return surrounding_blocks;
    }

    public void move(double t){
        int coord_X = this.getX();
        int coord_Y = this.getY();
        double delta_t = t-_t;
        coord_X += (int) (this.getVelocityX()*delta_t);
        coord_Y += (int) (this.getVelocityY()*delta_t);
        this.setCoordinates(coord_X, coord_Y);
        this._t = t;
    }

    public boolean checkCollision(Block target){
        Shape target_hitbox = target.getHitbox();
        Shape my_hitbox = this.getHitbox();
        Shape intersection = Shape.intersect(target_hitbox, my_hitbox);
        return !intersection.getLayoutBounds().isEmpty();
    }

    public boolean checkCollision(Map_object_dynamic target){
        return false;
    }
}
