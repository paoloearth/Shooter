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

        setScale(CustomSettings.PROJECTILE_SCALE);
        setSpeed(CustomSettings.PROJECTILE_SPEED);
        update_view();

        set_initial_and_translate_direction(S.get_currentDirection());

        moveTo(get_biased_starting_position(S));

        this.getChildren().add(getView());
    }

    private Coordinates get_biased_starting_position(Sprite S) {
        return new Coordinates(get_biased_x_position(S), get_biased_y_position(S));
    }

    private double get_biased_y_position(Sprite S) {
        return S.getFutureY() + this.biasY;
    }

    private double get_biased_x_position(Sprite S) {
        return S.getFutureX() + this.biasX;
    }

    private void translate(GameMap M)
    {
        if(illegal_move(M)) setIsDeadProperty(true);
        else moveTo(get_destination());
    }







    private void set_initial_and_translate_direction(Direction D) {
        biasX=biasY=0;
        switch (D) {
            case UP    ->  { set_biases(+(int)(getActualWidth()),-(int) (getActualHeight()/2)); set_deltaY(get_deltaY()- get_speed());}
            case DOWN  ->  { set_biases(+(int)(getActualWidth()),+ (int)(getActualHeight()*2)); set_deltaY(get_deltaY()+ get_speed());}
            case LEFT  ->  { set_biases(( 0), +(int) getActualHeight()/2);set_deltaX(get_deltaX()-get_speed());}
            case RIGHT ->  { set_biases((+(int)(getActualWidth()*2)), getActualHeight()/2);set_deltaX(get_deltaX()+get_speed());}
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
    public void defaultMovement(GameMap M){
        translate(M);
    };


    private void hit(Sprite S)
    {
        if(!isDead() && !Owner.equals(S.get_id()))
        {
            setIsDeadProperty(true);
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
    public boolean getPropertyToCheck(Tile t) {
        return t.is_passable_for_projectile;
    }
}
