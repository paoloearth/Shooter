package units.shooter_developers;


public class Box{


    public enum BOUNDS {TOP, LEFT, BOTTOM, RIGHT}
    double [] box ;
    int    [] tile;

    public Box(double top, double left, double width, double height)
    {
       box = new double[]{top, left,  top+height,left+width};
    }

    public void shrink_height_by(double v) {
        box[BOUNDS.TOP.ordinal()] += get_height() * v;
    }

    public double get_height()
    {
        return box[BOUNDS.BOTTOM.ordinal()]- box[BOUNDS.TOP.ordinal()];
    }
    public double get_width()
    {
        return box[BOUNDS.RIGHT.ordinal()]- box[BOUNDS.LEFT.ordinal()];
    }

    public  void  compute_tiles_bounds(Map M) {

        int top_tile = (int) (box[BOUNDS.TOP.ordinal()] / M.getTileHeight());
        int bottom_tile = (int) (box[BOUNDS.BOTTOM.ordinal()] / M.getTileHeight());

        int left_tile = (int) (box[BOUNDS.LEFT.ordinal()] / M.getTileWidth());
        int right_tile = (int) (box[BOUNDS.RIGHT.ordinal()] / M.getTileWidth());


        if (left_tile < 0) left_tile = 0;
        if (right_tile > M.get_width()) right_tile = (int) M.get_width();
        if (top_tile < 0) top_tile = 0;
        if (bottom_tile > M.get_height()) bottom_tile = (int) M.get_height();

        tile = new int[]{top_tile, left_tile, bottom_tile, right_tile};

    }

    /*Check if an element of the map is out of it*/
    protected boolean is_out_of_map(Map M) {
        return  check_top_and_left() || check_bottom_and_right(M);


    }

    private boolean check_top_and_left() {
        return box[BOUNDS.TOP.ordinal()] <= 0 ||  box[BOUNDS.LEFT.ordinal()] <= 0;
    }

    private boolean check_bottom_and_right(Map M) {
        return box[BOUNDS.RIGHT.ordinal()] >= M.get_width() || box[BOUNDS.BOTTOM.ordinal()] >= M.get_height();
    }



    public boolean  performs_check(Map M, String type)
    {
        for (int i =tile[BOUNDS.LEFT.ordinal()]; i<= tile[BOUNDS.RIGHT.ordinal()]; i++)
        {
            for (int j=tile[BOUNDS.TOP.ordinal()]; j<= tile[BOUNDS.BOTTOM.ordinal()]; j++)
            {
                Tile t =M.get_tile_matrix().get(M.single_index(i, j));
                var b = type.equals("PROJECTILE")? !t.is_passable_for_projectile : !t.is_passable;
                if(b) return true;
            }
        }
        return false;
    }




}
