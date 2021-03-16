package units.shooter_developers.menu_api;

import units.shooter_developers.customs.CustomCheckedException;

import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import java.util.Map;



class ChoiceBox extends VBox {


    final private Map<String, String> _dict;
    final private int _spritesheetRow;
    final private double _customScale;
    final private SelectorItem _selector;
    final String _name;

    public ChoiceBox(String name, Map<String, String> mapImageToURL, int spritesheetRow, double scale) {
        _name = name;
        _spritesheetRow = spritesheetRow;
        _customScale = scale;
        _selector = new SelectorItem("ChoiceBox_selector", 0.1, false);
        _selector.setAlignment(Pos.BASELINE_CENTER);
        _dict = mapImageToURL;

        setAlignment(Pos.TOP_CENTER);
        setSpacing(0.01*Menu.getMenuHeight());

        final HBox imageBox = new HBox();
        imageBox.setMinHeight(0);
        imageBox.setAlignment(Pos.BOTTOM_CENTER);

        _selector.getSelectionAsProperty().addListener((observable,  oldValue,  selected) ->
        {
            ImageView image;
            try {
                image = Menu.retrieveImage(_dict.get(selected), _spritesheetRow, 1);
            }catch (CustomCheckedException.FileManagementException e){
                System.out.println(e.toString() + " ChoiceBox's image image not found. Using alternative one. Continuing");
                image = new ImageView(new Rectangle(10, 10).snapshot(null, null));
            }

            image.setPreserveRatio(true);
            image.setFitHeight(0.2* _customScale *Menu.getMenuHeight());

            imageBox.getChildren().removeIf(i -> i instanceof ImageView);
            imageBox.getChildren().add(image);

        });

        mapImageToURL.forEach((key, value) -> _selector.addTag(key));
        getChildren().addAll(imageBox,_selector);

    }

    public ChoiceBox(String name, Map<String, String> mapImageToUrl, int nrows, double scale, int default_index) throws CustomCheckedException.IndexOutOfRangeException {
        this(name, mapImageToUrl, nrows, scale);
        _selector.setDefaultIndex(default_index);
    }

    public String getValue() { return _selector.getText(); }
    public String getName(){ return _name; }
}




