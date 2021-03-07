package units.shooter_developers.MenuAPI;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class _Text_Box extends _Submenu_leaf {

    TextField textField = new TextField();

    public _Text_Box(String commands_url, int nrows, double scale)
    {

        super(nrows, scale);

        setFillWidth(false);

        textField.setPromptText("Enter your first name.");

        getChildren().add(textField);

        HBox H = createCustomHbox();
        var I = Menu.retrieveImage(commands_url, _n_rows, 1);
        I.setPreserveRatio(true);

        DropShadow ds = new DropShadow( 50, Color.WHITE );
        I.setEffect(ds);

        scale_image_to_fit_box(H, I);
        H.getChildren().add(I);

        getChildren().add(H);


    }

    protected static HBox createCustomHbox() {
        HBox H = new HBox();
        H.setMinHeight(0);
        H.setAlignment(Pos.BOTTOM_CENTER);
        return H;
    }

    public String get_value()
    {
        return textField.getText();
    }



        /*
           To go back to previous configuration goes to
           Extends HBOX
           HBox.setHgrow(textField, Priority.NEVER);
        */







}
