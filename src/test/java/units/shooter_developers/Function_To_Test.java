package units.shooter_developers;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Function_To_Test {

    @ParameterizedTest
    @ValueSource(ints = {4,6,24})
    void Test_if_tests_works(int number) {
        assertEquals(number, number);
    }

    @ParameterizedTest
    @CsvSource({"-1,1,IllegalArgumentException", "1,-1,IllegalArgumentException", "300000,1,IllegalArgumentException", "2, 123456, IllegalArgumentException"})
    void Map_object_explodes_with_wrong_values(int coordX, int coordY, String exception){
        Map_object example = new Map_object(1920, 1080);
        try {
            example.setCoordinates(coordX, coordY);
            fail("exception not thrown");
        } catch(Exception e) {
            String error_name = e.getClass().getSimpleName();
            assertEquals(error_name, exception);
        }

    }
}