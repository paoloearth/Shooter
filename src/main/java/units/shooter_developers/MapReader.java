package units.shooter_developers;
import javafx.scene.image.Image;
import javafx.util.Pair;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
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


    MapReader(){ }

    /* Core function */
    public GameMap makeMapFromFileContent(String URL, double width, double height)
    {
        List<String[]> lines = readLinesFromFile(URL);


        String tileSetURL = lines.get(CustomSettings.URL_TILESET_INDEX)[0];
        Image  tileSet = getTilesetFromURL(tileSetURL);

        List<Integer> mapInfo = parseStringArrayToIntArray(lines.get(CustomSettings.MAP_INFO_INDEX));
        int horizonalCells = mapInfo.get(0);
        int verticalCells = mapInfo.get(1);
        int cellSide = mapInfo.get(2);

        Set<Integer> passableCodes   = fromIntListToSet(parseStringArrayToIntArray(lines.get(CustomSettings.PASSABLE_TILES_INDEX)));
        Set<Integer> unpassableCodes = fromIntListToSet(parseStringArrayToIntArray(lines.get(CustomSettings.NOT_PASSABLE_TILES_FOR_P_INDEX)));


        Map<String, Coordinates> coordinateDictionary = new HashMap<>();
        fillDictionaryPosition(coordinateDictionary,CustomSettings.PLAYER_CODE, parseStringArrayToIntArray(lines.get(CustomSettings.SPRITE_COORD_INDEX)));
        fillDictionaryPosition(coordinateDictionary, CustomSettings.TELEPORT_CODE, parseStringArrayToIntArray(lines.get(CustomSettings.TELEPORT_COORD_INDEX)));

        List<String[]> mapTileComposition = retrieveMapWithoutMetadata(lines, CustomSettings.NUMBER_OF_METADATA_LINES);


        return new GameMap(width, height,
                           tileSet,cellSide,
                           horizonalCells,verticalCells,
                           passableCodes,unpassableCodes,
                           mapTileComposition, coordinateDictionary);

    }


    protected Set<Integer> fromIntListToSet(List<Integer> S) { return new HashSet<>(S); }

    protected List<Integer> parseStringArrayToIntArray(String[] S) {
        return Arrays.stream(S).parallel().mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());
    }



    protected List<String[]> readLinesFromFile(String URL) {
        List<String[]> rows = new ArrayList<>();
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


    protected Image getTilesetFromURL(String URL) {
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
    

    protected List<String[]> retrieveMapWithoutMetadata(List<String[]> lines, int numberOfRowsToSkip) {
        return lines.stream().skip(numberOfRowsToSkip).collect(Collectors.toList());
    }

    public void fillDictionaryPosition( Map<String, Coordinates> dictionaryToFill, char ID, List<Integer> l){
        IntStream.range(0,l.size()).filter(i-> i%2 ==0).mapToObj(i ->
                new Pair<>(ID+String.valueOf(i/2),new Coordinates(l.get(i), l.get(i+1))))
                .forEach(pair->dictionaryToFill.put(pair.getKey(), pair.getValue()));
    }


}
