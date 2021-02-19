package units.shooter_developers;

import javafx.application.Application;
import javafx.geometry.Pos;
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

    public Menu() {
        _menu_items = new ArrayList<>();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public void createContent(Stage menu_stage) {

        Pane root = new Pane();

        root.setPrefSize(1050, 600);

        try (InputStream is = Files.newInputStream(Paths.get("src/main/resources/menu.jpeg"))) {
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1050);
            img.setFitHeight(600);
            root.getChildren().add(img);
        } catch (IOException e) {
            System.out.println("Couldn't load image");
        }

        Menu.Title title = new Menu.Title("C A M P A I G N");
        title.setTranslateX(50);
        title.setTranslateY(200);

        Menu.MenuBox vbox = new Menu.MenuBox();
        vbox.setTranslateX(100);
        vbox.setTranslateY(300);

        root.getChildren().addAll(title, vbox);

        _root = root;

    }

    public Parent getRoot(){
        return _root;
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
