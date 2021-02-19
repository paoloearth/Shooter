package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public class Tile extends ImageView {

    private final Pair<Integer,Integer> block_pixel_position;

    BooleanProperty is_passable = new SimpleBooleanProperty(true);
    BooleanProperty not_passable_for_p = new SimpleBooleanProperty(true);

    public Tile(Pair<Integer, Integer> block_pixel_position) {
        this.block_pixel_position = block_pixel_position;
    }


}
