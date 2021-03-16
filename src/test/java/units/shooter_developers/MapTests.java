package units.shooter_developers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.testfx.api.FxRobot;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import units.shooter_developers.customs.CustomSettings;

@ExtendWith(ApplicationExtension.class)
class MapTest {
    private Pane root;
    MapReader MR = new MapReader();
    String URL = "Five_lines.txt";
    List<String[]> lines = MR.readLinesFromFile(URL);

    @Start
    private void start(Stage stage) {
        root = new Pane();
        stage.setScene(new Scene(root, 100, 100));
        stage.show();
    }
    @Test
    void random_test( FxRobot robot)
    {
        robot.push(KeyCode.A);
        robot.sleep(500);
    }


    @ParameterizedTest
    @CsvSource({"Three_lines.txt,3", "Five_lines.txt,5"})
    void readsTrueNumberOfLines(String fileName, int numLines){
        assertEquals( MR.readLinesFromFile(fileName).size(), numLines);
    }


    @ParameterizedTest
    @CsvSource({ "map_desert.png"})
    void readExactFirstLine(String URL) {
        assertEquals(lines.get(CustomSettings.URL_TILESET_INDEX)[0],URL);
    }

    @ParameterizedTest
    @CsvSource({"1"})
    void readExactInteger(int i){
       var list = MR.parseStringArrayToIntArray(lines.get(1));
        boolean contains = list.stream().mapToInt(Integer::intValue).anyMatch(x-> x == i);
        assertTrue(contains);
    }

    @Test
    void canTransformURLInImage(){
        var b = MR.getTilesetFromURL(lines.get(CustomSettings.URL_TILESET_INDEX)[0]) != null;
        assertTrue(b);
    }

    @Test
    void skipExactNumberOflines(){
        var b = MR.retrieveMapWithoutMetadata(lines,2).size() == lines.size()-2;
        assertTrue(b);
    }

}





