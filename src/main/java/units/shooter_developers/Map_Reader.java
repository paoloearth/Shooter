package units.shooter_developers;
import javafx.scene.image.Image;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


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
    Map_Reader(){  }

    public GameMap read_Map(String URL, double width, double height) {
        GameMap M;
        try {
            _lines = extract_lines(URL);
        }
        catch(InvalidPathException e){
            System.out.println("The Path contains invalid character ");
        }
        catch(IOException e){
            System.out.println("Error with files "+e.toString());
        }
        catch(NullPointerException e){
            System.out.println("The file was not found ");
        }

            M = new GameMap(width, height, get_tileset(), get_cell_side(), get_row_and_column_num_of_tiles_composing_map(),
                    get_Set_of_tiles_at_row_index(2), get_Set_of_tiles_at_row_index(3), retrieve_map_without_metadata());
            fill_dictionary_position('P', 4, M.getDictionary_of_positions());
            fill_dictionary_position('T', 5, M.getDictionary_of_positions());
            return M;

    }

    List<String[]> extract_lines(String URL) throws InvalidPathException,IOException,NullPointerException {

        File file = new File(Objects.requireNonNull(getClass().getClassLoader().getResource(URL)).getFile());
        return Files.lines(file.toPath()).parallel().map(l -> l.split(Custom_Settings.FILE_SEPARATOR)).collect(Collectors.toList());
    }

    private String read_lines(int row, int col) throws IndexOutOfBoundsException{
        return _lines.get(row)[col];
    }

    private Image get_tileset() throws ArrayIndexOutOfBoundsException{
        String URL = " ";
        try {
           URL = read_lines(0, 0);

        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(" Error: Index out of bounds in _lines, URL not valid, fixed with default URL "+e.toString());
            URL = "TileSet.png";
        }
        return new Image(URL);
    }



    private Integer get_cell_side(){
      return  Integer.parseInt(read_lines(1,2));
    }

    // Return the number of tiles in the rows and columns in the map
    private Pair<Integer, Integer> get_row_and_column_num_of_tiles_composing_map() {
        return new Pair<>(Integer.parseInt(read_lines(1,0)),Integer.parseInt(read_lines(1,1)));
    }

    /* Reads a set of tiles at a specific row:
     * - in the 2nd row there is the set of passable tiles for the Sprites
     * - in the 3rd row there is the set of tiles not passable for the projectiles
     * */
    private Set<Integer> get_Set_of_tiles_at_row_index(int index) {
        return convertListToSet(get_list_of_integer_from_String(index));
    }

    private List<String[]> retrieve_map_without_metadata() {
        return _lines.stream().skip(Custom_Settings.NUMBER_OF_METADATA_LINES).collect(Collectors.toList());
    }

    public void fill_dictionary_position(char ID, int index, Map<String, Coordinates> dict){
        var l = get_list_of_integer_from_String(index);
        IntStream.range(0,l.size()).filter(i-> i%2 ==0).mapToObj(i ->
                new Pair<>(ID+String.valueOf(i/2),new Coordinates(l.get(i), l.get(i+1))))
                .forEach(pair->dict.put(pair.getKey(), pair.getValue()));
    }

    private List<Integer> get_list_of_integer_from_String(int index) {
        return Arrays.stream(_lines.get(index)).parallel().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    }

    public static Set<Integer> convertListToSet(List<Integer> list)
    {
        return new HashSet<>(list);
    }

}
