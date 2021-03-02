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



    SimpleBooleanProperty all_set = new SimpleBooleanProperty(false);

    Map_selection_menu(double width, double height)
    {
        super(width,height);
        create_menu();
    }


    private void create_menu() {

        /* DOUBLE MAP in order to store both the name-> csv mapping & name -> png mapping */

        Map_URL = new Hashtable<>();
        Map_URL.put("Map Island","map_island.png");
        Map_URL.put("Map Desert","map_desert.png");

        Map_CSV = new Hashtable<>();
        Map_CSV.put("Map Island","map_islands.csv");
        Map_CSV.put("Map Desert","map_desert.csv");

        CB_MAP = new Choice_Box(Map_URL,1,1);

        all_set.bind(CB_MAP.getComboBox().getSelectionModel().selectedItemProperty().isNull());

        add(CB_MAP,1,1);
    }



    public List<String> get_map_data()
    {
        var L = new ArrayList<String>();
        L.add(Map_CSV.get(CB_MAP.get_value()));

        return L;

    }





}
