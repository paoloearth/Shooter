package units.shooter_developers.MenuAPI;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class _Button_selection_menu extends _Submenu_component {

    Button launch_simulation;
    Button launch_default;

    public _Button_selection_menu(double width, double height)
    {
        super(width*Menu.getMenuWidth(), height*Menu.getMenuHeight());

        fix_submenu_size_to_width_and_height();

        setAlignment(Pos.CENTER_LEFT);

        VBox V = create_custom_VBOX();

        launch_simulation = create_custom_button("LAUNCH SIMULATION");
        launch_default    = create_custom_button("LAUNCH DEFAULT");

        V.getChildren().addAll(launch_simulation,launch_default);
        getChildren().add(V);

    }


    private VBox create_custom_VBOX() {
        VBox V = new VBox();
        V.setFillWidth(true);
        V.setAlignment(Pos.CENTER_LEFT);
        V.setSpacing(10);
        return V;
    }

    private Button create_custom_button(String text) {
        var B = new Button();
        B.setText(text);
        B.setAlignment(Pos.TOP_LEFT);
        return B;
    }


    public Button getMainLaunchButton() {
        return launch_simulation;
    }

    public Button getDefaultLaunchButton() {
        return launch_default;
    }
}
