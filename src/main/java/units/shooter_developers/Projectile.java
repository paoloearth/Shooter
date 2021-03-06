package units.shooter_developers;

import javafx.util.Pair;

public class Projectile extends Dynamic_Object{
    int biasX;
    int biasY;
    String Owner;


    public Projectile(Pair<Double, Double> scaling_factor, String url, Sprite S)
    {
        super(scaling_factor, url);

        this.Owner = S.get_id();              // Save the shooter's ID

        set_scale(Custom_Settings.PROJECTILE_SCALE);

        set_speed(Custom_Settings.PROJECTILE_SPEED);

        update_view();

        set_initial_and_translate_direction(S.get_current_direction());

        move_to(new Coordinates(S.get_future_x() + this.biasX, S.get_future_y() + this.biasY));

        this.getChildren().add(get_view());
    }

    public void translate(GameMap M)
    {
        if( illegal_move(M,0, this)) set_is_dead_property(true);
        else move_to(new Coordinates(get_future_x(), get_future_y()));
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



    public void set_biases(int bias_x, int biasY)
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


    public void hit(Sprite S)
    {
        if(!is_dead() && !Owner.equals(S.get_id())) // If player has not hit anything yet
        {
            set_is_dead_property(true);        // Now the projectile has hit something
            S.getHBar().damage();            //Compute damage
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
