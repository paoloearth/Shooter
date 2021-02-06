package units.shooter_developers;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.MissingResourceException;

import static java.lang.Math.abs;
import static java.lang.Math.floor;

public class Entity extends Map_object implements Map_object_renderizable, Map_object_dynamic{
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
        _room = null;
        _t = 0;
        this.set_coordinates(0, 0);
    }

    Entity(int width, int height, Room room){
        this(width, height);
        this.setRoom(room);
    }

    Entity(Entity object){
        //super(object);
        _room = object._room;
        _t = object._t;
        _velocity = object._velocity;

        this.getRoom().addEntity(this);
    }

    /********************************************************************************/
    /* PHYSICS AND MOVEMENT                                                         */
    /********************************************************************************/

    @Override
    public void set_coordinates(Pair<Integer, Integer> coordinates){
        if(this.getRoom() != null){
            if(this.getBlock() != null) {
                this.getBlock().removeEntity(this);
            }
        }
        super.set_coordinates(coordinates);
        if(this.getRoom() != null){
            if(this.getBlock() != null) {
                this.getBlock().addEntity(this);
            }
        }
    }

    @Override
    public void set_coordinates(int x, int y){
        this.set_coordinates(new Pair<Integer, Integer>(x, y));
    }

    public void update(double delta_t){
        Pair<Boolean, Boolean> legal_movements = new Pair<Boolean, Boolean>(true, true);

        int old_X = this.getX();
        int old_Y = this.getY();
        this.move(delta_t);
        int new_X = this.getX();
        int new_Y = this.getY();
        this.set_coordinates(old_X, old_Y);

        Pair<Integer, Integer> old_coordinates = new Pair<>(old_X, old_Y);
        Pair<Integer, Integer> new_coordinates = new Pair<>(new_X, new_Y);

        this.set_coordinates(new_X, new_Y);
        long collided = this.getRoom().getBlockMatrix().parallelStream()
                .flatMap(Collection::parallelStream)
                .filter(b -> b.isNeighbourOf(this))
                .filter(b -> !b.isPassable())
                .filter(b -> checkCollision(this))
                .count();
        if(collided > 0){
            this.set_coordinates(old_X, old_Y);
            return;
        }

        //implementacion con streams y sin lista de entidades
        this.set_coordinates(new_X, new_Y);
        collided = this.getRoom().getEntityList().parallelStream()
                .filter(e -> e.isNeighbourOf(this))
                .filter(e -> checkCollision(this))
                .count();

        if(collided > 0) this.set_coordinates(old_X, old_Y);

    }

    boolean isNeighbourOf(Entity neighbour_candidate){
        if(neighbour_candidate == this)
            return false;

        var my_block_coordinates = this.getRoom().toBlockCoordinates(this.getCoordinates());
        var neighbour_block_coordinates = this.getRoom().toBlockCoordinates(this.getCoordinates());

        var diference_x = abs(my_block_coordinates.getKey() - neighbour_block_coordinates.getKey());
        var diference_y = abs(my_block_coordinates.getValue() - neighbour_block_coordinates.getValue());

        if((diference_x <= 1) && (diference_y <= 1))
            return true;
        else return false;

    }

    boolean isNeighbourOf(Block neighbour_candidate){
        var my_block_coordinates = this.getRoom().toBlockCoordinates(this.getCoordinates());
        var neighbour_block_coordinates = this.getRoom().toBlockCoordinates(this.getCoordinates());

        var diference_x = abs(my_block_coordinates.getKey() - neighbour_block_coordinates.getKey());
        var diference_y = abs(my_block_coordinates.getValue() - neighbour_block_coordinates.getValue());

        if((diference_x <= 1) && (diference_y <= 1))
            return true;
        else return false;

    }

    private Pair<Boolean, Boolean> testCollisionInDirection(Rectangle target_hitbox,
                                             Pair<Integer, Integer> old_coordinates, Pair<Integer, Integer> new_coordinates,
                                             boolean consider_new_X, boolean consider_new_Y,
                                                            Pair<Boolean, Boolean> legal_movements){
        var hitbox = this.getHitbox();
        boolean legal_movement_X = legal_movements.getKey();
        boolean legal_movement_Y = legal_movements.getValue();

        hitbox.setX(old_coordinates.getKey());
        hitbox.setY(old_coordinates.getValue());

        var new_x = new_coordinates.getKey();
        var new_y = new_coordinates.getValue();

        if(consider_new_X) hitbox.setX(new_x);
        if(consider_new_Y) hitbox.setY(new_y);

        if(this.checkCollision(target_hitbox)){
            if(consider_new_X && consider_new_Y){
                legal_movement_X = false;
                legal_movement_Y = false;
            } else if(consider_new_X){
                legal_movement_X = false;
            } else if(consider_new_Y){
                legal_movement_Y = false;
            }
        }

        return new Pair<Boolean, Boolean>(legal_movement_X, legal_movement_Y);
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

    public void move(double delta_t){
        int coord_X = this.getX();
        int coord_Y = this.getY();
        coord_X += (int) (this.getVelocityX()*delta_t);
        coord_Y += (int) (this.getVelocityY()*delta_t);
        this.set_coordinates(coord_X, coord_Y);
    }

    public boolean checkCollision(Block target){
        Shape target_hitbox = target.getHitbox();
        return this.checkCollision(target.getHitbox());
    }

    public boolean checkCollision(Entity target){
        Shape target_hitbox = target.getHitbox();
        return this.checkCollision(target.getHitbox());
    }

    public boolean checkCollision(Shape target_hitbox){
        Shape my_hitbox = this.getHitbox();
        Shape intersection = Shape.intersect(target_hitbox, my_hitbox);
        return !intersection.getLayoutBounds().isEmpty();
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
        int col = (int) floor((double)this.getX()/this.getRoom().getBlockWidth());
        int row = (int) floor((double)this.getY()/this.getRoom().getBlockHeight());

        var coordinates = new Pair<Integer, Integer>(row, col);
        return coordinates;
    }

    public ArrayList<Block> getSurroundingBlocks(){
        if(_room == null) throw new MissingResourceException("Room not found!.", "Entity", "");

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
            throw new MissingResourceException("Room not found!.", "Entity", "");
        }

        var room_coordinates = this.computeRoomCoordinates();
        return this.getRoom().getBlock(room_coordinates);
    }

}
