package units.shooter_developers;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.MissingResourceException;

import static java.lang.Math.abs;

public class Block extends Map_object implements Map_object_renderizable{

    private boolean _passable;
    private boolean _affects_player;
    private Pair<Integer, Integer> _block_dimensions;
    private ArrayList<Entity> _entity_list;
    private Room _room;

    /********************************************************************************/
    /* CONSTRUCTORS                                                                 */
    /********************************************************************************/

    public Block(){
        this(800, 600);
    }

    public Block(int width, int height){
        super(width, height);
        this.setPassable(true);
        this.setAffectsPlayer(false);
        _block_dimensions = new Pair<Integer, Integer>(width, height);
        this._entity_list = new ArrayList<Entity>();
        Rectangle hitbox = new Rectangle(this.getX(),  this.getY(), this.getBlockWidth(), this.getBlockHeight());
    }

    public Block(int width, int height, Pair<Double, Double> block_dimensions_ratio){
        this(width, height);
        this.setBlockDimensionsRatio(block_dimensions_ratio);
    }

    public Block(int width, int height, double block_width_ratio, double block_height_ratio){
        this(width, height, new Pair<Double, Double>(block_width_ratio, block_height_ratio));
    }

    /********************************************************************************/
    /* SET/GET ATTRIBUTES                                                           */
    /********************************************************************************/

    public void copyFrom(Block block){
        super.copyFrom(block);
        this._entity_list = block._entity_list;
        this._passable = block._passable;
        this._block_dimensions = block._block_dimensions;
        this._affects_player = block._affects_player;

    }

    public void setBlockDimensionsRatio(Pair<Double, Double> block_dimensions_ratio){

        double block_width_ratio = block_dimensions_ratio.getKey();
        double block_height_ratio = block_dimensions_ratio.getValue();

        if((block_width_ratio > 1) || (block_width_ratio < 0)){
            throw new IllegalArgumentException("ERROR: Width ratio provided ("+ block_width_ratio +") must be between 0 and 1.");
        } else if((block_height_ratio > 1) || (block_height_ratio < 0)){
            throw new IllegalArgumentException("ERROR: Height ratio provided ("+ block_height_ratio +") must be between 0 and 1.");
        }

        Pair<Integer, Integer> block_dimensions= new Pair<Integer, Integer>(
                (int)(block_width_ratio*this.getWidth()),
                (int)(block_height_ratio*this.getHeight()));
        _block_dimensions = block_dimensions;

        Rectangle new_hitbox = new Rectangle(this.getBlockWidth(), this.getBlockHeight());
        this.setHitbox(new_hitbox);
    }

    public void setBlockDimensionsRatio(double block_width_ratio, double block_height_ratio){
        Pair<Double, Double> block_dimensions_ratio = new Pair<Double, Double>(block_width_ratio, block_height_ratio);
        this.setBlockDimensionsRatio(block_dimensions_ratio);
    }

    public double getBlockWidthRatio(){
        return (double)getBlockWidth()/this.getWidth();
    }

    public double getBlockHeightRatio(){
        return (double)getBlockHeight()/this.getHeight();
    }

    public Pair<Double, Double> getBlockDimensionsRatio() {
        Pair<Double, Double> block_dimensions_ratio = new Pair<Double, Double>(getBlockWidthRatio(), getBlockHeightRatio());
        return block_dimensions_ratio;
    }

    public int getBlockWidth(){
        return getBlockDimensions().getKey();
    }

    public int getBlockHeight(){
        return getBlockDimensions().getValue();
    }

    public Pair<Integer, Integer> getBlockDimensions() {
        return _block_dimensions;
    }

    public void setPassable(boolean passable){
        _passable = passable;
    }

    public boolean isPassable(){
        return _passable;
    }

    public void setRoom(Room room) {
        _room = room;
    }

    public Room getRoom() {
        return this._room;
    }

    /********************************************************************************/
    /* OTHER                                                                        */
    /********************************************************************************/

    @Override
    public void render() {
        // -> not implement the rendering up to having a class for sprites!!
        return;
    }

    public void affect_player(Map_object player){
        // -> Here it should be modified the features of the player (velocity, damage, collision damage, etc.)
        // -> The signature of this function will change. It probably will have as argument a sort of Player-type object.
    }

    public void setAffectsPlayer(boolean affects_player){
        _affects_player = affects_player;
    }

    public boolean affectsPlayer(){
        return _affects_player;
    }

    public void addEntity(Entity object){
        _entity_list.add(object);
    }

    public void removeEntity(Entity object) throws MissingResourceException{
        if(!_entity_list.contains(object)) throw new MissingResourceException("Missing object in this block.", "Block", "");
        _entity_list.remove(object);
    }

    public ArrayList<Entity> getEntityList(){
        return _entity_list;
    }

    boolean isNeighbourOf(Block neighbour_candidate){
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

    boolean isNeighbourOf(Entity neighbour_candidate){

        var my_block_coordinates = this.getRoom().toBlockCoordinates(this.getCoordinates());
        var neighbour_block_coordinates = this.getRoom().toBlockCoordinates(this.getCoordinates());

        var diference_x = abs(my_block_coordinates.getKey() - neighbour_block_coordinates.getKey());
        var diference_y = abs(my_block_coordinates.getValue() - neighbour_block_coordinates.getValue());

        if((diference_x <= 1) && (diference_y <= 1))
            return true;
        else return false;

    }
}
