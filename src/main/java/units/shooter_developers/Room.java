package units.shooter_developers;

import java.util.ArrayList;

import static java.lang.Math.floor;

public class Room implements Map_object_renderizable {
    private ArrayList<ArrayList<Block>> _block_matrix = new ArrayList<ArrayList<Block>>();
    private int _nrows;
    private int _ncols;
    private int _width;
    private int _height;
    private ArrayList<Map_object_dynamic> _dynamic_objects_list;

    Room(int width, int height, int nrows){
        this(width, height, 0, 0);
        _nrows = nrows;

        //_nrows is set to get blocks with square dimensions
        int height_per_block = (int) floor((double)_height/_nrows);
        int ncols = (int) floor(_width/height_per_block);
        _ncols = ncols;

        this.generateBlockMatrix();
        this.initializeBlocks();
    }

    Room(int width, int height, int nrows, int ncols){
        _width = width;
        _height = height;
        _nrows = nrows;
        _ncols = ncols; //square condition could break

        this.generateBlockMatrix();
        this.initializeBlocks();
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

    public void render(){
        return;
    }

    public Block getBlock(int row, int col){
        return _block_matrix.get(row).get(col);
    }

    public void setBlock(int row, int col, Block block){
        var blocks_row = _block_matrix.get(row);
        blocks_row.set(col, block);
        _block_matrix.set(row, blocks_row);
    }

    public int getBlockWidth(){
        return((int) floor((double)_width/_ncols));
    }

    public int getBlockHeight(){
        return((int) floor((double)_height/_nrows));
    }

    public int getNumberOfRows(){
        return _nrows;
    }

    public int getNumberOfColumns(){
        return _ncols;
    }

}
