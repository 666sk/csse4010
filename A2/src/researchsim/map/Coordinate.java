package researchsim.map;

import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.BadSaveException;
import researchsim.util.Encodable;
import java.util.Objects;

/**
 * A coordinate is a representation of the  X and Y positions on a graphical map.<br>
 * This X, Y position can be used to calculate the index of a Tile in the scenario tile map
 * depending on the currently active scenario. <br>
 * The X and Y positions will not change but the index will depending on the current scenario.
 * <p>
 * A coordinate is similar to a point on the cartesian plane.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 * @ass1_test_partial
 */
public class Coordinate implements Encodable {

    /**
     * The position in the Horizontal plane (Left-Right).
     */
    private final int xcoord;

    /**
     * The position in the Vertical plane (Up-Down).
     */
    private final int ycoord;

    /**
     * Creates a new coordinate at the top left position (0,0), index 0 (zero).
     *
     * @ass1
     */
    public Coordinate() {
        this(0, 0);
    }

    /**
     * Creates a new coordinate at the specified (x,y) position.
     *
     * @param xcoord horizontal position
     * @param ycoord vertical position
     * @ass1
     */
    public Coordinate(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * Creates a new coordinate at the specified index.
     *
     * @param index index in the tile grid
     * @ass1
     */
    public Coordinate(int index) {
        int width = ScenarioManager.getInstance().getScenario().getWidth();
        this.xcoord = index % width;
        this.ycoord = index / width;
    }

    /**
     * The position in the Horizontal plane (Left-Right)
     *
     * @return the horizontal position
     * @ass1
     */
    public int getX() {
        return xcoord;
    }

    /**
     * The position in the Horizontal plane (Left-Right) absolute value.
     * @return the absolute horizontal position
     */
    public int getAbsX() {
        return Math.abs(xcoord);
    }

    /**
     * The position in the Vertical plane (Up-Down)
     *
     * @return the vertical position
     * @ass1
     */
    public int getY() {
        return ycoord;
    }

    /**
     * The position in the Vertical plane (Up-Down) absolute value.
     * @return the absolute vertical position
     */
    public int getAbsY() {
        return Math.abs(ycoord);
    }

    /**
     * The index in the tile grid of this coordinate.
     *
     * @return the grid index
     * @ass1
     */
    public int getIndex() {
        return Coordinate.convert(xcoord, ycoord);
    }

    /**
     * Determines if the coordinate in the bounds of the current scenario map
     *
     * @return true, if 0 &le; coordinate's x position &lt; current scenarios' width AND 0 &le;
     * coordinate's y position &lt; current scenarios' height
     * else, false
     * @ass1
     */
    public boolean isInBounds() {
        Scenario scenario = ScenarioManager.getInstance().getScenario();
        return xcoord < scenario.getWidth() && xcoord >= 0
            && ycoord < scenario.getHeight() && ycoord >= 0;
    }

    /**
     * Utility method to convert an (x,y) integer pair to an array index location.
     *
     * @param xcoord the x portion of a coordinate
     * @param ycoord the y portion of a coordinate
     * @return the converted index
     * @ass1
     */
    public static int convert(int xcoord, int ycoord) {
        return xcoord + ycoord * ScenarioManager.getInstance().getScenario().getWidth();
    }


    /**
     * Returns a new Coordinate from the given encoded string
     * The format of the string should match the encoded representation
     * of a Coordinate, as described in encode()
     * @param encoded - the encoded coordinate string
     * @return decoded Coordinate
     * @throws BadSaveException -
     * if the format of the given string is invalid according to the rules above
     */
    public static Coordinate decode(String encoded) throws BadSaveException {
        int resultX;
        int resultY;
        String[] result = encoded.split(",", 2);
        try {
            resultX = Integer.parseInt(result[0]);
            resultY = Integer.parseInt(result[1]);
        } catch (Exception e) {
            throw new BadSaveException();
        }
        return new Coordinate(resultX, resultY);
    }

    /**
     * Returns a special Coordinate pair showing the difference
     * between the current instance and the other coordinate.
     * @param other coordinate to compare
     * @return special difference Coordinate pair
     */
    public Coordinate distance(Coordinate other) {
        int resultX;
        int resultY;
        resultX = other.getX() - this.getX();
        resultY = other.getY() - this.getY();
        return new Coordinate(resultX, resultY);
    }

    /**
     * Translate the coordinate the given amount of tiles in the x and y direction.
     * @param x - translation in the x- axis
     * @param y -  translation in the y- axis
     * @return new coordinate location
     */
    public Coordinate translate(int x, int y) {
        int resultX;
        int resultY;
        resultX = this.getX() + x;
        resultY = this.getY() + y;
        return new Coordinate(resultX, resultY);
    }

    /**
     * Return the machine-readable string representation of this Coordinate.
     * @specified by encode in interface Encodable
     * @return encoded string representation of this Coordinate.
     */
    public String encode() {
        return String.format("%d,%d", getX(), getY());
    }

    /**
     * Returns the hash code of this coordinate.
     * @return hash code of this coordinate.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.xcoord, this.ycoord);
    }

    /**
     * Returns true if and only if this coordinate is equal to the other given coordinate.
     * @param other - the reference object with which to compare
     * @return true if this coordinate is the same as the other argument; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Coordinate
                && ((Coordinate) other).getX() == this.getX()
                && ((Coordinate) other).getY() == this.getY();
    }


    /**
     * Returns the human-readable string representation of this Coordinate.
     * <p>
     * The format of the string to return is:
     * <pre>(x,y)</pre>
     * Where:
     * <ul>
     *   <li>{@code x} is the position in the Horizontal plane (Left-Right)</li>
     *   <li>{@code y} is the position in the Vertical plane (Up-Down)</li>
     * </ul>
     * For example:
     *
     * <pre>(1,3)</pre>
     *
     * @return human-readable string representation of this Coordinate.
     * @ass1
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)",
            this.xcoord, this.ycoord);
    }


}
