package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bonus_Generator extends Pictured_Object{

    private final Timeline timeline;

    public Bonus_Generator(Pane root, Map M, String url, int _n_rows, int _n_cols, Pair<Double, Double> scaling_factor)
    {
        super(scaling_factor,url,_n_rows,_n_cols);

        set_type(Custom_Settings.BONUS);
        set_scale(Custom_Settings.HEART_SCALE);

        update_view();

        timeline = get_bonus_animation();
        create_and_start_animation();

        generate(M);

        root.getChildren().add(this);

    }



    public void generate(Map M)
    {
        move_to(M.get_random_location());

        if (get_hitbox().is_out_of_map(M))
            push_inside_border();

        reproduce_animation();

    }
    private void push_inside_border() { move_to(compute_inmap_position_through_shifiting()); }

    private Coordinates compute_inmap_position_through_shifiting() { return new Coordinates(get_inmap_X_position(), get_inmap_Y_position()); }

    @Override
    public void update(Map M, Sprite S) {
        bonus_effect(S,M);
    }

    public void bonus_effect(Sprite S, Map M) {
        empty_Pane_from_ImageView(this);
        S.H.restore_life();
        generate(M);
    }

    private void empty_Pane_from_ImageView(Pane P) {
        P.getChildren().removeIf(i -> i instanceof ImageView);
    }


    /* Animations */
    private void create_and_start_animation() {
        Object_Animation anim = new Object_Animation(get_view(), Duration.seconds(1), 10, 10, 0, 0, get_width(), get_height());
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    private void reproduce_animation() {
        timeline.setCycleCount(1);
        timeline.play();
    }

    private Timeline get_bonus_animation() {
        return new Timeline(new KeyFrame(Duration.seconds(Custom_Settings.BONUS_COOLDOWN),
                event -> {
                    empty_Pane_from_ImageView(this);
                    add_nodes(get_view());
                }));
    }

    /* Getters */
    private double get_inmap_X_position() { return get_current_X_position()-get_actual_width(); }

    private double get_inmap_Y_position() { return get_current_Y_position()-get_actual_height(); }


}
