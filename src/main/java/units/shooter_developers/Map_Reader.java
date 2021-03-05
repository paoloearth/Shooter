package units.shooter_developers;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;


/* TEMPLATE
       0. URL_spritesheet_mappa
       1. n_cols, n_rows, cell_size
       2. set_of_passable_blocks
       3. set_of_non_passable_block_for_projectiles
       4. sprite_1_blocco_x, sprite_1_blocco_y,sprite_2_blocco_x, sprite_2_blocco_y
       5. Teleport_1_x;Teleport_1_y; Teleport_2_x, Teleport_2_y
*/
public class Map_Reader {

    private  List<String[]> _lines;


    // Constructor
    Map_Reader() throws IOException {


    }

    public GameMap read_Map(String URL, double width, double height) throws IOException {
        var M = new GameMap(width,height, get_tileset(),get_cell_side(),get_num_of_tiles(),
                            get_tiles_at_row_index(2),get_tiles_at_row_index(3),retrieve_map_without_metadata());
        _lines = extract_lines(URL);

        create_dictionary_position('P', 4, M.getDictionary_position());
        create_dictionary_position('T', 5, M.getDictionary_position());
        M._passable_tiles= M._tiles.stream().filter(b-> b.is_passable).collect(Collectors.toList());
        return M;
    }

    private List<String[]> retrieve_map_without_metadata() {
        return _lines.stream().skip(Custom_Settings.NUMBER_OF_METADATA_LINES).collect(Collectors.toList());
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




    // Return the number of tiles in the rows and columns in the map
    private Pair<Integer, Integer> get_num_of_tiles() {
        return new Pair<>(Integer.parseInt(_lines.get(1)[0]),Integer.parseInt(_lines.get(1)[1]));
    }

    /* Reads a set of tiles at a specific row:
     * - in the 2nd row there is the set of passable tiles for the Sprites
     * - in the 3rd row there is the set of tiles not passable for the projectiles
     * */
    private Set<Integer> get_tiles_at_row_index(int index) {
        return  Arrays.stream(_lines.get(index)).parallel().mapToInt(Integer::parseInt).boxed().collect(Collectors.toSet());
    }

    /*
     Reads the pair of positions of:
      - the players at row 4th
      - teleports at row 5th
     */
    private Coordinates[] get_positions(int index) {
        var values = Arrays.stream(_lines.get(index)).parallel().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
        return new Coordinates[]{new Coordinates(values.get(0), values.get(1)),new Coordinates(values.get(2), values.get(3))};
    }

    public void create_dictionary_position(char ID, int index, Map<String, Coordinates> dict){
        var l = Arrays.stream(_lines.get(index)).parallel().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());;
        //IntStream.range(1,l.size()).mapToObj(i -> new Coordinates(l.get(i-1), l.get(i))).forEach();
        for(int i= 0; i < l.size(); i+=2){
            Coordinates coord = new Coordinates(l.get(i), l.get(i+1));
            String key = ID+ String.valueOf(i/2);
           dict.put(key, coord);
        }

    }














}
