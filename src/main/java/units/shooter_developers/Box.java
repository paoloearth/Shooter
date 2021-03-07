package units.shooter_developers;

public class Box{


    public enum BOUNDS {TOP, LEFT, BOTTOM, RIGHT}
    double [] box ;
    int    [] tile;

    public Box(double top, double left, double width, double height) { box = new double[]{top, left,  top+height,left+width}; }

    public void shrink_height_by(double v) {
        set_top(get_top_box()+get_height() * v);
    }



    public  void  compute_tiles_bounds(GameMap M) {

        int top_tile = (int) (get_top_box() / M.getTileHeight());
        if (top_tile < 0) top_tile = 0;

        int bottom_tile = (int) (get_bottom_box() / M.getTileHeight());
        if (bottom_tile > M.get_height()) bottom_tile = (int) M.get_height();

        int left_tile = (int) (get_left_box() / M.getTileWidth());
        if (left_tile < 0) left_tile = 0;

        int right_tile = (int) (get_right_box()/ M.getTileWidth());
        if (right_tile > M.get_width()) right_tile = (int) M.get_width();


        tile = new int[]{top_tile, left_tile, bottom_tile, right_tile};

    }


    protected boolean is_out_of_map(GameMap M) {
        return  check_top_and_left() || check_bottom_and_right(M);
    }

    boolean intersect(Box B)
    {
        return B.get_right_box() > get_left_box()&& B.get_bottom_box() > get_top_box() && B.get_left_box() < get_right_box() && B.get_top_box() < get_bottom_box();
    }


    private boolean check_top_and_left() {
        return get_top_box()<= 0 ||  get_left_box() <= 0;
    }

    private boolean check_bottom_and_right(GameMap M) {
        return get_right_box() >= M.get_width() || get_bottom_box() >= M.get_height();
    }



    public boolean  performs_check(GameMap M, Dynamic_Object D)
    {
        for (int i =get_left_tile(); i<= get_right_tile(); i++)
        {
            for (int j=get_top_tile(); j<= get_bottom_tile(); j++)
            {
                Tile t =M.get_tile_matrix().get(M.single_index(i, j));
                var passable = D.get_property_to_check(t);
                if(!passable) return true;
            }
        }
        return false;
    }

    /* Setters */
    public void set_top(double top)
    {
        box[BOUNDS.TOP.ordinal()] = top;
    }
    public void set_bottom(double bottom)
    {
        box[BOUNDS.BOTTOM.ordinal()] = bottom;
    }
    public void set_left(double left)
    {
        box[BOUNDS.LEFT.ordinal()] = left;
    }
    public void set_right(double right)
    {
        box[BOUNDS.RIGHT.ordinal()] = right;
    }

    /* Getters */
    public double get_top_box()
    {
        return box[BOUNDS.TOP.ordinal()];
    }
    public double get_bottom_box()
    {
        return box[BOUNDS.BOTTOM.ordinal()];
    }
    public double get_left_box()
    {
        return box[BOUNDS.LEFT.ordinal()];
    }
    public double get_right_box()
    {
        return box[BOUNDS.RIGHT.ordinal()];
    }
    public double get_height()
    {
        return box[BOUNDS.BOTTOM.ordinal()]- box[BOUNDS.TOP.ordinal()];
    }
    public double get_width()
    {
        return box[BOUNDS.RIGHT.ordinal()]- box[BOUNDS.LEFT.ordinal()];
    }

    public int get_top_tile()
    {
        return tile[BOUNDS.TOP.ordinal()];
    }

    public int get_bottom_tile()
    {
        return tile[BOUNDS.BOTTOM.ordinal()];
    }
    public int get_left_tile()
    {
        return tile[BOUNDS.LEFT.ordinal()];
    }
    public int get_right_tile()
    {
        return tile[BOUNDS.RIGHT.ordinal()];
    }





}
