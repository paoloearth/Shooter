package units.shooter_developers;

import javafx.util.Pair;

public class Projectile extends DynamicObject {
    private int biasX;
    private int biasY;
    private final String Owner;


    public Projectile(Pair<Double, Double> scaling_factor, String url, Sprite S)
    {
        super(scaling_factor, url);

        this.Owner = S.get_id();

        set_scale(CustomSettings.PROJECTILE_SCALE);
        set_speed(CustomSettings.PROJECTILE_SPEED);
        update_view();

        set_initial_and_translate_direction(S.get_current_direction());

        move_to(get_biased_starting_position(S));

        this.getChildren().add(get_view());
    }

    private Coordinates get_biased_starting_position(Sprite S) {
        return new Coordinates(get_biased_x_position(S), get_biased_y_position(S));
    }

    private double get_biased_y_position(Sprite S) {
        return S.get_future_y() + this.biasY;
    }

    private double get_biased_x_position(Sprite S) {
        return S.get_future_x() + this.biasX;
    }

    private void translate(GameMap M)
    {
        if(illegal_move(M)) set_is_dead_property(true);
        else move_to(get_destination());
    }







    private void set_initial_and_translate_direction(Direction D) {
        biasX=biasY=0;
        switch (D) {
            case UP    ->  { set_biases(+(int)(get_actual_width()),-(int) (get_actual_height()/2)); set_deltaY(get_deltaY()- get_speed());}
            case DOWN  ->  { set_biases(+(int)(get_actual_width()),+ (int)(get_actual_height()*2)); set_deltaY(get_deltaY()+ get_speed());}
            case LEFT  ->  { set_biases(( 0), +(int)get_actual_height()/2);set_deltaX(get_deltaX()-get_speed());}
            case RIGHT ->  { set_biases((+(int)(get_actual_width()*2)), get_actual_height()/2);set_deltaX(get_deltaX()+get_speed());}
        }
    }



    private void set_biases(int bias_x, int biasY)
    {
        setBiasX(bias_x);
        setBiasY(biasY);
    }


    @Override
    public void update( Sprite S) {
        if(intersect(S)) hit( S);
    }

    @Override
    public void default_movement(GameMap M){
        translate(M);
    };


    private void hit(Sprite S)
    {
        if(!is_dead() && !Owner.equals(S.get_id()))
        {
            set_is_dead_property(true);
            S.getHBar().damage();
        }
    }

    public int getBiasX() {
        return biasX;
    }

    public void setBiasX(int biasX) {
        this.biasX = biasX;
    }

    public int getBiasY() {
        return biasY;
    }

    public void setBiasY(int biasY) {
        this.biasY = biasY;
    }

    @Override
    public boolean get_property_to_check(Tile t) {
        return t.is_passable_for_projectile;
    }
}
