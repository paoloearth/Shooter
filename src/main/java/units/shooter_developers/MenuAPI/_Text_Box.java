package units.shooter_developers.MenuAPI;

import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class _Text_Box extends _Submenu_leaf {

    TextField textField = new TextField();

    public _Text_Box(String commands_url, _TypeImage T)
    {

        super(T);

        setFillWidth(false);

        textField.setPromptText("Enter your first name.");

        getChildren().add(textField);

        HBox H = createCustomHbox();
        var I = retrieve_image(commands_url,n_rows);

        DropShadow ds = new DropShadow( 50, Color.WHITE );
        I.setEffect(ds);

        scale_image_to_fit_box(H, I);
        fill_HBox_with_image(H,I);



        add_generic_child_node_to_parent_node(this,H);


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
