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

        setTitle(player_name);


        var winner_image = retrieve_image(player_spriteSheet_url,4,1);
        var fireworks = retrieve_image("fireworks.png", 1,1);

        var sp = setCentralComposition(fireworks,winner_image);

        setCenter(sp);


    }

    private void setTitle(String winner){
        Text top = new Text("The winner is "+ winner);
        top.setFont(Font.font("Times New Roman", FontWeight.BOLD,_width*0.06));
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

    private StackPane setCentralComposition(ImageView background_sprite, ImageView player_sprite)
    {
        StackPane sp = new StackPane();
        sp.setMaxSize(_width/2,_height/2);
        sp.setMinSize(_width/2, _height/2);
        sp.getChildren().add(background_sprite);
        sp.getChildren().add(player_sprite);
        sp.setAlignment(Pos.CENTER);
        return sp;
    }

}