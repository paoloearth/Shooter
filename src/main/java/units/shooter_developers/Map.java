package units.shooter_developers;


import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    }


}
