package units.shooter_developers.menu_pages;

import units.shooter_developers.customs.CustomCheckedException;
import units.shooter_developers.menu_api.Menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.util.Pair;

public class OptionsMenu extends Menu {

    public OptionsMenu(){
        super();
    }

    public OptionsMenu(Menu otherMenu){
        super(otherMenu);
    }

    @Override
    public void createContent() throws CustomCheckedException.MissingMenuComponentException {

        setTitle("O P T I O N S");

        int defaultIndex = getColorMode().equals("dark") ? 0: 1;
        this.addSelectorItem("COLOR MODE", defaultIndex,  "dark", "light");

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
            GameMenu mainMenu = new GameMenu(this);
            try {
                mainMenu.start(getStage());
            } catch (CustomCheckedException.MissingMenuComponentException e) {
                throw new RuntimeException(e);
            }
        });

        getItem("APPLY").setOnMouseReleased(event -> {
            try {
                applyCurrentSettings();
            } catch (CustomCheckedException.MissingMenuComponentException e) {
                throw new RuntimeException(e);
            }
        });
    }



    private Pair<Double, Double> ParseSelectedResolution(String stringContainingResolution) throws CustomCheckedException.WrongParsingException {
        String widthString;
        String heightString;
        
        try {
            String regex = "\\d+";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(stringContainingResolution);
            matcher.find();
            widthString = matcher.group();
            matcher.find();
            heightString = matcher.group();

            double width = Integer.parseInt(widthString);
            double height = Integer.parseInt(heightString);

            return new Pair<>(width, height);
        }catch(Exception e){
            throw new CustomCheckedException.WrongParsingException(stringContainingResolution, int.class);
        }

    }

    private void launchConfirmChangesPage(double widthCandidate, double heightCandidate, String candidateColorMode) throws CustomCheckedException.MissingMenuComponentException {
        AlertWindow alertWindow = new AlertWindow(this, widthCandidate, heightCandidate, candidateColorMode);
        alertWindow.start(getStage());
    }

    private void applyCurrentSettings() throws CustomCheckedException.MissingMenuComponentException {
        try {
            writeSettings();
        }catch (CustomCheckedException.FileManagementException e){
            System.err.println(e.toString() + " Writing was wrong. Continuing.");
        }

        Pair<Double, Double> selectedResolution = getSelectedResolution();

        String candidate_color_mode = getSelectedColorMode();

        double candidateWidth = selectedResolution.getKey();
        double candidate_height = selectedResolution.getValue();


        if (isSimulationRunning() &&
                ((int)candidateWidth != (int)getMenuWidth() ||
                (int)candidate_height != (int)getMenuHeight())) {
            launchConfirmChangesPage(candidateWidth, candidate_height, candidate_color_mode);
        } else {
            launchChangedOptionsMenu(candidateWidth, candidate_height, candidate_color_mode);
        }

    }

    private void launchChangedOptionsMenu(double candidateWidth, double candidateHeight, String candidateColorMode) throws CustomCheckedException.MissingMenuComponentException {
        setStageDimensions(candidateWidth, candidateHeight);
        setColorMode(candidateColorMode);

        try {
            writeSettings();
        }catch (CustomCheckedException.FileManagementException e){
            System.err.println(e.toString() + " Writing was wrong. Continuing.");
        }

        OptionsMenu optionsMenu = new OptionsMenu(this);
        try {
            optionsMenu.readProperties();
        } catch(CustomCheckedException.FileManagementException e){
            System.err.println(e.toString() + " Using default settings.");
        }

        optionsMenu.start(getStage());
    }

    private String getSelectedColorMode() {
        String candidateColorMode;
        try {
            candidateColorMode = getSelectorValue("COLOR MODE");
        }catch (CustomCheckedException.MissingMenuComponentException e){
            System.err.println(e.toString() + " Color mode will not be changed. Continuing.");
            candidateColorMode = getColorMode();
        }
        return candidateColorMode;
    }

    private Pair<Double, Double> getSelectedResolution() {
        var selectedResolution = new Pair<>(getMenuWidth(), getMenuHeight());
        try {
            selectedResolution = ParseSelectedResolution(getSelectorValue("RESOLUTION"));
        }catch (Exception e) {
            System.err.println(e.toString() + " Resolution will not be changed. Continuing.");
        }
        return selectedResolution;
    }
}
