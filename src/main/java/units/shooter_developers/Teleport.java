package units.shooter_developers;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;
import units.shooter_developers.settings.Custom_Settings;

public class Teleport extends Pictured_Object{

    RotateTransition rotation;
    Coordinates destination;

    public Teleport(Pane root, String url, Map M, Pair<Double, Double> scaling_factor, String ID) {
        super(scaling_factor, url);


        this._type ="TELEPORT";
        this._scale = Custom_Settings.TELEPORT_SCALE;

        update_view();

        this.getChildren().add(_view);

        rotation_animation();

        if(ID.equals("T1"))
            move_to(M.get_pixel_position( M._MR._teleport_positions[0]));
        else
            move_to(M.get_pixel_position(M._MR._teleport_positions[1]));


        root.getChildren().add(this);   // Add local pane to global root
    }

    private void rotation_animation() {
        rotation = new RotateTransition(Duration.millis(3000), this);
        rotation.setByAngle(360);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.play();
    }

    public void setDestination(Teleport T)
    {
        destination = new Coordinates(T.get_current_X_position() + get_actual_width()/4 ,T.get_current_Y_position()+get_actual_height());
    }

    @Override
    public Rectangle2D get_bounds() {
        return new Rectangle2D(get_current_X_position() +  (get_actual_width()/4),
                get_current_Y_position() +  (get_actual_height()/4),
                get_actual_width()/2, get_actual_height()/2);
    }

    @Override
    public void update(Map M, Sprite S) {
        S.move_to(destination);
    }


}
