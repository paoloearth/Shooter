package units.shooter_developers;


import org.junit.jupiter.api.Test;
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
    @CsvSource({"-1,0,IllegalArgumentException", "0,-1,IllegalArgumentException", "300000,0,IllegalArgumentException", "0, 123456, IllegalArgumentException"})
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

    @Test
    void Block_dimensions_ratio_setting_and_getting_works(){
        Block testing_block = new Block(1920, 1080, 0.5, 0.5);
        testing_block.setBlockDimensionsRatio(0.3, 0.2);

        boolean width_equal = testing_block.getBlockWidthRatio() == 0.3;
        boolean height_equal = testing_block.getBlockHeightRatio() == 0.2;
        assertEquals(true, width_equal && height_equal);
    }

    @Test
    void Block_dimensions_getting_works(){
        Block testing_block = new Block(500, 400, 0.5, 0.5);

        assertEquals(true, (testing_block.getBlockWidth() == 250) && (testing_block.getBlockHeight() == 200));
    }
}