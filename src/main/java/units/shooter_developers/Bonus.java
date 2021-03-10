package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bonus extends PicturedObject {

    private  final Timeline _timeline;
    private final GameMap _map;

    /* Constructor */
    public Bonus(Pane root, GameMap map, String url, int n_rows, int n_cols, Pair<Double, Double> scalingFactor)
    {
        super(scalingFactor,url,n_rows,n_cols);
        this._map = map;

        setScale(CustomSettings.HEART_SCALE);

        updateView();

        _timeline = getBonusAnimation();
        createAndStartAnimation();

        generate(map);

        root.getChildren().add(this);

    }


    /* Movement & action management */
    private void pushInsideBorder() { moveTo(computeInMapPositionThroughShifiting()); }

    private Coordinates computeInMapPositionThroughShifiting() { return new Coordinates(getInMapXPosition(), getInMapYPosition()); }

    private double getInMapXPosition() { return getCurrentXPosition()- getActualWidth(); }

    private double getInMapYPosition() { return getCurrentYPosition()- getActualHeight(); }

    @Override
    public void update(Sprite S) {
        if(intersect(S)) bonus_effect(S);
    }

    public void bonus_effect(Sprite S) {
        emptyPaneFromImageView(this);
        S.getHBar().restoreLife();
        generate(_map);
    }

    public void generate(GameMap M)
    {
        moveTo(M.getRandomLocation());
        if (getHitbox().isOutOfMap(M)) pushInsideBorder();
        reproduceAnimation();

    }

    /* Utils */
    private void emptyPaneFromImageView(Pane P) {
        P.getChildren().removeIf(i -> i instanceof ImageView);
    }


    /* Animations */
    private  void createAndStartAnimation() {
        ObjectAnimation anim = new ObjectAnimation(getView(), Duration.seconds(1), 10, 10, 0, 0, get_width(), get_height());
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    private void reproduceAnimation() {
        _timeline.setCycleCount(1);
        _timeline.play();
    }

    private Timeline getBonusAnimation() {
        return new Timeline(new KeyFrame(Duration.seconds(CustomSettings.BONUS_COOLDOWN),
                event -> {
                    emptyPaneFromImageView(this);
                    addNodes(getView());
                }));
    }





}
