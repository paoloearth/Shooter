# Menu documentation

This file contains the documentation about the use of Menu API and the specific menus developed in the project. This menu is based on J. SIDERIUS'S Menu (https://github.com/Siderim/video-game-menu/) in the graphical aspects, but code structure and use has been deeply changed. This menu has been developed by using JavaFX, so specific terminology of this tool will be used on this document.



## Menu API

It has been implemented an API through an abstract class (`Menu`) that provides functionalities to easily creating menus with different features. In the first part it will be shown the different constructors and methods, then it will be seen how to use this API.

### 1. Constructors

+ `Menu()`: Constructs menu objects by using the screen resolution.
+ `Menu(Menu other_menu)`: Constructs a menu by using the resolution and position features of other menu. Note that it does not copy the content of the other menu, since it is not much useful.
+ `Menu(double stage_width, double stage_height)`: Construct a menu by providing a size for the `Stage` object that holds the menu.

### 2. Methods

**Elements Management**

* `addItem(String new_menu_item)`: Adds a new (interactive and animated) item to the menu. `new_menu_item` is the text shown on the item. This method must be used before launching the menu.
* `addUnanimatedItem(String new_menu_item)`: Adds a new non-interactive item th the menu. `new_menu_item` is the text shown on the item. This method must be used before launching the menu.
* `addSelectableItem(String item_name, String ... selection_tags)`: Adds a selector. The first argument `item_name` is the tittle of the selector while the rest of `selection_tags` arguments are the selector's options. This method must be used before launching the menu.
* `setTitle(String title)`: Removes the current (or default) tittle of the menu and substitute it by the `title` one. This method must be used before launching the menu.

**Set/Get Methods**

Screen:

* `double getScreenWidth()`: Detects and return the device's screen width in pixels.
* `double getScreenHeight()`: Detects and return the device's screen height in pixels.
* `setStage(Stage stage)`: Sets the stage in which the menu will be launch. This method must be used before launching the menu.

Stage:

* `Stage getStage()`: Return the current stage of the menu.
* `setStageDimensions(double width, double height)`: Sets the dimensions of the stage in terms of pixels. This method must be used before launching the menu.
* `double getStageHeight()`: Returns the stage height value in pixels.
* `double getStageWidth()`: Returns the stage width value in pixels.

Menu object:

* `resize(double width_ratio, double height_ratio)`: Changes the menu size by providing a custom relation aspect (ratios respect to the resolution).
* `double getMenuHeight()`: Returns the height of menu in pixels.
* `double getMenuWidth()`: Returns the width of menu in pixels.
* `setPositionRatio(double position_width_ratio, double position_height_ratio)`: Sets the position of the menu in the stage by providing a ratio respect to the resolution of the menu.
* `double getPositionX()`: Returns the horizontal position of the menu in pixels.
* `double getPositionY()`: Returns the vertical position of the menu in pixels.

Menu elements:

* `Parent getRoot()`: Returns the parent object that contains the objects of the menu.
* `MenuBox getItemsBox()`: Return the object which contains all the items.
* `ArrayList<MenuItem> getItems()`: Returns a list with all the menu items (buttons).
* `ArrayList<SelectableItem> getSelectableItems()`: Return a list with all selectable items (selectors).
* `getSelectableItem(String name)`: Return a specific selectable item by providing its name.

### 3. Menu Components

* `class MenuItem`:  Menu's interactive component which works as a button. Has the following constructor and method:
  * `MenuItem(String name)`: Construct the object by providing its name.
  * `String getName()`: Provides the name of the object.
* `class SelectableItem`: Interactive selector that let to choose an option among several ones. Options are represented with a list of Strings. There are present a constructor and a methods:
  * `SelectableItem(String name)`: Construct the object by providing its name.
  * `addTag(String selection_tag)`: Adds an option to the list of options.
  * `String getText()`: Provides the text of the currently selected option.
  * `String getName()`: Provides the name of the item.
* `class UnanimatedItem`: non-interactive element similar to `MenuItem` one. It has only a constructor:
  * `UnanimatedItem(String name)`: Constructs the object by providing its name.

### 4. Menu Composition Schema

![Menu_schema](/home/jose/Escritorio/Magistrale/Software_Development_Methods/Proyecto/testing_projects/repo/Shooter/docs/Menu_schema.png)

Red arrows represent that certain object instantiates a particular element shown on the menu.

### 5. Use

Menu is an abstract class that enforces the implementation of `start(Stage)`. A start method could have the following aspect:

```
@Override
public void start(Stage menu_stage){
	// INITIALISATION AND SETTING OF STAGE
    setStage(menu_stage);
    setStageDimensions(getStageWidth(), getStageHeight());
    menu_stage.setTitle("VIDEO GAME");
    Scene scene = new Scene(this.getRoot());
    menu_stage.setScene(scene);
	
	// CONTENT ADDITION
	setTitle("C A M P A I G N");
    this.addItem("ITEM 1");
    this.addItem("ITEM 2");
    this.addUnanimatedItem("ITEM 3");
    this.addSelectableItem("NAME", "option1", "option2", "option3");
    
    // MENU LAUNCHING
    menu_stage.show();

    // EVENTS FOR DETECTING THE PRESSED BUTTOMS
    item.setOnMouseReleased(event -> {
                if (item.getName().equals("ITEM 1")) {
                    menu_stage.close();
                    // do the stuff for item 1
                }
                
                ...
	}
```

 Note that at the beginning it has been set `setStage(menu_stage)`. This is an important aspect in order to avoid conflicts 