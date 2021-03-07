package units.shooter_developers.MenuAPI;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.stream.Collectors;

/************************ MENU BOX ****************************************/

class MenuBox extends VBox {

    MenuBox(MenuItem... items) {
        getChildren().add(createSeparator());

        for (MenuItem item : items) {
            getChildren().addAll(item, createSeparator());
        }
    }

    private Line createSeparator() {
        Color separator_color = Menu.getColorPalette().dead_color;

        Line separator_line = new Line();
        separator_line.setEndX(0.2 * Menu.getMenuWidth());
        separator_line.setStroke(separator_color);
        return separator_line;
    }

    protected void addItem(String new_menu_item) {
        MenuItem new_item = new MenuItem(new_menu_item);
        new_item.setTranslateX(0.005 * Menu.getMenuWidth());

        getChildren().addAll(new_item, createSeparator());
    }

    protected void addNonAnimatedItem(String new_menu_item) {
        NonAnimatedItem new_item = new NonAnimatedItem(new_menu_item);
        new_item.setTranslateX(0.005 * Menu.getMenuWidth());

        getChildren().addAll(new_item, createSeparator());
    }

    protected void addSelectorItem(String name, ArrayList<String> tag_list) {
        SelectorItem new_item = new SelectorItem(name);
        new_item.setTranslateX(0.005 * Menu.getMenuWidth());

        for (var tag : tag_list) {
            new_item.addTag(tag);
        }

        getChildren().addAll(new_item, createSeparator());
    }

    protected ArrayList<MenuItem> getItems() {
        return getChildren().parallelStream()
                .filter(e -> e instanceof MenuItem)
                .map(e -> (MenuItem) e)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    protected ArrayList<SelectorItem> getSelectorItems() {
        return getChildren().stream()
                .filter(e -> e instanceof SelectorItem)
                .map(e -> (SelectorItem) e)
                .collect(Collectors.toCollection(ArrayList::new));
    }

}
