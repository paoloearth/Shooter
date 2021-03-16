package units.shooter_developers.MenuAPI;

/*
 *
 *  MENU BASED ON MICHAEL J. SIDERIUS'S ONE:
 *
 *  DECEMBER 30 2015
 *  VIDEO GAME MENU CONCEPT V1
 *  GOAL: PRACTICE USING JAVAFX TECHNOLOGY AND METHODS
 *
 *
 *  https://github.com/Siderim/video-game-menu/
 */

import javafx.scene.shape.Rectangle;
import units.shooter_developers.CustomColors;
import units.shooter_developers.CustomSettings;
import units.shooter_developers.Simulation;
import units.shooter_developers.CustomCheckedException;
import static units.shooter_developers.CustomSettings.URL_CONFIG_FILE;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public abstract class Menu extends Application {
    private static double _heightScale;
    private static double _widthScale;
    private static double _stageHeight;
    private static double _stageWidth;
    private Pane _root;
    private Stage _stage;
    private double _positionWidthRatio;
    private double _positionHeightRatio;
    private static Simulation _simulationInstance;
    private static boolean _simulationRunning;
    private static ColorPalette _colorPalette;
    private static ImageView _background;
    private static String _colorMode;

    /************************** CONSTRUCTORS *****************************/

    public Menu() {
        _widthScale = 1;
        _heightScale = 1;
        _positionWidthRatio = 0;
        _positionHeightRatio = 0;
        _simulationRunning = false;
        _simulationInstance = null;
        _colorPalette = new ColorPalette();
        _colorMode = "dark";

        _colorPalette.setDark();
    }

    public Menu(Menu otherMenu){
        _positionWidthRatio = otherMenu._positionWidthRatio;
        _positionHeightRatio = otherMenu._positionHeightRatio;
        _stage = otherMenu._stage;
        this.createRootAndBackground();
    }

    private void createRootAndBackground() {
        _root = new Pane();

        _background.setFitWidth(getMenuWidth());
        _background.setFitHeight(getMenuHeight());
        _background.setX(getPositionX());
        _background.setY(getPositionY());


        _root.getChildren().add(_background);
    }

    /************************** START METHOD ************************************/

    public void start(Stage stage){
        setStage(stage);
        getStage().setMaximized(false);
        setStageDimensions(getMenuWidth(), getMenuHeight());
        createRootAndBackground();
        createContent();
        stage.centerOnScreen();
        show();
    }

    public abstract void createContent();

    // Move name of config file to custom settings?
    public void readProperties() throws CustomCheckedException.FileManagementException{
        File configFile = new File(URL_CONFIG_FILE);
        Properties config;
        ImageView backgroundLight;
        ImageView backgroundDark;

        try {
            FileReader reader;
            config = new Properties();
            reader = new FileReader(configFile);
            config.load(reader);
            reader.close();
        }catch(Exception e){
            throw new CustomCheckedException.FileManagementException(configFile.getPath());
        }

        setResolution(config);
        try {
            backgroundDark = retrieveImage(CustomSettings.URL_BACKGROUND_DARK, 1, 1);
            backgroundLight = retrieveImage(CustomSettings.URL_BACKGROUND_LIGHT, 1, 1);
        }catch(CustomCheckedException.FileManagementException e){
            System.out.println(e.toString() + " Some background were not found. Using default texture.");
            var rectangle = new Rectangle(10, 10);

            rectangle.setFill(Color.WHITE);
            backgroundDark = new ImageView((rectangle).snapshot(null, null));

            rectangle.setFill(Color.BLACK);
            backgroundLight = new ImageView(rectangle.snapshot(null, null));
        }

        var colorMode = config.getProperty("COLOR MODE");
        colorMode = colorMode == null? "" :  colorMode;

        if(colorMode.equals("light")){
            setColorMode("light");
            _background = backgroundLight;
        } else {
            setColorMode("dark");
            _background = backgroundDark;
        }
    }

    private void setResolution(Properties config){
        double width, height;

        try{
            var parsedResolution = parseResolutionFromStrings(config.getProperty("WIDTH"), config.getProperty("HEIGHT"));
            width = parsedResolution.getKey();
            height = parsedResolution.getValue();
        }catch(CustomCheckedException.WrongParsingException e) {
            System.out.println(e.toString() + " Using native resolution.");
            width = getScreenWidth();
            height = getScreenHeight();
        }

        setStageDimensions(width, height);
    }

    private Pair<Double, Double> parseResolutionFromStrings(String widthString, String heightString) throws CustomCheckedException.WrongParsingException {
        double width, height;

        try {
            width = Double.parseDouble(widthString);
        }catch(Exception e) {
            throw new CustomCheckedException.WrongParsingException(widthString, Double.class);
        }

        try {
            height = Double.parseDouble(heightString);
        }catch(Exception e) {
            throw new CustomCheckedException.WrongParsingException(heightString, Double.class);
        }

        return new Pair<>(width, height);
    }

    public void writeSettings() throws CustomCheckedException.FileManagementException {
        Properties config = new Properties();

        config.setProperty("COLOR MODE", getColorMode());
        config.setProperty("WIDTH", String.valueOf(getStageWidth()));
        config.setProperty("HEIGHT", String.valueOf(getStageHeight()));

        File configFile = new File("config.ini");
        try{
            FileWriter writer = new FileWriter(configFile);
            config.store(writer, "Game settings");
            writer.close();
        } catch (IOException e) {
            throw new CustomCheckedException.FileManagementException(configFile.getPath());
        }
    }


    /************************** CONTENT MANAGEMENT *****************************/

    public void addItem(String newMenuItem){
        generateMenuBoxIfNotExist();
        try {
            getItemsBox().addItem(newMenuItem);
        }catch(CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.toString() + " Main item box object was not found neither created. Fatal error. Closing application");
            Runtime.getRuntime().exit(1);
        }
    }

    public void addFreeItem(String newMenuItem, double positionRatioX, double positionRatioY){
        MenuItem new_item = new MenuItem(newMenuItem);
        new_item.setTranslateX(positionRatioX*getMenuWidth());
        new_item.setTranslateY(positionRatioY*getMenuHeight());
        new_item.setAlignment(Pos.CENTER);

        addGenericNode(new_item);
    }

    public void addNonAnimatedItem(String name){
        generateMenuBoxIfNotExist();
        try {
            getItemsBox().addNonAnimatedItem(name);
        }catch(CustomCheckedException.MissingMenuComponentException e){
        System.out.println(e.toString() + " Main item box object was not found neither created. Fatal error. Closing application");
        Runtime.getRuntime().exit(1);
    }
    }

    public void addSelectorItem(String name, String ... selection_tags){
        generateMenuBoxIfNotExist();
        ArrayList<String> tagList= new ArrayList<>();
        Collections.addAll(tagList, selection_tags);

        try{
            getItemsBox().addSelectorItem(name, tagList);
        }catch(CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.toString() + " Main item box object was not found neither created. Fatal error. Closing application");
            Runtime.getRuntime().exit(1);
        }
    }

    public void addSelectorItem(String name, int defaultIndex, String ... selectionTags){
        generateMenuBoxIfNotExist();
        ArrayList<String> tagList= new ArrayList<>();
        Collections.addAll(tagList, selectionTags);

        MenuBox menu_box = null;
        try{
            menu_box = getItemsBox();
            menu_box.addSelectorItem(name, defaultIndex, tagList);
        }catch(CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.toString() + " Selector item box object was not found neither created. Fatal error. Closing application");
            Runtime.getRuntime().exit(1);
        }catch(CustomCheckedException.IndexOutOfRange e){
            System.out.println(e.toString() + " Index not set. Using default construction indexing. Continuing.");
            menu_box.addSelectorItem(name, tagList);
        }
    }

    public void addGenericNode(Node genericNode){
        _root.getChildren().add(genericNode);
    }

    public void setTitle(String title){
        removeTitleIfItIs();

        Title title_object = new Title(title);

        title_object.setTranslateX(0.0476*getMenuWidth() + getPositionX());
        title_object.setTranslateY(0.333*getMenuHeight() + getPositionY());

        _root.getChildren().add(title_object);
    }

    public void removeTitleIfItIs(){
        try {
            var title_object = getTitleObject();
            _root.getChildren().remove(title_object);
        }catch(CustomCheckedException.MissingMenuComponentException ignored){}
    }

    public void addFlashDisclaimer(String disclaimer_text){
        var disclaimerObject = new FlashDisclaimer(disclaimer_text);

        var menuFrame = new BorderPane();
        menuFrame.setPrefSize(getMenuWidth(), getMenuHeight());

        BorderPane.setAlignment(disclaimerObject,Pos.BOTTOM_CENTER);
        menuFrame.setBottom(disclaimerObject);
        menuFrame.setDisable(true);

        _root.getChildren().add(menuFrame);
    }

    public void addCentralImageView(ImageView image, double scaleWidth, double scaleHeight){
        var imageFrame = new StackPane();
        image.fitHeightProperty().bind(imageFrame.heightProperty());
        image.setPreserveRatio(true);
        imageFrame.setMaxSize(scaleWidth*getMenuWidth(), scaleHeight*getMenuHeight());
        imageFrame.setMinSize(scaleWidth*getMenuWidth(), scaleHeight*getMenuHeight());
        imageFrame.getChildren().add(image);
        imageFrame.setAlignment(Pos.CENTER);

        var menuFrame = new BorderPane();
        menuFrame.setPrefSize(getMenuWidth(), getMenuHeight());
        menuFrame.setCenter(imageFrame);
        menuFrame.setDisable(true);

        _root.getChildren().add(menuFrame);
    }

    public void addSecondaryTitle(String title){
        var menuFrame = new BorderPane();
        menuFrame.setPrefSize(getMenuWidth(), getMenuHeight());

        Text titleObject = new Text(title);
        titleObject.setFont(Font.font("Times New Roman", FontWeight.BOLD,getMenuWidth()*0.06));
        titleObject.setFill(getColorPalette().basic_primary_color);

        BorderPane.setAlignment(titleObject,Pos.TOP_CENTER);
        menuFrame.setDisable(true);
        menuFrame.setTop(titleObject);

        _root.getChildren().add(menuFrame);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> mapImageToUrl, double scale, int spritesheetNumberOfRows){
        MenuGrid menuGridObject = getMenuGridAndCreateIfNotExist();
        menuGridObject.addChoiceBox(name, row, col, mapImageToUrl, scale, spritesheetNumberOfRows);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> mapImageToUrl, double scale, int spritesheetNumberOfRows, int defaultIndex) throws CustomCheckedException.IndexOutOfRange {
        MenuGrid menuGridObject = getMenuGridAndCreateIfNotExist();
        menuGridObject.addChoiceBox(name, row, col, mapImageToUrl, scale, spritesheetNumberOfRows, defaultIndex);
    }

    public void addTextBox(String name, int row, int col, String commandsUrl, int numberOfRowsSpritesheet, double scale, String defaultMessage, String default_content){
        MenuGrid menuGridObject = getMenuGridAndCreateIfNotExist();

        menuGridObject.addTextBox(name, row, col, commandsUrl, numberOfRowsSpritesheet, scale, defaultMessage, default_content);
    }

    private void generateMenuBoxIfNotExist(){
        MenuBox menuBox = (MenuBox) _root.getChildren().parallelStream()
                .filter(e -> e instanceof MenuBox)
                .findFirst()
                .orElse(null);

        if(menuBox == null){
            MenuBox vbox = new MenuBox();
            vbox.setTranslateX(0.0952*getMenuWidth() + getPositionX());
            vbox.setTranslateY(0.5*getMenuHeight() + getPositionY());

            _root.getChildren().addAll(vbox);
        }
    }


    /************************** SET/GET METHODS *****************************/

    /** GETTERS **/

    public static double getScreenWidth(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();

        return screenBounds.getWidth();
    }

    public static double getScreenHeight(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screenBounds = screen.getVisualBounds();

        return screenBounds.getHeight();
    }

    public Stage getStage(){
        return _stage;
    }

    public static double getStageHeight() {
        return _stageHeight;
    }

    public static double getStageWidth() {
        return _stageWidth;
    }

    public static double getMenuHeight() {
        return _heightScale * getStageHeight();
    }

    public static double getMenuWidth() {
        return _widthScale * getStageWidth();
    }

    public double getPositionX(){
        return _positionWidthRatio *getMenuWidth();
    }

    public double getPositionY(){
        return _positionHeightRatio *getMenuHeight();
    }

    public Scene getSceneFromStage(){
        return getStage().getScene();
    }

    public static String getColorMode(){
        return _colorMode;
    }

    public MenuItem getItem(String name) throws CustomCheckedException.MissingMenuComponentException {
        final var item = getItems().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(item == null){throw new CustomCheckedException.MissingMenuComponentException("Item with name \"" +name+ "\".", MenuItem.class);}
        else{return item;}
    }

    public String getSelectorValue(String name) throws CustomCheckedException.MissingMenuComponentException {
        return getSelectorItem(name).getText();
    }

    public String getChoiceBoxValue(String name){
        String value = "";
        try{
            value = getChoiceBox(name).getValue();
        }catch(CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.toString() + " Fatal error. Closing application.");
            Runtime.getRuntime().exit(1);
        }
        return value;
    }

    public String getTextBoxValue(String name){
        String value = "";
        try{
            value = getTextBox(name).getValue();
        }catch(CustomCheckedException.MissingMenuComponentException e){
            System.out.println(e.toString() + " Fatal error. Closing application.");
            Runtime.getRuntime().exit(1);
        }
        return value;
    }

    public static Simulation getSimulationInstance(){
        return _simulationInstance;
    }

    protected static ColorPalette getColorPalette(){
        return _colorPalette;
    }

    protected SelectorItem getSelectorItem(String name) throws CustomCheckedException.MissingMenuComponentException {
        final var selectorItem = getSelectorItems().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(selectorItem == null) {throw new CustomCheckedException.MissingMenuComponentException("Selector with name \"" + name + "\".", SelectorItem.class);}
        else return selectorItem;
    }

    private Title getTitleObject() throws CustomCheckedException.MissingMenuComponentException {
        final var titleObject = (Title)_root.getChildren().stream()
                .filter(e -> e instanceof Title)
                .findFirst()
                .orElse(null);

        if(titleObject == null){throw new CustomCheckedException.MissingMenuComponentException("Main title object.", Title.class);}
        else return titleObject;
    }

    private MenuBox getItemsBox() throws CustomCheckedException.MissingMenuComponentException {
        var menuBoxObject = (MenuBox)_root.getChildren().parallelStream()
                .filter(e -> e instanceof MenuBox)
                .findFirst()
                .orElse(null);

        if(menuBoxObject == null){throw new CustomCheckedException.MissingMenuComponentException("Main items box object.", MenuBox.class);}
        else{return menuBoxObject;}
    }

    private ArrayList<MenuItem> getItems(){
        var itemListFromBox = new ArrayList<MenuItem>();
        try {
            itemListFromBox = getItemsBox().getItems();
        }catch(CustomCheckedException.MissingMenuComponentException ignored){}

        //add items not contained in items box to the list
        ArrayList<MenuItem> fullItemList = itemListFromBox;
        _root.getChildren().stream()
                .filter(e -> e instanceof MenuItem)
                .forEach(e -> fullItemList.add((MenuItem)e));

        return fullItemList;
    }

    private ChoiceBox getChoiceBox(String name) throws CustomCheckedException.MissingMenuComponentException {
        var menuGrid = getMenuGridAndCreateIfNotExist();
        return menuGrid.getChoiceBox(name);
    }

    private TextBox getTextBox(String name) throws CustomCheckedException.MissingMenuComponentException {
        var menuGrid = getMenuGridAndCreateIfNotExist();
        return menuGrid.getTextBox(name);
    }

    private MenuGrid getMenuGridAndCreateIfNotExist() {
        var menuGridObject = (MenuGrid) _root.getChildren().stream()
                .filter(e -> e instanceof MenuGrid)
                .findFirst()
                .orElse(null);

        if (menuGridObject == null) {
            menuGridObject = new MenuGrid();
            _root.getChildren().add(menuGridObject);
        }
        return menuGridObject;
    }

    private ArrayList<SelectorItem> getSelectorItems(){
        try{
            return getItemsBox().getSelectorItems();
        }catch(CustomCheckedException.MissingMenuComponentException e){
            return new ArrayList<>();
        }
    }


    /** SETTERS **/

    public void setStage(Stage stage){
        _stage = stage;
        _stage.setMaximized(false);
    }

    public void setStageDimensions(double width, double height){
        _stageWidth = width;
        _stageHeight = height;

        if(getStage() != null) {
            getStage().setWidth(width);
            getStage().setHeight(height);
        }
    }

    public void setScaledPosition(double scaledPositionX, double scaledPositionY){
        _positionWidthRatio = scaledPositionX;
        _positionHeightRatio = scaledPositionY;
    }

    public void setMenuScale(double widthScale, double heightScale){
        _widthScale = widthScale;
        _heightScale = heightScale;
    }

    public static void setColorMode(String colorMode){
        if(colorMode.equals("light")){
            _colorMode = "light";
            getColorPalette().setLight();
        } else {
            _colorMode = "dark";
            getColorPalette().setDark();
        }
    }

    public void setSimulationInstance(Simulation simulationInstance){
        _simulationInstance = simulationInstance;
        _simulationRunning = true;
    }

    /** OTHER **/

    public void show(){
        Scene menu_scene = new Scene(_root);
        _stage.setScene(menu_scene);
        _stage.show();
    }

    public boolean isSimulationRunning(){
        return _simulationRunning;
    }

    public static ImageView retrieveImage(String url, int numberOfRowsSpritesheet, int numberOfColumnsSpritesheet) throws CustomCheckedException.FileManagementException {
        try {
            var image = new Image(url);
            var imageWrapped = new ImageView(image);
            imageWrapped.setViewport(new Rectangle2D(0, 0, image.getWidth() / numberOfColumnsSpritesheet, image.getHeight() / numberOfRowsSpritesheet));
            return imageWrapped;
        }catch(Exception e){
            throw new CustomCheckedException.FileManagementException(url);
        }
    }


    /************************** COLOR PALETTE OBJECT *****************************/

    protected static class ColorPalette {
        Color basic_primary_color;
        Color selected_primary_color;
        Color basic_secondary_color;
        Color selected_secondary_color;
        Color clicked_background_color;
        Color dead_color;

        protected ColorPalette(){
            setDark();
        }

        protected void setDark(){
            basic_primary_color = CustomColors.BASIC_PRIMARY_COLOR_DARK;
            selected_primary_color = CustomColors.SELECTED_PRIMARY_COLOR_DARK;
            basic_secondary_color = CustomColors.BASIC_SECONDARY_COLOR_DARK;
            selected_secondary_color = CustomColors.SELECTED_SECONDARY_COLOR_DARK;
            clicked_background_color = CustomColors.CLICKED_BACKGROUND_COLOR_DARK;
            dead_color = CustomColors.DEAD_COLOR_DARK;
        }

        protected void setLight(){
            basic_primary_color = CustomColors.BASIC_PRIMARY_COLOR_LIGHT;
            selected_primary_color = CustomColors.SELECTED_PRIMARY_COLOR_LIGHT;
            basic_secondary_color = CustomColors.BASIC_SECONDARY_COLOR_LIGHT;
            selected_secondary_color = CustomColors.SELECTED_SECONDARY_COLOR_LIGHT;
            clicked_background_color = CustomColors.CLICKED_BACKGROUND_COLOR_LIGHT;
            dead_color = CustomColors.DEAD_COLOR_LIGHT;
        }

    }

}
