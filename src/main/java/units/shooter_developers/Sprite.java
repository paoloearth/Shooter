package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Pair;


public class Sprite extends Dynamic_Object {
    public String _id;                                                      // Player Name
    IntegerProperty _frame  = new SimpleIntegerProperty(0);        // Frame property to update the moving sprite
    HealthBar H;
    private boolean goNorth, goSouth, goEast, goWest;
    BooleanProperty _can_shoot = new SimpleBooleanProperty(true);

    //constructor
    public Sprite(Pane root, Map M, Pair<Double, Double> scaling_factor, String url, int _n_rows, int _n_cols, String id, Direction D)
    {
        super(scaling_factor, url, _n_rows, _n_cols);

        this._id = id;
        this._speed = (int) (Custom_Settings.PLAYER_SPEED*scaling_factor.getKey());
        this._scale = Custom_Settings.PLAYER_SCALE;
        this._type = "SPRITE";

        _width   = (int)   (this._picture.getWidth()  /  _n_cols);
        _height =  (int)   (this._picture.getHeight() / _n_rows);

        update_view();

        /* Add a triggered event to changes of locations, set the default direction*/
        ChangeListener<Object> updateImage = getListener();
        _current_direction.addListener(updateImage);
        _current_direction.setValue(D);


        H = getHealthBar();

        this._isDead.bind(H.Health.lessThanOrEqualTo(0));


        move_to(M.get_player_pixel_position(id));


        this.getChildren().addAll(H, _view);
        root.getChildren().add(this);
    }

    private HealthBar getHealthBar() {
        return new HealthBar(this);
    }

    //Change the picture of the Sprite in the SpriteSheet according to the direction
    private ChangeListener<Object> getListener() {

        return (ov, o, o2) -> this._view.setViewport(new Rectangle2D( _frame.get()*_width,
                                                                 _current_direction.get().getOffset() * _height,
                                                                     _width, _height));

    }

    //Update the movement in the right direction
    public void update_delta() {
        _deltaX = _deltaY = 0;
        if (goNorth) _deltaY -= _speed;
        if (goSouth) _deltaY += _speed;
        if (goEast)  _deltaX += _speed;
        if (goWest)  _deltaX -= _speed;
    }

    // HERE I TRY TO INFER THE DIRECTION SO THAT I CAN PICK THE RIGHT SPRITE
    public void update_get_direction(double destination_x, double destination_y)
    {
        Direction D;

        if(Math.abs(_deltaX) >0 || Math.abs(_deltaY)  > 0)
        {
            if (Math.abs(_deltaX) > Math.abs(_deltaY))      // NEED TO CHOOSE BETWEEN RIGHT OR LEFT
            {
                if (destination_x  < get_current_X_position()) D=Direction.LEFT;
                else                                           D=Direction.RIGHT;

            } else {                  // NEED TO CHOOSE BETWEEN UP OR DOWN
                if (destination_y  < get_current_Y_position()) D = Direction.UP;
                else                                           D= Direction.DOWN;
            }

            _current_direction.set(D);
        }
    }



    public void move(Map M) {
        update_delta();

        if (this._deltaX == 0 && this._deltaY == 0) return;

        int future_x = get_current_X_position() + _deltaX;
        int future_y = get_current_Y_position() + _deltaY;

        update_get_direction(future_x, future_y);

        //if(future_x <= 0 || future_y <= 0 || future_x+get_actual_width()>=M.get_width() || future_y+get_actual_height()>=R.get_height()) return;
        if(!(is_out_of_map(M) || illegal_move(M))) move_to(new Pair<>(future_x, future_y));

    }

    @Override
    protected  boolean illegal_move(Map M) {
        var left = get_future_x();
        int top = get_future_y();

        // Bounds da controllare
        var bottom = top +get_actual_height() ;
        var right  = left+get_actual_width() ;

        top += get_actual_height() * 2/3;

        int left_tile = left/M.getTileWidth();
        int rigth_tile = right/M.getTileWidth();

        int top_tile = top/M.getTileHeight();
        int bottom_tile = bottom/M.getTileHeight();


        if(left_tile < 0) left_tile = 0;
        if(rigth_tile > M.get_width()) rigth_tile = M.get_width();
        if(top_tile < 0) top_tile = 0;
        if(bottom_tile>M.get_height()) bottom_tile = M.get_height();



        for (int i =left_tile; i<= rigth_tile; i++)
        {
            for (int j=top_tile; j<= bottom_tile; j++)
            {
               // System.out.println("Ispezione posizione "+ i + " " + j +" at pos " + M.single_index(i,j));
                Tile b = M.get_tile_matrix().get(M.single_index(i,j));
                if(!b.is_passable.getValue()) {
                  //  System.out.println("Tile with code "+ b.get_pixel_of_block_position() + "is NOT passable" );
                    return true;
                }
            }
        }
        return  false;

    }

    //Return the bounds of the player used to check collision
    public Rectangle2D get_bounds()
    {
        return new Rectangle2D(get_current_X_position(),
                get_current_Y_position() +get_actual_height() * 0.15 ,
                get_actual_width(),
                get_actual_height() * 3.0/4);
    }

    /* Set the boolean attributes according to the key pressed*/
    public void setGoNorth(boolean goNorth) {
        this.goNorth = goNorth;
    }

    public void setGoSouth(boolean goSouth) {
        this.goSouth = goSouth;
    }

    public void setGoEast(boolean goEast) {
        this.goEast = goEast;
    }

    public void setGoWest(boolean goWest) {
        this.goWest = goWest;
    }



}