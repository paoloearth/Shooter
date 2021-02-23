package units.shooter_developers;

import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Map_Reader_Tests {
    /*
    @ParameterizedTest
    @CsvSource({"Three_lines.txt,3", "Five_lines.txt,5"})
    void Reads_true_number_of_lines(String file_name, int num_lines) throws IOException {
        var MR = new Map_Reader(file_name);
        assertEquals(MR.get_lines().size(), num_lines);
    }

     */

}
