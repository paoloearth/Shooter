//JOSE: classe visitata
//  -Il generatore dovrebbe generare i bonus direttamente all'interno della mappa, senza dovere fare ulteriori
//   verifiche e spostamenti di posizione.
//  -Sono consapevole di avere commentate cose su quasi tutti i metodi. In generale penso che usare metodi "opachi"
//   che trasformino variabili della classe senza farsi vedere negli input renda difficile la lettura del codice.

package units.shooter_developers;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;

//JOSE: In realtà questo oggetto non è un generatore di bonus, ma il bonus stesso, quindi gli cambierei il nome a tipo BonusObject
public class BonusGenerator extends Pictured_Object{

    private final Timeline timeline;
    private final GameMap _M;
    //JOSE: cambiare M per un nome più autospiegativo

    //JOSE: cambiare root per un nome più autospiegativo del tipo simulation_root
    public BonusGenerator(Pane root, GameMap M, String url, int _n_rows, int _n_cols, Pair<Double, Double> scaling_factor)
    {
        super(scaling_factor,url,_n_rows,_n_cols);
        _M = M;

        set_scale(CustomSettings.HEART_SCALE);

        update_view();

        timeline = get_bonus_animation();
        create_and_start_animation();

        generate(M);

        root.getChildren().add(this);

    }


    //JOSE: siccome la classe contiene la variabile _M si può togliere l'input
    public void generate(GameMap M)
    {
        moveTo(M.get_random_location());

        //JOSE: perchè si genera fuori dalla mappa? questa verifica si puó rimuovere facendo che il generatore
        //      generi coordinate all'interno della mappa
        if (get_hitbox().is_out_of_map(M))
            push_inside_border();

        reproduce_animation();

    }

    private void push_inside_border() { moveTo(compute_inmap_position_through_shifiting()); }

    //JOSE: questo metodo non fa niente di che e si usa una sola volta, in più ha un nome poco chiaro. Meglio inlinearlo in push_inside_border
    private Coordinates compute_inmap_position_through_shifiting() { return new Coordinates(get_inmap_X_position(), get_inmap_Y_position()); }

    @Override
    public void update(Sprite S) {
        if(intersect(S)) bonus_effect(S);
    }
    //JOSE: questo metodo applica l'effetto del bonus sul giocatore, quindi dovrebbe chiamarsi tipo applyTo().

    //JOSE: questo metodo dovrebbe essere privato.
    public void bonus_effect(Sprite S) {
        empty_Pane_from_ImageView(this);
        S.getHBar().restore_life();
        generate(_M);
    }

    //JOSE: questo metodo si capisce meglio se si inlinea dove viene usato, anche se si usa due volte è un'operazione
    //      talmente microscopica che non si può considerare codice ripetuto.
    private void empty_Pane_from_ImageView(Pane P) {
        P.getChildren().removeIf(i -> i instanceof ImageView);
    }


    /* Animations */
    //JOSE: questo metodo dovrebbe tornare la animazione o una roba del genere o, se fa modifiche su oggetti della classe,
    //      riceverli come input. Questo permetterebbe di renderlo statico e riusarlo se ci fosse neccesario, anche spingerlo
    //      ad una classe superiore eventualmente (se si volesse per esempio creare un altro tipo di bonus).
    private void create_and_start_animation() {
        ObjectAnimation anim = new ObjectAnimation(get_view(), Duration.seconds(1), 10, 10, 0, 0, get_width(), get_height());
        anim.setCycleCount(Animation.INDEFINITE);
        anim.play();
    }

    //JOSE: Questo metodo da più chiarezza al codice se si inlinea.
    private void reproduce_animation() {
        timeline.setCycleCount(1);
        timeline.play();
    }

    //JOSE: Con "get" sembra si ottenga qualcosa dalla classe quando in realtá si sta  generando. Meglio
    //      generateBonusAnimation()
    private Timeline get_bonus_animation() {
        return new Timeline(new KeyFrame(Duration.seconds(CustomSettings.BONUS_COOLDOWN),
                event -> {
                    empty_Pane_from_ImageView(this);
                    addNodes(get_view());
                }));
    }

    /* Getters */
    private double get_inmap_X_position() { return getCurrentXPosition()- getActualWidth(); }
    private double get_inmap_Y_position() { return getCurrentYPosition()- getActualHeight(); }
    //JOSE: questi metodi usano metodi della classe genitore. Non avrebbero forse più senso in MapObject?


}
