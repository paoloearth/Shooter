package units.shooter_developers.MenuAPI;
// Visited
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import units.shooter_developers.CustomCheckedException;

import java.util.Map;

class MenuGrid extends GridPane {

    public MenuGrid(){
        final double scale_width = 0.8;
        final double scale_height = 0.95;
        setMaxSize(scale_width*Menu.getMenuWidth(), scale_height*Menu.getMenuHeight());
        setMinSize(scale_width*Menu.getMenuWidth(), scale_height*Menu.getMenuHeight());
        setTranslateX((1-scale_width)/2.*Menu.getMenuWidth());
        setTranslateY((1-scale_height)/2.*Menu.getMenuHeight());
        setHgap(0.3*Menu.getMenuHeight());
        setVgap(0.05*Menu.getMenuHeight());
        setAlignment(Pos.CENTER);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> map_image_to_URL, double scale, int number_of_rows_spritesheet){
        final ChoiceBox choice_box = new ChoiceBox(name, map_image_to_URL, number_of_rows_spritesheet, scale);
        add(choice_box, col, row);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> map_image_to_URL, double scale, int number_of_rows_spritesheet, int default_index) throws CustomCheckedException.IndexOutOfRange {
        final ChoiceBox choice_box = new ChoiceBox(name, map_image_to_URL, number_of_rows_spritesheet, scale, default_index);
        add(choice_box, col, row);
    }

    public ChoiceBox getChoiceBox(String name) throws CustomCheckedException.MissingMenuComponentException {
        final var choice_box_object = (ChoiceBox) getChildren().parallelStream()
                .filter(e -> e instanceof ChoiceBox)
                .filter(e -> ((ChoiceBox) e).getName().equals(name))
                .findFirst()
                .orElse(null);

        if(choice_box_object == null){throw new CustomCheckedException.MissingMenuComponentException("Choice box with name \"" + name + "\".", ChoiceBox.class);}
        else{return choice_box_object;}
    }

    public void addTextBox(String name, int row, int col, String commands_url, int number_of_rows_spritesheet, double scale, String default_message, String default_content){
        final TextBox text_box = new TextBox(name, commands_url, number_of_rows_spritesheet, scale, default_message, default_content);
        add(text_box, col, row);
    }

    public TextBox getTextBox(String name) throws CustomCheckedException.MissingMenuComponentException {
        final var text_box_object = (TextBox)getChildren().parallelStream()
                .filter(e -> e instanceof TextBox)
                .filter(e -> ((TextBox) e).getName().equals(name))
                .findFirst()
                .orElse(null);

        if(text_box_object == null){throw new CustomCheckedException.MissingMenuComponentException("Text box with name \"" + name + "\".", TextBox.class);}
        else{return text_box_object;}

    }
}
