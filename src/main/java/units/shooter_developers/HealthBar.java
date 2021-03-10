package units.shooter_developers;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.shape.Rectangle;


public class HealthBar extends MapObject {

    private final Rectangle remaining_life_rectangle;
    private final DoubleProperty Health;

    public HealthBar(Sprite S)
    {
        super(S.get_actual_width(), get_HBar_height_proportional_to_S_height(S.get_actual_height()));

        Rectangle lost_life_rectangle = create_custom_outer_rectangle();
        remaining_life_rectangle = create_custom_inner_rectangle();

        Health = new SimpleDoubleProperty(get_width());
        remaining_life_rectangle.widthProperty().bind(Health);

        move_to(get_default_HBar_position(S));

        addNodes(lost_life_rectangle, remaining_life_rectangle);

    }

    public BooleanBinding is_remaining_life_zero() {
        return Health.lessThanOrEqualTo(0);
    }


    public void damage()
    {
        set_remaining_life_to(get_current_health() - get_relative_damage());
        if (less_thant_half_life_remain()) this.remaining_life_rectangle.setFill(Custom_Colors.HALF_LIFE);
    }

    private boolean less_thant_half_life_remain() {
        return get_current_health() <= get_max_health() / 2;
    }

    public void  restore_life(){
        set_remaining_life_to(get_max_health());
        this.remaining_life_rectangle.setFill(Custom_Colors.INNER_RECTANGLE);
    }


    protected double get_current_health() {
        return Health.get();
    }

    protected double get_relative_damage()
    {
        return get_max_health() * Custom_Settings.PERCENTAGE_DAMAGE_PER_SHOOT;
    }

    protected double get_max_health()
    {
        return  get_width();
    }

    private void set_remaining_life_to( double d)
    {
        Health.set(d);
    }

    private Rectangle create_custom_inner_rectangle() {
        final Rectangle remaining_life_rectangle;
        remaining_life_rectangle = new Rectangle( 0, 0, get_width(), get_height());
        remaining_life_rectangle.setFill(Custom_Colors.INNER_RECTANGLE);
        return remaining_life_rectangle;
    }

    private Rectangle create_custom_outer_rectangle() {
        var R = new Rectangle(0, 0, get_width(), get_height());
        R.setFill(Custom_Colors.OUTER_RECTANGLE);
        R.setStroke(Custom_Colors.OUTER_RECTANGLE_STROKE);
        return R;
    }

    private static int get_HBar_height_proportional_to_S_height(double sprite_height) {
        return (int) (sprite_height * Custom_Settings.HB_PROPORTIONAL_WIDTH);
    }


    private Coordinates get_default_HBar_position(Sprite S) {
        return new Coordinates(0, (S.get_actual_height() * 1.1));
    }




}
