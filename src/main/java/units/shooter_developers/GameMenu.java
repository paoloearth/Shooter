package units.shooter_developers;/*
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
import java.nio.*;
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

public class GameMenu extends Menu{
    ArrayList<MenuItem> _menu_items;
    Simulation _gameInstance;
    boolean _game_running;

    public GameMenu(){
        _menu_items = new ArrayList<>();    // <- Elementos añadidos para gestionar mejor el ciclo
        _gameInstance = new Simulation();   //    de ejecución de la simulación.
        _game_running = false;
    }

    public GameMenu(Simulation game_instance){
        this();
        _gameInstance = game_instance;
        _game_running = true;
    }

    //implementa los aspectos gráficos del menú y define los elementos presentes
/*
    public Pane createContent(Stage menu_stage) {
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

        Title title = new Title ("C A M P A I G N");
        title.setTranslateX(50);
        title.setTranslateY(200);

        MenuBox vbox = new MenuBox(
                new MenuItem("CONTINUE", _menu_items),
                new MenuItem("NEW GAME", _menu_items),
                new MenuItem("NEW LAN-GAME", _menu_items),
                new MenuItem("OPTIONS", _menu_items),
                new MenuItem("EXIT", _menu_items));
        vbox.setTranslateX(100);
        vbox.setTranslateY(300);

        root.getChildren().addAll(title,vbox);

        return root;

    }
*/

    @Override
    public void start(Stage menu_stage) throws Exception{
        Pane root = createContent(menu_stage);
        this.addItem("CONTINUE", root);
        this.addItem("NEW GAME", root);
        this.addItem("NEW LAN-GAME", root);
        this.addItem("OPTIONS", root);
        this.addItem("EXIT", root);
        Scene scene = new Scene(root);
        menu_stage.setTitle("VIDEO GAME");
        menu_stage.setScene(scene);
        menu_stage.show();

        Stage game_stage = new Stage();

        //para cada elemento, se compara la etiqueta y se hace el trabajo para la opcion correspondiente
        for(var item:_menu_items)
        {
            item.setOnMouseReleased(event -> {
                if(item.getName() == "NEW GAME") {
                    try {                           //habría que quitar el try catch de alguna manera
                        menu_stage.close();
                        if(_game_running) {
                            _gameInstance.stop();
                            _gameInstance = new Simulation();
                        }
                        _gameInstance.start(game_stage);
                        _game_running = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(item.getName() == "CONTINUE") {
                    try {
                        if(_game_running)
                            menu_stage.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if(item.getName() == "EXIT") {
                    _gameInstance.stop();
                    _game_running = false;
                    menu_stage.close();
                }

                if(item.getName() == "OPTIONS") {
                    OptionsMenu options_menu = new OptionsMenu();

                    try {
                        options_menu.start(menu_stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


            });
        }
    }

    //Implementa las caracteristicas gráficas del título
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

    //wrapper para los elementos del menú
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

    //implementa las características gráficas de los distintos elementos del menú
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