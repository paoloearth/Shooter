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

    private boolean goNorth, goSouth, goEast, goWest;
    BooleanProperty _can_shoot = new SimpleBooleanProperty(true);

    //constructor
    public Sprite(Pane root, Room R, Pair<Double, Double> scaling_factor, String url, int _n_rows, int _n_cols, String id, Direction D)
    {
        super(scaling_factor, url, _n_rows, _n_cols);

        this._id = id;
        this._speed = (int) (Custom_Settings.PLAYER_SPEED*scaling_factor.getKey());
        this._scale = Custom_Settings.PLAYER_SCALE;
        this.type = "SPRITE";

        _width   = (int)   (this._picture.getWidth()  /  _n_cols);
        _height =  (int)   (this._picture.getHeight() / _n_rows);

        update_view();

        /* Add a triggered event to changes of locations, set the default direction*/
        ChangeListener<Object> updateImage = getListener();
        _current_direction.addListener(updateImage);
        _current_direction.setValue(D);


        H = getHealthBar();

        this.is_dead.bind(H.Health.lessThanOrEqualTo(0));


        move_to(R.get_player_pixel_position(id));


        this.getChildren().addAll(H, _view);
        root.getChildren().add(this);
    }


    @Override
    protected  boolean illegal_move(Room R) {
        var left = get_future_x();
        int top = get_future_y();

        // Bounds da controllare
        var bottom = top+get_actual_height() ;
        var right = left+get_actual_width() ;

        top += get_actual_height() * 2/3;

        int left_tile = left/R.getBlockWidth();
        int rigth_tile = right/R.getBlockWidth();

        int top_tile = top/R.getBlockHeight();
        int bottom_tile = bottom/R.getBlockHeight();


        if(left_tile < 0) left_tile = 0;
        if(rigth_tile > R.get_width()) rigth_tile = R.get_width();
        if(top_tile < 0) top_tile = 0;
        if(bottom_tile>R.get_height()) bottom_tile = R.get_height();



        for (int i =left_tile; i<= rigth_tile; i++)
        {
            for (int j=top_tile; j<= bottom_tile; j++)
            {
                Block b = R.get_block_matrix().get(R.single_index(i,j));
                if(!b.is_passable.getValue()) {
                    return true;
                }
            }
        }
        return  false;

    }



}
