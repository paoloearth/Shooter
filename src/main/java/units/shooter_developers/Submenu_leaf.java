package units.shooter_developers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract  class Submenu_leaf extends VBox {

    int n_rows=1;
    double custom_scale;

    Submenu_leaf(TypeImage T)
    {

        set_padding_and_spacing();

        set_custom_scale_on_T(T);

    }

    private void set_custom_scale_on_T(TypeImage T) {
        switch (T)
        {
            case SPRITE:
                n_rows = 4;
                custom_scale = Custom_Settings.SPRITE_SCALE;
            case WASD:
                custom_scale = Custom_Settings.WASD_SCALE;
            case ARROW:
                custom_scale = Custom_Settings.ARROWS_SCALE;
            case MAP:
                custom_scale = Custom_Settings.MAP_SCALE;

        }
    }


    private void set_padding_and_spacing() {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);
    }

    void fill_HBox_with_image(HBox h, ImageView i) {
        h.getChildren().add(i);
    }


    HBox createCustomHbox() {
        HBox H = new HBox();
        H.setMinHeight(0);
        H.setAlignment(Pos.BOTTOM_CENTER);
        return H;
    }

    void scale_image_to_fit_box(HBox H, ImageView I) {
        I.fitHeightProperty().bind(H.heightProperty());
        I.setScaleX(custom_scale);
        I.setScaleY(custom_scale);
    }

    void empty_HBox(HBox H) {
        H.getChildren().removeIf(i -> i instanceof ImageView);
    }


    ImageView retrieve_image(String URL, int n_rows)
    {
        var I = new Image(URL);
        var IM =  new ImageView(I);
        IM.setViewport(new Rectangle2D( 0, 0, I.getWidth(), I.getHeight()/n_rows));
        IM.setPreserveRatio(true);
        return IM;
    }

    void add_generic_child_node_to_parent_node(Pane parent, Pane children) {
        parent.getChildren().add(children);
    }



}