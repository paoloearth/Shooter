package units.shooter_developers.MenuAPI;

// VISITED
// Should be better to use more precise exceptions

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

// COLOCAR NUMEROS Y VALORES ESPECIFICOS EN Custom_Settings

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
import units.shooter_developers.CustomColors;
import units.shooter_developers.CustomSettings;
import units.shooter_developers.Simulation;
import units.shooter_developers.CustomException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static units.shooter_developers.CustomSettings.URL_CONFIG_FILE;


public abstract class Menu extends Application {
    private static double _height_scale;
    private static double _width_scale;
    private static double _stage_height;
    private static double _stage_width;
    private Pane _root;
    private Stage _stage;
    private double _position_width_ratio;
    private double _position_height_ratio;
    private static Simulation _simulation_instance;
    private static boolean _simulation_running;
    private static ColorPalette _color_palette;
    private static ImageView _background;
    private static String _color_mode;

    /************************** CONSTRUCTORS *****************************/

    public Menu() {
        _width_scale = 1;
        _height_scale = 1;
        _position_width_ratio = 0;
        _position_height_ratio = 0;
        _simulation_running = false;
        _simulation_instance = null;
        _color_palette = new ColorPalette();
        _color_mode = "dark";

        _color_palette.setDark();
    }

    public Menu(Menu other_menu){
        _position_width_ratio = other_menu._position_width_ratio;
        _position_height_ratio = other_menu._position_height_ratio;
        _stage = other_menu._stage;
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
    public void readProperties() throws CustomException.FileManagementException{
        File configFile = new File(URL_CONFIG_FILE);
        Properties config;
        ImageView background_light = null;
        ImageView background_dark = null;

        try {
            FileReader reader;
            config = new Properties();
            reader = new FileReader(configFile);
            config.load(reader);
            reader.close();
        }catch(Exception e){
            throw new CustomException.FileManagementException(configFile.getPath());
        }

        setResolution(config);

        background_dark = retrieveImage(CustomSettings.URL_BACKGROUND_DARK, 1, 1);
        background_light = retrieveImage(CustomSettings.URL_BACKGROUND_LIGHT, 1, 1);

        var color_mode = config.getProperty("COLOR MODE");
        color_mode = color_mode == null? "" :  color_mode;

        if(color_mode.equals("light")){
            setColorMode("light");
            if(background_light != null)
                _background = background_light;
        } else {
            setColorMode("dark");
            if(background_dark != null)
                _background = background_dark;
        }
    }

    private void setResolution(Properties config){
        double width, height;

        try{
            var parsed_resolution = parseResolutionFromStrings(config.getProperty("WIDTH"), config.getProperty("HEIGHT"));
            width = parsed_resolution.getKey();
            height = parsed_resolution.getValue();
        }catch(CustomException.WrongParsingException e) {
            System.out.println(e.getMessage() + " Using native resolution.");
            width = getScreenWidth();
            height = getScreenHeight();
        }

        setStageDimensions(width, height);
    }

    private Pair<Double, Double> parseResolutionFromStrings(String width_string, String height_string) throws CustomException.WrongParsingException {
        double width, height;

        try {
            width = Double.parseDouble(width_string);
        }catch(Exception e) {
            throw new CustomException.WrongParsingException(width_string, Double.class);
        }

        try {
            height = Double.parseDouble(height_string);
        }catch(Exception e) {
            throw new CustomException.WrongParsingException(height_string, Double.class);
        }

        return new Pair<>(width, height);
    }

    public void writeSettings() throws CustomException.FileManagementException {
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
            throw new CustomException.FileManagementException(configFile.getPath());
        }
    }


    /************************** CONTENT MANAGEMENT *****************************/

    public void addItem(String new_menu_item){
        generateMenuBoxIfNotExist();
        getItemsBox().addItem(new_menu_item);
    }

    public void addFreeItem(String new_menu_item, double position_ratio_X, double position_ratio_Y){
        MenuItem new_item = new MenuItem(new_menu_item);
        new_item.setTranslateX(position_ratio_X*getMenuWidth());
        new_item.setTranslateY(position_ratio_Y*getMenuHeight());

        addGenericNode(new_item);
    }

    public void addNonAnimatedItem(String name){
        generateMenuBoxIfNotExist();
        getItemsBox().addNonAnimatedItem(name);
    }

