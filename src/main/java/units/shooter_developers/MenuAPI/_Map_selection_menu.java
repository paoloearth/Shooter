package units.shooter_developers.MenuAPI;

import javafx.scene.layout.GridPane;
import units.shooter_developers.Custom_Settings;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class _Map_selection_menu extends GridPane {

    _ChoiceBox _choice_box_map;
    Map<String, String> Map_CSV;


    public _Map_selection_menu(double width_scale, double height_scale, Map<String, String> map_image_to_URL)
    {
        var width = Menu.getMenuWidth()* width_scale;
        var height = Menu.getMenuHeight()* height_scale;

        this.setTranslateY(0.017*height);
        this.setTranslateX(0.01*width);

        this.setMinSize(width, height);
        this.setPrefSize(width, height);
        this.setMaxSize(width, height);


        /* DOUBLE MAP in order to store both the name-> csv mapping & name -> png mapping */
        Map_CSV = new Hashtable<>();
        Map_CSV.put(Custom_Settings.ISLAND,Custom_Settings.URL_MAP_ISLAND_CSV);
        Map_CSV.put(Custom_Settings.DESERT,Custom_Settings.URL_MAP_DESERT_CSV);

        _choice_box_map = new _ChoiceBox(map_image_to_URL, 1, Custom_Settings.MAP_SCALE);

        //all_set.bind(CB_MAP.getComboBox().getSelectionModel().selectedItemProperty().isNull());

        add(_choice_box_map,1,1);
    }


    public List<String> get_map_data()
    {
        var L = new ArrayList<String>();
        L.add(Map_CSV.get(_choice_box_map.get_value()));

        return L;

    }





}
