package units.shooter_developers;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Teleport extends Pictured_Object{
    private Coordinates destination;

    public Teleport(Pane root, String url, GameMap M, Pair<Double, Double> scaling_factor, String ID) {
        super(scaling_factor, url);

        set_scale(Custom_Settings.TELEPORT_SCALE);
        update_view();
        add_nodes(get_view());

        rotation_animation();
        move_to(M.get_position_of(ID));
        root.getChildren().add(this);
    }

    private void rotation_animation() {
        var rotation = new RotateTransition(Duration.millis(3000), this);
        rotation.setByAngle(360);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.play();
    }

    public void setDestination(Teleport T)
    {
        destination = new Coordinates(T.get_current_X_position() + get_actual_width()/4.0 ,T.get_current_Y_position()+get_actual_height());
    }

    @Override
    public Box get_hitbox(){
        return new Box(get_current_Y_position() + (get_actual_height()*.25), get_current_X_position()+(get_actual_width()*.25),  get_actual_width()*.5 ,get_actual_height()*.5 );
    }


    @Override
    public void update( Sprite S) {
        if(intersect(S)) S.move_to(destination);
    }


}
