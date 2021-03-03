package units.shooter_developers;

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
        move_to(new Coordinates(S.get_future_x() + this.biasX, S.get_future_y() + this.biasY));

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

        double left = get_future_x();
        double top = get_future_y();

        // Bounds da controllare
        var bottom = top+get_actual_height() ;
        var right = left+get_actual_width() ;

        int left_tile = (int) (left/M.getTileWidth());
        int rigth_tile = (int) (right/M.getTileWidth());

        int top_tile = (int) (top/M.getTileHeight());
        int bottom_tile = (int) (bottom/M.getTileHeight());


        if(left_tile < 0) left_tile = 0;
        if(rigth_tile > M.get_width()) rigth_tile = (int) M.get_width();
        if(top_tile < 0) top_tile = 0;
        if(bottom_tile>M.get_height()) bottom_tile = (int) M.get_height();

        for (int i =left_tile; i<= rigth_tile; i++)
        {
            for (int j=top_tile; j<= bottom_tile; j++)
            {
                Tile t = M.get_tile_matrix().get(M.single_index(i,j));
                if(t.not_passable_for_p) return true;
            }
        }
        return false;

    }

    @Override
    public void update(Map M, Sprite S) {
        hit(S);
    }


    public void translate(Map M)
    {
        if(is_out_of_map(M) || illegal_move(M)) _isDead.setValue(true);
        else move_to(new Coordinates(get_future_x(), get_future_y()));
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
