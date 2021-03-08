package units.shooter_developers.MenuAPI;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import units.shooter_developers.Custom_Settings;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class _Player_selection_menu extends _Submenu_component {

    _Choice_Box CB_P1;
    _Choice_Box CB_P2;

    _Text_Box TB_P1;
    _Text_Box TB_P2;

    Map<String, String> Name_URL;

    public _Player_selection_menu(double width_scale, double height_scale)
    {
        super(width_scale, height_scale);

        create_2_columns_layout();

        create_menu();

        //set_bindings();
    }


    private void create_menu() {

        Name_URL = initialize_dictionary();

        create_choiceboxes();

        create_textboxes();


    }

    private void create_choiceboxes() {
        CB_P1 = new _Choice_Box( Name_URL, 4, Custom_Settings.SPRITE_SCALE);
        CB_P2 = new _Choice_Box(Name_URL, 4, Custom_Settings.SPRITE_SCALE);



        add(CB_P1, 0,1);
        add(CB_P2, 1,1);
    }

    private void create_textboxes() {
        /* TEXT BOX & ADD them to GRIDPANE in right position*/
        TB_P1 = new _Text_Box(Custom_Settings.URL_COMMANDS_P1, 1, Custom_Settings.WASD_SCALE);
        TB_P2 = new _Text_Box(Custom_Settings.URL_COMMANDS_P2, 1, Custom_Settings.ARROWS_SCALE);

        TB_P1.setAlignment(Pos.CENTER);
        TB_P1.setAlignment(Pos.CENTER);

        add(TB_P1, 0,0);
        add(TB_P2, 1,0);
    }

    private void set_bindings() {
        all_set.bind( TB_P1._textField.textProperty().isEmpty()
                .or(TB_P2._textField.textProperty().isEmpty())
                .or(CB_P1.getComboBox().getSelectionModel().selectedItemProperty().isNull())
                .or(CB_P2.getComboBox().getSelectionModel().selectedItemProperty().isNull()));
    }

    private Map<String, String> initialize_dictionary() {
        Name_URL = new Hashtable<>();

        Name_URL.put(Custom_Settings.ARTIST,Custom_Settings.URL_ARTIST);
        Name_URL.put(Custom_Settings.ASTROLOGER,Custom_Settings.URL_ASTROLOGER);
        Name_URL.put(Custom_Settings.WARRIOR,Custom_Settings.URL_WARRIOR);

        return Name_URL;
    }




    private void create_2_columns_layout() {
        set_column_costraints();
        set_row_costraints();
    }

    private void set_column_costraints() {
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);

        getColumnConstraints().addAll(column1, column2);
    }

    private void set_row_costraints() {
        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);

        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);

        getRowConstraints().addAll(row1, row2);
    }



    public List<String> get_players_names()
        {
                var NAMES = new ArrayList<String>();
                NAMES.add(TB_P1.get_value());
                NAMES.add(TB_P2.get_value());
                return NAMES;
        }

    public List<String> get_players_URL() {

                var URL = new ArrayList<String>();
                URL.add(Name_URL.get(CB_P1.get_value()));
                URL.add(Name_URL.get(CB_P2.get_value()));
                return URL;
    }






    }
