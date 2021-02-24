package units.shooter_developers;


import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.ceil;
import static java.lang.Math.floor;

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


        _passable_tiles = _tiles.stream().filter(b-> b.is_passable.getValue()).collect(Collectors.toList());


        root.getChildren().add(_cells);

    }

    private void populateCells() {

        int tile_per_row = _MR.get_tiles_per_row();

        IntStream.range(0, _MR._num_tiles.getKey()).mapToObj(i ->
                IntStream.range(0, _MR._num_tiles.getValue()).mapToObj(j -> {


                    var code = Integer.parseInt(_MR._map.get(j)[i]);

                    int pos_row = code / tile_per_row;
                    int pos_col = code % tile_per_row;

                    pos_row *= _MR._cell_side;
                    pos_col *= _MR._cell_side;

                    boolean passable = _MR._set_of_passable.contains(code);
                    boolean not_passable_for_p = _MR._set_of_NOT_passable_for_projectile.contains(code);

                    Rectangle2D R = new Rectangle2D(pos_col, pos_row, _MR._cell_side,_MR._cell_side);

                    var myblock = new Tile(i*getTileWidth(), j*getTileHeight(), getTileWidth(), getTileHeight(), passable,not_passable_for_p, _MR._tileset, R);

                   // System.out.println("TILE [ " + i + "," + j + " ] is positioned at [ " +i*getTileWidth() + " , " + j*getTileHeight() + "] ");


                    return myblock;
                })
        ).flatMap(s -> s).forEach(_cells.getChildren()::add);

        this._tiles = _cells.getChildren().stream().parallel().map(s->(Tile) s).collect(Collectors.toList());


    }


    Pair<Integer,Integer> get_player_pixel_position(String player_id)
    {
        if (player_id.equals("P1"))
            return  get_pixel_position(_MR._players_positions.getKey());
        else
            return get_pixel_position(_MR._players_positions.getValue());
    }

    //Given the coordinates of a tile
    Pair<Integer,Integer> get_pixel_position(Pair<Integer,Integer> tile_coordinates)
    {
        return new Pair<>(tile_coordinates.getKey()* getTileWidth(),tile_coordinates.getValue() * getTileHeight() );

    }


    public int get_width()  {
        return _width;
    }
    public int get_height() {
        return _height;
    }

    public int getTileWidth() {
       // System.out.println( "WIDTH TILE " +  (int) ceil((double)_width/_MR._num_tiles.getKey()));
        return((int) ceil((double)_width/_MR._num_tiles.getKey()));
       // return( _width/_MR._num_tiles.getKey());
    }
    public int getTileHeight(){
         // System.out.println("HEIGTH TILE " + (int) ceil((double)_height/_MR._num_tiles.getValue()));
         return((int) ceil((double)_height/_MR._num_tiles.getValue()));
        //  return(_height/_MR._num_tiles.getValue());

    }

    void set_value_at_index(int x, int y)
    {
        int i = single_index(x,y);
        Tile n = (Tile) _cells.getChildren().get(i);
        n.is_passable.set(false);
        _cells.getChildren().set(i,n);
    }

    int single_index(int x, int y)
    {
        return  (x * _MR._num_tiles.getValue()) + y;
    }

    public List<Tile> get_tile_matrix() {
        return _tiles;
    }

    public Pair<Integer,Integer> get_random_location(){
        int index = new Random().nextInt(_passable_tiles.size());

        return _passable_tiles.get(index).get_pixel_of_block_position();
    }




}
