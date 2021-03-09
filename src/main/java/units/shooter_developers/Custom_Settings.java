package units.shooter_developers;



    /* TEMPLATE
       0. URL_spritesheet_mappa
       1. n_cols, n_rows, cell_size
       2. set_of_passable_blocks
       3. set_of_non_passable_block_for_projectiles
       4. sprite_1_blocco_x, sprite_1_blocco_y,sprite_2_blocco_x, sprite_2_blocco_y
       5. teleport positions
    */


public class Custom_Settings {


    /* DIMENSIONS */
    public static final int DEFAULT_X  = 1000;
    public static final int DEFAULT_Y  = 600;

    /*  METADATA IN MAP */

    public static final int NUMBER_OF_METADATA_LINES  = 6;
    public static final String  FILE_SEPARATOR= ",";

    /* SPEEDS */
    public static final int PLAYER_SPEED  = 2;
    public static final int PROJECTILE_SPEED  = 5;

    /*  SCALES */
    public static final double PLAYER_SCALE  = 1.0/20;
    public static final double PROJECTILE_SCALE =  1.0/19;
    public static final double  TELEPORT_SCALE  = 1.0/18;
    public static final double HEART_SCALE = 1.0/6;

    /* MENU SCALES */
    public static final double WASD_SCALE =.5 ;
    public static final double ARROWS_SCALE = .2 ;
    public static final double SPRITE_SCALE = .9;
    public static final double MAP_SCALE = .5;


    /* BONUS */
    public static final int BONUS_COOLDOWN = 5 ;

    /*HEALTHBAR */
    public static final double SHOOTING_COOLDOWN = .5 ;


    /* HEALTHBAR */
    public static final double HB_PROPORTIONAL_WIDTH  = 0.1 ;   // HB height is 10 % of sprite's height
    public static final double PERCENTAGE_DAMAGE_PER_SHOOT = .25;

    /*  URLS */
    public static final String URL_TELEPORT   = "teleport.png";
    public static final String URL_PROJECTILE = "cannon_ball.png";
    public static final String URL_HEART      = "bonus.png";

    public static final String URL_ARTIST       = "artist.png";
    public static final String URL_WARRIOR      = "warrior.png";
    public static final String URL_ASTROLOGER   = "astrologer.png";


    public static final String URL_MAP_ISLAND_PNG      =  "map_island.png";
    public static final String URL_MAP_DESERT_PNG      =  "map_desert.png";

    public static final String URL_MAP_ISLAND_CSV     = "map_islands.csv";
    public static final String URL_MAP_DESERT_CSV     = "map_desert.csv";

    public static final String URL_COMMANDS_P1     = "WASD.png";
    public static final String URL_COMMANDS_P2     = "arrows.png";



    /*  DISPLAYED NAMES */

    public static final String ARTIST     = "Artist";
    public static final String WARRIOR     = "Warrior";
    public static final String ASTROLOGER     = "Astrologer";

    public static final String DESERT     = "Desert";
    public static final String ISLAND     = "Islands";

    public static final String BONUS  = "BONUS";
    public static final String PROJECTILE  ="PROJECTILE";
    public static final String SPRITE  ="SPRITE";
    public static final String TELEPORT  ="TELEPORT";




}
