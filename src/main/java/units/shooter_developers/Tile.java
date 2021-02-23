package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Pair;

public class Tile extends ImageView {

    private final Pair<Integer,Integer> block_pixel_position;

    /* Properties to encode the "walkability" for a player and for a projectile*/
    BooleanProperty is_passable = new SimpleBooleanProperty(true);
    BooleanProperty not_passable_for_p = new SimpleBooleanProperty(true);

    Tile(int pos_x, int pos_y, int width, int height, boolean passable, boolean not_passable_p, Image img, Rectangle2D r2)
    {
        /* Position the tile in the proper position */
        this.relocate(pos_x,pos_y);

        /* Position the tile in the proper position */
        this.setFitWidth(width);
        this.setFitHeight(height);

        /* Save the pixel posiion of the block */
        this.block_pixel_position = new Pair<>(pos_x,pos_y);

        /* Set passability parameters */
        this.is_passable.setValue(passable);
        this.not_passable_for_p.setValue(not_passable_p);

        /* Set image property of the figure */
        this.setImage(img);
        this.setViewport(r2);
        this.setPreserveRatio(false);

    }

    public Pair<Integer, Integer> get_pixel_of_block_position() {
        return this.block_pixel_position;
    }



}
