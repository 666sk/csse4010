package researchsim.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.BadSaveException;
import researchsim.util.CoordinateOutOfBoundsException;

import java.util.Arrays;

import static org.junit.Assert.*;


public class CoordinateTest {

    private Coordinate coordinate11;
    private Coordinate coordinate11Dup;
    private Coordinate coordinate21;
    private Coordinate coordinateneg21;

    @Before
    public void setUp() throws Exception {
        coordinate11 = new Coordinate(1, 1);
        coordinate11Dup = new Coordinate(1, 1);
        coordinate21 = new Coordinate(2, 1);
        coordinateneg21 = new Coordinate(-2, -1);
    }

    @After
    public void tearDown() throws Exception {
        ScenarioManager.getInstance().reset();
    }

    @Test
    public void testDefaultConstructor() {
        Coordinate origin = new Coordinate();
        assertEquals("Incorrect value was returned.", 0, origin.getX());
        assertEquals("Incorrect value was returned.", 0, origin.getY());
    }

    @Test
    public void testIndexConstructor() {
        createSafeTestScenario("testIndexConstructor", 5, 5);
        Coordinate origin = new Coordinate(12);
        assertEquals("Incorrect value was returned.", 2, origin.getX());
        assertEquals("Incorrect value was returned.", 2, origin.getY());
    }

    @Test
    public void testGetX() {
        assertEquals("Incorrect value was returned.", 1, coordinate11.getX());
        assertEquals("Incorrect value was returned.", -2, coordinateneg21.getX());

    }

    @Test
    public void testGetAbsX() {
        assertEquals("Incorrect value was returned.", 1, coordinate11.getAbsX());
        assertEquals("Incorrect value was returned.", 2, coordinateneg21.getAbsX());
    }

    @Test
    public void testGetY() {
        assertEquals("Incorrect value was returned.", 1, coordinate11.getY());
        assertEquals("Incorrect value was returned.", -1, coordinateneg21.getY());
    }

    @Test
    public void testGetAbsY() {
        assertEquals("Incorrect value was returned.", 1, coordinate11.getAbsY());
        assertEquals("Incorrect value was returned.", 1, coordinateneg21.getAbsY());
    }

    @Test
    public void testGetIndex() {
        createSafeTestScenario("testGetIndex", 5, 5);
        assertEquals("Incorrect value was returned.",
            6, coordinate11.getIndex());
        assertEquals("Incorrect value was returned.",
            7, coordinate21.getIndex());
        assertEquals("Incorrect value was returned.",
            -7, coordinateneg21.getIndex());
    }

    @Test
    public void testIsInBounds() {
        createSafeTestScenario("testIsInBounds", 10, 10);
        assertTrue("Incorrect value was returned.", coordinate11.isInBounds());
        assertFalse("Incorrect value was returned.", coordinateneg21.isInBounds());
    }

    @Test
    public void testConvert() {
        createSafeTestScenario("testConvert", 10, 10);
        assertEquals("Incorrect value was returned.",
            55, Coordinate.convert(5, 5));
        createSafeTestScenario("testConvert2", 5, 7);
        assertEquals("Incorrect value was returned.",
            30, Coordinate.convert(5, 5));
    }

    @Test
    public void testToString() {
        assertEquals("Incorrect value was returned.",
            "(1,1)", coordinate11.toString());
        assertEquals("Incorrect value was returned.",
            "(0,0)", new Coordinate().toString());
        assertEquals("Incorrect value was returned.",
            "(-2,-1)", coordinateneg21.toString());
    }

    @Test
    public void testDecode() throws BadSaveException {
        assertEquals("Incorrect decode value was returned.",
                true, coordinate11.equals(Coordinate.decode("1,1")));
        assertEquals("Incorrect decode value was returned.",
                false, coordinate21.equals(Coordinate.decode("1,2")));
        assertEquals("Incorrect decode value was returned.",
                true, coordinateneg21.equals(Coordinate.decode("-2,-1")));
    }

    @Test (expected = BadSaveException.class)
    public void testDecodeException() throws BadSaveException{
        Coordinate coordinate1 = Coordinate.decode("1:2");
    }

    @Test (expected = BadSaveException.class)
    public void testDecodeException2() throws BadSaveException{
        Coordinate coordinate2 = Coordinate.decode("1,,2");
    }

    @Test (expected = BadSaveException.class)
    public void testDecodeException3() throws BadSaveException{
        Coordinate coordinate3 = Coordinate.decode("1,2,");
    }

