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

public class OptionsMenu extends Menu{
    Simulation _gameInstance;
    boolean _game_running;

    public OptionsMenu(){
        super();
        _game_running = false;
    }

    public OptionsMenu(Simulation game_instance){
        this();
        _gameInstance = game_instance;
        _game_running = true;
    }

    @Override
    public void start(Stage menu_stage){
        setTitle("O P T I O N S");
        this.addItem("RESOLUTION");
        this.addItem("LIGHT/DARK MODE");
        this.addSelectableItem("hola", "item_1", "item_2", "item_3");
        this.addSelectableItem("RESOLUTION",
                "640x360 (widescreen)",
                "800x600",
                "1024x768",
                "1280x720 (widescreen)",
                "1536x864 (widescreen)",
                "1600x900 (widescreen)",
                "1920x1080 (widescreen)");
        this.addItem("BACK");

        Scene scene = new Scene(this.getRoot());
        menu_stage.setTitle("VIDEO GAME");
        menu_stage.setScene(scene);
        menu_stage.show();

        Stage game_stage = new Stage();


        for(var item:_menu_items)
        {
            item.setOnMouseReleased(event -> {
                var item_casted = (MenuItem)item;
                if(item_casted.getName() == "BACK") {
                    GameMenu main_menu = new GameMenu();
                    main_menu.start(menu_stage);
                }



            });
        }

    }
}
