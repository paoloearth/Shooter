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

    /* SPEEDS */
    public static final int PLAYER_SPEED  = 2;
    public static final int PROJECTILE_SPEED  = 3;

    /*  SCALES */
    public static final double PLAYER_SCALE  = 1.0/20;
    public static final double PROJECTILE_SCALE =  1.0/19;
    public static final double  TELEPORT_SCALE  = 1.0/18;
    public static final double HEART_SCALE = 1.0/6;



    /* HEALTHBAR */
    public static final double HB_PROPORTIONAL_WIDTH  = 0.1 ;   // HB height is 10 % of sprite's height

    /*  URLS */
    public static final String URL_TELEPORT   = "teleport.png";
    public static final String URL_PROJECTILE = "cannon_ball.png";
    public static final String URL_HEART      = "bonus.png";
}
