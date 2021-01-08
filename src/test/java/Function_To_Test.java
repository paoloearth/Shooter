



import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Function_To_Test {

    @ParameterizedTest
    @ValueSource(ints = {4,6,24})
    void Test_1(int number)
    {
        MyScene F = new MyScene();
        assertEquals("Pari", F.function_to_test(number));
    }




}