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
        setHgap(0.5*Menu.getMenuHeight());
        setVgap(0.1*Menu.getMenuHeight());
        setAlignment(Pos.CENTER);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> map_image_to_URL, double scale, int spritesheet_number_of_rows){
        ChoiceBox choice_box = new ChoiceBox(name, map_image_to_URL, spritesheet_number_of_rows, scale);
        add(choice_box, col, row);
    }
}
