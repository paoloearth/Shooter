package units.shooter_developers.MenuAPI;

import units.shooter_developers.CustomCheckedException;

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

        Line separatorLine = new Line();
        separatorLine.setEndX(0.2 * Menu.getMenuWidth());
        separatorLine.setStroke(separator_color);
        return separatorLine;
    }

    protected void addItem(String newMenuItem) {
        MenuItem menuItem = new MenuItem(newMenuItem);
        menuItem.setTranslateX(0.005 * Menu.getMenuWidth());

        getChildren().addAll(menuItem, createSeparator());
    }

    protected void addNonAnimatedItem(String newMenuItem) {
        NonAnimatedItem newItem = new NonAnimatedItem(newMenuItem);
        newItem.setTranslateX(0.005 * Menu.getMenuWidth());

        getChildren().addAll(newItem, createSeparator());
    }

    protected void addSelectorItem(String name, ArrayList<String> tagList) {
        SelectorItem newItem = new SelectorItem(name, true);
        newItem.setTranslateX(0.005 * Menu.getMenuWidth());

        tagList.forEach(newItem::addTag);

        getChildren().addAll(newItem, createSeparator());
    }

    protected void addSelectorItem(String name, int defaultIndex, ArrayList<String> tagList) throws CustomCheckedException.IndexOutOfRangeException {
        SelectorItem newItem = new SelectorItem(name, true);
        newItem.setTranslateX(0.005 * Menu.getMenuWidth());

        tagList.forEach(newItem::addTag);

        newItem.setDefaultIndex(defaultIndex);

        getChildren().addAll(newItem, createSeparator());
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
