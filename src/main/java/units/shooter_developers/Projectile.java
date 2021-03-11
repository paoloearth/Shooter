//JOSE: classe visitata

package units.shooter_developers;

import javafx.util.Pair;

public class Projectile extends DynamicObject {

    private double _biasX;
    private double _biasY;
    private final String Owner;

    /* Constructor */
    public Projectile(Pair<Double, Double> scalingFactor, String url, Sprite S)
    {
        super(scalingFactor, url);

        this.Owner = S.get_id();

        applyCustomScaleToObject(CustomSettings.PROJECTILE_SCALE);
        setSpeed(CustomSettings.PROJECTILE_SPEED);

        setInitialAndTranslateDirection(S.getCurrentDirection());

        moveTo(getBiasedStartingPosition(S));

        addNodes(getPicture());
    }

    /* Movement & action management */
    private void translate(GameMap M)
    {
        if(illegalMove(M)) getRemoveProperty(true);
        else moveTo(getFutureCoordinates());
    }

    @Override
    public void action(Sprite S) {
        if(intersect(S)) hit( S);
    }

    @Override
    public void defaultMovement(GameMap M){
        translate(M);
    }

    @Override
    public boolean checkIfPassable(Tile t) { return t.isPassableForProjectile(); }

    private void hit(Sprite S)
    {
        if(!hasToBeRemoved() && !Owner.equals(S.get_id()))
        {
            getRemoveProperty(true);
            S.getHBar().applyDamage();
        }
    }

    /* Utils */
    private void setInitialAndTranslateDirection(Direction D) {
        _biasX = _biasY =0;
        switch (D) {
            case UP    ->  { set_biases(+(getScaledWidth()),-(getScaledHeight()/2)); setDeltaY(- getSpeed());}
            case DOWN  ->  { set_biases(+(getScaledWidth()),+ (getScaledHeight()*2)); setDeltaY(getSpeed());}
            case LEFT  ->  { set_biases(( 0), + getScaledHeight()/2);setDeltaX(-getSpeed());}
            case RIGHT ->  { set_biases((+(getScaledWidth()*2)), getScaledHeight()/2);setDeltaX(+getSpeed());}
        }
    }
    private double get_biased_y_position(Sprite S) { return S.getFutureY() + _biasY; }

    private double get_biased_x_position(Sprite S) {
        return S.getFutureX() + _biasX;
    }

    private Coordinates getBiasedStartingPosition(Sprite S) { return new Coordinates(get_biased_x_position(S), get_biased_y_position(S)); }

    private void set_biases(double biasX, double biasY)
    {
        set_biasX(biasX);
        set_biasY(biasY);
    }

    public void set_biasX(double biasX) {
        this._biasX = biasX;
    }

    public void set_biasY(double biasY) {
        this._biasY = biasY;
    }


}
