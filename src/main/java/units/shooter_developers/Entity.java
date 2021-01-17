package units.shooter_developers;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.MissingResourceException;

import static java.lang.Math.floor;

public class Entity extends Map_object implements Map_object_renderizable, Map_object_dynamic{
    private Pair<Integer, Integer> _block_dimensions;
    private Room _room;
    private Pair<Integer, Integer> _velocity;
    private double _t;

    /********************************************************************************/
    /* CONSTRUCTORS                                                                 */
    /********************************************************************************/

    Entity(){
        this(800, 60);
    }

    Entity(int width, int height){
        super(width, height);
        _block_dimensions = null;
        _room = null;
        _t = 0;
        this.setCoordinates(0, 0);
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

        this.getRoom().addEntity(this);
    }

    /********************************************************************************/
    /* PHYSICS AND MOVEMENT                                                         */
    /********************************************************************************/

    @Override
    public void setCoordinates(Pair<Integer, Integer> coordinates){
        if(this.getRoom() != null){
            if(this.getBlock() != null) {
                this.getBlock().removeEntity(this);
            }
        }
        super.setCoordinates(coordinates);
        if(this.getRoom() != null){
            if(this.getBlock() != null) {
                this.getBlock().addEntity(this);
            }
        }
    }

    @Override
    public void setCoordinates(int x, int y){
        this.setCoordinates(new Pair<Integer, Integer>(x, y));
    }

    public void update(double t){
        boolean legal_movement_X = true;
        boolean legal_movement_Y = true;

        int old_X = this.getX();
        int old_Y = this.getY();
        this.move(t);
        int new_X = this.getX();
        int new_Y = this.getY();
        this.setCoordinates(old_X, old_Y);

        Rectangle hitbox = this.getHitbox();

        for(Block block:this.getSurroundingBlocks()){
            if(!block.isPassable()){

                hitbox.setX(new_X);
                hitbox.setX(old_Y);
                if(this.checkCollision(block)){
                    legal_movement_X = false;
                }
                hitbox.setX(old_X);
                hitbox.setY(new_Y);
                if(this.checkCollision(block)){
                    legal_movement_Y = false;
                }
                hitbox.setX(new_X);
                hitbox.setY(new_Y);
                if(this.checkCollision(block)){
                    legal_movement_X = false;
                    legal_movement_Y = false;
                }

            } else {
                var entity_list = block.getEntityList();
                var entity_iterator = entity_list.iterator();
                while(entity_iterator.hasNext()){
                    var entity = entity_iterator.next();
                    if((legal_movement_X || legal_movement_Y) && !this.equals(entity)){
                        hitbox.setX(new_X);
                        hitbox.setX(old_Y);
                        if(this.checkCollision(entity)){
                            legal_movement_X = false;
                        }
                        hitbox.setX(old_X);
                        hitbox.setY(new_Y);
                        if(this.checkCollision(entity)){
                            legal_movement_Y = false;
                        }
                        hitbox.setX(new_X);
                        hitbox.setY(new_Y);
                        if(this.checkCollision(entity)){
                            legal_movement_X = false;
                            legal_movement_Y = false;
                        }
                    }
                }
            }
        }

        if(!legal_movement_X) new_X = old_X;
        if(!legal_movement_Y) new_Y = old_Y;
        this.setCoordinates(new_X, new_Y);
        this._t = t;
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

    public boolean checkCollision(Shape target_hitbox){
        Shape my_hitbox = this.getHitbox();
        Shape intersection = Shape.intersect(target_hitbox, my_hitbox);
        return !intersection.getLayoutBounds().isEmpty();
    }

    public boolean checkCollision(Map_object_dynamic target){
        return false;
    }

    public Pair<Integer, Integer> getVelocity(){
        return _velocity;
    }

    /********************************************************************************/
    /* OTHER                                                                        */
    /********************************************************************************/

    public void render(){
        return;
    }

    public void setRoom(Room room){
        room.addEntity(this);
        _room = room;
    }

    public Room getRoom(){
        return _room;
    }

    public Pair<Integer, Integer> computeRoomCoordinates(){
        int col = (int) floor(this.getX()/this.getRoom().getBlockWidth());
        int row = (int) floor(this.getY()/this.getRoom().getBlockHeight());

        var coordinates = new Pair<Integer, Integer>(row, col);
        return coordinates;
    }

    public ArrayList<Block> getSurroundingBlocks(){
        ArrayList<Block> surrounding_blocks= new ArrayList<Block>();

        var my_coordinates = this.computeRoomCoordinates();
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

    public Block getBlock(){
        if(_room == null){
            throw new MissingResourceException("Room is not defined!.", "Entity", "");
        }

        var room_coordinates = this.computeRoomCoordinates();
        return this.getRoom().getBlock(room_coordinates);
    }

}
