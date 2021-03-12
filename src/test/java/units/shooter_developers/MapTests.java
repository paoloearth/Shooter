package units.shooter_developers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class MapTest {
    private Pane root;
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
        var MR = new MapReader();
        assertEquals( MR.readLinesFromFile(fileName).size(), numLines);
    }

    /*
    @ParameterizedTest
    @CsvSource({"Five_lines.txt, map_desert.png"})
    void readExactFirstLine(String file_name, String URL) throws IOException, URISyntaxException {
        var MR = new MapReader(file_name);
        assertEquals(MR.readLines(0,0),URL);
    }
    */


}





