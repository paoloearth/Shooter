package units.shooter_developers;
import javafx.scene.image.Image;
import javafx.util.Pair;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


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

        try { _lines = extract_lines(URL); }
        catch(InvalidPathException e){ System.out.println(URL + ": path contains invalid characters"); }
        catch(FileNotFoundException e){ System.out.println(URL + ": was not found "); }
        catch(IOException e){ System.out.println(URL + ": problems interacting with the file "); }
        catch(NullPointerException e){ System.out.println("File is null ");}
        catch(URISyntaxException e){ System.out.println("Wrong URL format ");}

        M = new GameMap(width, height,
                    get_tileset(), get_cell_side(),
                    get_row_and_column_num_of_tiles_composing_map(), get_Set_of_tiles_at_row_index(2),
                    get_Set_of_tiles_at_row_index(3), retrieve_map_without_metadata());


        fill_dictionary_position('P', 4, M.getDictionary_of_positions());
        fill_dictionary_position('T', 5, M.getDictionary_of_positions());

        return M;

    }

    List<String[]> extract_lines(String URL) throws InvalidPathException, IOException, NullPointerException, URISyntaxException {
        Stream<String> lines = Files.lines(Paths.get(ClassLoader.getSystemResource(URL).toURI()), Charset.defaultCharset());
        return lines.parallel().map(l -> l.split(Custom_Settings.FILE_SEPARATOR)).collect(Collectors.toList());
    }


    private Image get_tileset() {
        String  URL = read_lines(0, 0);
        
        try { return read_image(URL); }
        catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Image " +URL + " was not found. Set URL to default");
            URL = "TileSet.png";
        }
        return read_image(URL);
    }

    private Image read_image(String URL) throws IllegalArgumentException, NullPointerException
    {
        return new Image(URL);
    }
    
    private int to_int(String s)
    {
        try {
            return Integer.parseInt(s);
        }catch (NumberFormatException NE)
        {
            System.out.println("The value "+ s + " cannot be cast to integer, wrong format ");
        }
        return Integer.parseInt(s);
        
    }

    private Integer get_cell_side(){
        // if(cell_side <= 0) throw new CustomException.NegativeNumberException("Cell side must be a positive number, please modify it ");
      return to_int(read_lines(1,2));
    }


    private Pair<Integer, Integer> get_row_and_column_num_of_tiles_composing_map() {
        return new Pair<>(to_int(read_lines(1,0)),to_int(read_lines(1,1)));
    }

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
        return Arrays.stream(read_lines(index)).parallel().mapToInt(this::to_int).boxed().collect(Collectors.toList());
    }

    public static Set<Integer> convertListToSet(List<Integer> list)
    {
        return new HashSet<>(list);
    }



    private String[] read_lines(int row) {
        try {
            return _lines.get(row);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Line" + row + " was not found");
        }
        return _lines.get(row);
    }

    private String read_lines(int row, int col) {
        try {
        var L = read_lines(row);
        return L[col];
        } catch (ArrayIndexOutOfBoundsException e)
        {
            System.out.println("Column " + col + " was not found");
        }

        var L = read_lines(row);
        return L[col];


    }

}
