package units.shooter_developers.MenuAPI;
// VISITED
import javafx.beans.property.StringPropertyBase;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/************************ SELECTOR ITEM ****************************************/

class SelectorItem extends HBox {
    private final ArrayList<String> _selection_list;
    private int _selection_index;
    private double _width_selection_item;
    private final String _name;
    private final double _selection_section_translation;  // Is this needed globally?
    private StringPropertyBase _selection_as_property;   // Is this needed globally?
    private final boolean _show_name;

    public SelectorItem(String name, boolean show_name){
        this(name, 0.25, show_name);
    }

    public SelectorItem(String name, double scale_width_text_box, boolean show_name) {
        _selection_index = 0;
        _selection_list = new ArrayList<>();
        _width_selection_item = 0.25;
        _selection_section_translation = 0.10;
        _name = name;
        _show_name = show_name;
        _width_selection_item = scale_width_text_box;
        _selection_as_property = new StringPropertyBase(){
            @Override
            public Object getBean() { return this; }
            @Override
            public String getName() { return "selectionProperty"; }
        };


        //setAlignment(Pos.CENTER_LEFT);
        NonAnimatedItem name_text_box = new NonAnimatedItem(name);

        var long_space = new Rectangle(_selection_section_translation * Menu.getMenuWidth(), 0);

        MenuItem left_arrow_button = new MenuItem("<", 0.04, -1);
        left_arrow_button.setOnMouseReleased(event -> {
            previous();
        });

        var short_space_1 = new Rectangle(0.01 * Menu.getMenuWidth(), 0);

        NonAnimatedItem selection_text_box;
        selection_text_box = new NonAnimatedItem("not_found", _width_selection_item, -1);

        var short_space_2 = new Rectangle(0.01 * Menu.getMenuWidth(), 0);

        MenuItem right_arrow_button = new MenuItem(">", 0.04, -1);
        right_arrow_button.setOnMouseReleased(event -> {
            next();
        });

        if(_show_name) {
            this.getChildren().add(name_text_box);
            this.getChildren().add(long_space);
        }
        // How about squueze this down?
        // this.getChildren().addAll(left_arrow_button,short_space_1,selection_text_box,short_space_2,right_arrow_button);

        this.getChildren().add(left_arrow_button);
        this.getChildren().add(short_space_1);
        this.getChildren().add(selection_text_box);
        this.getChildren().add(short_space_2);
        this.getChildren().add(right_arrow_button);
    }

    private void next() {

        // More compact notation
        _selection_index = _selection_index == _selection_list.size()-1? 0 : _selection_index+1;
        /*
        if (_selection_index == _selection_list.size() - 1)
            _selection_index = 0;
        else
            _selection_index += 1;
            */

        updateTagText();
    }

    private void previous() {

        // More compact notation
        _selection_index = _selection_index == 0? _selection_list.size() - 1 :  _selection_index - 1;
/*
        if (_selection_index == 0)
            _selection_index = _selection_list.size() - 1;
        else
            _selection_index -= 1;

 */
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

        // Added assertion
        if (!_selection_list.isEmpty()) {
            assert selection_item != null;
            selection_item.setName(_selection_list.get(_selection_index));
        }

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
 // Dead code?
    protected void setWidthSelectionZone(double scale_width){
        _width_selection_item = scale_width;
    }
}
