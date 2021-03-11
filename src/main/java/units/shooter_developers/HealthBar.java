package units.shooter_developers;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;


public class HealthBar extends MapObject {

    private final Rectangle remainingLifeRectangle;
    private final DoubleProperty healthPercentage;

    /* Constructors */
    public HealthBar(Sprite S)
    {
        super(S.getScaledWidth(), getHBarHeightProportionalToSpriteHeight(S.getScaledHeight()));

        final Rectangle lostLifeRectangle = createCustomOuterRectangle();
        remainingLifeRectangle = createCustomInnerRectangle();

        healthPercentage = new SimpleDoubleProperty(get_width());
        remainingLifeRectangle.widthProperty().bind(healthPercentage);

        moveTo(getDefaultHBarPosition(S));

        addNodes(lostLifeRectangle, remainingLifeRectangle);
    }

    /* Damage/Life handling */
    protected final void applyDamage()
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

    protected final BooleanBinding isRemainingLifeZero() { return healthPercentage.lessThanOrEqualTo(0); }

    protected final double getCurrentHealth() {
        return healthPercentage.get();
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
        healthPercentage.set(d);
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

    private static double getHBarHeightProportionalToSpriteHeight(double spriteHeight) {
        return  (spriteHeight * CustomSettings.HB_PROPORTIONAL_WIDTH);}

    private static Coordinates getDefaultHBarPosition(Sprite S) {
        return new Coordinates(0, (S.getScaledHeight() * 1.1));
    }

}
