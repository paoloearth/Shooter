package units.shooter_developers;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import javax.naming.Name;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.stream.Collectors;

public class Choice_Box extends VBox{

        ComboBox<String> comboBox;
        Map<String, String> DICT;

        public Choice_Box( Map<String, String> Name_URL, int n_rows, int n_cols) {

            this.DICT = Name_URL;

            /* Padding */
            set_padding();

            var displayed_names =FXCollections.observableArrayList(Name_URL.keySet());
            comboBox = new ComboBox<>(displayed_names);
            add_combo_to_vbox(comboBox );

            HBox H = new HBox();
            H.setMinHeight(0);
            H.setAlignment(Pos.BOTTOM_CENTER);
            getChildren().add(H);


            comboBox.valueProperty().addListener((observable,  oldValue,  selected) ->
            {
                empty_HBox(H);
                var I = retrieve_image(Name_URL.get(selected), n_rows,n_cols);
                scale_image_to_fit_box(H, I);
                H.getChildren().add(I);

            });

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

    private void add_combo_to_vbox( ComboBox<String> c) {
            getChildren().add(c);
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

    public String get_value()
    {
        return comboBox.getValue();
    }



}




