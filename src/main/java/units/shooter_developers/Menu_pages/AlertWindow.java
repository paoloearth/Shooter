package units.shooter_developers.Menu_pages;
// VISITED

/* All these should be renamed  following the google standard
   lowerCamelCase() for methods & non constant values

   Remove this keyword when it is not necessary
*/
import units.shooter_developers.CustomSettings;
import units.shooter_developers.MenuAPI.Menu;

public class AlertWindow extends Menu {
    double _candidate_width;
    double _candidate_height;
    String _candidate_color_mode;

    AlertWindow(Menu other_menu, double candidate_width, double candidate_height, String candidate_color_mode){
        super(other_menu);
        _candidate_width = candidate_width;
        _candidate_height = candidate_height;
        _candidate_color_mode = candidate_color_mode;
    }

    @Override
    public void createContent(){

        /* Maybe move this URL to the right place */
        var alert_image = Menu.retrieveImage(CustomSettings.URL_WARNING_ICON, 1,1);

        addCentralImageView(alert_image, 0.7, 0.7);
        addSecondaryTitle("CAUTION!");
        addFreeItem("BACK", 0.05, 0.2);
        addFreeItem("CONTINUE", 0.76, 0.2);
        addFlashDisclaimer("Game will be reset. Do you want to confirm?");


        getItem("BACK").setOnMouseReleased(event -> {
            OptionsMenu options_menu = new OptionsMenu(this);
            options_menu.start(getStage());
        });

        getItem("CONTINUE").setOnMouseReleased(event -> {
            setStageDimensions(_candidate_width, _candidate_height);
            setColorMode(_candidate_color_mode);
            writeSettings();
            OptionsMenu options_menu = new OptionsMenu();
            options_menu.start(getStage());
        });
    }

}
