package units.shooter_developers.MenuAPI;

/* VISITED */
import javafx.geometry.Pos;
import javafx.scene.chart.ScatterChart;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import units.shooter_developers.CustomException;

import java.util.Map;



class ChoiceBox extends VBox {


    final private Map<String, String> _dict;
    final private int _nrows;
    final private double _custom_scale;
    final private SelectorItem _selector;
    final String _name;

    public ChoiceBox(String name, Map<String, String> map_image_to_URL, int nrows, double scale) {
        _name = name;
        _nrows = nrows;
        _custom_scale = scale;
        _selector = new SelectorItem("ChoiceBox_selector", 0.1, false);
        _selector.setAlignment(Pos.BASELINE_CENTER);
        _dict = map_image_to_URL;

        setAlignment(Pos.TOP_CENTER);
        setSpacing(0.01*Menu.getMenuHeight());

        final HBox image_box = new HBox();
        image_box.setMinHeight(0);
        image_box.setAlignment(Pos.BOTTOM_CENTER);

        _selector.getSelectionAsProperty().addListener((observable,  oldValue,  selected) ->
        {
            final var image = Menu.retrieveImage(_dict.get(selected), _nrows, 1);
            image.setPreserveRatio(true);
            image.setFitHeight(0.2*_custom_scale*Menu.getMenuHeight());

            image_box.getChildren().removeIf(i -> i instanceof ImageView);
            image_box.getChildren().add(image);

        });

        map_image_to_URL.forEach((key, value) -> _selector.addTag(key));
        getChildren().addAll(image_box,_selector);

    }

    public ChoiceBox(String name, Map<String, String> map_image_to_URL, int nrows, double scale, int default_index) throws CustomException.IndexOutOfRange {
        this(name, map_image_to_URL, nrows, scale);
        _selector.setDefaultIndex(default_index);
    }

    public String getValue() { return _selector.getText(); }
    public String getName(){ return _name; }
}




