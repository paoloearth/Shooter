import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This class is the main class
 */
public class App {

    /**
     * A simple function that check wheter an integer is
     * pari or dispari
     * @param value Value to test
     * @return Pari if value is pari, dispari otherwise
     */
    public   String function_to_test(int value)
    {
        return value%2 == 0 ?  "Pari": "Dispari";
    }

    public static void main(String[] args)
    {
        // Instanciate the class
        App C = new App();

        // I functionally create:
        // A Stream of consecutive numbers from 1 to 100
        // I apply the convert function to each one of them
        Stream<String> mapped =  IntStream.rangeClosed(1, 100).mapToObj(C::function_to_test);

        // Print the stream out
        mapped.forEach(System.out::println);

    }
}
