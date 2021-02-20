package units.shooter_developers;


import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.ceil;

public class Map {

    /* List of tiles that will be used to fast access the blocks property by indices*/
    private List<Tile> _tiles = new ArrayList<>();

    /* List of passable blocks */
    List<Tile> _passable_tiles;

    /* JavaFX component to store and shows the tiles*/
    Pane _cells = new Pane();

    /* Width & Height of the tiles */
    private  int _width;
    private  int _height;

    /* Map_Reader object, delegated to interact with the files */
    Map_Reader _MR;

    /* URL to the map.csv */
    String _map_URL;

    Map(Pane root, String map_URL, int width, int height) throws IOException {

        /* Set variables  */
        _MR = new Map_Reader(map_URL);
        _width = width;
        _height = height;
        _map_URL = map_URL;

        /* */
        this.populateCells();

        root.getChildren().add(_cells);

    }

    private void populateCells() {

        /* Used to get the right position */
        int n_cols = _MR._num_tiles.getKey();
        int n_rows = _MR._num_tiles.getValue();

        int flat_length = n_cols * n_rows;

        /* Used to get the tile sprite */
        int tile_per_row = _MR.get_tiles_per_row();


        IntStream.range(0, flat_length).forEach(index -> {

                                  /* From single index to double */
                                   var i = index / n_cols;
                                   var j = index % n_cols;

                                   /* Read cell of the code */
                                   var code = Integer.parseInt(_MR._map.get(i)[j]);

                                   /* From single index to double */
                                   int pos_row = code / tile_per_row;
                                   int pos_col = code % tile_per_row;

                                   /* Compute final position */
                                   pos_row *= _MR._cell_side;
                                   pos_col *= _MR._cell_side;

                                   /* Set property of the node */
                                   boolean passable = _MR._set_of_passable.contains(code);
                                   boolean not_passable_for_p = _MR._set_of_NOT_passable_for_projectile.contains(code);

                                    /* Picture the right tile on tilese of the map */
                                   Rectangle2D R = new Rectangle2D(pos_col, pos_row, _MR._cell_side,_MR._cell_side);

                                    /* Picture the right tile on tileset */
                                   _tiles.add(  new Tile(j*getBlockWidth(), i*getBlockHeight(),
                                                               getBlockWidth(), getBlockHeight(),
                                                               passable,not_passable_for_p, _MR._tileset, R));

                                    });

        /* Picture the right tile on tileset */
        _tiles.forEach(tile -> _cells.getChildren().add(tile));

    }

    public int get_width() {
        return _width;
    }
    public int get_height() {
        return _height;
    }
    public int getBlockWidth() {
        return( _width/_MR._num_tiles.getKey());
    }

    public int getBlockHeight(){
        return(_height/_MR._num_tiles.getValue());
    }



}
