package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bonus_Generator extends Pictured_Object{
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
        anim = new Object_Animation(get_view(), Duration.seconds(1),10,10,0,0,get_width(), get_height());
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    public void generate(Map M)
    {
        move_to(M.get_random_location());

        if (get_hitbox().is_out_of_map(M))  push_inside_border();

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            empty_Pane_from_ImageView(this);
            this.getChildren().add(get_view());
        }));

        timeline.setCycleCount(1);
        timeline.play();

    }

    // The commented part is the older one. I put (get_hitbox().is_out_of_map(M) in generate, and here I only do the pushing
    // Not completely satisfied since it could potentially push the heart in unreachable places(?)
    // Stick with it and se if it causes problems
    private void push_inside_border() {

            move_to(new Coordinates( get_current_X_position()-get_actual_width(),get_current_Y_position()-get_actual_height()));

       // if(this.get_current_X_position()+get_actual_width() >= M.get_width()) this.setLayoutX(get_current_X_position()-get_actual_width());
       // if(this.get_current_Y_position()+get_actual_height() >= M.get_height()) this.setLayoutY(get_current_Y_position()-get_actual_height());
    }

    @Override
    public void update(Map M, Sprite S) {
        bonus_effect(S,M);
    }

    //If player intersects bonus, player's life is restored
    public void bonus_effect(Sprite S, Map M) {
        empty_Pane_from_ImageView(this);
       // this.getChildren().remove(_view);
        S.H.restore_life();
        generate(M);
    }

    private void empty_Pane_from_ImageView(Pane P) {
        P.getChildren().removeIf(i -> i instanceof ImageView);
    }


}
