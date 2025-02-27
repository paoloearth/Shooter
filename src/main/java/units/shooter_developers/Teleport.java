package units.shooter_developers;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;
import units.shooter_developers.customs.CustomSettings;

public class Teleport extends PicturedObject {
    private Coordinates destination;

    /* Constructors  */
    public Teleport(Pane simulationRoot, String url, GameMap M, Pair<Double, Double> scalingFactor, String ID) {
        super(scalingFactor, url);

        applyCustomScaleToObject(CustomSettings.TELEPORT_SCALE);

        addNodes(getPicture());

        rotationAnimation();

        positionTo(M.get_position_of(ID));

        simulationRoot.getChildren().add(this);
    }


    /* Collisions & action management */
    @Override
    public HitBox getHitbox(){
        return new HitBox(getCurrentYPosition() + (getScaledHeight()*.25), getCurrentXPosition()+(getScaledWidth()*.25),
                      getScaledWidth()*.5 , getScaledHeight()*.5 );
    }

    @Override
    public void action(Sprite S) {
        if(intersect(S)) S.positionTo(destination);
    }

    protected final void setDestination(Teleport T)
    {
        destination = new Coordinates(T.getCurrentXPosition() + getScaledWidth()/4.0 ,T.getCurrentYPosition()+ getScaledHeight());
    }


    /* Animations */
    private void rotationAnimation() {
        var rotation = new RotateTransition(Duration.millis(CustomSettings.TELEPORT_TIME_TO_ROTATE), this);
        rotation.setByAngle(360);
        rotation.setInterpolator(Interpolator.LINEAR);
        rotation.setCycleCount(Animation.INDEFINITE);
        rotation.play();
    }



}
