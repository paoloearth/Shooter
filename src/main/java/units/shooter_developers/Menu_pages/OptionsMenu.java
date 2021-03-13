package units.shooter_developers.Menu_pages;

/* All these should be renamed  following the google standard
   lowerCamelCase() for methods & non constant values

   Remove this keyword when it is not necessary
*/

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

        try {
            getItem("BACK").setOnMouseReleased(event -> {
                GameMenu main_menu = new GameMenu(this);
                main_menu.start(getStage());
            });
            getItem("APPLY").setOnMouseReleased(event -> {
                applyCurrentSettings();
            });
        } catch (CustomException.MissingMenuComponentException e){
            System.out.println(e.getMessage() + " Fatal error. Closing application.");
            Runtime.getRuntime().exit(1);
        }
    }



    private Pair<Double, Double> ParseSelectedResolution(String string_containing_resolution) throws CustomException.WrongParsingException {
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
            throw new CustomException.WrongParsingException(string_containing_resolution, int.class);
        }

    }

    private void LaunchConfirmChangesPage(double width_candidate, double height_candidate, String candidate_color_mode){
        AlertWindow alert_window = new AlertWindow(this, width_candidate, height_candidate, candidate_color_mode);
        alert_window.start(getStage());
    }

    private void applyCurrentSettings(){
        try {
            writeSettings();
        }catch (CustomException.FileManagementException e){
            System.out.println(e.getMessage() + " Writing was wrong. Continuing.");
        }

        Pair<Double, Double> selected_resolution = getSelectedResolution();

        String candidate_color_mode = getSelectedColorMode();

        double candidate_width = selected_resolution.getKey();
        double candidate_height = selected_resolution.getValue();


        if (isSimulationRunning() &&
                ((int)candidate_width != (int)getMenuWidth() ||
                (int)candidate_height != (int)getMenuHeight())) {
            LaunchConfirmChangesPage(candidate_width, candidate_height, candidate_color_mode);
        } else {
            LaunchChangedOptionsMenu(candidate_width, candidate_height, candidate_color_mode);
        }

    }

    private void LaunchChangedOptionsMenu(double candidate_width, double candidate_height, String candidate_color_mode) {
        setStageDimensions(candidate_width, candidate_height);
        setColorMode(candidate_color_mode);

        try {
            writeSettings();
        }catch (CustomException.FileManagementException e){
            System.out.println(e.getMessage() + " Writing was wrong. Continuing.");
        }

        OptionsMenu options_menu = new OptionsMenu(this);
        try {
            options_menu.readProperties();
        } catch(CustomException.FileManagementException e){
            System.out.println(e.getMessage() + " Using default settings.");
        }

        options_menu.start(getStage());
    }

    private String getSelectedColorMode() {
        String candidate_color_mode;
        try {
            candidate_color_mode = getSelectorValue("COLOR MODE");
        }catch (CustomException.MissingMenuComponentException e){
            System.out.println(e.getMessage() + " Color mode will not be changed. Continuing.");
            candidate_color_mode = getColorMode();
        }
        return candidate_color_mode;
    }

    private Pair<Double, Double> getSelectedResolution() {
        var selected_resolution = new Pair<>(getMenuWidth(), getMenuHeight());
        try {
            selected_resolution = ParseSelectedResolution(getSelectorValue("RESOLUTION"));
        }catch (Exception e) {
            System.out.println(e.getMessage() + " Resolution will not be changed. Continuing.");
        }
        return selected_resolution;
    }
}
