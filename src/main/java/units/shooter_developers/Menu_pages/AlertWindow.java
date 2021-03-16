package units.shooter_developers.Menu_pages;

import units.shooter_developers.CustomCheckedException;
import units.shooter_developers.CustomSettings;
import units.shooter_developers.MenuAPI.Menu;

import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class AlertWindow extends Menu {
    double _candidateWidth;
    double _candidateHeight;
    String _candidateColorMode;

    AlertWindow(Menu otherMenu, double candidateWidth, double candidateHeight, String candidateColorMode){
        super(otherMenu);
        _candidateWidth = candidateWidth;
        _candidateHeight = candidateHeight;
        _candidateColorMode = candidateColorMode;
    }

    @Override
    public void createContent(){
        ImageView alertImage;
        try {
            alertImage = Menu.retrieveImage(CustomSettings.URL_WARNING_ICON, 1, 1);
        }catch(CustomCheckedException.FileManagementException e){
            System.out.println(e.toString() + " Alert image not found. Using alternative one. Continuing");
            alertImage = new ImageView(new Rectangle(10, 10).snapshot(null, null));
        }

        addCentralImageView(alertImage, 0.7, 0.7);
        addSecondaryTitle("CAUTION!");
        addFreeItem("BACK", 0.05, 0.2);
        addFreeItem("CONTINUE", 0.76, 0.2);
        addFlashDisclaimer("Game will be reset. Do you want to confirm?");

        try {
            getItem("BACK").setOnMouseReleased(event -> {
                OptionsMenu optionsMenu = new OptionsMenu(this);
                optionsMenu.start(getStage());
            });

            getItem("CONTINUE").setOnMouseReleased(event -> {
                setStageDimensions(_candidateWidth, _candidateHeight);
                setColorMode(_candidateColorMode);
                try {
                    writeSettings();
                } catch (CustomCheckedException.FileManagementException e) {
                    System.out.println(e.toString() + " Writing was wrong. Continuing.");
                }
                OptionsMenu optionsMenu = new OptionsMenu();
                optionsMenu.start(getStage());
            });
        }catch (CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.toString() + " Fatal error. Closing application.");
            Runtime.getRuntime().exit(1);
        }
    }

}
