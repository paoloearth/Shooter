package units.shooter_developers;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Pictured_Object extends Map_Object {
    protected String _url;                // URL path to locate the spritesheet containing the pictures
    protected Image _picture;             // Store reference file for the image to render
    protected ImageView _view;            // Will store the current view

    protected int _n_rows;                // Number of rows  of the sprite-sheet
    protected int _n_cols;                // Number of columns of the sprite-sheet


    protected double _scale;              // Scale to make the loaded image of desired size

    // Will store the type of the object
    protected String _type;

    // Boolean dead;
    BooleanProperty _isDead = new SimpleBooleanProperty(false);

}
