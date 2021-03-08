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

// COLOCAR NUMEROS Y VALORES ESPECIFICOS EN Custom_Settings
// USAR DICCIONARIOS PARA LOS SELECTORES ¿? quizás

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import units.shooter_developers.Simulation;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;


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
    }

    public Menu(Menu other_menu){
        _position_width_ratio = other_menu._position_width_ratio;
        _position_height_ratio = other_menu._position_height_ratio;
        _stage = other_menu._stage;
        this.createRootAndBackground(_stage_width, _stage_height);
    }

    private void createRootAndBackground(double stage_width, double stage_height) {
        Pane root = new Pane();
        _root = root;

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
        createRootAndBackground(getMenuWidth(), getMenuHeight());
        createContent();
        stage.centerOnScreen();
        show();
    }

    public abstract void createContent();

    public void readProperties(){
        File configFile = new File("config.ini");
        Properties config = new Properties();
        ImageView background_light = null;
        ImageView background_dark = null;

        try {
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            reader.close();
        }catch (IOException e) {
            System.out.println("Config file not found. Using default properties.");
        }

        try{
            double width = Double.parseDouble(config.getProperty("WIDTH"));
            double height = Double.parseDouble(config.getProperty("HEIGHT"));
            setStageDimensions(width, height);
        } catch (Exception e) {
            System.out.println("Parse of resolution failed. Using native resolution");
            setStageDimensions(getScreenWidth(), getScreenHeight());
        }

        try{
            background_light = new ImageView("menu_light.jpg");
            background_dark = new ImageView("menu_dark.jpeg");
        } catch(Exception e){
            System.out.println("Background images not found!");
            var rectangle_image = new Rectangle(2, 2).snapshot(null, null);
            var default_background = new ImageView(rectangle_image);
            _background = default_background;
        }

        var color_mode = config.getProperty("COLOR MODE");
        if(color_mode != null){
            setColorMode(color_mode);
        }

        if(getColorMode().equals("light")){
            if(background_light != null)
                _background = background_light;

            getColorPalette().basic_primary_color = Color.WHEAT;
            getColorPalette().basic_secondary_color = Color.BLACK;
            getColorPalette().dead_color = Color.SANDYBROWN;
            getColorPalette().selected_primary_color = Color.DARKRED;
            getColorPalette().selected_secondary_color = Color.WHITESMOKE;
            getColorPalette().clicked_background_color = Color.ORANGERED;
        } else {
            if(background_dark != null)
                _background = background_dark;

            setColorPalette(new ColorPalette());
        }
    }

    public void writeSettings() {
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
            e.printStackTrace();
        }
    }


    /************************** ELEMENTS MANAGEMENT *****************************/

    public void addItem(String new_menu_item){
        generateMenuBoxIfNotExist();
        getItemsBox().addItem(new_menu_item);
    }

    public void addFreeItem(String new_menu_item, double position_ratio_X, double position_ratio_Y){
        MenuItem new_item = new MenuItem(new_menu_item);
        var hola = getMenuWidth();
        var adios = getMenuHeight();
        new_item.setTranslateX(position_ratio_X*getMenuWidth());
        new_item.setTranslateY(position_ratio_Y*getMenuHeight());

        //_root.getChildren().addAll(new_item);
        addGenericNode(new_item);
    }

    public void addNonAnimatedItem(String name){
        generateMenuBoxIfNotExist();
        getItemsBox().addNonAnimatedItem(name);
    }

    public void addSelectorItem(String name, String ... selection_tags){
        generateMenuBoxIfNotExist();
        ArrayList<String> tag_list= new ArrayList<String>();
        Collections.addAll(tag_list, selection_tags);

        getItemsBox().addSelectorItem(name, tag_list);
    }

    public void addGenericNode(Node generic_node){
        _root.getChildren().add(generic_node);
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

    public void setTitle(String title){
        removeTitle();

        Title title_object = new Title(title);

        title_object.setTranslateX(0.0476*getMenuWidth() + getPositionX());
        title_object.setTranslateY(0.333*getMenuHeight() + getPositionY());

        _root.getChildren().add(title_object);
    }

    public void removeTitle(){
        var title_object = getTitleObject();

        if(title_object != null)
            _root.getChildren().remove(title_object);
    }

    public void addFlashDisclaimer(String disclaimer_text){
        var disclaimer_object = new FlashDisclaimer(this, disclaimer_text);

        var menu_frame = new BorderPane();
        menu_frame.setPrefSize(getMenuWidth(), getMenuHeight());

        menu_frame.setAlignment(disclaimer_object,Pos.BOTTOM_CENTER);
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
        menu_frame.setAlignment(title_object,Pos.TOP_CENTER);
        menu_frame.setDisable(true);
        menu_frame.setTop(title_object);

        _root.getChildren().add(menu_frame);
    }

    /************************** SET/GET METHODS *****************************/

    /** SCREEN **/
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

    /** STAGE **/
    public void setStage(Stage stage){
        _stage = stage;
        _stage.setMaximized(false);
    }

    public Stage getStage(){
        return _stage;
    }

    public void setStageDimensions(double width, double height){
        _stage_width = width;
        _stage_height = height;

        if(getStage() != null) {
            getStage().setWidth(width);
            getStage().setHeight(height);
        }
    }

    public static double getStageHeight() {
        return _stage_height;
    }

    public static double getStageWidth() {
        return _stage_width;
    }

    /** MENU **/

    public void scaleMenu(double width_scale, double height_scale){
        _width_scale = width_scale;
        _height_scale = height_scale;
    }

    public static double getMenuHeight() {
        return _height_scale * getStageHeight();
    }

    public static double getMenuWidth() {
        return _width_scale * getStageWidth();
    }

    public void setScaledPosition(double scaled_position_X, double scaled_position_Y){
        _position_width_ratio = scaled_position_X;
        _position_height_ratio = scaled_position_Y;
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

    public void show(){
        Scene menu_scene = new Scene(_root);
        _stage.setScene(menu_scene);
        _stage.show();
    }

    public static void setColorMode(String color_mode){
        _color_mode = color_mode;
    }

    public static String getColorMode(){
        return _color_mode;
    }

    /** MENU ELEMENTS **/

    private Title getTitleObject() {
        var title_object = _root.getChildren().stream()
                .filter(e -> e instanceof Title)
                .findFirst()
                .orElse(null);

        if(title_object == null)
            return null;
        else
            return (Title)title_object;
    }

    private MenuBox getItemsBox() {
        var menu_box_object = _root.getChildren().parallelStream()
                .filter(e -> e instanceof MenuBox)
                .findFirst()
                .orElse(null);

        if(menu_box_object == null)
            return null;
        else
            return (MenuBox) menu_box_object;
    }


    public ArrayList<MenuItem> getItems(){
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

    protected ArrayList<SelectorItem> getSelectorItems(){
        return getItemsBox().getSelectorItems();
    }

    protected SelectorItem getSelectorItem(String name){
        var selector_object = getSelectorItems().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        return selector_object;
    }

    public String getSelectionFor(String name){
        var selector_object = getSelectorItem(name);

        if(selector_object != null){
            return selector_object.getText();
        } else {
            return null;
        }
    }

    /** GAME INSTANCE **/

    public Simulation getSimulationInstance(){
        return _simulation_instance;
    }

    public void setSimulationInstance(Simulation simulation_instance){
        _simulation_instance = simulation_instance;
        _simulation_running = true;
    }

    public boolean isSimulationRunning(){
        return _simulation_running;
    }

    /** other **/

    public static ImageView retrieveImage(String URL, int number_of_rows, int number_of_columns)
    {
        var image = new Image(URL);
        var image_wrapped =  new ImageView(image);
        image_wrapped.setViewport(new Rectangle2D( 0, 0, image.getWidth()/number_of_columns, image.getHeight()/number_of_rows));
        return image_wrapped;
    }

    public static ColorPalette getColorPalette(){
        return _color_palette;
    }

    public static void setColorPalette(ColorPalette new_color_palette){
        _color_palette = new_color_palette;
    }

    protected class ColorPalette {
        Color basic_primary_color;
        Color selected_primary_color;
        Color basic_secondary_color;
        Color selected_secondary_color;
        Color clicked_background_color;
        Color dead_color;

        protected ColorPalette(){
            basic_primary_color = Color.SILVER;
            selected_primary_color = Color.WHITE;
            basic_secondary_color = Color.BLACK;
            selected_secondary_color = Color.DARKBLUE;
            clicked_background_color = Color.DARKVIOLET;
            dead_color = Color.DARKGREY;
        }

    }

}
