package units.shooter_developers;

public class Custom_Settings {


    /* DIMENSIONS */
    public static final int DEFAULT_X  = 1000;
    public static final int DEFAULT_Y  = 600;

    /*  METADATA IN MAP */

    /* TEMPLATE
       0. URL_spritesheet_mappa
       1. n_cols, n_rows, cell_size
       2. set_of_passable_blocks
       3. set_of_non_passable_block_for_projectiles
       4. sprite_1_blocco_x, sprite_1_blocco_y,sprite_2_blocco_x, sprite_2_blocco_y
       5. teleport positions
    */

    public static final int NUMBER_OF_METADATA_LINES  = 6;

    /* HEALTHBAR */
    public static final double HB_PROPORTIONAL_WIDTH  = 0.1 ;   // HB height is 10 % of sprite's height
}
