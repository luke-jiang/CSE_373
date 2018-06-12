package mazes.entities;

import misc.graphs.Edge;

/**
 * Represents a wall between two rooms.
 *
 * A 'Wall' object also serves as an 'edge' of sorts between rooms.
 *
 * A Wall is also comparable according to the distance between the two
 * given rooms.
 */
public class Wall implements Edge<Room>, Comparable<Wall> {
    private Room room1;
    private Room room2;
    private LineSegment dividingLine;
    private double distance;

    /**
     * Constructs a wall between the two given rooms.
     *
     * The 'distance' is the length (in pixels) between the center of the two
     * rooms; the dividingLine is the actual line to draw on the screen.
     */
    public Wall(Room room1, Room room2, LineSegment dividingLine, double distance) {
        this.room1 = room1;
        this.room2 = room2;
        this.dividingLine = dividingLine;
        this.distance = distance;
    }

    /**
     * Constructs a wall between the two given rooms, and automatically constructs
     * the distance.
     */
    public Wall(Room room1, Room room2, LineSegment dividingLine) {
        this(room1, room2, dividingLine, room1.getCenter().distance(room2.getCenter()));
    }

    /**
     * Returns a pointer to the first room.
     */
    public Room getRoom1() {
        return this.room1;
    }

    /**
     * Returns a pointer to the other room.
     */
    public Room getRoom2() {
        return this.room2;
    }

    /**
     * Returns the physical line dividing the two rooms.
     */
    public LineSegment getDividingLine() {
        return this.dividingLine;
    }

    /**
     * Returns the distance between two rooms.
     */
    public double getDistance() {
        return this.distance;
    }


    /**
     * Equivalent to getRoom1()
     */
    public Room getVertex1() {
        return this.getRoom1();
    }

    /**
     * Equivalent to getRoom2()
     */
    public Room getVertex2() {
        return this.getRoom2();
    }

    /**
     * Equivalent to getDistance()
     */
    public double getWeight() {
        return this.getDistance();
    }

    /**
     * Changes the distance to some custom value.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }

    /**
     * Recomputes the distance to be the actual distance between the
     * center of the two rooms.
     */
    public void resetDistanceToOriginal() {
        this.distance = this.getVertex1().getCenter().distance(this.getVertex2().getCenter());
    }

    /**
     * Note: we deliberately DO NOT include the distance when checking to see if two
     * walls are equal.
     *
     * This means two walls that connect two rooms but have different distances
     * are considered to be the same.
     *
     * The reason why we do this is because in KruskalMazeCarver, we want to randomize
     * the weights of the walls before finding a MST. However, this could potentially
     * present an issue since what we're doing is changing an object we may be storing
     * inside a hash map as a key or inside of a hash set. This can lead to potentially
     * inconsistent behavior -- once we stick an object inside of a map or set, the
     * hashCode() should never change.
     *
     * So, to dodge this, we just make the .equals(...) and .hashCode() methods never
     * reliant on what the distance is.
     *
     * That said, this is still technically unsound because now the behavior of .equals(...) and
     * .hashCode() are inconsistent with the behavior of .compareTo(...), which we should
     * normally never do.
     *
     * In theory, we should have constructed this assignment so that this discrepancy is
     * never an issue. That said, although this discrepancy is unlikely to be an issue in
     * practice, we're still choosing between two evils here.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Wall wall = (Wall) o;

        if (!room1.equals(wall.room1)) { return false; }
        if (!room2.equals(wall.room2)) { return false; }
        return dividingLine.equals(wall.dividingLine);
    }

    @Override
    public int hashCode() {
        int result;
        result = room1.hashCode();
        result = 31 * result + room2.hashCode();
        result = 31 * result + dividingLine.hashCode();
        return result;
    }

    @Override
    public int compareTo(Wall other) {
        return Double.compare(this.distance, other.distance);
    }

    @Override
    public String toString() {
        return String.format("Wall(room1=%s, room2=%s, distance=%s", this.room1, this.room2, this.distance);
    }
}
