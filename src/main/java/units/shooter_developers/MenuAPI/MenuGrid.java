package units.shooter_developers.MenuAPI;

import javafx.geometry.Pos;
import javafx.scene.layout.*;

import java.util.Map;

public class MenuGrid extends GridPane {

    public MenuGrid(){
        double scale_width = 0.8;
        double scale_height = 0.95;
        setMaxSize(scale_width*Menu.getMenuWidth(), scale_height*Menu.getMenuHeight());
        setMinSize(scale_width*Menu.getMenuWidth(), scale_height*Menu.getMenuHeight());
        setTranslateX((1-scale_width)/2.*Menu.getMenuWidth());
        setTranslateY((1-scale_height)/2.*Menu.getMenuHeight());
        setHgap(0.3*Menu.getMenuHeight());
        setVgap(0.05*Menu.getMenuHeight());
        setAlignment(Pos.CENTER);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> map_image_to_URL, double scale, int number_of_rows_spritesheet){
        ChoiceBox choice_box = new ChoiceBox(name, map_image_to_URL, number_of_rows_spritesheet, scale);
        add(choice_box, col, row);
    }

    public ChoiceBox getChoiceBox(String name){
        var choice_box_object = getChildren().parallelStream()
                .filter(e -> e instanceof ChoiceBox)
                .filter(e -> ((ChoiceBox) e).getName().equals(name))
                .findFirst()
                .orElse(null);

        if(choice_box_object == null)
            return null;
        else
            return (ChoiceBox) choice_box_object;
    }

    public void addTextBox(String name, int row, int col, String commands_url, int number_of_rows_spritesheet, double scale, String default_message){
        TextBox text_box = new TextBox(name, commands_url, number_of_rows_spritesheet, scale, default_message, "not_found");
        add(text_box, col, row);
    }

    public TextBox getTextBox(String name){
        var text_box_object = getChildren().parallelStream()
                .filter(e -> e instanceof TextBox)
                .filter(e -> ((TextBox) e).getName().equals(name))
                .findFirst()
                .orElse(null);

        if(text_box_object == null)
            return null;
        else
            return (TextBox) text_box_object;
    }
}
