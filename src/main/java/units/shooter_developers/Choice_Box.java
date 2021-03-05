package units.shooter_developers;

import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import units.shooter_developers.Menu_pages.Submenu_leaf;

import java.util.Map;



public class Choice_Box extends Submenu_leaf {

        private ComboBox<String> comboBox;
        private Map<String, String> _dict;


        public Choice_Box( Map<String, String> Name_URL, TypeImage  T) {
            super(T);

            set_dict(Name_URL);
            create_combobox_with_DICT(Name_URL);
            add_combobox_to_vbox();

            HBox H = createCustomHbox();
            add_generic_child_node_to_parent_node(this,H);
            set_listener_to_change_figure(H);

        }


    private void set_listener_to_change_figure(HBox H) {
        getComboBox().valueProperty().addListener((observable,  oldValue,  selected) ->
        {
            empty_HBox(H);
            var I = retrieve_image(retrieve_selected_value_from_dict(selected), n_rows);
            scale_image_to_fit_box(H, I);
            fill_HBox_with_image(H,I);

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




