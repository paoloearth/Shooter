package units.shooter_developers;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Menu extends Application {
    ArrayList<Menu.MenuItem> _menu_items;
    Pane _root;
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

        root.setPrefSize(_width_ratio*_width, _height_ratio*_height);
        _width = 1050;///////////////////////////////////////////////////
        _height = 600;//////////////////////////////////////////////////
        //original default resolution was 1050x600

        try (InputStream is = Files.newInputStream(Paths.get("src/main/resources/menu.jpeg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(_width_ratio*_width);
            img.setFitHeight(_height_ratio*_height);
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

    private static class Title extends StackPane {
        public Title(String name) {
            Rectangle bg = new Rectangle(375, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg, text);
        }
    }

    //wrapper para los elementos del menú
    private static class MenuBox extends VBox {

        public MenuBox(Menu.MenuItem... items) {
            getChildren().add(createSeperator());

            for (Menu.MenuItem item : items) {
                getChildren().addAll(item, createSeperator());
            }
        }

        private Line createSeperator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }

        public void addItem(Menu.MenuItem new_item){
            getChildren().addAll(new_item, createSeperator());
        }

    }

    public static class MenuItem extends StackPane {
        String _name;

        public MenuItem(String name) {
            _name = name;

            LinearGradient gradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, new Stop[] {
                    new Stop(0, Color.DARKBLUE),
                    new Stop(0.1, Color.BLACK),
                    new Stop(0.9, Color.BLACK),
                    new Stop(1, Color.DARKBLUE)

            });

            Rectangle bg = new Rectangle(200,30);
            bg.setOpacity(0.4);

            Text text = new Text(name);
            text.setFill(Color.DARKGREY);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD,20));

            setAlignment(Pos.CENTER);
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

        public MenuItem(String name, ArrayList<Menu.MenuItem> items_list) {
            this(name);
            items_list.add(this);
        }

        public String getName() {
            return _name;
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
