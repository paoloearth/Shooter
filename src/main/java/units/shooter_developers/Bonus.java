package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bonus extends PicturedObject {

    private final Timeline _waiting_time;
    private final GameMap _map;

    /* Constructor */
    public Bonus(Pane simulation_root, GameMap map, String url, int n_rows, int n_cols, int number_of_frames, Pair<Double, Double> scalingFactor)
    {
        super(scalingFactor,url,n_rows,n_cols);
        this._map = map;

        setScale(CustomSettings.HEART_SCALE);

        scalePicture();

        _waiting_time = waitSomeTimeBeforeDisplayingBonusAgain();
        createAndStartAnimation(number_of_frames,n_cols);

        generate();

        simulation_root.getChildren().add(this);

    }

    /* Movement & action management */
    private void pushInsideBorder() { moveTo(new Coordinates(getInMapXPosition(), getInMapYPosition())); }

    private double getInMapXPosition() { return getCurrentXPosition()- getScaledWidth(); }

    private double getInMapYPosition() { return getCurrentYPosition()- getScaledHeight(); }

    @Override
    public void action(Sprite S) {
        if(intersect(S)) bonus_effect(S);
    }

    private void bonus_effect(Sprite S) {
        emptyPaneFromImageView(this);
        S.getHBar().restoreLife();
        generate();
    }

    public void generate()
    {
        moveTo(_map.getRandomLocation());
        if (getHitbox().isOutOfMap(_map)) pushInsideBorder();
        _waiting_time.play();

    }

    /* Utils */
    private void emptyPaneFromImageView(Pane P) {
        P.getChildren().removeIf(i -> i instanceof ImageView);
    }

    /* Animations */
    private void createAndStartAnimation(int frames, int n_cols) {
        ObjectAnimation anim = new ObjectAnimation(getImageView(), Duration.seconds(1), frames, n_cols, 0, 0, get_width(), get_height());
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    private Timeline waitSomeTimeBeforeDisplayingBonusAgain() {
        return new Timeline(new KeyFrame(Duration.seconds(CustomSettings.BONUS_COOLDOWN),
                event -> {
                    emptyPaneFromImageView(this);
                    addNodes(getImageView());
                }));
    }

}
