package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bonus_Generator {
    BooleanProperty is_waiting = new SimpleBooleanProperty(true);
    Object_Animation anim;
    boolean already_called = false;

}
