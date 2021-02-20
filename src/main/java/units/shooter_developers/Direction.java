package units.shooter_developers;

public enum Direction {
    DOWN(0), LEFT(1), RIGHT(2), UP(3);
    private final int offset;
    Direction(int offset) {
        this.offset = offset;
    }
    public int getOffset() {
        return offset;
    }
}
