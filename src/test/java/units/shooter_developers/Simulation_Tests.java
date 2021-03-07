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


    @Start
    private void start(Stage stage) throws IOException {

        var FAKE_NAMES = new ArrayList<String>();
        FAKE_NAMES.add("ROBERTUCCIO");
        FAKE_NAMES.add("FILIBERTA");

        var FAKE_URLS = new ArrayList<String>();
        FAKE_URLS.add("warrior.png");
        FAKE_URLS.add("astrologer.png");

        var FAKE_MAP = new ArrayList<String>();
        FAKE_MAP.add("map_islands.csv");

        stage.setWidth(1000);
        stage.setHeight(600);


        SIMULATION = new Simulation(FAKE_NAMES,FAKE_URLS, FAKE_MAP);
        SIMULATION.start(stage);

    }

    @Test
    void width_is_correct_width () {
      Assertions.assertThat(SIMULATION.getWIDTH()).isEqualTo(1000);
    }

    @Test
    void height_is_correct_height() { Assertions.assertThat(SIMULATION.getHEIGHT()).isEqualTo(600); }


    void movement(FxRobot robot) {

        for (int i = 0; i < 10; i++) {
            robot.push(KeyCode.DOWN);
        }
        for (int i = 0; i < 10; i++) {
            robot.push(KeyCode.UP);
        }
    }



    @ParameterizedTest
    @ValueSource(strings = "PER ME E LA CIPOLLA")
    void Test_if_tests_works(String s) {
        assertEquals(s, "PER ME E LA CIPOLLA");
    }



}