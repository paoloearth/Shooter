
package units.shooter_developers;

public class Coordinates {
    private final double x;
    private final double y;

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() { return "Coordinates{" + "x=" + x + ", y=" + y + '}'; }

    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }



}
