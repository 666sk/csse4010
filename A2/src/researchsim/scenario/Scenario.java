package researchsim.scenario;

import researchsim.entities.*;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.logging.Logger;
import researchsim.util.CoordinateOutOfBoundsException;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;
import java.util.regex.*;
import java.util.*;
import researchsim.util.BadSaveException;
import researchsim.util.CoordinateOutOfBoundsException;
import researchsim.util.Encodable;
import researchsim.util.NoSuchEntityException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;


/**
 * The scenario is the overriding class of the simulation.
 * It is similar to a level in a video game.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 */
public class Scenario implements Encodable {

    /**
     * The minimum dimensions of the map grid.
     * The value of this constant is {@value}
     *
     * @ass1
     */
    public static final int MIN_SIZE = 5;
    /**
     * The maximum dimensions of the map grid.
     * The value of this constant is {@value}
     *
     * @ass1
     */
    public static final int MAX_SIZE = 15;
    /**
     * Maximum number of tiles that the grid contains.
     * The value of this constant is {@value}
     *
     * @ass1
     */
    public static final int MAX_TILES = MAX_SIZE * MAX_SIZE;
    /**
     * The name of this scenario.
     */
    private final String name;
    /**
     * The width of the map in the scenario.
     */
    private final int width;
    /**
     * The height of the map in the scenario.
     */
    private final int height;
    /**
     * The tile grid for this scenario.
     */
    private Tile[] mapGrid;
    /**
     * The seed of the map in the scenario
     */
    private int seed;

    /**
     * An instance of Random in the scenario
     */
    private Random random;

    /**
     * The AnimalController to facilitate the movement of animals in the scenario
     */
    private AnimalController animalcontroller;

    /**
     * The game log to log any events in the scenario
     */
    private Logger logger;

    /**
     * Creates a new Scenario with a given name, width, height and random seed. <br>
     * A one dimensional (1D) array of tiles is created as the board with the given width and
     * height. <br>
     * An empty Animal Controller and logger is also initialised. <br>
     * An instance of the {@link Random} class in initialised with the given seed.
     *
     * @param name   scenario name
     * @param width  width of the board
     * @param height height of the board
     * @param seed   the random seed for this scenario
     * @throws IllegalArgumentException if width &lt; {@value Scenario#MIN_SIZE} or width &gt;
     *                                  {@value Scenario#MAX_SIZE} or height
     *                                  &lt; {@value Scenario#MIN_SIZE} or height &gt;
     *                                  {@value Scenario#MAX_SIZE} or seed &lt; 0 or name is {@code
     *                                  null}
     * @ass1_partial
     * @see Random (<a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Random.html">Link</a>)
     */
    public Scenario(String name, int width, int height, int seed)
        throws IllegalArgumentException {
        if (width > MAX_SIZE || width < MIN_SIZE) {
            throw new IllegalArgumentException("The given width does not conform to the "
                + "requirement: " + MIN_SIZE + " <= width <= " + MAX_SIZE + ".");
        }
        if (height > MAX_SIZE || height < MIN_SIZE) {
            throw new IllegalArgumentException("The given height does not conform to the "
                + "requirement: " + MIN_SIZE + " <= height <= " + MAX_SIZE + ".");
        }
        if (name == null) {
            throw new IllegalArgumentException("The given name does not conform to the "
                + "requirement: name != null.");
        }
        this.name = name;
        this.width = width;
        this.height = height;
        this.mapGrid = new Tile[width * height];
        this.animalcontroller = new AnimalController();
        this.random = new Random(seed);
        this.logger = new Logger();
    }

    /**
     * Returns the name of the scenario.
     *
     * @return scenario name
     * @ass1
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the map grid for this scenario.
     * <p>
     * Adding or removing elements from the returned array should not affect the original array.
     *
     * @return map grid
     * @ass1
     */
    public Tile[] getMapGrid() {
        return Arrays.copyOf(mapGrid, getSize());
    }

