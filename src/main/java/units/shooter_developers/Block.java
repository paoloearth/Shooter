package units.shooter_developers;

import javafx.scene.shape.Rectangle;
import javafx.util.Pair;

public class Block extends Map_object implements Map_object_renderizable{

    private boolean _passable;
    private boolean _affects_player;
    private Pair<Integer, Integer> _block_dimensions;

    public Block(int width, int height, Pair<Double, Double> block_dimensions_ratio){
        super(width, height);
        this.setPassable(true);
        this.setAffectsPlayer(false);

        this.setBlockDimensionsRatio(block_dimensions_ratio);
        Rectangle hitbox = new Rectangle(this.getWidth(), this.getHeight());
        this.setHitbox(hitbox);
    }

    public Block(int width, int height, double block_width_ratio, double block_height_ratio){
        this(width, height, new Pair<Double, Double>(block_width_ratio, block_height_ratio));
    }

    @Override
    public void render() {
        // -> not implement the rendering up to having a class for sprites!!
        return;
    }

    public void affect_player(Map_object player){
        // -> Here it should be modified the features of the player (velocity, damage, collision damage, etc.)
        // -> The signature of this function will change. It probably will have as argument a sort of Player-type object.
    }

    public void setBlockDimensionsRatio(Pair<Double, Double> block_dimensions_ratio){

        double block_width_ratio = block_dimensions_ratio.getKey();
        double block_height_ratio = block_dimensions_ratio.getValue();
        Pair<Integer, Integer> block_dimensions= new Pair<Integer, Integer>(
                (int)(block_width_ratio*this.getWidth()),
                (int)(block_height_ratio*this.getHeight()));
        _block_dimensions = block_dimensions;
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
        return _block_dimensions.getKey();
    }

    public int getBlockHeight(){
        return _block_dimensions.getValue();
    }

    public Pair<Integer, Integer> getBlockDimensions() {
        return _block_dimensions;
    }

    public void setAffectsPlayer(boolean affects_player){
        _affects_player = affects_player;
    }

    public boolean affectsPlayer(){
        return _affects_player;
    }

    public void setPassable(boolean passable){
        _passable = passable;
    }

    public boolean isPassable(){
        return _passable;
    }
}
