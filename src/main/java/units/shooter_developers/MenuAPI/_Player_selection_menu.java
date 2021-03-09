package units.shooter_developers.MenuAPI;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import units.shooter_developers.Custom_Settings;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class _Player_selection_menu extends GridPane {

    _ChoiceBox CB_P1;
    _ChoiceBox CB_P2;

    _Text_Box TB_P1;
    _Text_Box TB_P2;

    Map<String, String> Name_URL;

    public _Player_selection_menu(double width_scale, double height_scale, Map<String, String> map_image_to_URL)
    {
        Name_URL = map_image_to_URL;

        var width = Menu.getMenuWidth()* width_scale;
        var height = Menu.getMenuHeight()* height_scale;

        this.setTranslateY(0.017*height);
        this.setTranslateX(0.01*width);

        this.setMinSize(width, height);
        this.setPrefSize(width, height);
        this.setMaxSize(width, height);

        //cow c
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        getColumnConstraints().addAll(column1, column2);
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        getRowConstraints().addAll(row1, row2);


        CB_P1 = new _ChoiceBox(map_image_to_URL, 4, Custom_Settings.SPRITE_SCALE);
        CB_P2 = new _ChoiceBox(map_image_to_URL, 4, Custom_Settings.SPRITE_SCALE);


        add(CB_P1, 0,1);
        add(CB_P2, 1,1);


        /* TEXT BOX & ADD them to GRIDPANE in right position*/
        TB_P1 = new _Text_Box(Custom_Settings.URL_COMMANDS_P1, 1, Custom_Settings.WASD_SCALE);
        TB_P2 = new _Text_Box(Custom_Settings.URL_COMMANDS_P2, 1, Custom_Settings.ARROWS_SCALE);

        TB_P1.setAlignment(Pos.CENTER);
        TB_P1.setAlignment(Pos.CENTER);

        add(TB_P1, 0,0);
        add(TB_P2, 1,0);


        //set_bindings();
    }



    public List<String> get_players_URL() {

                var URL = new ArrayList<String>();
                URL.add(Name_URL.get(CB_P1.get_value()));
                URL.add(Name_URL.get(CB_P2.get_value()));
                return URL;
    }
}