    @Test (expected = BadSaveException.class)
    public void testDecodeException4() throws BadSaveException{
        Coordinate coordinate5 = Coordinate.decode(",-5");
    }

    @Test (expected = BadSaveException.class)
    public void testDecodeException5() throws BadSaveException{
        Coordinate coordinate5 = Coordinate.decode(",");
    }

    @Test (expected = BadSaveException.class)
    public void testDecodeException6() throws BadSaveException{
        Coordinate coordinate6 = Coordinate.decode("c,a");
    }

    @Test
    public void testDistance() {
        assertEquals("Incorrect distance value was returned.", new Coordinate(1, 0), coordinate11
                .distance(coordinate21));
        assertEquals("Incorrect distance value was returned.", new Coordinate(-3, -2), coordinate11Dup
                .distance(coordinateneg21));
        assertEquals("Incorrect distance value was returned.", new Coordinate(-4, -2), coordinate21
                .distance(coordinateneg21));
    }

    @Test
    public void testTranslate() {
        assertEquals("Incorrect coordinate translation", coordinate21, coordinate11.translate(
                1, 0));
        assertEquals("Incorrect coordinate translation", coordinateneg21, coordinate21.translate(
                -4, -2));
    }

    @Test
    public void testEncode() {
        assertEquals("Incorrect encode value was returned", "1,1", coordinate11.encode());
        assertEquals("Incorrect encode value was returned", "-2,-1", coordinateneg21.encode());
    }

    @Test
    public void testHashCode() {
        assertEquals("Incorrect hashcode value was returned", true, coordinate11
                .hashCode() == coordinate11Dup.hashCode());
        assertEquals("Incorrect hashcode value was returned", false, coordinateneg21
                .hashCode() == coordinate11Dup.hashCode());
    }

    @Test
    public void testEquals() {
        assertEquals("Incorrect value was returned", true,
                coordinate11.equals(coordinate11Dup));
        assertEquals("Incorrect value was returned", false,
                coordinate11.equals(coordinate21));
        assertEquals("Incorrect value was returned", false,
                coordinate11.equals("coordinate11"));
        assertEquals("Incorrect value was returned", false,
                coordinate11.equals("1,1"));
        assertEquals("Incorrect value was returned", false,
                coordinate11.equals("(1,1)"));
    }


    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has a 5x5 map of LAND. A Seed of 0.
     *
     * @param name of the scenario
     * @return generated scenario
     * @see #createSafeTestScenario(String, TileType[])
     */
    public static Scenario createSafeTestScenario(String name) {
        return createSafeTestScenario(name, new TileType[] {
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND
        });
    }

    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has a 5x5 map with the array of tiles based on the array provided. A
     * Seed of 0.
     *
     * @param name  of the scenario
     * @param tiles the map of the scenario
     * @return generated scenario
     * @see #createSafeTestScenario(String, TileType[], int, int)
     */
    public static Scenario createSafeTestScenario(String name, TileType[] tiles) {
        return createSafeTestScenario(name, tiles, 5, 5);
    }

    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has an n x m map with the array of LAND tiles. A
     * Seed of 0.
     *
     * @param name  of the scenario
     * @param width  the width of the scenario
     * @param height the height of the scenario
     * @return generated scenario
     * @see #createSafeTestScenario(String, TileType[], int, int)
     */
    public static Scenario createSafeTestScenario(String name, int width, int height) {
        int size = width * height;
        TileType[] tiles = new TileType[size];
        Arrays.fill(tiles,0,size,TileType.LAND);
        return createSafeTestScenario(name, tiles, width, height);
    }

    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has a n x m map with the array of tiles based on the array provided. A
     * Seed of 0.
     *
     * @param name   of the scenario
     * @param tiles  the map of the scenario
     * @param width  the width of the scenario
     * @param height the height of the scenario
     * @return generated scenario
     */
    public static Scenario createSafeTestScenario(String name, TileType[] tiles,
                                                  int width, int height) {
        Scenario s = new Scenario(name, width, height, 0);
        Tile[] map = Arrays.stream(tiles).map(Tile::new).toArray(Tile[]::new);
        try {
            s.setMapGrid(map);
        } catch (CoordinateOutOfBoundsException error) {
            fail("Failed to update a scenario map for test: " + name + "\n "
                + error.getMessage());
        }
        ScenarioManager.getInstance().addScenario(s);
        try {
            ScenarioManager.getInstance().setScenario(name);
        } catch (BadSaveException error) {
            fail("Failed to update a scenario map for test: " + name + "\n "
                + error.getMessage());
        }
        return s;
    }
}