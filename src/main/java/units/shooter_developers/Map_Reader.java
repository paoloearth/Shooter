package units.shooter_developers;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/* TEMPLATE
       0. URL_spritesheet_mappa
       1. n_cols, n_rows, cell_size
       2. set_of_passable_blocks
       3. set_of_non_passable_block_for_projectiles
       4. sprite_1_blocco_x, sprite_1_blocco_y,sprite_2_blocco_x, sprite_2_blocco_y
*/
public class Map_Reader {

    Image _tileset;
    private final List<String[]> _lines;
    Integer _cell_side;
    Pair<Integer,Integer> _num_tiles;         //number of tiles of the map
    final Set<Integer> _set_of_passable;      //set of passable tiles for the Sprites

    // Constructor
    Map_Reader(String URL) throws IOException {

        _lines = extract_lines(URL);
        _tileset = get_tileset();
        _cell_side = get_cell_side();
        _num_tiles = get_num_of_tiles();
        _set_of_passable = get_tiles_at_row_index(2);

        //Lines representing the map
        //_map = _lines.stream().skip(Custom_Settings.NUMBER_OF_METADATA_LINES).collect(Collectors.toList());
    }

    private Set<Integer> get_tiles_at_row_index(int index) {
        return  Arrays.stream(_lines.get(index)).parallel().mapToInt(Integer::parseInt).boxed().collect(Collectors.toSet());
    }

    private Pair<Integer, Integer> get_num_of_tiles() {
        return new Pair<>(Integer.parseInt(_lines.get(1)[0]),Integer.parseInt(_lines.get(1)[1]));
    }

    private Image get_tileset() {
        return new Image(_lines.get(0)[0]);
    }

    List<String[]> extract_lines(String URL) throws IOException {
        File file = new File(getClass().getClassLoader().getResource(URL).getFile());
        return Files.lines(file.toPath()).parallel().map(l -> l.split(",")).collect(Collectors.toList());
    }
    public List<String[]> get_lines() {
        return _lines;
    }

    private Integer get_cell_side() {
        return  Integer.parseInt(_lines.get(1)[2]);
    }





}
