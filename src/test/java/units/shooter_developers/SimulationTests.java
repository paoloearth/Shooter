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
class SimulationTests {

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
    void widthIsCorrectWidth() {
      Assertions.assertThat(SIMULATION.get_width()).isEqualTo(1000);
    }

    @Test
    void heightIsCorrectHeight() { Assertions.assertThat(SIMULATION.get_height()).isEqualTo(600); }


   @Test
    void player1IsSteadyWhileMovingPlayer2(FxRobot robot) {

        var P1 = SIMULATION.getPlayer_1();
        HitBox C = P1.getHitbox();

       P2_MOVEMENTS.forEach(
               keyCode -> {  robot.push(keyCode);  assertEquals(C, P1.getHitbox());}
       );

    }

    @Test
    void player2IsSteadyWhileMovingPlayer1(FxRobot robot) {

        var P1 = SIMULATION.getPlayer_2();
        HitBox C = P1.getHitbox();

        P1_MOVEMENTS.forEach(
                keyCode -> {  robot.push(keyCode);  assertEquals(C, P1.getHitbox());}
        );

    }

    @Test
    void shootIsAddingObjectsToRoot(FxRobot robot)
    {
         robot.push(KeyCode.ENTER);
         var b = SIMULATION.all_sprites().stream().anyMatch(p-> p instanceof Projectile);
         assertTrue(b);
    }

    @Test
    void shootCooldownStopMultipleshoots(FxRobot robot)
    {
        robot.push(KeyCode.ENTER);
        robot.push(KeyCode.ENTER);
        var b = SIMULATION.all_sprites().stream().filter(p-> p instanceof Projectile).count() < 2;
        assertTrue(b);
    }

    @Test
    void shootCooldownAllowsShootagainAfterTime(FxRobot robot)  {
        robot.push(KeyCode.ENTER);
        robot.sleep(500);
        robot.push(KeyCode.ENTER);
        var b = SIMULATION.all_sprites().stream().filter(p-> p instanceof Projectile).count() == 2;
        assertTrue(b);
    }


    @Test
    void shootAtSameTime(FxRobot robot)  {
        robot.push(KeyCode.ENTER);
        robot.push(KeyCode.SPACE);
        var b = SIMULATION.all_sprites().stream().filter(p-> p instanceof Projectile).count() == 2;
        assertTrue(b);
    }

    @Test
    void moveToIsActuallyMovingTheSprite(FxRobot robot)
    {
        var  M = SIMULATION.get_gameMap();
        var P1    = SIMULATION.getPlayer_1();

        HitBox original_position = P1.getHitbox();

        Coordinates C = M.convert_tiles_in_pixel(M.getRandomLocation());
        SIMULATION.getPlayer_1().positionTo(C);

        assertNotSame(P1.getHitbox(), original_position);
    }

    @Test
    void getRandomLocationAlwaysReturningReachableTiles(FxRobot robot)
    {
        var  M = SIMULATION.get_gameMap();
        var P1    = SIMULATION.getPlayer_1();

        IntStream.range(0,2).forEach(
                i ->
                {
                    Coordinates C = M.convert_tiles_in_pixel(M.getRandomLocation());

                   var number_of_movements=  P1_MOVEMENTS.stream().map(keyCode ->
                    {
                        P1.positionTo(C);
                        HitBox b = P1.getHitbox();
                        robot.push(keyCode);
                        return P1.getHitbox() !=  b;
                    }).filter(t -> t).count();

                   assertTrue(number_of_movements > 0);

                });

    }

    @Test
    void doesHealtbarDecreaseWithDamage(FxRobot robot)
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
                                            projectile.setSpeed(0);
                                            projectile.positionTo(P2.getCurrentPosition());
                                          });

         robot.sleep(50);
         assertTrue(H.getCurrentHealth() != life_before_been_hit);

    }

    @Test
    void doesHealtbarIncreaseWithBonus(FxRobot robot)
    {
        var  P2    = SIMULATION.getPlayer_2();
        var  H = P2.getHBar();

        robot.push(KeyCode.SPACE);

        SIMULATION.getRoot().getChildren()
                .stream()
                .filter(pictured_object -> pictured_object instanceof Projectile)
                .map(pictured_object -> (Projectile) pictured_object)
                .forEach(projectile -> {
                    projectile.setSpeed(0);
                    projectile.positionTo(P2.getCurrentPosition());
                });

        robot.sleep(50);
        var lifeAfterBeenHit = H.getCurrentHealth();
        SIMULATION.getRoot().getChildren()
                .stream()
                .filter(pictured_object -> pictured_object instanceof Bonus)
                .map(pictured_object -> (Bonus) pictured_object)
                .forEach(bonus -> bonus.positionTo(P2.getCurrentPosition()));
        robot.sleep(50);
        assertTrue(H.getCurrentHealth() > lifeAfterBeenHit);

    }

}