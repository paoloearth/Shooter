package units.shooter_developers;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


public class ObjectAnimation extends Transition {
    private final ImageView imageView;
    private final int number_of_frames;
    private final int columns;
    private final int offsetX;
    private final int offsetY;
    private final double width;
    private final double height;

    private int lastIndex;

    //constructor
    public ObjectAnimation(ImageView imageView,
                           Duration duration,
                           int number_of_frames, int columns,
                           int offsetX, int offsetY,
                           double width, double height) {
        this.imageView = imageView;
        this.number_of_frames = number_of_frames;
        this.columns   = columns;
        this.offsetX   = offsetX;
        this.offsetY   = offsetY;
        this.width     = width;
        this.height    = height;
        setCycleDuration(duration);
        setInterpolator(Interpolator.LINEAR);
        setAutoReverse(false);
    }


    protected void interpolate(double k) {
        final int index = Math.min((int) Math.floor(k * number_of_frames), number_of_frames - 1);
        if (index != lastIndex) {
            final double x = (index % columns) * width  + offsetX;
            final double y = (index / columns) * height + offsetY;
            imageView.setViewport(new Rectangle2D(x, y, width, height));
            lastIndex = index;
        }
    }
}
