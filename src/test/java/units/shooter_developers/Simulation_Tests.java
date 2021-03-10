package units.shooter_developers;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class Simulation_Tests {

    private Simulation SIMULATION;
    ArrayList<KeyCode> P1_MOVEMENTS = new ArrayList<>(Arrays.asList(KeyCode.W, KeyCode.A, KeyCode.D,KeyCode.S));
    ArrayList<KeyCode> P2_MOVEMENTS = new ArrayList<>(Arrays.asList(KeyCode.UP, KeyCode.DOWN, KeyCode.LEFT,KeyCode.RIGHT));


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


   @Test
    void Player_1_is_steady_while_moving_player_2(FxRobot robot) {

        var P1 = SIMULATION.getPlayer_1();
        Box C = P1.get_hitbox();

       P2_MOVEMENTS.forEach(
               keyCode -> {  robot.push(keyCode);  assertEquals(C, P1.get_hitbox());}
       );

    }

    @Test
    void Player_2_is_steady_while_moving_player_1(FxRobot robot) {

        var P1 = SIMULATION.getPlayer_2();
        Box C = P1.get_hitbox();

        P1_MOVEMENTS.forEach(
                keyCode -> {  robot.push(keyCode);  assertEquals(C, P1.get_hitbox());}
        );

    }

    @Test
    void shoot_is_adding_objects_to_root(FxRobot robot)
    {
         robot.push(KeyCode.ENTER);
         var b = SIMULATION.all_sprites().stream().anyMatch(p-> p instanceof Projectile);
         assertTrue(b);
    }

    @Test
    void shoot_cooldown_stop_multipleshoots(FxRobot robot)
    {
        robot.push(KeyCode.ENTER);
        robot.push(KeyCode.ENTER);
        var b = SIMULATION.all_sprites().stream().filter(p-> p instanceof Projectile).count() < 2;
        assertTrue(b);
    }

    @Test
    void shoot_cooldown_allows_shootagain_after_time(FxRobot robot)  {
        robot.push(KeyCode.ENTER);
        robot.sleep(500);
        robot.push(KeyCode.ENTER);
        var b = SIMULATION.all_sprites().stream().filter(p-> p instanceof Projectile).count() == 2;
        assertTrue(b);
    }


    @Test
    void shoot_at_same_time(FxRobot robot)  {
        robot.push(KeyCode.ENTER);
        robot.push(KeyCode.SPACE);
        var b = SIMULATION.all_sprites().stream().filter(p-> p instanceof Projectile).count() == 2;
        assertTrue(b);
    }

    @Test
    void move_to_is_actually_moving_the_sprite(FxRobot robot)
    {
        var  M = SIMULATION.getGamemap();
        var P1    = SIMULATION.getPlayer_1();

        Box original_position = P1.get_hitbox();

        Coordinates C = M.convert_tiles_in_pixel(M.get_random_location());
        SIMULATION.getPlayer_1().moveTo(C);

        assertNotSame(P1.get_hitbox(), original_position);
    }

    @Test
    void get_random_location_always_returning_reachable_tiles(FxRobot robot)
    {
        var  M = SIMULATION.getGamemap();
        var P1    = SIMULATION.getPlayer_1();

        IntStream.range(0,2).forEach(
                i ->
                {
                    Coordinates C = M.convert_tiles_in_pixel(M.get_random_location());

                   var number_of_movements=  P1_MOVEMENTS.stream().map(keyCode ->
                    {
                        P1.moveTo(C);
                        Box b = P1.get_hitbox();
                        robot.push(keyCode);
                        return P1.get_hitbox() !=  b;
                    }).filter(t -> t).count();

                   assertTrue(number_of_movements > 0);

                });

    }

    @Test
    void does_healtbar_decrease_with_damage(FxRobot robot)
    {

        var  P2    = SIMULATION.getPlayer_2();
        var  H = P2.getHBar();
        var life_before_been_hit = H.getCurrentHealth();

        robot.push(KeyCode.SPACE);

         SIMULATION.getRoot().getChildren()
                                          .stream()
                                          .filter(pictured_object -> pictured_object instanceof Projectile)
                                          .map(pictured_object -> (Projectile) pictured_object)
                                          .forEach(projectile -> {
                                            projectile.set_speed(0);
                                            projectile.moveTo(P2.getCurrentPosition());
                                          });

         robot.sleep(50);
         assertTrue(H.getCurrentHealth() != life_before_been_hit);

    }








}