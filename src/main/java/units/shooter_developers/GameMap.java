package units.shooter_developers;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameMap {

    /* List of tiles that will be used to fast access the blocks property by indices*/
    List<Tile> _tiles = new ArrayList<>();

    /* List of passable blocks */
    List<Tile> _passable_tiles;

    /* JavaFX component to store and shows the tiles*/
    Pane _cells = new Pane();

    /* Width & Height of the tiles */
    private  double _width;
    private  double _height;

    /* Map_Reader object, delegated to interact with the files */
    Map_Reader _MR;

    /* URL to the map.csv */
    String _map_URL;
    Image _tileset;

    Integer _cell_side;
    Pair<Integer,Integer> _num_tiles;                              //number of tiles of the map
    Set<Integer> _set_of_passable;                           //set of passable tiles for the Player
    Set<Integer> _set_of_NOT_passable_for_projectile;
    List<String[]> _map;                                           // List of tiles composing the map
    Map<String,Coordinates> dictionary_position;

    GameMap(double width, double height,
            Image tileset, Integer cell_side,
            Pair<Integer,Integer> num_tiles,Set<Integer> set_of_passable,
            Set<Integer> set_of_NOT_passable_for_projectile, List<String[]> map) throws IOException {


        /* Set variables  */
      //  _MR = new Map_Reader(map_URL);
        _width = width;
        _height = height;
       // _map_URL = map_URL;
        dictionary_position = new HashMap<>();
        _tileset = tileset;
        _cell_side = cell_side;
        _num_tiles = num_tiles;
        _set_of_passable = set_of_passable;
        _set_of_NOT_passable_for_projectile = set_of_NOT_passable_for_projectile;
       _map =map;

        /* */
        this.populateCells();

        _passable_tiles = _tiles.stream().filter(b-> b.is_passable).collect(Collectors.toList());

      //  root.getChildren().add(_cells);

    }



    public void populateCells() {

        int tile_per_row = get_tiles_per_row_of_tileset();

        IntStream.range(0, _num_tiles.getKey()).mapToObj(i ->
                IntStream.range(0, _num_tiles.getValue()).mapToObj(j -> {
                    var code = Integer.parseInt(_map.get(j)[i]);

                    int pos_row = code / tile_per_row;
                    int pos_col = code % tile_per_row;

                    pos_row *= _cell_side;
                    pos_col *= _cell_side;

                    boolean passable = _set_of_passable.contains(code);
                    boolean not_passable_for_p = _set_of_NOT_passable_for_projectile.contains(code);

                    Rectangle2D R = new Rectangle2D(pos_col, pos_row, _cell_side,_cell_side);

                    var myblock = new Tile(i*getTileWidth(), j*getTileHeight(), getTileWidth(), getTileHeight(), passable,not_passable_for_p, _tileset, R);

                    // System.out.println("TILE [ " + i + "," + j + " ] is positioned at [ " +i*getTileWidth() + " , " + j*getTileHeight() + "] ");


                    return myblock;
                })
        ).flatMap(s -> s).forEach(_cells.getChildren()::add);

        this._tiles = _cells.getChildren().stream().parallel().map(s->(Tile) s).collect(Collectors.toList());

    }

    public Map<String, Coordinates> getDictionary_position() {
        return dictionary_position;
    }
    // Return the number of tiles in a row of the tileset image
    public int get_tiles_per_row_of_tileset() {
        return (int) (_tileset.getWidth() / _cell_side);
    }
    Coordinates get_position_of(String id) { return convert_tiles_in_pixel(getDictionary_position().get(id)); }

    //Given the coordinates of a tile
    Coordinates convert_tiles_in_pixel(Coordinates tile_coordinates)
    {
        return new Coordinates(tile_coordinates.getX()* getTileWidth(),
                                tile_coordinates.getY() * getTileHeight() );
    }
    public double get_width()  {
        return _width;
    }
    public double get_height() {
        return _height;
    }

    public double getTileWidth() { return _width/_num_tiles.getKey();   }
    public double getTileHeight(){ return _height/_num_tiles.getValue(); }

    int single_index(int x, int y)
    {
        return  (x * _num_tiles.getValue()) + y;
    }

    public List<Tile> get_tile_matrix() {
        return _tiles;
    }

    public Coordinates get_random_location(){
        int index = new Random().nextInt(_passable_tiles.size());
        return _passable_tiles.get(index).get_pixel_of_block_position();
    }
}
