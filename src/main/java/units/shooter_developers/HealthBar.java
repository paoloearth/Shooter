//JOSE: classe visitata

package units.shooter_developers;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;


public class HealthBar extends MapObject {

    private final Rectangle remainingLifeRectangle;
    private final DoubleProperty health;

    public HealthBar(Sprite S)
    {
        super(S.getActualWidth(), get_HBar_height_proportional_to_S_height(S.getActualHeight()));

        Rectangle lostLifeRectangle = create_custom_outer_rectangle();
        remainingLifeRectangle = create_custom_inner_rectangle();

        health = new SimpleDoubleProperty(get_width());
        remainingLifeRectangle.widthProperty().bind(health);

        moveTo(getDefaultHBarPosition(S));

        addNodes(lostLifeRectangle, remainingLifeRectangle);

    }

    //JOSE: come suggeriento, forse un nome un po più intuitivo, del tipo: isLifeSpent
    protected BooleanBinding isRemainingLifeZero() { return health.lessThanOrEqualTo(0); }

    //JOSE: intendo che questo metodo applica danneggio, per chiarezza lo chiamerei una roba del tipo: applyDamage
    public void damage()
    {
        setRemainingLifeTo(getCurrentHealth() - getRelativeDamage());
        if (lessThantHalfLifeRemains()) this.remainingLifeRectangle.setFill(CustomColors.HALF_LIFE);
    }

    private boolean lessThantHalfLifeRemains() {
        return getCurrentHealth() <= get_max_health() / 2;
    }

    public void  restore_life(){
        setRemainingLifeTo(get_max_health());
        this.remainingLifeRectangle.setFill(CustomColors.INNER_RECTANGLE);
    }


    protected double getCurrentHealth() {
        return health.get();
    }

    protected double getRelativeDamage()
    {
        return get_max_health() * CustomSettings.PERCENTAGE_DAMAGE_PER_SHOOT;
    }

    protected double get_max_health()
    {
        return  get_width();
    }

    private void setRemainingLifeTo(double d)
    {
        health.set(d);
    }

    private Rectangle create_custom_inner_rectangle() {
        final Rectangle remaining_life_rectangle;
        remaining_life_rectangle = new Rectangle( 0, 0, get_width(), get_height());
        remaining_life_rectangle.setFill(CustomColors.INNER_RECTANGLE);
        return remaining_life_rectangle;
    }

    private Rectangle create_custom_outer_rectangle() {
        var R = new Rectangle(0, 0, get_width(), get_height());
        R.setFill(CustomColors.OUTER_RECTANGLE);
        R.setStroke(CustomColors.OUTER_RECTANGLE_STROKE);
        return R;
    }

    //JOSE: Non avrebbe più senso tornare un double visto che parliamo di width (la cui quantità è double)?
    private static int get_HBar_height_proportional_to_S_height(double sprite_height) {
        return (int) (sprite_height * CustomSettings.HB_PROPORTIONAL_WIDTH);
    }


    private Coordinates getDefaultHBarPosition(Sprite S) {
        return new Coordinates(0, (S.getActualHeight() * 1.1));
    }




}
