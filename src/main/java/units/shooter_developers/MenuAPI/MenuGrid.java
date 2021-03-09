package units.shooter_developers.MenuAPI;

import javafx.geometry.Pos;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.Map;

public class MenuGrid extends GridPane {

    public MenuGrid(){
        setWidth(0.8*Menu.getMenuWidth());
        setHeight(0.8*Menu.getMenuHeight());
        setHgap(0.1*Menu.getMenuHeight());
        setVgap(0.1*Menu.getMenuHeight());
        setAlignment(Pos.CENTER);

        ColumnConstraints column_constraint = new ColumnConstraints();
        column_constraint.setPercentWidth(50);
        RowConstraints row_constraint = new RowConstraints();
        row_constraint.setPercentHeight(50);
        getRowConstraints().addAll(row_constraint, row_constraint);
    }

    public void addChoiceBox(String name, Map<String, String> map_image_to_URL, double scale, int spritesheet_number_of_rows){
        ChoiceBox choice_box = new ChoiceBox(name, map_image_to_URL, spritesheet_number_of_rows, scale);
    }
}
