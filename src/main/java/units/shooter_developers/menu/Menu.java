package units.shooter_developers.menu;

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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
import units.shooter_developers.Simulation;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;
import java.util.stream.Collectors;


public abstract class Menu extends Application {
    private Pane _root;
    private Stage _stage;
    private double _stage_width;
    private double _stage_height;
    private double _width_ratio;
    private double _height_ratio;
    private double _position_width_ratio;
    private double _position_height_ratio;
    private Simulation _gameInstance;
    private boolean _game_running;

    /************************** CONSTRUCTORS *****************************/

    public Menu() {
        this(tryReadWidth(), tryReadHeight());
    }

    public Menu(double stage_width, double stage_height) {
        _width_ratio = 1;
        _height_ratio = 1;
        _position_width_ratio = 0;
        _position_height_ratio = 0;
        this.createContent(stage_width, stage_height);
    }

    public Menu(Menu other_menu){
        _width_ratio = other_menu._width_ratio;
        _height_ratio = other_menu._height_ratio;
        _stage_width = other_menu._stage_width;
        _stage_height = other_menu._stage_height;
        _position_width_ratio = other_menu._position_width_ratio;
        _position_height_ratio = other_menu._position_height_ratio;
        _gameInstance = other_menu._gameInstance;
        _game_running = other_menu._game_running;
        this.createContent(_stage_width, _stage_height);
    }

    private void createContent(double stage_width, double stage_height) {
        Pane root = new Pane();

        setStageDimensions(stage_width, stage_height);

        root.setPrefSize(getMenuWidth(), getMenuHeight());

        try (InputStream is = Files.newInputStream(Paths.get("src/main/resources/menu.jpeg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(getMenuWidth());
            img.setFitHeight(getMenuHeight());
            img.setX(getPositionX());
            img.setY(getPositionY());
            root.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Menu background image not found");
        }

        Menu.Title title = new Menu.Title("title_not_found");
        title.setTranslateX(0.0476*getMenuWidth() + getPositionX());
        title.setTranslateY(0.333*getMenuHeight() + getPositionY());

        Menu.MenuBox vbox = new Menu.MenuBox();
        vbox.setTranslateX(0.0952*getMenuWidth() + getPositionX());
        vbox.setTranslateY(0.5*getMenuHeight() + getPositionY());

        root.getChildren().addAll(title, vbox);

        _root = root;

    }

    private static double tryReadWidth(){
        File configFile = new File("config.ini");
        Properties config = new Properties();

        try{
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            double width = Double.parseDouble(config.getProperty("WIDTH"));
            return width;
        } catch(IOException e){
            return getScreenWidth();
        }
    }

    private static double tryReadHeight(){
        File configFile = new File("config.ini");
        Properties config = new Properties();

        try{
            FileReader reader = new FileReader(configFile);
            config.load(reader);
            double width = Double.parseDouble(config.getProperty("HEIGHT"));
            return width;
        } catch(IOException e){
            return getScreenHeight();
        }
    }


    /************************** ELEMENTS MANAGEMENT *****************************/

    public void addItem(String new_menu_item){
        getItemsBox().addItem(new_menu_item);
    }

    public void addUnanimatedItem(String new_menu_item){
        getItemsBox().addUnanimatedItem(new_menu_item);
    }

    public void addSelectableItem(String item_name, String ... selection_tags){
        ArrayList<String> tag_list= new ArrayList<String>();
        for(var tag:selection_tags){ tag_list.add(tag); }

        getItemsBox().addSelectableItem(item_name, tag_list);
    }

    public void setTitle(String title){
        Menu.Title new_title = new Menu.Title(title);
        Title old_title = getTitle();

        new_title.setTranslateX(0.0476*getMenuWidth() + getPositionX());
        new_title.setTranslateY(0.333*getMenuHeight() + getPositionY());

        var index = _root.getChildren().indexOf(old_title);
        _root.getChildren().remove(old_title);
        _root.getChildren().add(index, new_title);
    }


    /************************** SET/GET METHODS *****************************/

    /** SCREEN **/
    public static double getScreenWidth(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        return bounds.getWidth();
    }

    public static double getScreenHeight(){
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        return bounds.getHeight();
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
        //PUT HERE THE SIMULATION (if it procceed) REDIMENSIONING
    }

    public double getStageHeight() {
        return _stage_height;
    }

    public double getStageWidth() {
        return _stage_width;
    }

    /** MENU **/

    public void resize(double width_ratio, double height_ratio){
        _width_ratio = width_ratio;
        _height_ratio = height_ratio;
    }

    public double getMenuHeight() {
        return _height_ratio*_stage_height;
    }

    public double getMenuWidth() {
        return _width_ratio*_stage_width;
    }

    public void setPositionRatio(double position_width_ratio, double position_height_ratio){
        _position_width_ratio = position_width_ratio;
        _position_height_ratio = position_height_ratio;
    }

    public double getPositionX(){
        return _position_width_ratio*getMenuWidth();
    }

    public double getPositionY(){
        return _position_height_ratio*getMenuHeight();
    }

    public Scene getScene(){
        return getStage().getScene();
    }

    /** MENU ELEMENTS **/

    public Parent getRoot(){
        return _root;
    }

    private Title getTitle() {
        return (Title) _root.getChildren().stream()
                .filter(e -> e instanceof Title)
                .findFirst()
                .orElse(null);
    }

        public MenuBox getItemsBox() {
        return (MenuBox) _root.getChildren().parallelStream()
                .filter(e -> e instanceof MenuBox)
                .findFirst()
                .orElse(null);
    }


    public ArrayList<MenuItem> getItems(){
        return getItemsBox().getItems();
    }

    public ArrayList<SelectableItem> getSelectableItems(){
        return getItemsBox().getSelectableItems();
    }

    public SelectableItem getSelectableItem(String name){
        return getSelectableItems().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    /** GAME INSTANCE **/

    public Simulation getGameInstance(){
        return _gameInstance;
    }

    public void setGameInstance(Simulation game_instance){
        _gameInstance = game_instance;
    }

    public boolean isGameRunning(){
        return _game_running;
    }

    public void setGameRunning(boolean is_game_running){
        _game_running = is_game_running;
    }

    /*******************************************************************************/
    /*                          MENU ELEMENTS                                      */
    /*******************************************************************************/


    /************************ TITLE ****************************************/
    private class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(0.357*getMenuWidth(), 0.1*getMenuHeight());
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0833*getMenuHeight()));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }


