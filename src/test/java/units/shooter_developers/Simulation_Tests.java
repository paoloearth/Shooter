package units.shooter_developers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Simulation_Tests   {

    @ParameterizedTest
    @ValueSource(ints = {4})
    void Test_if_tests_works(int number) {
        assertEquals(number, number);
    }




}