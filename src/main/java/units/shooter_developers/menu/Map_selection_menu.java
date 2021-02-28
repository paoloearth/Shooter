package units.shooter_developers.menu;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class Map_selection_menu extends HBox {
    double _width, _height;
    Choice_Box CB_MAP;

    SimpleBooleanProperty all_set = new SimpleBooleanProperty(false);

    Map_selection_menu(double width, double height)
    {
        this._width = width;
        this._height = height;

        this.setMinSize(_width,_height);
        this.setPrefSize(_width,_height);
        this.setMaxSize(_width,_height);

        create_menu();
    }


    private void create_menu() {

        Map<String, String> Map_URL = new Hashtable<>();
        Map_URL.put("Map Island","map_island.png");
        Map_URL.put("Map Desert","map_desert.png");

        CB_MAP = new Choice_Box(Map_URL,1,1);

        all_set.bind(CB_MAP.comboBox.getSelectionModel().selectedItemProperty().isNull());

        getChildren().add(CB_MAP);
    }


    public SimpleBooleanProperty all_setProperty() {
        return all_set;
    }

    public List<String> get_map_data()
    {
        var L = new ArrayList<String>();
        L.add(CB_MAP.get_value());

        return L;

    }



}
