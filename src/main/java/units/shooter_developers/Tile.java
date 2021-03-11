package units.shooter_developers;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView {

    private final Coordinates tilePixelPosition;

    /* to encode properties of each tile*/
    private final boolean passableForPlayer;
    private final boolean passableForProjectile;

    Tile(double posX, double posY, double width, double height, boolean PassableForPlayers, boolean notPassableForProjectile, Image tileSet, Rectangle2D portionOfTileSet)
    {
        relocate(posX,posY);

        setFitWidth(width);
        setFitHeight(height);

        tilePixelPosition = new Coordinates(posX,posY);

        passableForPlayer = PassableForPlayers;
        passableForProjectile = !notPassableForProjectile;
        
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
