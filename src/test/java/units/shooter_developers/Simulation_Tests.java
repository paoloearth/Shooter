package units.shooter_developers;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

@ExtendWith(ApplicationExtension.class)
class Simulation_Tests {

    private Simulation SIMULATION;
    private Stage STAGE;

    /**
     * Will be called with {@code @Before} semantics, i. e. before each test method.
     *
     * @param stage - Will be injected by the test runner.
     */

    @Start
    private void start(Stage stage) throws IOException {

        STAGE = stage;

        var FAKE_NAMES = new ArrayList<String>();
        FAKE_NAMES.add("ROBERTUCCIO");
        FAKE_NAMES.add("FILIBERTA");

        var FAKE_URLS = new ArrayList<String>();
        FAKE_URLS.add("warrior.png");
        FAKE_URLS.add("astrologer.png");

        var FAKE_MAP = new ArrayList<String>();
        FAKE_MAP.add("map_islands.csv");

        STAGE.setWidth(1000);
        STAGE.setHeight(600);


        SIMULATION = new Simulation(FAKE_NAMES,FAKE_URLS, FAKE_MAP);
        SIMULATION.start(STAGE);

    }

    @Test
    void width_is_correct_width(FxRobot robot) {
      Assertions.assertThat(SIMULATION.WIDTH).isEqualTo(1000);
    }

    @Test
    void height_is_correct_height(FxRobot robot) {
        Assertions.assertThat(SIMULATION.HEIGHT).isEqualTo(600);
    }

    @Test
    void movement(FxRobot robot) {


        for (int i = 0; i < 10; i++) {
            robot.push(KeyCode.DOWN);
        }



    }





    @ParameterizedTest
    @ValueSource(ints = {4,6,24})
    void Test_if_tests_works(int number) {
        assertEquals(number, number);
    }







}