package units.shooter_developers;
/*
 *
 *  MICHAEL J. SIDERIUS
 *
 *  DECEMBER 30 2015
 *  VIDEO GAME MENU CONCEPT V1
 *  GOAL: PRACTICE USING JAVAFX TECHNOLOGY AND METHODS
 *
 *
 *  Credit to: https://github.com/Siderim/video-game-menu/
 */


import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import javafx.application.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.*;
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
import javafx.scene.text.*;

public class OptionsMenu extends Application{
    ArrayList<MenuItem> _menu_items;
    Simulation _gameInstance;
    boolean _game_running;

    public OptionsMenu(){
        _menu_items = new ArrayList<>();
        _gameInstance = new Simulation();
        _game_running = false;
    }

    public OptionsMenu(Simulation game_instance){
        this();
        _gameInstance = game_instance;
        _game_running = true;
    }

    private Parent createContent(Stage menu_stage) {
        Pane root = new Pane();

        root.setPrefSize(1050, 600);

        try(InputStream is = Files.newInputStream(Paths.get("src/main/resources/menu.jpeg"))){
            ImageView img = new ImageView(new Image(is));
            img.setFitWidth(1050);
            img.setFitHeight(600);
            root.getChildren().add(img);
        }
        catch(IOException e) {
            System.out.println("Couldn't load image");
        }

        Title title = new Title ("O P T I O N S");
        title.setTranslateX(50);
        title.setTranslateY(200);

        Simulation gameInstance = new Simulation();

        MenuBox vbox = new MenuBox(
                new MenuItem("RESOLUTION", _menu_items),
                new MenuItem("LIGHT/DARK MODE", _menu_items),
                new MenuItem("BACK", _menu_items));
        vbox.setTranslateX(100);
        vbox.setTranslateY(300);

        root.getChildren().addAll(title,vbox);

        return root;

    }
    @Override
    public void start(Stage menu_stage) throws Exception{
        Scene scene = new Scene(createContent(menu_stage));
        menu_stage.setTitle("VIDEO GAME");
        menu_stage.setScene(scene);
        menu_stage.show();

        Stage game_stage = new Stage();

        for(var item:_menu_items)
        {
            item.setOnMouseReleased(event -> {
                if(item.getName() == "BACK") {
                    try {
                        GameMenu menu_instance = new GameMenu(_gameInstance);
                        menu_instance.start(menu_stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private static class Title extends StackPane{
        public Title(String name) {
            Rectangle bg = new Rectangle(375, 60);
            bg.setStroke(Color.WHITE);
            bg.setStrokeWidth(2);
            bg.setFill(null);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
        }
    }

    private static class MenuBox extends VBox{
        public MenuBox(MenuItem...items) {
            getChildren().add(createSeperator());

            for(MenuItem item : items) {
                getChildren().addAll(item, createSeperator());
            }
        }

        private Line createSeperator() {
            Line sep = new Line();
            sep.setEndX(210);
            sep.setStroke(Color.DARKGREY);
            return sep;
        }

    }

    public interface Operations {
        void performOperations();
    }

    private static class MenuItem extends StackPane{
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

        public MenuItem(String name, ArrayList<MenuItem> items_list){
            this(name);
            items_list.add(this);
        }

        public String getName(){
            return _name;
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}
