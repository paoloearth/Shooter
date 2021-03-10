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
    private List<Tile> _tiles = new ArrayList<>();

    /* List of passable blocks */
    private final List<Tile> _passable_tiles;

    /* JavaFX component to store and shows the tiles*/
    private Pane _cells = new Pane();

    /* Width & Height of the tiles */
    private final double _width;
    private final double _height;
    private final Image _tileset;
    private final Integer _cell_side;
    private final Pair<Integer,Integer> _num_tiles;                              //number of tiles of the map
    private final Set<Integer> _values_of_passable_tiles;                                 //set of passable tiles for the Player
    private final Set<Integer> _values_of_NOT_passable_tiles_for_projectile;
    private final List<String[]> _map;                                           // List of tiles composing the map
    private final Map<String,Coordinates> dictionary_of_positions;

    GameMap(double width, double height,
            Image tileset, Integer cell_side,
            Pair<Integer,Integer> num_tiles,Set<Integer> set_of_passable,
            Set<Integer> set_of_NOT_passable_for_projectile, List<String[]> map){
        
        /* Set variables  */
        _width = width;
        _height = height;
        dictionary_of_positions = new HashMap<>();
        _tileset = tileset;
        _cell_side = cell_side;
        _num_tiles = num_tiles;
        _values_of_passable_tiles = set_of_passable;
        _values_of_NOT_passable_tiles_for_projectile = set_of_NOT_passable_for_projectile;
        _map =map;

        this.populateCells();

        _passable_tiles = _tiles.stream().filter(b-> b.is_passable).collect(Collectors.toList());
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

                    boolean passable = _values_of_passable_tiles.contains(code);
                    boolean not_passable_for_p = _values_of_NOT_passable_tiles_for_projectile.contains(code);

                    Rectangle2D R = new Rectangle2D(pos_col, pos_row, _cell_side,_cell_side);

                    return new Tile(i*getTileWidth(), j*getTileHeight(), getTileWidth(),
                                         getTileHeight(), passable,not_passable_for_p, _tileset, R);
                })
        ).flatMap(s -> s).forEach(_cells.getChildren()::add);

        this._tiles = _cells.getChildren().stream().parallel().map(s->(Tile) s).collect(Collectors.toList());

    }

    public Map<String, Coordinates> getDictionary_of_positions() {
        return dictionary_of_positions;
    }
    // Return the number of tiles in a row of the tileset image
    public int get_tiles_per_row_of_tileset() {
        return (int) (_tileset.getWidth() / _cell_side);
    }
    Coordinates get_position_of(String id) { return convert_tiles_in_pixel(getDictionary_of_positions().get(id)); }

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

    public Coordinates getRandomLocation(){
        int index = new Random().nextInt(_passable_tiles.size());
        return _passable_tiles.get(index).get_pixel_of_block_position();
    }
    public Pane get_cells() {
        return _cells;
    }
}
