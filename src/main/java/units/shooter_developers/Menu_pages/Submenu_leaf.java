package units.shooter_developers.Menu_pages;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import units.shooter_developers.Custom_Settings;
import units.shooter_developers.TypeImage;

public abstract  class Submenu_leaf extends VBox {

    protected int n_rows=1;
    double custom_scale;

    protected Submenu_leaf(TypeImage T)
    {

        set_padding_and_spacing();

        System.out.println(T);

        set_custom_scale_on_T(T);

    }

    private void set_custom_scale_on_T(TypeImage T) {
        switch (T) {
            case SPRITE -> {
                n_rows = 4;
                custom_scale = Custom_Settings.SPRITE_SCALE;
            }
            case WASD -> {
                custom_scale = Custom_Settings.WASD_SCALE;
            }
            case ARROW -> {
                custom_scale = Custom_Settings.ARROWS_SCALE;
            }
            case MAP -> {
                custom_scale = Custom_Settings.MAP_SCALE;
            }
        }
    }


    private void set_padding_and_spacing() {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);
    }

    protected void fill_HBox_with_image(HBox h, ImageView i) {
        h.getChildren().add(i);
    }


    protected HBox createCustomHbox() {
        HBox H = new HBox();
        H.setMinHeight(0);
        H.setAlignment(Pos.BOTTOM_CENTER);
        return H;
    }

    protected void scale_image_to_fit_box(HBox H, ImageView I) {
        I.fitHeightProperty().bind(H.heightProperty());
        I.setScaleY(custom_scale);
        I.setScaleX(custom_scale);

    }

    protected void empty_HBox(HBox H) {
        H.getChildren().removeIf(i -> i instanceof ImageView);
    }


    protected ImageView retrieve_image(String URL, int n_rows)
    {
        var I = new Image(URL);
        var IM =  new ImageView(I);
        IM.setViewport(new Rectangle2D( 0, 0, I.getWidth(), I.getHeight()/n_rows));
        IM.setPreserveRatio(true);
        return IM;
    }

    protected void add_generic_child_node_to_parent_node(Pane parent, Pane children) {
        parent.getChildren().add(children);
    }



}