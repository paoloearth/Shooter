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
public class MapReader {

    private  List<String[]> _lines;
    private final String _URL;

    // Constructor
    MapReader(String URL){
        _URL = URL;
    }

    public GameMap makeMapFromFileContent(double width, double height) {
        _lines = readLinesFromFile(_URL);

        GameMap M;

        M = new GameMap(width, height,
                    getTileset(), getCellSide(),
                    getRowAndColumnNumOfTilesComposingMap(), getSetOfTilesAtRowIndex(2),
                    getSetOfTilesAtRowIndex(3), retrieveMapWithoutMetadata());

        fillDictionaryPosition('P', 4, M.getDictionary_of_positions());
        fillDictionaryPosition('T', 5, M.getDictionary_of_positions());

        return M;

    }

    protected List<String[]> readLinesFromFile(String URL) {
        List<String[]> rows = null;
       try(Stream<String> lines =
                   Files.lines(Paths.get(ClassLoader.getSystemResource(URL).toURI()), Charset.defaultCharset())){
                  rows = lines.parallel().map(l -> l.split(CustomSettings.FILE_SEPARATOR)).collect(Collectors.toList());
       }
       catch(FileNotFoundException e){ System.out.println(URL + ": was not found "); }
       catch(IOException e){ System.out.println(URL + ": problems interacting with the map file "); }
       catch(NullPointerException e){ System.out.println("Map File "+URL+" is null ");}
       catch(URISyntaxException e){ System.out.println("Wrong URL"+URL+" format of map file ");}

       return rows;
    }


    protected Image getTileset() {
        String  URL = readLines(0, 0);
        
        try { return readImage(URL); }
        catch (IllegalArgumentException | NullPointerException e)
        {
            System.out.println("Image " +URL + " was not found. Set URL to default");
            URL = "TileSet.png";
        }
        return readImage(URL);
    }

    protected Image readImage(String URL) throws IllegalArgumentException, NullPointerException
    {
        return new Image(URL);
    }
    
    protected int toInt(String s)
    {
        try {
            return Integer.parseInt(s);
        }catch (NumberFormatException NE)
        {
            System.out.println("The value "+ s + " cannot be cast to integer, wrong format ");
        }
        return Integer.parseInt(s);
        
    }

    protected int getCellSide(){
        // if(cell_side <= 0) throw new CustomException.NegativeNumberException("Cell side must be a positive number, please modify it ");
      return toInt(readLines(1,2));
    }


    protected Pair<Integer, Integer> getRowAndColumnNumOfTilesComposingMap() {
        return new Pair<>(toInt(readLines(1,0)), toInt(readLines(1,1)));
    }

    protected Set<Integer> getSetOfTilesAtRowIndex(int index) {
        return convertListToSet(getListOfIntegerFromString(index));
    }

    protected List<String[]> retrieveMapWithoutMetadata() {
        return _lines.stream().skip(CustomSettings.NUMBER_OF_METADATA_LINES).collect(Collectors.toList());
    }

    public void fillDictionaryPosition(char ID, int index, Map<String, Coordinates> dict){
        var l = getListOfIntegerFromString(index);
        IntStream.range(0,l.size()).filter(i-> i%2 ==0).mapToObj(i ->
                new Pair<>(ID+String.valueOf(i/2),new Coordinates(l.get(i), l.get(i+1))))
                .forEach(pair->dict.put(pair.getKey(), pair.getValue()));
    }

    protected List<Integer> getListOfIntegerFromString(int index) {
        return Arrays.stream(readLines(index)).parallel().mapToInt(this::toInt).boxed().collect(Collectors.toList());
    }

    protected static Set<Integer> convertListToSet(List<Integer> list)
    {
        return new HashSet<>(list);
    }



    protected String[] readLines(int row) {
        try {
            return _lines.get(row);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Line" + row + " was not found"+e.toString());
        }
        return _lines.get(row);
    }

    protected String readLines(int row, int col){
        try {
        var L = readLines(row);
        return L[col];
        } catch (IndexOutOfBoundsException e)
        {
            System.out.println("Column " + col + " was not found"+e.toString());
        }

        var L = readLines(row);
        return L[col];
    }

    public List<String[]> get_lines() {
        return _lines;
    }
}
