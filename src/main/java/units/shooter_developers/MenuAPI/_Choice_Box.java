package units.shooter_developers.MenuAPI;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.Map;



public class _Choice_Box extends _Submenu_leaf {

    private ComboBox<String> comboBox;
    private Map<String, String> _dict;


    public _Choice_Box(Map<String, String> Name_URL, int nrows, double scale) {
        super(nrows, scale);
        set_dict(Name_URL);
        create_combobox_with_DICT(Name_URL);
        add_combobox_to_vbox();
        HBox H = createCustomHbox();
        getChildren().add(H);
        set_listener_to_change_figure(H);
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
            var I = Menu.retrieveImage(retrieve_selected_value_from_dict(selected), _n_rows, 1);
            I.setPreserveRatio(true);
            scale_image_to_fit_box(H, I);
            H.getChildren().add(I);

        });
    }






    private void create_combobox_with_DICT(Map<String, String> Name_URL) {
       setComboBox(new ComboBox<>(FXCollections.observableArrayList(Name_URL.keySet())));
    }

    private void add_combobox_to_vbox() {
            getChildren().add(getComboBox());
        }
    public String get_value()
    {
        return getComboBox().getValue();
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
        return comboBox;
    }
    public void setComboBox(ComboBox<String> comboBox) {
        this.comboBox = comboBox;
    }



}



