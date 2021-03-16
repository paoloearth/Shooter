package units.shooter_developers.MenuAPI;

import units.shooter_developers.CustomCheckedException;

import javafx.beans.property.StringPropertyBase;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;

/************************ SELECTOR ITEM ****************************************/

class SelectorItem extends HBox {
    private final ArrayList<String> _selectionList;
    private int _selectionIndex;
    private final String _name;
    private final StringPropertyBase _selectionAsProperty;

    public SelectorItem(String name, boolean showName){
        this(name, 0.25, showName);
    }

    public SelectorItem(String name, double scaleWidthTextBox, boolean showName) {
        _selectionIndex = 0;
        _selectionList = new ArrayList<>();
        _name = name;
        _selectionAsProperty = new StringPropertyBase(){
            @Override
            public Object getBean() { return this; }
            @Override
            public String getName() { return "selectionProperty"; }
        };
        var selectionSectionTranslation = 0.10;

        final NonAnimatedItem nameTextBox = new NonAnimatedItem(name);

        final var longSpace = new Rectangle(selectionSectionTranslation * Menu.getMenuWidth(), 0);

        MenuItem leftArrowButton = new MenuItem("<", 0.04, -1);
        leftArrowButton.setOnMouseReleased(event -> previous());

        final var shortSpace1 = new Rectangle(0.01 * Menu.getMenuWidth(), 0);

        final NonAnimatedItem selectionTextBox;
        selectionTextBox = new NonAnimatedItem("not_found", scaleWidthTextBox, -1);
        selectionTextBox.getNameAsProperty().bind(_selectionAsProperty);

        final var shortSpace2 = new Rectangle(0.01 * Menu.getMenuWidth(), 0);

        final MenuItem rightArrowButton = new MenuItem(">", 0.04, -1);
        rightArrowButton.setOnMouseReleased(event -> next());

        if(showName) {
            this.getChildren().add(nameTextBox);
            this.getChildren().add(longSpace);
        }
        this.getChildren().add(leftArrowButton);
        this.getChildren().add(shortSpace1);
        this.getChildren().add(selectionTextBox);
        this.getChildren().add(shortSpace2);
        this.getChildren().add(rightArrowButton);
    }

    private void next() {
        _selectionIndex = _selectionIndex == _selectionList.size()-1? 0 : _selectionIndex +1;
        _selectionAsProperty.setValue(_selectionList.get(_selectionIndex));
    }

    private void previous() {
        _selectionIndex = _selectionIndex == 0? _selectionList.size()-1 : _selectionIndex -1;
        _selectionAsProperty.setValue(_selectionList.get(_selectionIndex));
    }

    protected void addTag(String selection_tag) {
        _selectionList.add(selection_tag);
        _selectionAsProperty.setValue(_selectionList.get(_selectionIndex));
    }

    protected String getText() {
        return _selectionAsProperty.getValue();
    }

    protected String getName() {
        return _name;
    }

    protected StringPropertyBase getSelectionAsProperty(){
        return _selectionAsProperty;
    }

    protected void setDefaultIndex(int index) throws CustomCheckedException.IndexOutOfRangeException {
        if(index >= _selectionList.size() || index < 0){throw new CustomCheckedException.IndexOutOfRangeException(
                "Menu selector item",
                _selectionList.size()-1,
                index, SelectorItem.class);}

        _selectionIndex = index;
        _selectionAsProperty.setValue(_selectionList.get(_selectionIndex));
    }
}
