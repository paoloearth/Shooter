package units.shooter_developers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
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

        this.is_dead.bind(H.Health.lessThanOrEqualTo(0));


        move_to(M.get_player_pixel_position(id));


        this.getChildren().addAll(H, _view);
        root.getChildren().add(this);
    }

    private HealthBar getHealthBar() {
        return new HealthBar(this);
    }

    //Change the picture of the Sprite in the SpriteSheet according to the direction
    private ChangeListener<Object> getListener() {

        return (ov, o, o2) -> this._view.setViewport(new Rectangle2D( _frame.get()*_width, _current_direction.get().getOffset() * _height, _width, _height));

    }


    @Override
    protected  boolean illegal_move(Map M) {
        var left = get_future_x();
        int top = get_future_y();

        // Bounds da controllare
        var bottom = top+get_actual_height() ;
        var right = left+get_actual_width() ;

        top += get_actual_height() * 2/3;

        int left_tile = left/M.getBlockWidth();
        int rigth_tile = right/M.getBlockWidth();

        int top_tile = top/M.getBlockHeight();
        int bottom_tile = bottom/M.getBlockHeight();


        if(left_tile < 0) left_tile = 0;
        if(rigth_tile > M.get_width()) rigth_tile = M.get_width();
        if(top_tile < 0) top_tile = 0;
        if(bottom_tile>M.get_height()) bottom_tile = M.get_height();



        for (int i =left_tile; i<= rigth_tile; i++)
        {
            for (int j=top_tile; j<= bottom_tile; j++)
            {
                Block b = M.get_block_matrix().get(M.single_index(i,j));
                if(!b.is_passable.getValue()) {
                    return true;
                }
            }
        }
        return  false;

    }



}
