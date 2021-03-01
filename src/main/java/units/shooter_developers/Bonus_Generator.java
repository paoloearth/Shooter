package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bonus_Generator extends Pictured_Object{
    BooleanProperty is_waiting = new SimpleBooleanProperty(true);
    Object_Animation anim;


    public Bonus_Generator(Pane root, Map M, String url, int _n_rows, int _n_cols, Pair<Double, Double> scaling_factor)
    {
        super(scaling_factor,url,_n_rows,_n_cols);

        this._type = "BONUS";
        this._scale = Custom_Settings.HEART_SCALE;

        update_view();
        create_and_start_animation();

        generate(M);


        root.getChildren().add(this);

    }

    private void create_and_start_animation() {
        anim = new Object_Animation(_view, Duration.seconds(1),10,10,0,0,_width, _height);
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    public void generate(Map M)
    {
        move_to(M.get_random_location());
        push_inside_border(M);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            this.getChildren().add(_view);
        }));
        timeline.play();
    }

    //relocate heart inside the map taking into account the size of heart.png
    private void push_inside_border(Map M) {
        if(this.get_current_X_position()+get_actual_width() >= M.get_width()) this.setLayoutX(get_current_X_position()-get_actual_width());
        if(this.get_current_Y_position()+get_actual_height() >= M.get_height()) this.setLayoutY(get_current_Y_position()-get_actual_height());
    }

    @Override
    public void update(Map M, Sprite S) {
        bonus_effect(S,M);
    }

    //If player intersects bonus, player's life is restored
    public void bonus_effect(Sprite S, Map M) {
        this.getChildren().remove(_view);
        S.H.restore_life();
        generate(M);
    }


}
