package units.shooter_developers.Menus;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/************************ SELECTOR ITEM ****************************************/

public class SelectorItem extends HBox {
    private final ArrayList<String> _selection_list;
    private int _selection_index;
    private final double _width_selection_item;
    private final String _name;
    private final double _selection_section_translation;

    public SelectorItem(String name) {
        _selection_index = 0;
        _selection_list = new ArrayList<>();
        _width_selection_item = 0.25;
        _selection_section_translation = 0.10;
        _name = name;


        setAlignment(Pos.CENTER_LEFT);
        NonAnimatedItem name_text_box = new NonAnimatedItem(name);

        Menu.MenuItem left_arrow_button = new Menu.MenuItem("<", 0.04, -1);
        left_arrow_button.setTranslateX(_selection_section_translation * Menu.getMenuWidth());
        left_arrow_button.setOnMouseReleased(event -> {
            previous();
        });

        NonAnimatedItem selection_text_box;
        selection_text_box = new NonAnimatedItem("not_found", _width_selection_item, -1);
        selection_text_box.setTranslateX((_selection_section_translation + 0.01) * Menu.getMenuWidth());

        Menu.MenuItem right_arrow_button = new Menu.MenuItem(">", 0.04, -1);
        right_arrow_button.setTranslateX((_selection_section_translation + 0.02) * Menu.getMenuWidth());
        right_arrow_button.setOnMouseReleased(event -> {
            next();
        });

        this.getChildren().addAll(name_text_box,
                left_arrow_button,
                selection_text_box,
                right_arrow_button);
    }

    private void next() {
        if (_selection_index == _selection_list.size() - 1)
            _selection_index = 0;
        else
            _selection_index += 1;
        updateTagText();
    }

    private void previous() {
        if (_selection_index == 0)
            _selection_index = _selection_list.size() - 1;
        else
            _selection_index -= 1;
        updateTagText();
    }

    public void addTag(String selection_tag) {
        _selection_list.add(selection_tag);
        updateTagText();
    }

    private void updateTagText() {
        var selection_item = (NonAnimatedItem) getChildren().stream()
                .filter(e -> e instanceof NonAnimatedItem)
                .skip(1)
                .findFirst()
                .orElse(null);

        var index = getChildren().indexOf(selection_item);
        getChildren().remove(selection_item);
        if (!_selection_list.isEmpty()) {
            selection_item = new NonAnimatedItem(_selection_list.get(_selection_index),
                    _width_selection_item, -1);
            selection_item.setTranslateX((_selection_section_translation + 0.01) * Menu.getMenuWidth());
        }
        getChildren().add(index, selection_item);
    }

    public String getText() {
        return _selection_list.get(_selection_index);
    }

    public String getName() {
        return _name;
    }
}