    /**
     * Updates the map grid for this scenario.
     * <p>
     * Adding or removing elements from the array that was passed should not affect the class
     * instance array.
     *
     * @param map the new map
     * @throws CoordinateOutOfBoundsException (param) map length != size of current scenario map
     * @ass1_partial
     */
    public void setMapGrid(Tile[] map) throws CoordinateOutOfBoundsException {
        if (map.length != this.getSize()) {
            throw new CoordinateOutOfBoundsException();
        }
        mapGrid = Arrays.copyOf(map, getSize());
    }


    /**
     * Returns the width of the map for this scenario.
     *
     * @return map width
     * @ass1
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the map for this scenario.
     *
     * @return map height
     * @ass1
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the size of the map in the scenario.<br>
     * The size of a map is the total number of tiles in the Tile array.
     *
     * @return map size
     * @ass1
     */
    public int getSize() {
        return width * height;
    }

    /**
     * Returns the scenarios random instance.
     * @return random
     */
    public Random getRandom() {
        return this.random;
    }

    /**
     * Returns the scenario's activity log.
     * @return game log
     */
    public Logger getLog() {
        return this.logger;
    }

    /**
     * Returns the scenarios animal manager.
     * @return animal manager
     */
    public AnimalController getController() {
        return this.animalcontroller;
    }


    /**
     * Determine if the basic construction information of a scenario is correct
     * if correct, obtain the info
     * @param lineNum the number of line which is currently reading
     * @param line the contents of the line
     * @return the basic construction information of a scenario
     * @throws BadSaveException if the contents read not in format
     */
    private static int testBasicInfo(int lineNum, String line) throws BadSaveException {
        //The value read from file in string format
        String strInfo = null;
        //The value read from file in integer format
        int intInfo = 0;

        //Determine if the given message is in format using RE
        if (lineNum == 2) {
            Pattern pattern = Pattern.compile("(Width:)(\\d+|-1)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                strInfo = matcher.group(2);
            } else {
                throw new BadSaveException();
            }
        } else if (lineNum == 3) {

            Pattern pattern = Pattern.compile("(Height:)(\\d+|-1)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                strInfo = matcher.group(2);
            } else {
                throw new BadSaveException();
            }
        } else if (lineNum == 4) {

            Pattern pattern = Pattern.compile("(Seed:)(\\d+|-1)");
            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                strInfo = matcher.group(2);
            } else {
                throw new BadSaveException();
            }
        }

        try {
            intInfo = Integer.parseInt(strInfo);
        } catch (NumberFormatException e) {
            throw new BadSaveException();
        }

        if (intInfo == -1) {
            intInfo = Scenario.MIN_SIZE;
        }

