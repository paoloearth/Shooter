package units.shooter_developers.MenuAPI;
// VISITED
import javafx.beans.property.StringPropertyBase;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import units.shooter_developers.CustomException;

import java.util.ArrayList;

/************************ SELECTOR ITEM ****************************************/

class SelectorItem extends HBox {
    private ArrayList<String> _selection_list = null;
    private int _selection_index;
    private final String _name;
    private StringPropertyBase _selection_as_property = null;

    public SelectorItem(String name, boolean show_name){
        this(name, 0.25, show_name);
    }

    public SelectorItem(String name, double scale_width_text_box, boolean show_name) {
        _selection_index = 0;
        _selection_list = new ArrayList<>();
        _name = name;
        _selection_as_property = new StringPropertyBase(){
            @Override
            public Object getBean() { return this; }
            @Override
            public String getName() { return "selectionProperty"; }
        };
        var selection_section_translation = 0.10;

        final NonAnimatedItem name_text_box = new NonAnimatedItem(name);

        final var long_space = new Rectangle(selection_section_translation * Menu.getMenuWidth(), 0);

        MenuItem left_arrow_button = new MenuItem("<", 0.04, -1);
        left_arrow_button.setOnMouseReleased(event -> {
            previous();
        });

        final var short_space_1 = new Rectangle(0.01 * Menu.getMenuWidth(), 0);

        final NonAnimatedItem selection_text_box;
        selection_text_box = new NonAnimatedItem("not_found", scale_width_text_box, -1);
        selection_text_box.getNameAsProperty().bind(_selection_as_property);

        final var short_space_2 = new Rectangle(0.01 * Menu.getMenuWidth(), 0);

        final MenuItem right_arrow_button = new MenuItem(">", 0.04, -1);
        right_arrow_button.setOnMouseReleased(event -> {next();});

        if(show_name) {
            this.getChildren().add(name_text_box);
            this.getChildren().add(long_space);
        }
        this.getChildren().add(left_arrow_button);
        this.getChildren().add(short_space_1);
        this.getChildren().add(selection_text_box);
        this.getChildren().add(short_space_2);
        this.getChildren().add(right_arrow_button);
    }

    private void next() {
        _selection_index = _selection_index == _selection_list.size()-1? 0 : _selection_index+1;
        _selection_as_property.setValue(_selection_list.get(_selection_index));
    }

    private void previous() {
        _selection_index = _selection_index == 0? _selection_list.size()-1 : _selection_index-1;
        _selection_as_property.setValue(_selection_list.get(_selection_index));
    }

    protected void addTag(String selection_tag) {
        _selection_list.add(selection_tag);
        _selection_as_property.setValue(_selection_list.get(_selection_index));
    }

    protected String getText() {
        return _selection_as_property.getValue();
    }

    protected String getName() {
        return _name;
    }

    protected StringPropertyBase getSelectionAsProperty(){
        return _selection_as_property;
    }

    protected void setDefaultIndex(int index) throws CustomException.IndexOutOfRange {
        if(index >= _selection_list.size()){throw new CustomException.IndexOutOfRange(
                "Menu selector item",
                _selection_list.size()-1,
                index, SelectorItem.class);}

        _selection_index = index;
        _selection_as_property.setValue(_selection_list.get(_selection_index));
    }
}
