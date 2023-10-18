package researchsim.scenario;

import researchsim.map.Tile;
import java.util.*;

/**
 * A class representing a simulation of an environment and its inhabitants
 */
public class Scenario {

    /** The minimum dimensions of the map grid */
    public static final int MIN_SIZE = 5;

    /** The maximum dimensions of the map grid. */
    public static final int MAX_SIZE = 15;

    /** Maximum number of tiles that the grid contains */
    public static final int MAX_TILES = 225;

    /** scenario name */
    private String name;

    /** width of the board */
    private int width;

    /** height of the board */
    private int height;

    /** An array of tile storing map grid for the scenario */
    private Tile[] mapGrid;

    /**
     * Create a new Scenario with a given name, width, and height
     * Also create a 1D array of tiles
     * @param name Scenario name
     * @param width Width of the board
     * @param height Height of the board
     * @throws IllegalArgumentException Throw IllegalArgumentException
     * if width < 5 or width > 15 or height < 5 or height > 15 or name is null
     */
    public Scenario(String name,
                    int width,
                    int height)
            throws IllegalArgumentException {

        if (width < MIN_SIZE || width > MAX_SIZE
                || height < MIN_SIZE || height > MAX_SIZE
                || name == null) {

            throw new IllegalArgumentException();
        }

        this.name = name;
        this.width = width;
        this.height = height;
        mapGrid = new Tile[width * height];
    }

    /**
     * Return the name of the scenario
     * @return name The name of the scenario
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the map grid for this scenario
     * @return mapGrid A copy of map grid array
     */
    public Tile[] getMapGrid() {
        return Arrays.copyOf(mapGrid, getSize());
    }

    /**
     * Update the map grid for this scenario
     * @param map The new map
     */
    public void setMapGrid(Tile[] map) {
        mapGrid = Arrays.copyOf(map, map.length);
    }

    /**
     * Returns the size of the map in this scenario
     * @return size The map size
     */
    public int getSize() {
        int mapSize = this.height * this.width;
        return mapSize;
    }

    /**
     * Return the width of the map for this scenario
     * @return width The map width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Return the height of the map for this scenario
     * @return height The map height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Return the human-readable string representation of this scenario
     * @return human-readable string representation of this scenario
     */
    public String toString() {
        int numOfEntities = 0;

        //Find out how many entities in a map grid
        for (Tile i : mapGrid) {
            if (i.hasContents()) {
                numOfEntities++;
            }
        }

        return String.format("%s" + System.lineSeparator() + "Width: %d, Height: %d"
                        + System.lineSeparator() + "Entities: %d",
                getName(), getWidth(), getHeight(), numOfEntities);
    }
}

