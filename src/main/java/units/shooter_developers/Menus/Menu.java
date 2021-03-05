package units.shooter_developers.Menus;

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
import units.shooter_developers.Simulation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    /************************** CONSTRUCTORS *****************************/

    public Menu() {
        this(tryToReadWidth(), tryToReadHeight());
    }

    public Menu(double stage_width, double stage_height) {
        _width_scale = 1;
        _height_scale = 1;
        _position_width_ratio = 0;
        _position_height_ratio = 0;
        _simulation_running = false;
        _simulation_instance = null;
        this.createRootAndBackground(stage_width, stage_height);
    }

    public Menu(Menu other_menu){
        _position_width_ratio = other_menu._position_width_ratio;
        _position_height_ratio = other_menu._position_height_ratio;
        this.createRootAndBackground(_stage_width, _stage_height);
    }

    private void createRootAndBackground(double stage_width, double stage_height) {
        Pane root = new Pane();
        _root = root;

        setStageDimensions(stage_width, stage_height);

        root.setPrefSize(getMenuWidth(), getMenuHeight());

        try (InputStream background_input_stream = Files.newInputStream(Paths.get("src/main/resources/menu.jpeg"))) {
            ImageView background_img = new ImageView(new Image(background_input_stream));
            background_img.setFitWidth(getMenuWidth());
            background_img.setFitHeight(getMenuHeight());
            background_img.setX(getPositionX());
            background_img.setY(getPositionY());
            root.getChildren().add(background_img);
        } catch (IOException e) {
            System.out.println("Menu background image not found");
        }
    }

    private static double tryToReadWidth(){
        File configFile = new File("config.ini");
        Properties config = new Properties();

        try{
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            double width = Double.parseDouble(config.getProperty("WIDTH"));
            reader.close();
            return width;
        } catch(Exception e){
            return getScreenWidth();
        }
    }

    private static double tryToReadHeight(){
        File configFile = new File("config.ini");
        Properties config = new Properties();

        try{
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            reader.close();
            double width = Double.parseDouble(config.getProperty("HEIGHT"));
            return width;
        } catch(Exception e){
            return getScreenHeight();
        }
    }


    /************************** ELEMENTS MANAGEMENT *****************************/

    public void addItem(String new_menu_item){
        generateMenuBoxIfNotExist();
        getItemsBox().addItem(new_menu_item);
    }

    public void addFreeItem(String new_menu_item, double position_ratio_X, double position_ratio_Y){
        MenuItem new_item = new MenuItem(new_menu_item);
        new_item.setTranslateX(position_ratio_X*getMenuWidth());
        new_item.setTranslateY(position_ratio_Y*getMenuWidth());

        _root.getChildren().addAll(new_item);
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
            MenuBox vbox = new MenuBox(this);
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

    public void addFlashDisclaimer(String disclaimer_text, double scaled_position_X, double scaled_position_Y){
        var disclaimer_object = new FlashDisclaimer(this, disclaimer_text, scaled_position_X, scaled_position_Y);
        _root.getChildren().add(disclaimer_object);
    }

    public void addCentralImageView(ImageView image, double scale_width, double scale_height){
        var image_frame = new StackPane();
        image.fitHeightProperty().bind(image_frame.heightProperty());
        image.setPreserveRatio(true);
        image_frame.setMaxSize(scale_width*getMenuWidth(), scale_height*getMenuHeight());
        image_frame.getChildren().add(image);
        image_frame.setAlignment(Pos.CENTER);

        var menu_frame = new BorderPane();
        menu_frame.setPrefSize(getMenuWidth(), getMenuHeight());
        menu_frame.setCenter(image_frame);

        _root.getChildren().add(menu_frame);
    }

    public void addSecondaryTitle(String title){
        var menu_frame = new BorderPane();
        menu_frame.setPrefSize(getMenuWidth(), getMenuHeight());
        Text top = new Text(title);
        top.setFont(Font.font("Times New Roman", FontWeight.BOLD,getMenuWidth()*0.06));
        top.setFill(Color.SILVER);
        menu_frame.setAlignment(top,Pos.TOP_CENTER);
        menu_frame.setTop(top);

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

}
