package units.shooter_developers;

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

import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.Collectors;


public abstract class Menu extends Application {
    private Pane _root;
    private Stage _stage;
    private double _stage_width;
    private double _stage_height;
    private double _width_scale;
    private double _height_scale;
    private double _position_width_ratio;
    private double _position_height_ratio;
    private Simulation _simulation_instance;
    private boolean _simulation_running;

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
        _width_scale = other_menu._width_scale;
        _height_scale = other_menu._height_scale;
        _stage_width = other_menu._stage_width;
        _stage_height = other_menu._stage_height;
        _position_width_ratio = other_menu._position_width_ratio;
        _position_height_ratio = other_menu._position_height_ratio;
        _simulation_instance = other_menu._simulation_instance;
        _simulation_running = other_menu._simulation_running;
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
        MenuItem new_item = new Menu.MenuItem(new_menu_item);
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
            Menu.MenuBox vbox = new Menu.MenuBox();
            vbox.setTranslateX(0.0952*getMenuWidth() + getPositionX());
            vbox.setTranslateY(0.5*getMenuHeight() + getPositionY());

            _root.getChildren().addAll(vbox);
        }
    }

    public void setTitle(String title){
        removeTitle();

        Menu.Title title_object = new Menu.Title(title);

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
        var disclaimer_object = new FlashDisclaimer(disclaimer_text, scaled_position_X, scaled_position_Y);
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

    public double getStageHeight() {
        return _stage_height;
    }

    public double getStageWidth() {
        return _stage_width;
    }

    /** MENU **/

    public void scaleMenu(double width_scale, double height_scale){
        _width_scale = width_scale;
        _height_scale = height_scale;
    }

    public double getMenuHeight() {
        return _height_scale * getStageHeight();
    }

    public double getMenuWidth() {
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
        var item_list_from_box = new ArrayList<Menu.MenuItem>();
        if(getItemsBox() != null)
            item_list_from_box = getItemsBox().getItems();

        //add items not contained in items box to the list
        ArrayList<MenuItem> full_item_list = item_list_from_box;
        _root.getChildren().stream()
                .filter(e -> e instanceof MenuItem)
                .forEach(e -> full_item_list.add((MenuItem)e));

        return full_item_list;
    }

    public ArrayList<SelectorItem> getSelectorItems(){
        return getItemsBox().getSelectorItems();
    }

    public SelectorItem getSelectorItem(String name){
        var selector_object = getSelectorItems().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);

        return selector_object;
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

    /*******************************************************************************/
    /*                          MENU ELEMENTS                                      */
    /*******************************************************************************/


    /************************ TITLE ****************************************/
    private class Title extends StackPane {
        public Title(String name) {
            Rectangle box = new Rectangle(0.357*getMenuWidth(), 0.1*getMenuHeight());
            box.setStroke(Color.WHITE);
            box.setStrokeWidth(2);
            box.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0833*getMenuHeight()));

            setAlignment(Pos.CENTER);

            //Text is transformed into an image and redimensionated
            var params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            var textImage = new ImageView(text.snapshot(params, null));
            if(textImage.getBoundsInLocal().getWidth() > box.getWidth())
                textImage.setFitWidth(box.getWidth());

            getChildren().addAll(box, textImage);
        }
    }


    /************************ MENU BOX ****************************************/

    private class MenuBox extends VBox {

        private MenuBox(Menu.MenuItem... items) {
            getChildren().add(createSeparator());

            for (Menu.MenuItem item : items) {
                getChildren().addAll(item, createSeparator());
            }
        }

        private Line createSeparator() {
            Color separator_color = Color.DARKGREY;

            Line separator_line = new Line();
            separator_line.setEndX(0.2*getMenuWidth());
            separator_line.setStroke(separator_color);
            return separator_line;
        }

        private void addItem(String new_menu_item){
            MenuItem new_item = new Menu.MenuItem(new_menu_item);
            new_item.setTranslateX(0.005*getMenuWidth());

            getChildren().addAll(new_item, createSeparator());
        }

        private void addNonAnimatedItem(String new_menu_item){
            NonAnimatedItem new_item = new NonAnimatedItem(new_menu_item);
            new_item.setTranslateX(0.005*getMenuWidth());

            getChildren().addAll(new_item, createSeparator());
        }

        private void addSelectorItem(String name, ArrayList<String> tag_list){
            SelectorItem new_item = new SelectorItem(name);
            new_item.setTranslateX(0.005*getMenuWidth());

            for(var tag:tag_list){
                new_item.addTag(tag);
            }

            getChildren().addAll(new_item, createSeparator());
        }

        private ArrayList<MenuItem> getItems(){
            return getChildren().parallelStream()
                    .filter(e -> e instanceof MenuItem)
                    .map(e -> (MenuItem) e)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        private ArrayList<SelectorItem> getSelectorItems(){
            return getChildren().stream()
                    .filter(e -> e instanceof SelectorItem)
                    .map(e -> (SelectorItem) e)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

    }


    /************************ MENU ITEM ****************************************/

    public class MenuItem extends StackPane {
        String _name;

        public MenuItem(String name) {
            this(name, -1, -1);
        }

        public MenuItem(String name, double item_width_ratio, double item_height_ratio) {
            var effective_item_width = item_width_ratio;
            var effective_item_height = item_height_ratio;
            if(item_width_ratio < 0){
                effective_item_width = 0.19;
            }
            if(item_height_ratio < 0){
                effective_item_height = 0.05;
            }

            /* COLORS SHOULD BE PUT in a different file, eg Custom_colors */
            Color text_color = Color.SILVER;
            Color item_clicked_color = Color.DARKVIOLET;
            Color item_selected_color_lateral = Color.DARKBLUE;
            Color item_background_color = Color.BLACK;
            Color text_selected_color = Color.WHITE;

            _name = name;

            this.setMaxWidth(effective_item_width*getMenuWidth());
            this.setMaxHeight(effective_item_height*getMenuHeight());

            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop(0, item_selected_color_lateral),
                    new Stop(0.1, item_background_color),
                    new Stop(0.9, item_background_color),
                    new Stop(1, item_selected_color_lateral));

            Rectangle box = new Rectangle(effective_item_width*getMenuWidth(),effective_item_height*getMenuHeight());
            box.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(text_color);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,0.0333* _stage_height * _height_scale));

            //Text is transformed into an image and resized
            var params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            var textImage = new ImageView(text.snapshot(params, null));
            if(textImage.getBoundsInLocal().getWidth() > box.getWidth()) {
                textImage.setFitWidth(box.getWidth());
            }

            setAlignment(Pos.CENTER_LEFT);

            setOnMouseEntered(event -> {
                box.setFill(gradient);
                text.setFill(text_selected_color);
            });

            setOnMouseExited(event -> {
                box.setFill(item_background_color);
                text.setFill(text_color);
            });

            setOnMousePressed(event -> {
                box.setFill(item_clicked_color);
            });

            setOnMouseReleased(event -> {
                box.setFill(gradient);
            });

            getChildren().addAll(box, textImage);

        }

        public String getName() {
            return _name;
        }
    }


    /************************ SELECTOR ITEM ****************************************/

    public class SelectorItem extends HBox{
        private final ArrayList<String> _selection_list;
        private int _selection_index;
        private final double _width_selection_item;
        private final String _name;
        private final double _selection_section_translation;

        public SelectorItem(String name){
            _selection_index = 0;
            _selection_list = new ArrayList<>();
            _width_selection_item = 0.25;
            _selection_section_translation = 0.10;
            _name = name;


            setAlignment(Pos.CENTER_LEFT);
            NonAnimatedItem name_text_box = new NonAnimatedItem(name);

            MenuItem left_arrow_button = new MenuItem("<", 0.04, -1);
            left_arrow_button.setTranslateX(_selection_section_translation*getMenuWidth());
            left_arrow_button.setOnMouseReleased(event -> {
                previous();
            });

            NonAnimatedItem selection_text_box;
            selection_text_box = new NonAnimatedItem("not_found", _width_selection_item, -1);
            selection_text_box.setTranslateX((_selection_section_translation+0.01)*getMenuWidth());

            MenuItem right_arrow_button = new MenuItem(">", 0.04, -1);
            right_arrow_button.setTranslateX((_selection_section_translation+0.02)*getMenuWidth());
            right_arrow_button.setOnMouseReleased(event -> {
                next();
            });

            this.getChildren().addAll(name_text_box,
                    left_arrow_button,
                    selection_text_box,
                    right_arrow_button);
        }

        private void next(){
            if(_selection_index == _selection_list.size()-1)
                _selection_index = 0;
            else
                _selection_index += 1;
            updateTagText();
        }

        private void previous(){
            if(_selection_index == 0)
                _selection_index = _selection_list.size()-1;
            else
                _selection_index -= 1;
            updateTagText();
        }

        public void addTag(String selection_tag){
            _selection_list.add(selection_tag);
            updateTagText();
        }

        private void updateTagText(){
            var selection_item = (NonAnimatedItem)getChildren().stream()
                    .filter(e -> e instanceof NonAnimatedItem)
                    .skip(1)
                    .findFirst()
                    .orElse(null);

            var index = getChildren().indexOf(selection_item);
            getChildren().remove(selection_item);
            if(!_selection_list.isEmpty()){
                selection_item = new NonAnimatedItem(_selection_list.get(_selection_index),
                        _width_selection_item, -1);
                selection_item.setTranslateX((_selection_section_translation+0.01)*getMenuWidth());
            }
            getChildren().add(index, selection_item);
        }

        public String getText(){
            return _selection_list.get(_selection_index);
        }

        public String getName(){
            return _name;
        }
    }


    /************************ NON-ANIMATED ITEM ****************************************/

    public class NonAnimatedItem extends StackPane {

        public NonAnimatedItem(String name){
            this(name, -1, -1);
        }

        private NonAnimatedItem(String name, double item_width_ratio, double item_height_ratio) {
            var effective_width_ratio = 0.19;
            var effective_height_ratio = 0.05;
            if(item_width_ratio >= 0){
                effective_width_ratio = item_width_ratio;
            }
            if(item_height_ratio >= 0){
                effective_height_ratio = item_height_ratio;
            }

            Color text_color = Color.DARKGREY;
            Color background_color = Color.BLACK;

            Rectangle box = new Rectangle(effective_width_ratio*getMenuWidth(),effective_height_ratio*getMenuHeight());
            box.setOpacity(0.3);
            box.setFill(background_color);

            Text text = new Text(name);
            text.setFill(text_color);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,0.0333*getMenuHeight()));

            //Text is transformed into an image and resized
            var params = new SnapshotParameters();
            params.setFill(Color.TRANSPARENT);
            var textImage = new ImageView(text.snapshot(params, null));
            if(textImage.getBoundsInLocal().getWidth() > box.getWidth())
                textImage.setFitWidth(box.getWidth());

            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(box, textImage);
        }
    }


    /************************ FLASH DISCLAIMER ITEM ****************************************/

    private class FlashDisclaimer extends StackPane{

        private FlashDisclaimer(String text, double position_ratio_X, double position_ratio_Y){
            Text disclaimer_text = new Text(text);
            disclaimer_text.setFont(Font.font("Times New Roman", FontWeight.BOLD,getMenuWidth()*0.025));
            disclaimer_text.setFill(Color.SILVER);
            textAnimation(disclaimer_text);
            setAlignment(disclaimer_text,Pos.TOP_CENTER);

            setTranslateX(position_ratio_X*getMenuWidth());
            setTranslateY(position_ratio_Y*getMenuHeight());
            getChildren().add(disclaimer_text);
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

    public static void main(String[] args) {
        launch(args);
    }
}
