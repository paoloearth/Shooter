package units.shooter_developers;

import javafx.geometry.Rectangle2D;
import javafx.util.Pair;

public class Projectile extends Dynamic_Object{
    int biasX;
    int biasY;
    String Owner;


    public Projectile(Pair<Double, Double> scaling_factor, String url, Sprite S)
    {
        super(scaling_factor, url);

        this._type ="PROJECTILE";        // Set the type
        this.Owner = S._id;             // Save the shooter's ID
        this._scale = Custom_Settings.PROJECTILE_SCALE;
        this._speed = (int) (Custom_Settings.PROJECTILE_SPEED*scaling_factor.getKey());    // Set characteristics of the projectile

        _width   = (int)   (this._picture.getWidth()  /  _n_cols);
        _height =  (int)   (this._picture.getHeight() / _n_rows);

        update_view();

        update_deltas(S);

        // Position the
        move_to(new Pair<>(S.get_future_x() + this.biasX, S.get_future_y() + this.biasY));

        this.getChildren().add(this._view);
    }

    private void update_deltas(Sprite s) {
        _deltaY = _deltaX = 0;
        biasX=biasY=0;
        switch (s._current_direction.getValue()) {
            case UP    ->  {this._deltaY -= _speed;
                this.biasY= -(int) (get_actual_height()/2);
                this.biasX=+(int)(get_actual_width());}
            case DOWN  ->  {this._deltaY += _speed;
                this.biasY= + (int)(get_actual_height()*2);
                this.biasX=+(int)(get_actual_width()); }
            case LEFT  ->  {this._deltaX -= _speed;
                this.biasY=+(int)(get_actual_height()/2);
                this.biasX= 0; }
            case RIGHT ->  {this._deltaX += _speed;
                this.biasY= (int)(get_actual_height()/2);
                this.biasX=+(int)(get_actual_width()*2);}
        }
    }

    @Override
    protected  boolean illegal_move(Map M) {

        int left = get_future_x();
        int top = get_future_y();

        // Bounds da controllare
        var bottom = top+get_actual_height() ;
        var right = left+get_actual_width() ;

        int left_tile = left/M.getTileWidth();
        int rigth_tile = right/M.getTileWidth();

        int top_tile = top/M.get_height();
        int bottom_tile = bottom/M.get_height();


        if(left_tile < 0) left_tile = 0;
        if(rigth_tile > M.get_width()) rigth_tile = M.get_width();
        if(top_tile < 0) top_tile = 0;
        if(bottom_tile>M.get_height()) bottom_tile = M.get_height();


        for (int i =left_tile; i<= rigth_tile; i++)
        {
            for (int j=top_tile; j<= bottom_tile; j++)
            {
                Tile t = M.get_tile_matrix().get(M.single_index(i,j));
                if(t.not_passable_for_p.getValue()) return true;
            }
        }
        return  false;

    }


    @Override
    public Rectangle2D get_bounds() {
        return new Rectangle2D(get_current_X_position(), get_current_Y_position(), get_actual_width(), get_actual_height());
    }

    public void hit(Sprite S)
    {
        if(!_isDead.getValue() && !Owner.equals(S._id)) // If player has not hit anything yet
        {
            _isDead.setValue(true);        // Now the projectile has hit something
            S.H.damage();                  //Compute damage
        }
    }

}
