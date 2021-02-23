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

public class Bonus_Generator extends Pictured_Object{
    BooleanProperty is_waiting = new SimpleBooleanProperty(true);
    Object_Animation anim;

    public Bonus_generator(Pane root, Map M, String url, int _n_rows, int _n_cols, Pair<Double, Double> scaling_factor)
    {
        super(scaling_factor,url,_n_rows,_n_cols);

        this._type = "BONUS";
        this._scale = Custom_Settings.HEART_SCALE;

        update_view();

        generate(M);


        // ADD to the main pane
        root.getChildren().add(this);

    }

    public void generate(Map M)
    {

        move_to(M.get_random_location());
        push_inside_border(M);
        this.getChildren().add(_view);

    }


}
