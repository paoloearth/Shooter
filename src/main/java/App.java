import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * This class is the main class
 */
public class App extends Application {


    private static Label createLabel(String text, String styleClass){
        Label label = new Label(text);
        label.getStyleClass().add(styleClass);
        BorderPane.setMargin(label, new Insets(5));
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        return label;
    }



    private Node header() {

        return createLabel("Top", "bg-2");

    }

    private Node center() {
        return  createLabel("Center", "bg-4");
    }

    private Node bottom() {

       return createLabel("Bottom", "bg-6");

    }



    private BorderPane getBorderPane() {
        BorderPane borderPane = new BorderPane();

        borderPane.setTop(header());

        borderPane.setCenter(center());

        borderPane.setBottom(bottom());

        return borderPane;
    }

    @Override
    public void start(Stage stage) {

        // Set the title of the window
        stage.setTitle("Shooter");


        BorderPane root = getBorderPane();

        Scene scene = new Scene(root,1920, 1080);


        String styleSheet = getClass().getResource("/style.css").toExternalForm();
        scene.getStylesheets().add(styleSheet);


        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }









    /**
     * THIS FUNCTION IS HERE ONLY TO CHECK
     * IF GRADLE IS WORKING
     * A simple function that check wheter an integer is
     * pari or dispari
     * @param value Value to test
     * @return Pari if value is pari, dispari otherwise
     */
    public   String function_to_test(int value)
    {
        return value%2 == 0 ?  "Pari": "Dispari";
    }

}
