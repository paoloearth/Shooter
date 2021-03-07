package units.shooter_developers.MenuAPI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import units.shooter_developers.Custom_Settings;

public abstract  class _Submenu_leaf extends VBox {

    protected int n_rows = 1;
    public double custom_scale;

    protected _Submenu_leaf(int nrows, double scale)
    {

        set_padding_and_spacing();

        n_rows = nrows;
        custom_scale = scale;

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


    protected static ImageView retrieve_image(String URL, int n_rows)
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