    public void addSelectorItem(String name, String ... selection_tags){
        generateMenuBoxIfNotExist();
        ArrayList<String> tag_list= new ArrayList<>();
        Collections.addAll(tag_list, selection_tags);

        getItemsBox().addSelectorItem(name, tag_list);
    }

    public void addGenericNode(Node generic_node){
        _root.getChildren().add(generic_node);
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
        }catch(CustomException.MissingMenuComponent e){}
    }

    public void addFlashDisclaimer(String disclaimer_text){
        var disclaimer_object = new FlashDisclaimer(this, disclaimer_text);

        var menu_frame = new BorderPane();
        menu_frame.setPrefSize(getMenuWidth(), getMenuHeight());

        BorderPane.setAlignment(disclaimer_object,Pos.BOTTOM_CENTER);
        menu_frame.setBottom(disclaimer_object);
        menu_frame.setDisable(true);

        _root.getChildren().add(menu_frame);
    }

    public void addCentralImageView(ImageView image, double scale_width, double scale_height){
        var image_frame = new StackPane();
        image.fitHeightProperty().bind(image_frame.heightProperty());
        image.setPreserveRatio(true);
        image_frame.setMaxSize(scale_width*getMenuWidth(), scale_height*getMenuHeight());
        image_frame.setMinSize(scale_width*getMenuWidth(), scale_height*getMenuHeight());
        image_frame.getChildren().add(image);
        image_frame.setAlignment(Pos.CENTER);

        var menu_frame = new BorderPane();
        menu_frame.setPrefSize(getMenuWidth(), getMenuHeight());
        menu_frame.setCenter(image_frame);
        menu_frame.setDisable(true);

        _root.getChildren().add(menu_frame);
    }

    public void addSecondaryTitle(String title){
        var menu_frame = new BorderPane();
        menu_frame.setPrefSize(getMenuWidth(), getMenuHeight());

        Text title_object = new Text(title);
        title_object.setFont(Font.font("Times New Roman", FontWeight.BOLD,getMenuWidth()*0.06));
        title_object.setFill(getColorPalette().basic_primary_color);

        BorderPane.setAlignment(title_object,Pos.TOP_CENTER);
        menu_frame.setDisable(true);
        menu_frame.setTop(title_object);

        _root.getChildren().add(menu_frame);
    }

    public void addChoiceBox(String name, int row, int col, Map<String, String> map_image_to_URL, double scale, int spritesheet_number_of_rows){
        MenuGrid menu_grid_object = getMenuGridAndCreateIfNotExist();
        menu_grid_object.addChoiceBox(name, row, col, map_image_to_URL, scale, spritesheet_number_of_rows);
    }

    public void addTextBox(String name, int row, int col, String commands_url, int number_of_rows_spritesheet, double scale, String default_message, String default_content){
        MenuGrid menu_grid_object = getMenuGridAndCreateIfNotExist();

        menu_grid_object.addTextBox(name, row, col, commands_url, number_of_rows_spritesheet, scale, default_message, default_content);
    }

    private void generateMenuBoxIfNotExist(){
        MenuBox menu_box = (MenuBox) _root.getChildren().parallelStream()
                .filter(e -> e instanceof MenuBox)
                .findFirst()
                .orElse(null);

        if(menu_box == null){
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
        Rectangle2D screen_bounds = screen.getVisualBounds();

        return screen_bounds.getWidth();
    }

    public static double getScreenHeight(){
        Screen screen = Screen.getPrimary();
        Rectangle2D screen_bounds = screen.getVisualBounds();

        return screen_bounds.getHeight();
    }

    public Stage getStage(){
        return _stage;
    }

    public static double getStageHeight() {
        return _stage_height;
    }

    public static double getStageWidth() {
        return _stage_width;
    }

    public static double getMenuHeight() {
        return _height_scale * getStageHeight();
    }

    public static double getMenuWidth() {
        return _width_scale * getStageWidth();
    }

    public double getPositionX(){
        return _position_width_ratio*getMenuWidth();
    }

    public double getPositionY(){
        return _position_height_ratio*getMenuHeight();
    }

    public Scene getSceneFromStage(){
        return getStage().getScene();
    }

    public static String getColorMode(){
        return _color_mode;
    }

    public MenuItem getItem(String name) throws CustomException.MissingMenuComponent {
        final var item = getItems().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(item == null){throw new CustomException.MissingMenuComponent("Item with name \"" +name+ "\".", MenuItem.class);}
        else{return item;}
    }

    public String getSelectorValue(String name) throws CustomException.MissingMenuComponent {
        return getSelectorItem(name).getText();
    }

    public String getChoiceBoxValue(String name){
        return getChoiceBox(name).getValue();
    }

    public String getTextBoxValue(String name){
        return getTextBox(name).getValue();
    }

    public static Simulation getSimulationInstance(){
        return _simulation_instance;
    }

    protected static ColorPalette getColorPalette(){
        return _color_palette;
    }


    protected SelectorItem getSelectorItem(String name) throws CustomException.MissingMenuComponent {
        final var selector_item = getSelectorItems().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        if(selector_item == null) {throw new CustomException.MissingMenuComponent("Selector with name \"" + name + "\".", SelectorItem.class);}
        else return selector_item;
    }

    private Title getTitleObject() throws CustomException.MissingMenuComponent {
        final var title_object = (Title)_root.getChildren().stream()
                .filter(e -> e instanceof Title)
                .findFirst()
                .orElse(null);

        if(title_object == null){throw new CustomException.MissingMenuComponent("Main title object.", Title.class);}
        else return title_object;
    }

    private MenuBox getItemsBox() {
        return (MenuBox)_root.getChildren().parallelStream()
                .filter(e -> e instanceof MenuBox)
                .findFirst()
                .orElse(null);
    }

    private ArrayList<MenuItem> getItems(){
        var item_list_from_box = new ArrayList<MenuItem>();
        if(getItemsBox() != null)
            item_list_from_box = getItemsBox().getItems();

        //add items not contained in items box to the list
        ArrayList<MenuItem> full_item_list = item_list_from_box;
        _root.getChildren().stream()
                .filter(e -> e instanceof MenuItem)
                .forEach(e -> full_item_list.add((MenuItem)e));

        return full_item_list;
    }

    private ChoiceBox getChoiceBox(String name){
        var menu_grid = getMenuGridAndCreateIfNotExist();
        return menu_grid.getChoiceBox(name);
    }

    private TextBox getTextBox(String name){
        var menu_grid = getMenuGridAndCreateIfNotExist();
        return menu_grid.getTextBox(name);
    }

    private MenuGrid getMenuGridAndCreateIfNotExist() {
        var menu_grid_object = (MenuGrid) _root.getChildren().stream()
                .filter(e -> e instanceof MenuGrid)
                .findFirst()
                .orElse(null);

        if (menu_grid_object == null) {
            menu_grid_object = new MenuGrid();
            _root.getChildren().add(menu_grid_object);
        }
        return menu_grid_object;
    }

    private ArrayList<SelectorItem> getSelectorItems(){
        return getItemsBox().getSelectorItems();
    }


    /** SETTERS **/

    public void setStage(Stage stage){
        _stage = stage;
        _stage.setMaximized(false);
    }

    public void setStageDimensions(double width, double height){
        _stage_width = width;
        _stage_height = height;

        if(getStage() != null) {
            getStage().setWidth(width);
            getStage().setHeight(height);
        }
    }

    public void setScaledPosition(double scaled_position_X, double scaled_position_Y){
        _position_width_ratio = scaled_position_X;
        _position_height_ratio = scaled_position_Y;
    }

    public void scaleMenu(double width_scale, double height_scale){
        _width_scale = width_scale;
        _height_scale = height_scale;
    }

    public static void setColorMode(String color_mode){
        if(color_mode == "light"){
            _color_mode = "light";
            getColorPalette().setLight();
        } else {
            _color_mode = "dark";
            getColorPalette().setDark();
        }
    }

    public void setSimulationInstance(Simulation simulation_instance){
        _simulation_instance = simulation_instance;
        _simulation_running = true;
    }

    /** OTHER **/

    public void show(){
        Scene menu_scene = new Scene(_root);
        _stage.setScene(menu_scene);
        _stage.show();
    }

    public boolean isSimulationRunning(){
        return _simulation_running;
    }

    public static ImageView retrieveImage(String URL, int number_of_rows, int number_of_columns)
    {
        var image = new Image(URL);
        var image_wrapped =  new ImageView(image);
        image_wrapped.setViewport(new Rectangle2D( 0, 0, image.getWidth()/number_of_columns, image.getHeight()/number_of_rows));
        return image_wrapped;
    }

    static private Node retrieveFirstObjectOfType(Object type, Pane node_set){
        return node_set.getChildren().stream()
                .filter(e -> e instanceof MenuGrid)
                .findFirst()
                .orElse(null);
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
