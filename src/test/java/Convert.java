



import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class Convert {


    @ParameterizedTest
    @CsvSource({"1, Dispari", "17, Dispari", "99, Dispari"})
    void ControllaDispari(int number, String expected)
    {
        Cipolla F = new Cipolla();
        assertEquals(expected, F.convert(number));
    }

    @ParameterizedTest
    @ValueSource(ints = {4,6,24})
    void ControllaPari(int number)
    {
        Cipolla F = new Cipolla();
        assertEquals("Pari", F.convert(number));
    }




}