    /************************ MENU BOX ****************************************/

    private class MenuBox extends VBox {

        public MenuBox(Menu.MenuItem... items) {
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

        public void addItem(String new_menu_item){
            MenuItem new_item = new Menu.MenuItem(new_menu_item);
            new_item.setTranslateX(0.005*getMenuWidth());

            getChildren().addAll(new_item, createSeparator());
        }

        public void addUnanimatedItem(String new_menu_item){
            UnanimatedItem new_item = new UnanimatedItem(new_menu_item);
            new_item.setTranslateX(0.005*getMenuWidth());

            getChildren().addAll(new_item, createSeparator());
        }

        public void addSelectableItem(String selectable_name, ArrayList<String> tag_list){
            SelectableItem new_item = new Menu.SelectableItem(selectable_name);
            new_item.setTranslateX(0.005*getMenuWidth());

            for(var tag:tag_list){
                new_item.addTag(tag);
            }

            getChildren().addAll(new_item, createSeparator());
        }

        public ArrayList<MenuItem> getItems(){

            return getChildren().stream()
                    .filter(e -> e instanceof MenuItem)
                    .map(e -> (MenuItem) e)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        public ArrayList<SelectableItem> getSelectableItems(){

            return getChildren().stream()
                    .filter(e -> e instanceof SelectableItem)
                    .map(e -> (SelectableItem) e)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

    }


    /************************ MENU ITEM ****************************************/

    public class MenuItem extends StackPane {
        String _name;

        public MenuItem(String name) {
            this(name, -1, -1);
        }

        private MenuItem(String name, double item_width_ratio, double item_height_ratio) {
            var effective_item_width = item_width_ratio;
            var effective_item_height = item_height_ratio;
            if(item_width_ratio < 0){
                effective_item_width = 0.19;
            }
            if(item_height_ratio < 0){
                effective_item_height = 0.05;
            }

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

            Rectangle bg = new Rectangle(effective_item_width*getMenuWidth(),effective_item_height*getMenuHeight());
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(text_color);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,0.0333* _stage_height *_height_ratio));

            setAlignment(Pos.CENTER_LEFT);

            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(text_selected_color);
            });

