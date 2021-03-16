package units.shooter_developers.menu_api;

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/************************ FLASH DISCLAIMER ITEM ****************************************/

class FlashDisclaimer extends StackPane {
    FlashDisclaimer(String text) {
        Text disclaimerText = new Text(text);
        disclaimerText.setFont(Font.font("Times New Roman", FontWeight.BOLD, Menu.getMenuWidth() * 0.025));
        disclaimerText.setFill(Menu.getColorPalette().basic_primary_color);
        textAnimation(disclaimerText);
        setAlignment(disclaimerText, Pos.TOP_CENTER);

        getChildren().add(disclaimerText);
    }

    private void textAnimation(Text bottom) {
        FadeTransition textTransition = new FadeTransition(Duration.seconds(1.0), bottom);
        textTransition.setAutoReverse(true);
        textTransition.setFromValue(0);
        textTransition.setToValue(1);
        textTransition.setCycleCount(Transition.INDEFINITE);
        textTransition.play();
    }


}
