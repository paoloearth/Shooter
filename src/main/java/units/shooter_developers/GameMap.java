package units.shooter_developers;


import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameMap {

    /* List of tiles that will be used to fast access the blocks property by indices*/
    private List<Tile> tiles = new ArrayList<>();

    /* List of passable blocks */
    private final List<Tile> passableTiles;

    Pane cells = new Pane();


    private final double _width;
    private final double _height;
    private final int _columns;
    private final int _rows;


    private final Map<String,Coordinates> playerAndBonusPositions;


    public GameMap(double width, double height,
                   Image tileSet, int cellSide,
                   int columns, int rows,
                   Set<Integer> passableCodes, Set<Integer> unpassableCodes,
                   List<String[]> mapTileComposition,
                   Map<String, Coordinates> coordinateDictionary)
    {

        _width = width;
        _height = height;

        _columns = columns;
        _rows = rows;

        playerAndBonusPositions = coordinateDictionary;

        generateMap(columns,rows,cellSide,mapTileComposition, passableCodes, unpassableCodes, tileSet);

        passableTiles = tiles.stream().filter(b-> b.is_passable).collect(Collectors.toList());

    }

    /* Core function */
    public void generateMap(int horizontalCells, int verticalCells, int cellSide,
                            List<String[]> mapTileComposition,
                            Set<Integer> passableCodes,Set<Integer> unpassableCodes,
                            Image tileSet
                            )
    {
        int  tilePerRow = (int) (tileSet.getWidth()/ cellSide);

        IntStream.range(0,horizontalCells ).mapToObj(i ->
                IntStream.range(0, verticalCells).mapToObj(j -> {

                    var code = Integer.parseInt(mapTileComposition.get(j)[i]);

                    int pos_row = code / tilePerRow;
                    int pos_col = code % tilePerRow;

                    pos_row *= cellSide;
                    pos_col *= cellSide;

                    boolean passable = passableCodes.contains(code);
                    boolean not_passable_for_p = unpassableCodes.contains(code);

                    Rectangle2D R = new Rectangle2D(pos_col, pos_row, cellSide, cellSide);

                    return new Tile(i*getTileWidth(), j*getTileHeight(), getTileWidth(),
                                         getTileHeight(), passable,not_passable_for_p,tileSet, R);

                })
        ).flatMap(s -> s).forEach(cells.getChildren()::add);

        tiles = cells.getChildren().stream().parallel().map(s->(Tile) s).collect(Collectors.toList());

    }


    /* Utils */
    Coordinates get_position_of(String id) { return convert_tiles_in_pixel(getPlayerAndBonusPositions().get(id)); }

    Coordinates convert_tiles_in_pixel(Coordinates tile_coordinates)
    {
        return new Coordinates(tile_coordinates.getX()* getTileWidth(),
                               tile_coordinates.getY() * getTileHeight() );
    }

    int single_index(int x, int y) {  return  (x * _rows) + y;  }

    public double getTileWidth() { return _width/ _columns;   }

    public double getTileHeight(){ return _height/ _rows; }

    public Coordinates getRandomLocation(){
        int index = new Random().nextInt(passableTiles.size());
        return passableTiles.get(index).get_pixel_of_block_position();
    }

    /* Getters  */
    public List<Tile> get_tile_matrix() { return tiles;  }
    public Map<String, Coordinates> getPlayerAndBonusPositions() { return playerAndBonusPositions; }
    public Pane getCells() { return cells; }
    public double get_width()  { return _width; }
    public double get_height() { return _height; }

}
