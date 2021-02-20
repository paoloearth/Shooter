package units.shooter_developers;


import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

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

    }

    private void populateCells() {

        int LENGTH = 100;
        int tile_per_row = 50;

        IntStream.range(0, 100).parallel()
                               .forEach(index -> {

                                   var i = index / tile_per_row;
                                   int j = index % tile_per_row;

                                  // var code = Integer.parseInt(_MR._map.get(j)[i]);

                                                });

                                  //  1) Leggere la cella della matrice codici (i, j)
                                  //  var code = Integer.parseInt(_MR._map.get(j)[i]);

    }


}
