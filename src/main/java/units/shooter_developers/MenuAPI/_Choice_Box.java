package units.shooter_developers.MenuAPI;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;



public class _Choice_Box extends VBox {

    private ComboBox<String> _comboBox;
    private Map<String, String> _dict;
    private int _nrows;
    private double _custom_scale;
    private SelectorItem _selector;


    public _Choice_Box(Map<String, String> map_name_imageURL, int nrows, double scale) {
        super();
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);

        _nrows = nrows;
        _custom_scale = scale;
        _selector = new SelectorItem("hola", false);
        set_dict(map_name_imageURL);

        //create_combobox_with_DICT(map_name_imageURL);
        create_selector_with_DICT(map_name_imageURL);
        //getChildren().add(getComboBox());
        getChildren().add(_selector);


        HBox H = createCustomHbox();
        getChildren().add(H);

        //set_listener_to_change_figure(H);
        set_listener_to_change_figure_selector(H);
    }

    protected static HBox createCustomHbox() {
        HBox H = new HBox();
        H.setMinHeight(0);
        H.setAlignment(Pos.BOTTOM_CENTER);
        return H;
    }

    private void set_listener_to_change_figure(HBox H) {
        getComboBox().valueProperty().addListener((observable,  oldValue,  selected) ->
        {
            H.getChildren().removeIf(i -> i instanceof ImageView);
            var I = Menu.retrieveImage(retrieve_selected_value_from_dict(selected), _nrows, 1);
            I.setPreserveRatio(true);
            scale_image_to_fit_box(H, I);
            H.getChildren().add(I);

        });
    }

    private void set_listener_to_change_figure_selector(HBox H) {
        _selector.getSelectionAsProperty().addListener((observable,  oldValue,  selected) ->
        {
            H.getChildren().removeIf(i -> i instanceof ImageView);
            var I = Menu.retrieveImage(retrieve_selected_value_from_dict(selected), _nrows, 1);
            I.setPreserveRatio(true);
            scale_image_to_fit_box(H, I);
            H.getChildren().add(I);

        });
    }

    protected void scale_image_to_fit_box(HBox H, ImageView I) {
        I.fitHeightProperty().bind(H.heightProperty());
        I.setScaleY(_custom_scale);
        I.setScaleX(_custom_scale);
    }




    private void create_combobox_with_DICT(Map<String, String> map_image_to_URL) {
       _comboBox = new ComboBox<>(FXCollections.observableArrayList(map_image_to_URL.keySet()));
    }

    private void create_selector_with_DICT(Map<String, String> map_image_to_url) {
        for(int i=0; i<map_image_to_url.size(); i++){
            _selector.addTag(map_image_to_url.get(i));
        }
    }

    public String get_value()
    {
        //return getComboBox().getValue();
        return _selector.getName();
    }
    private String retrieve_selected_value_from_dict(String selected) {
        return get_dict().get(selected);
    }






    /**************************************   Getters and Setters ******************************************/

    /* For dictionary variable */
    public void set_dict(Map<String, String> _dict) {
        this._dict = _dict;
    }
    public Map<String, String> get_dict() {
        return _dict;
    }

    /* For combobox varibale */
    public ComboBox<String> getComboBox() {
        return _comboBox;
    }



}




