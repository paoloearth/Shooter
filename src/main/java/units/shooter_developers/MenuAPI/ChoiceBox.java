package units.shooter_developers.MenuAPI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;



public class ChoiceBox extends VBox {

    private Map<String, String> _dict;
    private int _nrows;
    private double _custom_scale;
    private SelectorItem _selector;
    String _name


    public ChoiceBox(String name, Map<String, String> map_image_to_URL, int nrows, double scale) {
        _name = name;
        _nrows = nrows;
        _custom_scale = scale;
        _selector = new SelectorItem("ChoiceBox_selector", 0.1, false);
        _selector.setAlignment(Pos.BASELINE_CENTER);
        _dict = map_image_to_URL;

        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);

        HBox image_box = new HBox();
        image_box.setMinHeight(0);
        image_box.setAlignment(Pos.BOTTOM_CENTER);

        _selector.getSelectionAsProperty().addListener((observable,  oldValue,  selected) ->
        {
            var image = Menu.retrieveImage(_dict.get(selected), _nrows, 1);

            image.setPreserveRatio(true);
            image.fitHeightProperty().bind(image_box.heightProperty());
            image.setScaleY(_custom_scale);
            image.setScaleX(_custom_scale);

            image_box.getChildren().removeIf(i -> i instanceof ImageView);
            image_box.getChildren().add(image);
        });

        for(var elem : map_image_to_URL.entrySet()){
            _selector.addTag(elem.getKey());
        }

        getChildren().add(image_box);
        getChildren().add(_selector);
    }

    public String get_value()
    {
        return _selector.getName();
    }

    public String getName(){
        return _name;
    }
}




