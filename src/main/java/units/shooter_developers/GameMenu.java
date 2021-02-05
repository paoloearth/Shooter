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
    Simulation _gameInstance;
    boolean _game_running;

    public GameMenu(){
        super();   // <- Elementos añadidos para gestionar mejor el ciclo
        _gameInstance = new Simulation();   //    de ejecución de la simulación.
        _game_running = false;
    }

    public GameMenu(Simulation game_instance){
        this();
        _gameInstance = game_instance;
        _game_running = true;
    }

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

    public static void main(String[] args) {
        launch(args);
    }
}