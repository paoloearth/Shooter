package units.shooter_developers;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {

    private final Coordinates tilePixelPosition;

    /* encode the "walkability" for a player and for a projectile*/
    private final boolean passableForPlayer;
    private final boolean passableForProjectile;

    Tile(double posX, double posY, double width, double height, boolean PassableForPlayers, boolean notPassableForProjectile, Image tileSet, Rectangle2D portionOfTileSet)
    {
        /* Position the tile in the proper position */

        relocate(posX,posY);

        /* Position the tile in the proper position */
        setFitWidth(width);
        setFitHeight(height);

        /* Save the pixel posiion of the block */
        tilePixelPosition = new Coordinates(posX,posY);

        /* Set passability parameters */
        passableForPlayer = PassableForPlayers;
        passableForProjectile = !notPassableForProjectile;

        /* Set image property of the figure */
        setImage(tileSet);
        setViewport(portionOfTileSet);
        setPreserveRatio(false);

    }

    public Coordinates getPixelPositionOfTheTile() {
        return this.tilePixelPosition;
    }

    public boolean isPassableForPlayer() { return passableForPlayer; }

    public boolean isPassableForProjectile() { return passableForProjectile; }
}
