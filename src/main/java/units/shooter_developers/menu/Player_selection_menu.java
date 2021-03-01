package units.shooter_developers.menu;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Player_selection_menu extends GridPane {
    double _width;
    double _height;

    Choice_Box CB_P1;
    Choice_Box CB_P2;

    Text_Box TB_P1;
    Text_Box TB_P2;

    SimpleBooleanProperty all_set = new SimpleBooleanProperty(false);

    Map<String, String> Name_URL;

    Player_selection_menu(double width, double height)
    {
        set_height(height);
        set_width(width);

        set_padding();

        fix_submenu_size_to_width_and_height();

        create_2_columns_layout();

        create_menu();
    }


    public SimpleBooleanProperty all_setProperty() {
        return all_set;
    }

    private void create_menu() {

        Name_URL = initialize_dictionary();

        /* CHOICE BOX  & ADD them to GRIDPANE in right position*/

        CB_P1 = new Choice_Box( Name_URL,4,1);
        CB_P2 = new Choice_Box(Name_URL,4,1);

        add(CB_P1, 0,1);
        add(CB_P2, 1,1);

        /* TEXT BOX & ADD them to GRIDPANE in right position*/
        TB_P1 = new Text_Box();
        TB_P2 = new Text_Box();
        add(TB_P1, 0,0);
        add(TB_P2, 1,0);


        /* BINDINGS */
        all_set.bind( TB_P1.textField.textProperty().isEmpty()
                .or(TB_P2.textField.textProperty().isEmpty())
                .or(CB_P1.comboBox.getSelectionModel().selectedItemProperty().isNull())
                .or(CB_P2.comboBox.getSelectionModel().selectedItemProperty().isNull()));


    }

    private Map<String, String> initialize_dictionary() {
        Name_URL = new Hashtable<>();
        Name_URL.put("Artist","artist.png");
        Name_URL.put("Astrologer","astrologer.png");
        Name_URL.put("Warrior","warrior.png");

        return Name_URL;
    }


    public void set_width(double _width)   { this._width = _width; }
    public void set_height(double _height) { this._height = _height; }

    private void fix_submenu_size_to_width_and_height() {
        this.setMinSize(get_width(), get_height());
        this.setPrefSize(get_width(), get_height());
        this.setMaxSize(get_width(), get_height());
    }

    private void set_padding() {
        setHgap(10);                                                //horizontal gap in pixels
        setVgap(10);                                                //vertical gap in pixels
        setPadding(new Insets(10, 10, 10, 10));  //margins around the whole grid
    }

    private void create_2_columns_layout() {
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);

        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);

        getColumnConstraints().addAll(column1, column2);
    }
    public double get_width() {
        return _width;
    }

    public double get_height() {
        return _height;
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
