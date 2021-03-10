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
    //JOSE: cambiare root per un nome più autospiegativo del tipo simulation_root
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

    //JOSE: questo metodo non fa niente di che e si usa una sola volta, in più ha un nome poco chiaro. Meglio inlinearlo in push_inside_border
    private Coordinates computeInMapPositionThroughShifiting() { return new Coordinates(getInMapXPosition(), getInMapYPosition()); }

    private double getInMapXPosition() { return getCurrentXPosition()- getActualWidth(); }

    private double getInMapYPosition() { return getCurrentYPosition()- getActualHeight(); }
    //JOSE: questi metodi usano elementi della classe genitore. Non avrebbero forse più senso in MapObject?

    @Override
    public void update(Sprite S) {
        if(intersect(S)) bonus_effect(S);
    }
    //JOSE: questo metodo applica l'effetto del bonus sul giocatore, quindi dovrebbe chiamarsi tipo applyTo().

    //JOSE: questo metodo dovrebbe essere privato.
    public void bonus_effect(Sprite S) {
        emptyPaneFromImageView(this);
        S.getHBar().restoreLife();
        generate(_map);
    }

    //JOSE: siccome la classe contiene la variabile _M si può togliere l'input
    //      non è un metodo "opaco" perchè è pubblico.
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
    //JOSE: questo metodo dovrebbe tornare la animazione o una roba del genere o, se fa modifiche su oggetti della classe,
    //      riceverli come input. Questo permetterebbe di renderlo statico e riusarlo se fosse neccesario, anche spingerlo
    //      ad una classe superiore eventualmente (se si volesse per esempio creare un altro tipo di bonus u oggetto animato).
    private  void createAndStartAnimation() {
        ObjectAnimation anim = new ObjectAnimation(getView(), Duration.seconds(1), 10, 10, 0, 0, get_width(), get_height());
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    //JOSE: Questo metodo da più chiarezza al codice forse se si inlinea o se si rende statico e passando _timeline come input.
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
