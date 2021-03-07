package units.shooter_developers.MenuAPI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public abstract  class _Submenu_leaf extends VBox {

    protected int _n_rows = 1;
    public double _custom_scale;

    protected _Submenu_leaf(int nrows, double scale)
    {

        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);

        _n_rows = nrows;
        _custom_scale = scale;

    }


    protected void scale_image_to_fit_box(HBox H, ImageView I) {
        I.fitHeightProperty().bind(H.heightProperty());
        I.setScaleY(_custom_scale);
        I.setScaleX(_custom_scale);

    }
}