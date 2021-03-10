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

        set_scale(CustomSettings.TELEPORT_SCALE);
        update_view();
        addNodes(get_view());

        rotation_animation();
        moveTo(M.get_position_of(ID));
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
        destination = new Coordinates(T.getCurrentXPosition() + getActualWidth()/4.0 ,T.getCurrentYPosition()+ getActualHeight());
    }

    @Override
    public Box get_hitbox(){
        return new Box(getCurrentYPosition() + (getActualHeight()*.25), getCurrentXPosition()+(getActualWidth()*.25),  getActualWidth()*.5 , getActualHeight()*.5 );
    }


    @Override
    public void update( Sprite S) {
        if(intersect(S)) S.moveTo(destination);
    }


}
