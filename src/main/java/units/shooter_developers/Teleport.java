package units.shooter_developers;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Teleport extends Pictured_Object{

    RotateTransition rotation;
    Pair<Integer, Integer> destination;

    public Teleport(Pane root, String url, Map M, Pair<Double, Double> scaling_factor, String ID) {
        super(scaling_factor, url);


        this._type ="TELEPORT";
        this._scale = Custom_Settings.TELEPORT_SCALE;

        update_view();


        this.getChildren().add(_view);  // Add the view to local pane

        rotation_animation();

        if(ID.equals("T1"))
            move_to(M.get_pixel_position(M._MR._teleport_positions.getKey()));
        else
            move_to(M.get_pixel_position(M._MR._teleport_positions.getValue()));


        root.getChildren().add(this);   // Add local pane to global root
    }

    private void rotation_animation() {
        rotation = new RotateTransition(Duration.millis(3000), this);
        rotation.setByAngle(360);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.play();
    }


}
