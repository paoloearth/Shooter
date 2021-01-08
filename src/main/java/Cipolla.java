import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Scopo della classe
 */
public class Cipolla {


    /**
     * Descrizione della funzione
     * @param value Description text text text.
     * @return Description text text text.
     */
    public   String convert(int value)
    {
        // ADD COMMENT 
        return value%2 == 0 ?  "Pari": "Dispari";

    }

    public static void main(String[] args)
    {
        // Instanciate the class
        Cipolla C = new Cipolla();

        // I functionally create:
        // A Stream of consecutive numbers from 1 to 100
        // I apply the convert function to each one of them
        Stream<String> mapped =  IntStream.rangeClosed(1, 100).mapToObj(C::convert);

        // Print the stream out
        mapped.forEach(System.out::println);

    }
}
