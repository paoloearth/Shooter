package units.shooter_developers;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;


public class HealthBar extends MapObject {

    private final Rectangle remainingLifeRectangle;
    private final DoubleProperty health;

    /* Constructors */
    public HealthBar(Sprite S)
    {
        super(S.getActualWidth(), get_HBar_height_proportional_to_S_height(S.getActualHeight()));

        final Rectangle lostLifeRectangle = createCustomOuterRectangle();
        remainingLifeRectangle = createCustomInnerRectangle();

        health = new SimpleDoubleProperty(get_width());
        remainingLifeRectangle.widthProperty().bind(health);

        moveTo(getDefaultHBarPosition(S));

        addNodes(lostLifeRectangle, remainingLifeRectangle);
    }


    /* Damage/Life handling */
    protected final void damage()
    {
        setRemainingLifeTo(getCurrentHealth() - getRelativeDamage());
        if (lessThantHalfLifeRemains()) this.remainingLifeRectangle.setFill(CustomColors.HALF_LIFE);
    }

    protected final void restoreLife(){
        setRemainingLifeTo(getMaxHealth());
        this.remainingLifeRectangle.setFill(CustomColors.INNER_RECTANGLE);
    }
    private  boolean lessThantHalfLifeRemains() {
        return getCurrentHealth() <= getMaxHealth() / 2;
    }

    protected final BooleanBinding isRemainingLifeZero() { return health.lessThanOrEqualTo(0); }

    protected final double getCurrentHealth() {
        return health.get();
    }

    private double getRelativeDamage()
    {
        return getMaxHealth() * CustomSettings.PERCENTAGE_DAMAGE_PER_SHOOT;
    }

    private double getMaxHealth()
    {
        return  get_width();
    }

    private void setRemainingLifeTo(double d)
    {
        health.set(d);
    }




    /* Graphical components */
    private  Rectangle createCustomInnerRectangle() {
        final Rectangle remainingLifeRectangle;
        remainingLifeRectangle = new Rectangle( 0, 0, get_width(), get_height());
        remainingLifeRectangle.setFill(CustomColors.INNER_RECTANGLE);
        return remainingLifeRectangle;
    }
    private  Rectangle createCustomOuterRectangle() {
        var R = new Rectangle(0, 0, get_width(), get_height());
        R.setFill(CustomColors.OUTER_RECTANGLE);
        R.setStroke(CustomColors.OUTER_RECTANGLE_STROKE);
        return R;
    }
    private static int get_HBar_height_proportional_to_S_height(double spriteHeight) {
        return (int) (spriteHeight * CustomSettings.HB_PROPORTIONAL_WIDTH);}

    private static Coordinates getDefaultHBarPosition(Sprite S) {
        return new Coordinates(0, (S.getActualHeight() * 1.1));
    }




}
