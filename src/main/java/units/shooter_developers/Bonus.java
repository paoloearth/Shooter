package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

public class Bonus extends PicturedObject {

    private final Timeline _waitingTime;
    private final GameMap _map;

    /* Constructor */
    public Bonus(Pane simulationRoot, GameMap map, String url, int nRows, int nCols, int numberOfFrames, Pair<Double, Double> scalingFactor)
    {
        super(scalingFactor,url,nRows,nCols);
        this._map = map;

        setScale(CustomSettings.HEART_SCALE);

        scalePicture();

        _waitingTime = waitSomeTimeBeforeDisplayingBonusAgain();
        createAndStartAnimation(numberOfFrames,nCols);

        generate();

        simulationRoot.getChildren().add(this);

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
        _waitingTime.play();

    }

    /* Utils */
    private void emptyPaneFromImageView(Pane P) {
        P.getChildren().removeIf(i -> i instanceof ImageView);
    }

    /* Animations */
    private void createAndStartAnimation(int frames, int n_cols) {
        ObjectAnimation anim = new ObjectAnimation(getPicture(), Duration.seconds(1), frames, n_cols, 0, 0, get_width(), get_height());
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    private Timeline waitSomeTimeBeforeDisplayingBonusAgain() {
        return new Timeline(new KeyFrame(Duration.seconds(CustomSettings.BONUS_COOLDOWN),
                event -> {
                    emptyPaneFromImageView(this);
                    addNodes(getPicture());
                }));
    }

}
