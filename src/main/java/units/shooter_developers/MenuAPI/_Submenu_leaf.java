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


    protected _Submenu_leaf()
    {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(10));
        setSpacing(10);
    }
}