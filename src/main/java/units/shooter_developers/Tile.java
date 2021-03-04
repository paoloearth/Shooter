package units.shooter_developers;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {

    private final Coordinates block_pixel_position;

    /* encode the "walkability" for a player and for a projectile*/
    boolean is_passable;
    boolean is_passable_for_projectile;

    Tile(double pos_x, double pos_y, double width, double height, boolean passable, boolean not_passable_p, Image img, Rectangle2D r2)
    {
        /* Position the tile in the proper position */
        this.relocate(pos_x,pos_y);

        /* Position the tile in the proper position */
        this.setFitWidth(width);
        this.setFitHeight(height);

        /* Save the pixel posiion of the block */
        this.block_pixel_position = new Coordinates(pos_x,pos_y);

        /* Set passability parameters */
        this.is_passable = passable;
        this.is_passable_for_projectile = !not_passable_p;

        /* Set image property of the figure */
        this.setImage(img);
        this.setViewport(r2);
        this.setPreserveRatio(false);

    }

    public Coordinates get_pixel_of_block_position() {
        return this.block_pixel_position;
    }



}
