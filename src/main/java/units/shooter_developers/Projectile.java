package units.shooter_developers;

import javafx.geometry.Rectangle2D;
import javafx.util.Pair;

public class Projectile extends Dynamic_Object{
    int biasX;
    int biasY;
    String Owner;


    public Projectile(Pair<Double, Double> scaling_factor, String url, Sprite S)
    {
        super(scaling_factor, url);

        this._type ="PROJECTILE";        // Set the type
        this.Owner = S._id;             // Save the shooter's ID
        this._scale = Custom_Settings.PROJECTILE_SCALE;
        this._speed = (int) (Custom_Settings.PROJECTILE_SPEED*scaling_factor.getKey());    // Set characteristics of the projectile

        _width   = (int)   (this._picture.getWidth()  /  _n_cols);
        _height =  (int)   (this._picture.getHeight() / _n_rows);

        update_view();

      //  update_deltas(S);

        // Position the
        move_to(new Pair<>(S.get_future_x() + this.biasX, S.get_future_y() + this.biasY));

        this.getChildren().add(this._view);
    }

    @Override
    protected boolean illegal_move(Map M) {
        return false;
    }

    @Override
    public Rectangle2D get_bounds() {
        return null;
    }
}
