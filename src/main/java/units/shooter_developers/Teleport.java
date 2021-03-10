//JOSE: classe visitata

package units.shooter_developers;

import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Teleport extends PicturedObject {
    private Coordinates destination;

    /* Constructors  */
        //JOSE: meglio cambiare root per simulation_root. Nome migliore per M.
    public Teleport(Pane root, String url, GameMap M, Pair<Double, Double> scalingFactor, String ID) {
        super(scalingFactor, url);

        setScale(CustomSettings.TELEPORT_SCALE);
        updateView();

        addNodes(getView());

        rotationAnimation();

        moveTo(M.get_position_of(ID));

        root.getChildren().add(this);
    }


    /* Collisions & action management */
    @Override
    public Box getHitbox(){
        return new Box(getCurrentYPosition() + (getActualHeight()*.25), getCurrentXPosition()+(getActualWidth()*.25),  getActualWidth()*.5 , getActualHeight()*.5 );
    }
    //JOSE: Se si uniforma la gestione della hitbox, si può evitare modificare questo metodo. Basterebbe passare i
    //      parametri per generare la hitbox come input in qualche modo (costruzione, metodo, ect).

    @Override
    public void update( Sprite S) {
        if(intersect(S)) S.moveTo(destination);
    }

    protected final void setDestination(Teleport T)
    {
        destination = new Coordinates(T.getCurrentXPosition() + getActualWidth()/4.0 ,T.getCurrentYPosition()+ getActualHeight());
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