        return intInfo;
    }

    /**
     * Determine if the separator has exactly the Width value
     * @param line the contents of the line
     * @param scenarioWidth the width of the current scenario
     * @throws BadSaveException if the separator is not in format
     */
    private static void testSeparator(String line, int scenarioWidth) throws BadSaveException {
        if (line.length() != scenarioWidth) {
            throw new BadSaveException();
        }
        for (int i = 0; i < scenarioWidth; i++) {
            if (line.charAt(i) != '=') {
                throw new BadSaveException();
            }
        }

    }

    /**
     * Determine if the number of characters on the line is exactly the width value
     * And if the characters number is same as the sceenario size
     * @param line the contents of the line
     * @param scenarioWidth the width of the current scenario
     * @param totalTiles the total characters (tiles) in the scenario
     * @return A list of tiles containing all the tiles
     * @throws BadSaveException if the characters are not in format
     */
    private static List<Tile> testMap(String line, int scenarioWidth,
                                      List<Tile> totalTiles) throws BadSaveException {

        if (line.length() != scenarioWidth) {
            throw new BadSaveException("Usage: Invalid tile number");
        }
        for (int i = 0; i < scenarioWidth; i++) {
            totalTiles.add(new Tile(TileType.decode(String.valueOf(line.charAt(i)))));
        }

        return totalTiles;
    }

    /**
     * Set the map grid of the current scenario
     * @param totalTiles the list of total tiles to construct the scenario
     * @param scenarioWidth the width of the scenario
     * @param scenarioHeight the height of the scenario
     * @param scenario the current scenario
     * @return the map grid of the current scenario
     * @throws BadSaveException if the map grid is not in format
     */
    private static Tile[] setMap(List<Tile> totalTiles, int scenarioWidth,
                                 int scenarioHeight, Scenario scenario)
            throws BadSaveException {
        Tile[] mapGrid = totalTiles.toArray(new Tile[scenarioWidth * scenarioHeight]);
        try {
            scenario.setMapGrid(mapGrid);
        } catch (CoordinateOutOfBoundsException e) {
            throw new BadSaveException("Usage: invalid map grid!");
        }
        ScenarioManager.getInstance().addScenario(scenario);
        return mapGrid;
    }

    /**
     * Determine if the entity information read is in required pattern using RE
     * @param line the contents of the line
     * @return the entity to set into the scenario
     * @throws BadSaveException if the entity information is not in format
     */
    private static Entity testEntity(String line) throws BadSaveException {
        Tile[] mapGrid = ScenarioManager.getInstance().getScenario().getMapGrid();

        Pattern faunaPattern = Pattern.compile("(Fauna)"
                + "(-)(SMALL|MEDIUM|LARGE|GIANT)(-)((\\d+),(\\d+))(-)(LAND|OCEAN)");
        Matcher faunaMatcher = faunaPattern.matcher(line);

        Pattern floraPattern = Pattern.compile(
                "(Flora)(-)(SMALL|MEDIUM|LARGE|GIANT)(-)((\\d+),(\\d+))");
        Matcher floraMatcher = floraPattern.matcher(line);

        Pattern userPattern = Pattern.compile("(User)(-)((\\d+),(\\d+))(-)(.+)");
        Matcher userMatcher = userPattern.matcher(line);

        Entity entity = null;

        if (faunaMatcher.matches()) {
            entity = testFauna(faunaMatcher, mapGrid);
        } else if (floraMatcher.matches()) {
            entity = testFlora(floraMatcher, mapGrid);
        } else if (userMatcher.matches()) {
            entity = testUser(userMatcher, mapGrid);
        } else {
            throw new BadSaveException("Usage: Invalid pattern!");
        }

        return entity;
    }

    /**
     * If the line starts with "Fauna", determine if it is in format
     * @param faunaMatcher the matcher pattern for fauna info
     * @param mapGrid the map grid of the current scenario
     * @return the Fauna entity that is in format
     * @throws BadSaveException if the Fauna entity is not in format
     */
    private static Entity testFauna(Matcher faunaMatcher, Tile[] mapGrid) throws BadSaveException {

        Coordinate coordinate = Coordinate.decode(faunaMatcher.group(5));
        String size = faunaMatcher.group(3);
        String tileType = faunaMatcher.group(9);

        if (mapGrid[coordinate.getIndex()].hasContents()) {
            throw new BadSaveException("Usage: Tile already assigned entity!");
        }

        Size faunaSize;
        try {
            faunaSize = Size.valueOf(size);
        } catch (Exception e) {
            throw new BadSaveException("Usage: Invalid size of entity!");
        }

        TileType faunaHabitat;
        try {
            faunaHabitat = TileType.valueOf(tileType);
        } catch (Exception e) {
            throw new BadSaveException("Usage: Invalid fauna TileType!");
        }

        if (faunaHabitat == TileType.OCEAN && mapGrid[coordinate.getIndex()].getType()
                != TileType.OCEAN) {
            throw new BadSaveException("Usage: Invalid fauna TileType!");
        } else if (faunaHabitat == TileType.LAND && mapGrid[coordinate.getIndex()].getType()
                == TileType.OCEAN) {
            throw new BadSaveException("Usage: Invalid fauna TileType!");
        }

        try {
            return new Fauna(faunaSize, coordinate, faunaHabitat);
        } catch (Exception e) {
            ;
        }

        return null;
    }


    /**
     *  If the line starts with "Flora", determine if it is in format
     * @param floraMatcher the matcher pattern for the flora info
     * @param mapGrid the map grid of the current scenario
     * @return the Flora entity that is in format
     * @throws BadSaveException if the flora info is not in format
     */
    private static Entity testFlora(Matcher floraMatcher, Tile[] mapGrid) throws BadSaveException {

        Coordinate coordinate = Coordinate.decode(floraMatcher.group(5));
        String size = floraMatcher.group(3);
        Size floraSize;

        if (mapGrid[coordinate.getIndex()].hasContents()) {
            throw new BadSaveException("Usage: Tile already assigned entity!");
        }

        try {
            floraSize = Size.valueOf(size);
        } catch (Exception e) {
            throw new BadSaveException("Usage: Invalid size of entity!");
        }

        if (mapGrid[coordinate.getIndex()].getType() == TileType.OCEAN) {
            throw new BadSaveException("Usage: Invalid flora TileType!");
        }

        return new Flora(floraSize, coordinate);
    }

    /**
     * If the line starts with "User", determine if it is in format
     * @param userMatcher the matcher pattern for the user info
     * @param mapGrid the map grid of the scenario
     * @return the user entity what is in format
     * @throws BadSaveException if the user info is not in format
     */
    private static Entity testUser(Matcher userMatcher, Tile[] mapGrid) throws BadSaveException {

        Coordinate coordinate = Coordinate.decode(userMatcher.group(3));
        String name = userMatcher.group(7);

        if (mapGrid[coordinate.getIndex()].hasContents()) {
            throw new BadSaveException("Usage: Tile already assigned entity!");
        }

        if (mapGrid[coordinate.getIndex()].getType() == TileType.OCEAN || mapGrid[coordinate
                .getIndex()].getType() == TileType.MOUNTAIN) {
            throw new BadSaveException("Usage: Invalid user TileType!");
        }

        return new User(coordinate, name);
    }


    /**
     * Creates a Scenario instance by reading information from the given reader.
     * @param reader reader from which to load all info (will not be null)
     * @return scenario created by reading from the given reader
     * @throws IOException - if an IOException is encountered when reading from the reader
     * @throws BadSaveException - if the reader contains a line that does not adhere to
     * the rules above (thus indicating that the contents of the reader are invalid)
     */
    public static Scenario load(Reader reader) throws IOException, BadSaveException {

        BufferedReader bufferedReader = new BufferedReader(reader);
        String line = bufferedReader.readLine();
        if (line == null) {
            throw new BadSaveException("Usage: Reader is empty!");
        }

        int lineNum = 0;
        String scenarioName = null;
        int scenarioWidth = 0;
        int scenarioHeight = 0;
        int scenarioSeed = 0;
        Scenario scenario = null;
        List<Tile> totalTiles = new ArrayList<Tile>();
        Tile[] mapGrid = null;

        while (line != null) {
            lineNum++;

            if (lineNum == 1) {
                scenarioName = line;
            } else if (lineNum == 2) {
                scenarioWidth = testBasicInfo(lineNum, line);
            } else if (lineNum == 3) {
                scenarioHeight = testBasicInfo(lineNum, line);
            } else if (lineNum == 4) {
                scenarioSeed = testBasicInfo(lineNum, line);
                try {
                    scenario = new Scenario(scenarioName, scenarioWidth,
                            scenarioHeight, scenarioSeed);
                } catch (IllegalArgumentException e) {
                    throw new BadSaveException("Failed to load this scenario!");
                }
            } else if (lineNum == 5) {
                testSeparator(line, scenarioWidth);
            } else if ((lineNum > 5) && (lineNum <= (scenarioHeight + 5))) {
                totalTiles = testMap(line, scenarioWidth, totalTiles);
                if (lineNum == (scenarioHeight + 5)) {
                    mapGrid = setMap(totalTiles, scenarioWidth, scenarioHeight, scenario);
                }
            } else if (lineNum == (scenarioHeight + 6)) {
                testSeparator(line, scenarioWidth);
            } else if (lineNum > (scenarioHeight + 6)) {    //entity
                Entity entity = testEntity(line);
                mapGrid[entity.getCoordinate().getIndex()].setContents(entity);
                if (entity instanceof Fauna) {
                    scenario.animalcontroller.addAnimal((Fauna) entity);
                }
            }

            line = bufferedReader.readLine();
        }
        try {
            scenario.setMapGrid(mapGrid);
        } catch (CoordinateOutOfBoundsException e) {
            throw new BadSaveException();
        }
        ScenarioManager.getInstance().addScenario(scenario);

        return scenario;
    }


    /**
     * Returns the hash code of this scenario.
     * @return hash code of this scenario.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.width, this.height, this.mapGrid);
    }

    /**
     * Returns true if and only if this scenario is equal to the other given object.
     * @param other - the reference object with which to compare
     * @return true if this scenario is the same as the other argument; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Scenario)) {
            return false;
        }

        if (!(((Scenario) other).name.equals(this.name)
                || ((Scenario) other).width == this.width
                ||  ((Scenario) other).height == this.height)) {
            return false;
        }

        //Determine if the map contents are equal
        for (int i = 0; i < this.getMapGrid().length; i++) {
            if (!this.getMapGrid()[i].equals(((Scenario) other).getMapGrid()[i])) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the human-readable string representation of this scenario.
     * <p>
     * The format of the string to return is:
     * <pre>
     *     (name)
     *     Width: (width), Height: (height)
     *     Entities: (entities)
     * </pre>
     * Where:
     * <ul>
     *   <li>{@code (name)} is the scenario's name</li>
     *   <li>{@code (width)} is the scenario's width</li>
     *   <li>{@code (height)} is the scenario's height</li>
     *   <li>{@code (entities)} is the number of entities currently on the map in the scenario</li>
     * </ul>
     * For example:
     *
     * <pre>
     *     Beach retreat
     *     Width: 6, Height: 5
     *     Entities: 4
     * </pre>
     * <p>
     * Each line should be separated by a system-dependent line separator.
     *
     * @return human-readable string representation of this scenario
     * @ass1
     */
    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        result.add(name);
        result.add(String.format("Width: %d, Height: %d", width, height));
        result.add(String.format("Entities: %d",
            Arrays.stream(mapGrid).filter(Objects::nonNull).filter(Tile::hasContents).count()));
        return result.toString();
    }

    /**
     * Returns the machine-readable string representation of this Scenario.
     * @specified by encode in interface Encodable
     * @return encoded string representation of this Scenario
     */
    public String encode() {
        StringJoiner scenario = new StringJoiner(System.lineSeparator());

        scenario.add(this.name);
        scenario.add(String.format("Width:%d", this.width));
        scenario.add(String.format("Height:%d", this.height));
        scenario.add(String.format("Seed:%d", this.seed));
        scenario.add("=".repeat(this.width));

        for (int i = 0; i < this.height; i++) {
            StringBuilder horizTiles = new StringBuilder(this.width);
            for (int j = 0; j < this.width; j++) {
                horizTiles.append(this.mapGrid[i * this.width + j].getType().encode());
            }
            scenario.add(horizTiles);
        }

        scenario.add("=".repeat(this.width));

        //Go through all the tiles in the scenario to see if there's entity on a tile
        for (int i = 0; i < this.getSize(); i++) {
            Tile curTile = this.mapGrid[i];
            if (curTile.hasContents()) {
                try {
                    scenario.add(curTile.getContents().encode());
                } catch (NoSuchEntityException e) {
                    ;
                }
            }
        }

        return scenario.toString();
    }
}