            setOnMouseExited(event -> {
                bg.setFill(item_background_color);
                text.setFill(text_color);
            });

            setOnMousePressed(event -> {
                bg.setFill(item_clicked_color);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });

            getChildren().addAll(bg, text);

        }

        public String getName() {
            return _name;
        }
    }


    /************************ SELECTABLE ITEM ****************************************/

    public class SelectableItem extends HBox{
        private final ArrayList<String> _selection_list;
        private int _selection_index;
        private final double _width_selection_item;
        private final String _name;

        public SelectableItem(String name){
            _selection_index = 0;
            _selection_list = new ArrayList<>();
            _width_selection_item = 0.25;
            _name = name;


            UnanimatedItem selection_item;
            selection_item = new UnanimatedItem("not_found", _width_selection_item, 0.05);

            setAlignment(Pos.CENTER_LEFT);
            UnanimatedItem name_item = new UnanimatedItem(name);
            MenuItem left_arrow = new MenuItem("<", 0.04, -1);
            left_arrow.setOnMouseReleased(event -> {
                previous();
            });

            MenuItem right_arrow = new MenuItem(">", 0.04, -1);
            right_arrow.setOnMouseReleased(event -> {
                next();
            });

            Rectangle long_space = new Rectangle(0.10*getMenuWidth(), 0.05*getMenuHeight());
            long_space.setOpacity(0);

            Rectangle short_space_1 = new Rectangle(0.005*getMenuWidth(), 0.05*getMenuHeight());
            short_space_1.setOpacity(0);

            Rectangle short_space_2 = new Rectangle(0.005*getMenuWidth(), 0.05*getMenuHeight());
            short_space_2.setOpacity(0);

            this.getChildren().addAll(name_item,
                    long_space,
                    left_arrow,
                    short_space_1,
                    selection_item,
                    short_space_2,
                    right_arrow);
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
            var selection_item = (UnanimatedItem)getChildren().stream()
                    .filter(e -> e instanceof UnanimatedItem)
                    .skip(1)
                    .findFirst()
                    .orElse(null);

            var index = getChildren().indexOf(selection_item);
            getChildren().remove(selection_item);
            if(_selection_list.isEmpty()){
                selection_item = new UnanimatedItem("not_found", _width_selection_item, -1);
            } else {
                selection_item = new UnanimatedItem(_selection_list.get(_selection_index), _width_selection_item, -1);
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


    /************************ UNANIMATED ITEM ****************************************/

    public class UnanimatedItem extends StackPane {
        private final String _name;

        public UnanimatedItem(String name){
            this(name, -1, -1);
        }

        private UnanimatedItem(String name, double item_width_ratio, double item_height_ratio) {
            var effective_width_ratio = 0.19;
            var effective_height_ratio = 0.05;
            if(item_width_ratio >= 0){
                effective_width_ratio = item_width_ratio;
            }
            if(item_height_ratio >= 0){
                effective_height_ratio = item_height_ratio;
            }


            _name = name;

            Color text_color = Color.DARKGREY;
            Color background_color = Color.BLACK;

            Rectangle bg = new Rectangle(effective_width_ratio*getMenuWidth(),effective_height_ratio*getMenuHeight());
            bg.setOpacity(0.3);
            bg.setFill(background_color);

            Text text = new Text(name);
            text.setFill(text_color);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,0.0333*getMenuHeight()));

            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(bg, text);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
