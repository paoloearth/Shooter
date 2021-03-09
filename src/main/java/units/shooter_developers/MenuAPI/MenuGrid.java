package units.shooter_developers.MenuAPI;

import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.Map;

public class MenuGrid extends GridPane {

    public MenuGrid(){
        double scale_width = 0.8;
        double scale_height = 0.8;
        setMaxSize(scale_width*Menu.getMenuWidth(), scale_height*Menu.getMenuHeight());
        setMinSize(scale_width*Menu.getMenuWidth(), scale_height*Menu.getMenuHeight());
        setTranslateX((1-scale_width)/2.*Menu.getMenuWidth());
        setTranslateY((1-scale_height)/2.*Menu.getMenuHeight());
        //setHgap(0.3*Menu.getMenuHeight());
        //setVgap(0.1*Menu.getMenuHeight());
        setAlignment(Pos.CENTER);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> map_image_to_URL, double scale, int number_of_rows_spritesheet){
        ChoiceBox choice_box = new ChoiceBox(name, map_image_to_URL, number_of_rows_spritesheet, scale);
        add(choice_box, col, row);
    }

    public void addTextBox(String name, int row, int col, String commands_url, int number_of_rows_spritesheet, double scale, String default_message){
        Text_Box text_box = new Text_Box(name, commands_url, number_of_rows_spritesheet, scale, default_message);
        add(text_box, col, row);
    }
}
