package units.shooter_developers;

import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class WinnerWindow extends BorderPane {

    double _width, _height;

    WinnerWindow(double width, double height, String player_name, String player_spriteSheet_url )
    {
        this._width = width;
        this._height = height;
        this.setPrefSize(width,height);

        create_title(player_name);


        var winner_image = retrieve_image(player_spriteSheet_url,4,1);
        var fireworks = retrieve_image("fireworks.png", 1,1);
        
        var sp = create_central_image_using_stackPane(fireworks,winner_image);

        setCenter(sp);


    }

    private void create_title(String winner){
        Text top = new Text("The winner is "+ winner);
        top.setFont(Font.font("Times New Roman", FontWeight.BOLD,60));
        setAlignment(top,Pos.TOP_CENTER);
        setTop(top);
    }


    private ImageView retrieve_image(String URL, int n_rows, int n_cols)
    {
        var I = new Image(URL);
        var IM =  new ImageView(I);
        IM.setViewport(new Rectangle2D( 0, 0, I.getWidth()/n_cols, I.getHeight()/n_rows));
        return IM;
    }

    private StackPane create_central_image_using_stackPane(ImageView background, ImageView foreground)
    {
        StackPane sp = new StackPane();
        //sp.setMinSize(_width/2,_height/2);
        sp.setMaxSize(_width/2,_height/2);
        sp.setMinSize(_width/2, _height/2);
        add_image_stackPane(background,sp);
        add_image_stackPane(foreground,sp);
        return sp;
    }

    private void add_image_stackPane(ImageView image, StackPane sp){
        image.fitHeightProperty().bind(sp.heightProperty());
        image.setPreserveRatio(true);
        sp.getChildren().add(image);
        sp.setAlignment(Pos.CENTER);
    }

}