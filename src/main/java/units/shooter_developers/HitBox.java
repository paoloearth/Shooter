//JOSE: Classe visitata
//     -Questo metodo non ha particolari problemi, semplicemente suggerire che si potrebbe usare
//      Rectangle di javafx per semplificare il lavoro. Comunque, non penso migliorerebbe l'implementazione,
//      Solo riducerebbe un po' le righe di codice.

package units.shooter_developers;


public class HitBox {

    public enum BOUNDS {TOP, LEFT, BOTTOM, RIGHT}
    double [] box ;
    int    [] tileNeighborhood;

    public HitBox(double top, double left, double width, double height) { box = new double[]{top, left,  top+height,left+width}; }


    public  void  compute_tiles_bounds(GameMap map) {

        int top_tile = (int) (get_top_box() / map.getTileHeight());
        if (top_tile < 0) top_tile = 0;

        int bottom_tile = (int) (get_bottom_box() / map.getTileHeight());
        if (bottom_tile > map.get_height()) bottom_tile = (int) map.get_height();

        int left_tile = (int) (get_left_box() / map.getTileWidth());
        if (left_tile < 0) left_tile = 0;

        int right_tile = (int) (get_right_box()/ map.getTileWidth());
        if (right_tile > map.get_width()) right_tile = (int) map.get_width();


        tileNeighborhood = new int[]{top_tile, left_tile, bottom_tile, right_tile};

    }


    protected boolean isOutOfMap(GameMap M) {
        return  check_top_and_left() || check_bottom_and_right(M);
    }

    protected boolean intersect(HitBox B)
    {
        return B.get_right_box() > get_left_box()&& B.get_bottom_box() > get_top_box() && B.get_left_box() < get_right_box() && B.get_top_box() < get_bottom_box();
    }


    private boolean check_top_and_left() {
        return get_top_box()<= 0 ||  get_left_box() <=0;
    }

    private boolean check_bottom_and_right(GameMap M) {
        return get_right_box() >= M.get_width() || get_bottom_box() >= M.get_height();
    }

    protected boolean checkIfObjectCanMoveOnNeighboursTiles(GameMap M, DynamicObject D)
    {
        for (int i =get_left_tile(); i<= get_right_tile(); i++)
        {
            for (int j=get_top_tile(); j<= get_bottom_tile(); j++)
            {
                Tile t =M.get_tile_matrix().get(M.single_index(i, j));
                var passable = D.checkIfPassable(t);
                if(!passable) return true;
            }
        }
        return false;
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

    public int get_top_tile()
    {
        return tileNeighborhood[BOUNDS.TOP.ordinal()];
    }
    public int get_bottom_tile()
    {
        return tileNeighborhood[BOUNDS.BOTTOM.ordinal()];
    }
    public int get_left_tile()
    {
        return tileNeighborhood[BOUNDS.LEFT.ordinal()];
    }
    public int get_right_tile()
    {
        return tileNeighborhood[BOUNDS.RIGHT.ordinal()];
    }





}
