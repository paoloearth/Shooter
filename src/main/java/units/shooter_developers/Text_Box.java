package units.shooter_developers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class Text_Box extends HBox {

    TextField textField = new TextField();

    public Text_Box()
    {
        getChildren().add(textField);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(10));
        HBox.setHgrow(textField, Priority.NEVER);
        textField.setPromptText("Enter your first name.");
        textField.setAlignment(Pos.CENTER);

    }

    public String get_value()
    {
        return textField.getText();
    }





}
