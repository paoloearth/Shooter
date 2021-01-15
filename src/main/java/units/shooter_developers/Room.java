package units.shooter_developers;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.MissingResourceException;

import static java.lang.Math.floor;

public class Room implements Map_object_renderizable {
    private ArrayList<ArrayList<Block>> _block_matrix = new ArrayList<ArrayList<Block>>();
    private int _nrows;
    private int _ncols;
    private int _width;
    private int _height;
    private ArrayList<Entity> _entity_list;

    /********************************************************************************/
    /* CONSTRUCTORS                                                                 */
    /********************************************************************************/

    Room(){
        this.setWidth(800);
        this.setHeight(600);
        this._nrows = 0;
        this._ncols = 0;
        this._entity_list = new ArrayList<Entity>();
    }

    Room(int width, int height){
        this();
        _width = width;
        _height = height;
    }

    Room(int width, int height, int nrows){
        this(width, height);
        _nrows = nrows;

        //_nrows is set to get blocks with square dimensions
        int height_per_block = (int) floor((double)_height/_nrows);
        int ncols = (int) floor(_width/height_per_block);
        _ncols = ncols;

        this.generateBlockMatrix();
        this.initializeBlocks();
    }

    Room(int width, int height, int nrows, int ncols){
        this(width, height);
        _nrows = nrows;
        _ncols = ncols; //square condition could break

        this.generateBlockMatrix();
        this.initializeBlocks();
    }

    /********************************************************************************/
    /* BLOCKS AND ENTITIES MANAGEMENT                                               */
    /********************************************************************************/

    public Pair<Integer, Integer> toBlockCoordinates(Pair<Integer, Integer> coordinates){
        int x = coordinates.getKey();
        int y = coordinates.getValue();
        x = (int) floor((double)x/this.getBlockWidth());
        y = (int) floor((double)y/this.getBlockHeight());
        return(new Pair<Integer, Integer>(x, y));
    }

    public void addEntity(Entity object) throws  MissingResourceException{
        this._entity_list.add(object);
        var object_block_coordinates = this.toBlockCoordinates(object.getCoordinates());
        this.getBlock(object_block_coordinates).addEntity(object);
    }

    public void removeEntity(Entity object){
        //if(!_entity_list.contains(object)) throw new MissingResourceException("Missing object in this room.", "Entity", "");
        var object_block_coordinates = this.toBlockCoordinates(object.getCoordinates());
        this.getBlock(object_block_coordinates).removeEntity(object);
        this._entity_list.remove(object);
    }

    private void generateBlockMatrix(){
        for(int i=0; i<_nrows; i++){
            ArrayList<Block> row = new ArrayList<Block>();
            for(int j=0; j<_ncols; j++){
                row.add(null);
            }
            _block_matrix.add(row);
        }
    }

    private void initializeBlocks(){
        for(int i=0; i<_nrows; i++){
            for(int j=0; j<_ncols; j++){
                Block block = new Block(_width, _height, 1./_nrows, 1./_ncols);
                int coord_X = j*this.getBlockWidth();
                int coord_Y = i*this.getBlockHeight();
                block.setCoordinates(coord_X, coord_Y);
                this.setBlock(i, j, block);
            }
        }
    }

    public void setBlock(int row, int col, Block block){
        var blocks_row = _block_matrix.get(row);
        blocks_row.set(col, block);
        _block_matrix.set(row, blocks_row);
    }

    public Block getBlock(Pair<Integer, Integer> block_coordinates){
        int row = block_coordinates.getKey();
        int col = block_coordinates.getValue();
        if(((row >= _nrows-1) || (col >= _ncols-1)) || ((row < 0) || (col < 0))){
            return null;
        }
        return _block_matrix.get(row).get(col);
    }

    public Block getBlock(int row, int col){
        var block_coordinates = new Pair<Integer, Integer>(row, col);
        return this.getBlock(block_coordinates);
    }

    public int getBlockWidth(){
        return((int) floor((double)_width/_ncols));
    }

    public int getBlockHeight(){
        return((int) floor((double)_height/_nrows));
    }

    /********************************************************************************/
    /* OTHER                                                                        */
    /********************************************************************************/

    public void render(){
        return;
    }

    public int getNumberOfRows(){
        return _nrows;
    }

    public int getNumberOfColumns(){
        return _ncols;
    }

    public void setWidth(int width){
        this._width = width;
    }

    public void setHeight(int height){
        this._height = height;
    }

    public int getWidth(){
        return this._width;
    }

    public int getHeight(){
        return this._height;
    }

}
