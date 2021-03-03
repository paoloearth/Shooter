package units.shooter_developers;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Map_selection_menu extends Submenu_component {

    Choice_Box CB_MAP;
    Map<String, String> Map_URL;
    Map<String, String> Map_CSV;


    Map_selection_menu(double width, double height)
    {
        super(width,height);
        create_menu();
    }


    private void create_menu() {

        /* DOUBLE MAP in order to store both the name-> csv mapping & name -> png mapping */
        set_name_png_dictionary();
        set_name_csv_dictionary();

        CB_MAP = new Choice_Box(Map_URL,TypeImage.MAP);

        all_set.bind(CB_MAP.getComboBox().getSelectionModel().selectedItemProperty().isNull());

        add(CB_MAP,1,1);
    }

    private void set_name_csv_dictionary() {
        Map_CSV = new Hashtable<>();
        Map_CSV.put(Custom_Settings.ISLAND,Custom_Settings.URL_MAP_ISLAND_CSV);
        Map_CSV.put(Custom_Settings.DESERT,Custom_Settings.URL_MAP_DESERT_CSV);
    }

    private void set_name_png_dictionary() {
        Map_URL = new Hashtable<>();
        Map_URL.put(Custom_Settings.ISLAND,Custom_Settings.URL_MAP_ISLAND_PNG);
        Map_URL.put(Custom_Settings.DESERT,Custom_Settings.URL_MAP_DESERT_PNG);
    }


    public List<String> get_map_data()
    {
        var L = new ArrayList<String>();
        L.add(Map_CSV.get(CB_MAP.get_value()));

        return L;

    }





}
