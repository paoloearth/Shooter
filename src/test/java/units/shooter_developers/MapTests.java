package units.shooter_developers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapTests {

    @ParameterizedTest
    @CsvSource({"Three_lines.txt,3", "Five_lines.txt,5"})
    void Reads_true_number_of_lines(String file_name, int num_lines) throws IOException, URISyntaxException {
        var MR = new Map_Reader();
        assertEquals(MR.readLinesFromFile(file_name).size(), num_lines);
    }



}
