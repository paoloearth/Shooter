package units.shooter_developers.Menus;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.stream.Collectors;

/************************ MENU BOX ****************************************/

class MenuBox extends VBox {

    private final Menu menu;

    MenuBox(Menu menu, Menu.MenuItem... items) {
        this.menu = menu;
        getChildren().add(createSeparator());

        for (Menu.MenuItem item : items) {
            getChildren().addAll(item, createSeparator());
        }
    }

    private Line createSeparator() {
        Color separator_color = Color.DARKGREY;

        Line separator_line = new Line();
        separator_line.setEndX(0.2 * menu.getMenuWidth());
        separator_line.setStroke(separator_color);
        return separator_line;
    }

    protected void addItem(String new_menu_item) {
        Menu.MenuItem new_item = new Menu.MenuItem(new_menu_item);
        new_item.setTranslateX(0.005 * menu.getMenuWidth());

        getChildren().addAll(new_item, createSeparator());
    }

    protected void addNonAnimatedItem(String new_menu_item) {
        Menu.NonAnimatedItem new_item = new Menu.NonAnimatedItem(new_menu_item);
        new_item.setTranslateX(0.005 * menu.getMenuWidth());

        getChildren().addAll(new_item, createSeparator());
    }

    protected void addSelectorItem(String name, ArrayList<String> tag_list) {
        Menu.SelectorItem new_item = new Menu.SelectorItem(name);
        new_item.setTranslateX(0.005 * menu.getMenuWidth());

        for (var tag : tag_list) {
            new_item.addTag(tag);
        }

        getChildren().addAll(new_item, createSeparator());
    }

    protected ArrayList<Menu.MenuItem> getItems() {
        return getChildren().parallelStream()
                .filter(e -> e instanceof Menu.MenuItem)
                .map(e -> (Menu.MenuItem) e)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected ArrayList<Menu.SelectorItem> getSelectorItems() {
        return getChildren().stream()
                .filter(e -> e instanceof Menu.SelectorItem)
                .map(e -> (Menu.SelectorItem) e)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
