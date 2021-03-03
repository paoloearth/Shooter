package units.shooter_developers;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import java.util.Map;

public class Choice_Box extends VBox{

        private ComboBox<String> comboBox;
        private Map<String, String> _dict;

        public Choice_Box( Map<String, String> Name_URL, int n_rows, int n_cols) {

            set_dict(Name_URL);
            set_padding();

            create_combobox_with_DICT(Name_URL);
            add_combobox_to_vbox();

            HBox H = createCustomHbox();
            add_generic_child_node_to_parent_node(this,H);

            set_listener_to_change_figure(H,n_rows, n_cols);

        }

    private void add_generic_child_node_to_parent_node(Pane parent, Pane children) {
            parent.getChildren().add(children);
    }

    private void set_listener_to_change_figure(HBox H, int n_rows, int n_cols) {
        getComboBox().valueProperty().addListener((observable,  oldValue,  selected) ->
        {
            empty_HBox(H);
            var I = retrieve_image(retrieve_selected_value_from_dict(selected), n_rows, n_cols);
            scale_image_to_fit_box(H, I);
            fill_HBox_with_image(H,I);

        });
    }



    private String retrieve_selected_value_from_dict(String selected) {
        return get_dict().get(selected);
    }
    private void fill_HBox_with_image(HBox h, ImageView i) {
        h.getChildren().add(i);
    }


    private HBox createCustomHbox() {
        HBox H = new HBox();
        H.setMinHeight(0);
        H.setAlignment(Pos.BOTTOM_CENTER);
        return H;
    }

    private void create_combobox_with_DICT(Map<String, String> Name_URL) {
       setComboBox(new ComboBox<>(FXCollections.observableArrayList(Name_URL.keySet())));
    }

    private void scale_image_to_fit_box(HBox H, ImageView I) {
        I.fitHeightProperty().bind(H.heightProperty());
        I.setScaleX(.8);
        I.setScaleY(.8);
    }

    private void set_padding() {
        setAlignment(Pos.TOP_CENTER);
        setSpacing(10);
    }

    private void add_combobox_to_vbox() {
            getChildren().add(getComboBox());
        }
    public String get_value()
    {
        return getComboBox().getValue();
    }


    private void empty_HBox(HBox H) {
        H.getChildren().removeIf(i -> i instanceof ImageView);
    }

            // Create an image given an URL
    ImageView retrieve_image(String URL, int n_rows, int n_cols)
            {
                var I = new Image(URL);
                var IM =  new ImageView(I);
                IM.setViewport(new Rectangle2D( 0, 0, I.getWidth()/n_cols, I.getHeight()/n_rows));
                IM.setPreserveRatio(true);
                return IM;
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




