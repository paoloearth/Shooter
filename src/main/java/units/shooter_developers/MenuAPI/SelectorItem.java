package units.shooter_developers.MenuAPI;

import javafx.beans.property.StringPropertyBase;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

/************************ SELECTOR ITEM ****************************************/

class SelectorItem extends HBox {
    private final ArrayList<String> _selection_list;
    private int _selection_index;
    private final double _width_selection_item;
    private final String _name;
    private final double _selection_section_translation;
    private StringPropertyBase _selection_as_property;
    private final boolean _show_name;

    public SelectorItem(String name, boolean show_name) {
        _selection_index = 0;
        _selection_list = new ArrayList<>();
        _width_selection_item = 0.25;
        _selection_section_translation = 0.10;
        _name = name;
        _show_name = show_name;
        _selection_as_property = new StringPropertyBase(){
            @Override
            public Object getBean() { return this; }
            @Override
            public String getName() { return "selectionProperty"; }
        };


        setAlignment(Pos.CENTER_LEFT);
        NonAnimatedItem name_text_box = new NonAnimatedItem(name);

        MenuItem left_arrow_button = new MenuItem("<", 0.04, -1);
        left_arrow_button.setTranslateX(_selection_section_translation * Menu.getMenuWidth());
        left_arrow_button.setOnMouseReleased(event -> {
            previous();
        });

        NonAnimatedItem selection_text_box;
        selection_text_box = new NonAnimatedItem("not_found", _width_selection_item, -1);
        selection_text_box.setTranslateX((_selection_section_translation + 0.01) * Menu.getMenuWidth());

        MenuItem right_arrow_button = new MenuItem(">", 0.04, -1);
        right_arrow_button.setTranslateX((_selection_section_translation + 0.02) * Menu.getMenuWidth());
        right_arrow_button.setOnMouseReleased(event -> {
            next();
        });

        if(_show_name)
            this.getChildren().add(name_text_box);
        this.getChildren().add(left_arrow_button);
        this.getChildren().add(selection_text_box);
        this.getChildren().add(right_arrow_button);
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
        int how_to_skip = _show_name? 1: 0;
        var selection_item = (NonAnimatedItem) getChildren().stream()
                .filter(e -> e instanceof NonAnimatedItem)
                .skip(how_to_skip)
                .findFirst()
                .orElse(null);

        if (!_selection_list.isEmpty())
            selection_item.setName(_selection_list.get(_selection_index));

        _selection_as_property.setValue(_selection_list.get(_selection_index));
    }

    public String getText() {
        return _selection_list.get(_selection_index);
    }

    public String getName() {
        return _name;
    }

    public StringPropertyBase getSelectionAsProperty(){
        return _selection_as_property;
    }
}
