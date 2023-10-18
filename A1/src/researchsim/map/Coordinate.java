package researchsim.map;

import researchsim.scenario.*;

/**
 * A class representing the coordinate of a tile.
 */
public class Coordinate {

    /** Horizontal position of the tile */
    private Integer xcoord = null;

    /** Vertical position of the tile */
    private Integer ycoord = null;

    /** Index of the tile */
    private Integer index = null;

    /**
     * Create a new coordinate at the top left position (0,0), index 0 (zero)
     */
    public Coordinate() {
        this.xcoord = 0;
        this.ycoord = 0;
        this.index = 0;
    }

    /**
     * Create a new coordinate at the specified index
     * @param index The index in the tile grid
     */
    public Coordinate(int index) {
        this.index = index;
    }

    /**
     * Create a new coordinate at the specified index
     * @param xcoord The position in the horizontal plane
     * @param ycoord The position in the vertical plane
     */
    public Coordinate(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * Get the position in the horizontal plane (Left-Right)
     * @return this.xcoord The horizontal position
     */
    public int getX() {
        if (this.xcoord == null) {

            //Converting index into x coordinate
            this.xcoord = this.index % ScenarioManager.getInstance()
                    .getScenario().getWidth();
        }

        return this.xcoord;
    }

    /**
     * Get the position in the vertical plane (Up-Down)
     * @return this.ycoord The vertical position
     */
    public int getY() {
        if (this.ycoord == null) {

            //Converting index into y coordinate
            this.ycoord = this.index / ScenarioManager.getInstance()
                    .getScenario().getWidth();
        }

        return this.ycoord;
    }

    /**
     * Get the index in the tile grid of this coordinate
     * @return this.index The grid index
     */
    public int getIndex() {
        if (this.index == null) {

            //Converting coordinates back to index
            this.index = convert(this.xcoord, this.ycoord);
        }

        return this.index;
    }

    /**
     * Determine if the coordinate in the bounds of the current scenario map
     * @return boolean representation True if coordinate is in the bounds, false otherwise
     */
    public boolean isInBounds() {
        if (0 <= getX()
                && getX() < ScenarioManager.getInstance().getScenario().getWidth()
                && 0 <= getY()
                && getY() < ScenarioManager.getInstance().getScenario().getHeight()) {

            return true;
        } else {

            return false;
        }
    }

    /**
     * Utility method to convert an(x,y) integer pair to an array index location
     * @param xcoord The x portion of a coordinate
     * @param ycoord The y portion of a coordinate
     * @return The converted index
     */
    public static int convert(int xcoord, int ycoord) {
        return xcoord + ScenarioManager.getInstance().getScenario().getWidth() * ycoord;
    }

    /**
     * Return the human-readable string representation of this coordinate
     * Format: (x,y)
     * @return Human-readable string representation of this coordinate
     */
    public String toString() {
        return String.format("(%d,%d)", getX(), getY());
    }

}
