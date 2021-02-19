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
    private List<String[]> _lines;

    // Constructor
    Map_Reader(String URL) throws IOException {

        _lines = extract_lines(URL);
        _tileset = get_tileset();

        //Lines representing the map
        //_map = _lines.stream().skip(Custom_Settings.NUMBER_OF_METADATA_LINES).collect(Collectors.toList());
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



}
