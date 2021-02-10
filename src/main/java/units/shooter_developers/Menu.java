package units.shooter_developers;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Menu extends Application {
    ArrayList<Node> _menu_items;    //hay que privatizar esta variable
    private Pane _root;
    private double _width;
    private double _height;
    private double _width_ratio;
    private double _height_ratio;

    public Menu() {
        _width_ratio = 1;
        _height_ratio = 1;
        _menu_items = new ArrayList<>();
        this.createContent();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void createContent() {
        Pane root = new Pane();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        _width = bounds.getWidth();
        _height = bounds.getHeight();

        root.setPrefSize(getMenuWidth(), getMenuHeight());
        //_width = 1050;///////////////////////////////////////////////////
        //_height = 600;//////////////////////////////////////////////////
        //original default resolution was 1050x600

        try (InputStream is = Files.newInputStream(Paths.get("src/main/resources/menu.jpeg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(getMenuWidth());
            img.setFitHeight(getMenuHeight());
            root.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        Menu.Title title = new Menu.Title("C A M P A I G N");
        title.setTranslateX(0.0476*_width*_width_ratio);
        title.setTranslateY(0.333*_height*_height_ratio);

        Menu.MenuBox vbox = new Menu.MenuBox();
        vbox.setTranslateX(0.0952*_width*_width_ratio);
        vbox.setTranslateY(0.5*_height*_height_ratio);

        root.getChildren().addAll(title, vbox);

        _root = root;

    }

    public double getMenuHeight() {
        return _height_ratio*_height;
    }

    public double getMenuWidth() {
        return _width_ratio*_width;
    }

    public Parent getRoot(){
        return _root;
    }

    public void rescale(double width_ratio, double height_ratio){
        _width_ratio = width_ratio;
        _height_ratio = height_ratio;
    }

    public void addItem(String new_menu_item){
        MenuItem new_item = new Menu.MenuItem(new_menu_item, _menu_items);

        var vbox = _root.getChildren().parallelStream()
                .filter(e -> e instanceof Menu.MenuBox)
                .findFirst()
                .orElse(null);

        Menu.MenuBox vbox1 = (MenuBox) vbox;
        vbox1.addItem(new_item);
    }

    public void addSelectableItem(String new_selectable_item, String ... selection_tags){
        SelectableItem new_item = new Menu.SelectableItem(new_selectable_item);

        for(var tag:selection_tags){
            new_item.addSelectionElement(tag);
        }

        var vbox = _root.getChildren().parallelStream()
                .filter(e -> e instanceof Menu.MenuBox)
                .findFirst()
                .orElse(null);

        Menu.MenuBox vbox1 = (MenuBox) vbox;
        vbox1.addSelectableItem(new_item);
    }

    private class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(0.357*_width*_width_ratio, 0.1*_height*_height_ratio);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 0.0833*_height*_height_ratio));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    //wrapper para los elementos del menú
    private class MenuBox extends VBox {

        public MenuBox(Menu.MenuItem... items) {
            getChildren().add(createSeperator());

            for (Menu.MenuItem item : items) {
                getChildren().addAll(item, createSeperator());
            }
        }

        private Line createSeperator() {
            Line sep = new Line();
            sep.setEndX(0.2*_width*_width_ratio);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }

        public void addItem(Menu.MenuItem new_item){
            new_item.setTranslateX(0.005*_width*_width_ratio);
            getChildren().addAll(new_item, createSeperator());
        }

        public void addSelectableItem(Menu.SelectableItem new_item){
            new_item.setTranslateX(0.005*_width*_width_ratio);
            getChildren().addAll(new_item, createSeperator());
        }

    }

    public class MenuItem extends StackPane {
        String _name;

        public MenuItem(String name) {
            this(name, -1, -1);
        }

        public MenuItem(String name, double item_width, double item_height) {
            var effective_item_width = item_width;
            var effective_item_height = item_height;
            if(item_width < 0){
                effective_item_width = 0.19;
            }
            if(item_height < 0){
                effective_item_height = 0.05;
            }
            _name = name;

            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.DARKBLUE),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKBLUE)

            });

            Rectangle bg = new Rectangle(effective_item_width*_width*_width_ratio,effective_item_height*_height*_height_ratio);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,0.0333*_height*_height_ratio));

            //setAlignment(Pos.CENTER);
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(bg, text);
            setOnMouseEntered(event -> {
                bg.setFill(gradient);
                text.setFill(Color.WHITE);
            });

            setOnMouseExited(event -> {
                bg.setFill(Color.BLACK);
                text.setFill(Color.DARKGREY);
            });
            setOnMousePressed(event -> {
                bg.setFill(Color.DARKVIOLET);
            });

            setOnMouseReleased(event -> {
                bg.setFill(gradient);
            });

        }

        public MenuItem(String name, ArrayList<Node> items_list) {
            this(name);
            items_list.add(this);
        }

        public String getName() {
            return _name;
        }
    }

    public class SelectableItem extends HBox{
        private ArrayList<String> _selection_list;
        private int _selection_index;

        public SelectableItem(String name){
            _selection_index = 0;
            _selection_list = new ArrayList<String>();

            UnanimatedItem selection_item;
            selection_item = new UnanimatedItem("not_found");

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

            Rectangle long_space = new Rectangle(0.10*_width*_width_ratio, 0.05*_height*_height_ratio);
            long_space.setOpacity(0);

            Rectangle short_space_1 = new Rectangle(0.005*_width*_width_ratio, 0.05*_height*_height_ratio);
            short_space_1.setOpacity(0);

            Rectangle short_space_2 = new Rectangle(0.005*_width*_width_ratio, 0.05*_height*_height_ratio);
            short_space_2.setOpacity(0);

            this.getChildren().addAll(name_item,
                    long_space,
                    left_arrow,
                    short_space_1,
                    selection_item,
                    short_space_2,
                    right_arrow);
        }

        public SelectableItem(String name, String ... selection_tag){
            this(name);
            for(var tag:selection_tag){
                addSelectionElement(tag);
            }
        }

        public void next(){
            if(_selection_index == _selection_list.size()-1)
                _selection_index = 0;
            else
                _selection_index += 1;
            updateText();
        }

        public void previous(){
            if(_selection_index == 0)
                _selection_index = _selection_list.size()-1;
            else
                _selection_index -= 1;
            updateText();
        }

        public void addSelectionElement(String selection_tag){
            _selection_list.add(selection_tag);
            updateText();
        }

        private void updateText(){
            var selection_item = (UnanimatedItem)getChildren().stream()
                    .filter(e -> e instanceof UnanimatedItem)
                    .skip(1)
                    .findFirst()
                    .orElse(null);

            var index = getChildren().indexOf(selection_item);
            getChildren().remove(selection_item);
            if(_selection_list.isEmpty()){
                selection_item = new UnanimatedItem("not_found");
            } else {
                selection_item = new UnanimatedItem(_selection_list.get(_selection_index));
            }
            getChildren().add(index, selection_item);
        }
    }

    public class UnanimatedItem extends StackPane {
        String _name;

        public UnanimatedItem(String name) {
            _name = name;

            Rectangle bg = new Rectangle(0.19*_width*_width_ratio,0.05*_height*_height_ratio);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,0.0333*_height*_height_ratio));

            //setAlignment(Pos.CENTER);
            setAlignment(Pos.CENTER_LEFT);
            getChildren().addAll(bg, text);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
