package units.shooter_developers;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Pair;


public class HealthBar extends Map_Object{
    Rectangle Red_rectangle;     // Lost life
    Rectangle Green_rectangle;   // Remaining life

    DoubleProperty Health;       // Bindings


    public HealthBar(Sprite S)
    {
        this._width  =  S.get_actual_width();                                             // Width of HB is = to width of Sprite
        this._height =  (int) (S.get_actual_height() * Custom_Settings.HB_PROPORTIONAL_WIDTH);   // Height of HB is 10%  of the height of Sprite

        // Create the 2 overlapping rectangles
        Red_rectangle = new Rectangle( 0, 0, _width, _height);
        Red_rectangle.setFill(Color.RED);

        Green_rectangle = new Rectangle( 0, 0, _width, _height);
        Green_rectangle.setFill(Color.LIMEGREEN);

        // Initialize Binding
        Health = new SimpleDoubleProperty(_width);

        // Make the binding
        Green_rectangle.widthProperty().bind(Health);

        move_to( new Pair<>(0, (int) (S.get_actual_height() * 1.1)));

        this.getChildren().addAll(Red_rectangle, Green_rectangle);

    }

    public void  restore_life(){
        Health.set(get_max_health());
        // When health goes below half, change bar colour to orange
        this.Green_rectangle.setFill(Color.LIMEGREEN);
    }


    public void damage()
    {
        Health.set(Health.getValue() - get_relative_damage());

        if (Health.getValue() <= get_max_health()/2)  // When health goes below half, change bar colour to orange
            this.Green_rectangle.setFill(Color.ORANGE);




    }

    public double get_relative_damage()
    {
        return get_max_health() * 0.25;
    }

    public double get_max_health()
    {
        return  _width;
    }



}