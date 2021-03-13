package units.shooter_developers.Menu_pages;

/* All these should be renamed  following the google standard
   lowerCamelCase() for methods & non constant values

   Remove this keyword when it is not necessary
*/

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.Pair;
import units.shooter_developers.CustomException;
import units.shooter_developers.MenuAPI.Menu;

public class OptionsMenu extends Menu {

    public OptionsMenu(){
        super();
    }

    public OptionsMenu(Menu other_menu){
        super(other_menu);
    }

    @Override
    public void createContent(){

        setTitle("O P T I O N S");
        this.addSelectorItem("COLOR MODE", "dark", "light");

        this.addSelectorItem("RESOLUTION",
                (int) getStageWidth() + "x" + (int) getStageHeight() + " (current)",
                ((int) getScreenWidth()) + "x" + ((int) getScreenHeight()) + " (native)",
                "640x360 (widescreen)",
                "1000x600",
                "1024x768",
                "1280x720 (widescreen)",
                "1536x864 (widescreen)",
                "1600x900 (widescreen)",
                "1920x1080 (widescreen)");
        this.addItem("APPLY");
        this.addItem("BACK");

        getStage().setTitle("VIDEO GAME");

        getItem("BACK").setOnMouseReleased(event -> {
            GameMenu main_menu = new GameMenu(this);
            main_menu.start(getStage());
        });
        getItem("APPLY").setOnMouseReleased(event -> {
            applyCurrentSettings();
        });
    }



    private Pair<Double, Double> ParseSelectedResolution(String string_containing_resolution){
        String width_string;
        String height_string;

       /*  - Maybe here consider not ignoring matcher.find()
        *  - Exception should be more specific, here no message is printed
        *  */
        try {
            String regex = "\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(string_containing_resolution);
            matcher.find();
            width_string = matcher.group();
            matcher.find();
            height_string = matcher.group();

            double width = Integer.parseInt(width_string);
            double height = Integer.parseInt(height_string);

            return new Pair<>(width, height);
        }catch(Exception e){
            return new Pair<>(null, null);
        }

    }

    private void askConfirmChanges(double width_candidate, double height_candidate, String candidate_color_mode){
        AlertWindow alert_window = new AlertWindow(this, width_candidate, height_candidate, candidate_color_mode);
        alert_window.start(getStage());
    }

    private void applyCurrentSettings(){
        writeSettings();
        var selected_resolution = ParseSelectedResolution(getSelectorValue("RESOLUTION"));
        var candidate_width = selected_resolution.getKey();
        var candidate_height = selected_resolution.getValue();

        String candidate_color_mode = getSelectorValue("COLOR MODE");

        if (isSimulationRunning()) {
            if (candidate_width != getMenuWidth() || candidate_height != getMenuHeight())
                askConfirmChanges(candidate_width, candidate_height, candidate_color_mode);
            else {
                OptionsMenu options_menu = new OptionsMenu(this);
                try {
                    setColorMode(candidate_color_mode);
                    writeSettings();
                    options_menu.readProperties();
                }catch(CustomException.FileManagementException e){
                    System.out.println(e.getMessage() + " Using default settings.");
                }

                options_menu.start(getStage());
            }
        }
        else {
            setStageDimensions(candidate_width, candidate_height);
            setColorMode(candidate_color_mode);
            writeSettings();
            OptionsMenu options_menu = new OptionsMenu(this);
            try {
                options_menu.readProperties();
            } catch(CustomException.FileManagementException e){
                System.out.println(e.getMessage() + " Using default settings.");
            }
            options_menu.start(getStage());
        }

    }
}
