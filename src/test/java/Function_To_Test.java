



import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Function_To_Test {

    @ParameterizedTest
    @ValueSource(ints = {4,6,24})
    void Test_if_tests_works(int number)
    {
        assertEquals(number, number);
    }




}