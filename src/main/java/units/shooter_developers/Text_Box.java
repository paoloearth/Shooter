package units.shooter_developers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

public class Text_Box extends HBox {

    TextField textField = new TextField();

    public Text_Box(String commands_url)
    {

        setAlignment(Pos.CENTER);

        setPadding(new Insets(10));


        HBox.setHgrow(textField, Priority.NEVER);


        textField.setPromptText("Enter your first name.");
        textField.setAlignment(Pos.CENTER);

        getChildren().add(textField);

        HBox H = createCustomHbox();
        var I = retrieve_image(commands_url,1,1);

        DropShadow ds = new DropShadow( 50, Color.WHITE );
        I.setEffect(ds);

        scale_image_to_fit_box(H, I);
        fill_HBox_with_image(H,I);

        getChildren().add(H);



    }

    public String get_value()
    {
        return textField.getText();
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


    private void scale_image_to_fit_box(HBox H, ImageView I) {
        I.fitHeightProperty().bind(H.heightProperty());
        I.fitWidthProperty().bind(H.widthProperty());
        I.setScaleX(.4);
        I.setScaleY(.4);
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





}
