package units.shooter_developers;


import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Function_To_Test {

    @ParameterizedTest
    @ValueSource(ints = {4,6,24})
    void Test_if_tests_works(int number) {
        assertEquals(number, number);
    }

    @ParameterizedTest
    @CsvSource({"-1,0,IllegalArgumentException", "0,-1,IllegalArgumentException", "300000,0,IllegalArgumentException", "0, 123456, IllegalArgumentException"})
    void Map_object_explodes_with_wrong_values(int coordX, int coordY, String exception){
        Map_object example = new Map_object(1920, 1080);
        try {
            example.setCoordinates(coordX, coordY);
            fail("exception not thrown");
        } catch(Exception e) {
            String error_name = e.getClass().getSimpleName();
            assertEquals(error_name, exception);
        }
    }

    @Test
    void Block_dimensions_ratio_setting_and_getting_works(){
        Block testing_block = new Block(1920, 1080, 0.5, 0.5);
        testing_block.setBlockDimensionsRatio(0.3, 0.2);
        double widthratio = testing_block.getBlockWidthRatio();
        double heightratio = testing_block.getBlockHeightRatio();
        boolean width_equal = testing_block.getBlockWidthRatio() == 0.3;
        boolean height_equal = testing_block.getBlockHeightRatio() == 0.2;
        assertEquals(true, width_equal && height_equal);
    }

    @Test
    void Block_dimensions_getting_works(){
        Block testing_block = new Block(500, 400, 0.5, 0.5);

        assertEquals(true, (testing_block.getBlockWidth() == 250) && (testing_block.getBlockHeight() == 200));
    }

    @ParameterizedTest
    @CsvSource({"-1,0,IllegalArgumentException",
            "0,-1,IllegalArgumentException",
            "1.3,0,IllegalArgumentException",
            "1.1, -0.3, IllegalArgumentException"})
    void Block_explodes_with_wrong_size_values(double width_block_ratio, double height_block_ratio, String exception){
        Block testing_block = new Block(500, 400, 0.5, 0.5);
        Pair<Double, Double> block_dimensions_ratio = new Pair<>(width_block_ratio, height_block_ratio);

        try {
            testing_block.setBlockDimensionsRatio(block_dimensions_ratio);
            fail("exception not thrown");
        } catch(Exception e) {
            String error_name = e.getClass().getSimpleName();
            assertEquals(error_name, exception);
        }
    }

    @Test
    void BlocksAddEntities(){
        Entity test_entity1 = new Entity(1920, 1080);
        Entity test_entity2 = new Entity(1920, 1080);
        Entity test_entity3 = new Entity(1920, 1080);
        Block test_block = new Block(1920, 1080, 0.1, 0.1);

        test_block.addDynamicObject(test_entity1);
        test_block.addDynamicObject(test_entity2);
        test_block.addDynamicObject(test_entity3);
        ArrayList<Map_object_dynamic> entities_list = test_block.getDynamicObjectList();
        assertEquals(3,entities_list.size());
    }

    @Test
    void BlocksRemoveEntities(){
        Entity test_entity1 = new Entity(1920, 1080);
        Entity test_entity2 = new Entity(1920, 1080);
        Entity test_entity3 = new Entity(1920, 1080);
        Block test_block = new Block(1920, 1080, 0.1, 0.1);

        test_block.addDynamicObject(test_entity1);
        test_block.addDynamicObject(test_entity2);
        test_block.addDynamicObject(test_entity3);
        test_block.removeDynamicObject(test_entity1);
        test_block.removeDynamicObject(test_entity2);
        test_block.removeDynamicObject(test_entity3);
        ArrayList<Map_object_dynamic> entities_list = test_block.getDynamicObjectList();
        assertEquals(0,entities_list.size());
    }

    @Test
    void ErrorWhenRemovingNonexistentEntity(){
        Entity test_entity1 = new Entity(1920, 1080);
        Block test_block = new Block(1920, 1080, 0.1, 0.1);
        String exception = "MissingResourceException";
        try {
            test_block.removeDynamicObject(test_entity1);
            fail("exception not thrown");
        } catch(Exception e) {
            String error_name = e.getClass().getSimpleName();
            assertEquals(error_name, exception);
        }
    }

    @Test
    void testCollisionEntityBlockX(){
        Room room = new Room(100, 100, 10);
        Entity test_entity1 = new Entity(100, 100, room);
        test_entity1.setCoordinates(5, 5);
        test_entity1.setHitbox(new Rectangle(2, 2));
        test_entity1.setVelocity(1, 0);
        room.getBlock(0, 1).setPassable(false);
        room.getBlock(0, 0).addDynamicObject(test_entity1);

        for(int t=1; t<=50; t++){
            test_entity1.update(t);
        }

        boolean entity_not_crossed_block = test_entity1.getX() < room.getBlock(0, 1).getX();
        assertEquals(true, entity_not_crossed_block);
    }

    @Test
    void testMovementWorksInPositiveDirections(){
        Room room = new Room(100, 100, 10);
        Entity test_entity1 = new Entity(100, 100, room);
        test_entity1.setCoordinates(5, 5);
        test_entity1.setHitbox(new Rectangle(2, 2));
        test_entity1.setVelocity(1, 1);
        room.getBlock(0, 0).addDynamicObject(test_entity1);
        for(int t=1; t<=10; t++){
            test_entity1.move(t);
        }

        Shape hola = new Rectangle(1, 1, 1, 1);
        Shape adios = new Rectangle(5, 5, 1, 1);
        var inter = Shape.intersect(hola, adios);

        boolean entity_not_throwed_block = (test_entity1.getX()==15) && (test_entity1.getY()==15);
        assertEquals(true, entity_not_throwed_block);
    }
}