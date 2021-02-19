package units.shooter_developers;


import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;



class Simulation_Test  extends ApplicationTest {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new Pane(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void test_set_title() {


    }



}