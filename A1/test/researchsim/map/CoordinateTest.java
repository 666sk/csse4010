package researchsim.map;

import org.junit.Before;
import org.junit.Test;
import researchsim.scenario.*;

import static org.junit.Assert.assertEquals;

public class CoordinateTest {

    /** coordinate1 constructed by the constructor with no parameters */
    private Coordinate coordinate1;

    /** coordinate2 constructed by the constructor with X and Y parameters */
    private Coordinate coordinate2;

    /** coordinate3 constructed by the constructor with Tile index parameters */
    private Coordinate coordinate3;

    /** scenario manager used to test coordinate */
    private ScenarioManager scenarioManager;

    /** scenario used to test coordinates */
    private Scenario scenario;

    @Before
    public void setup() {
        coordinate1 = new Coordinate();
        coordinate2 = new Coordinate(8, 7);
        coordinate3 = new Coordinate(64);
        scenarioManager = ScenarioManager.getInstance();
        scenario = new Scenario("scenario", 10,8);
        scenarioManager.addScenario(scenario);
    }

    @Test
    public void test1GetX() {
        assertEquals("getX() gets the wrong X value",
                0, coordinate1.getX());
    }

    @Test
    public void test2GetX() {
        assertEquals("getX() gets the wrong X value",
                8, coordinate2.getX());
    }

    @Test
    public void test3GetX() {
        assertEquals("getX() gets the wrong X value",
                4, coordinate3.getX());
    }

    @Test
    public void test1getY() {
        assertEquals("getY() gets the wrong Y value",
                0, coordinate1.getY());
    }

    @Test
    public void test2getY() {
        assertEquals("getY() gets the wrong Y value",
                7, coordinate2.getY());
    }

    @Test
    public void test3getY() {
        assertEquals("getY() gets the wrong Y value",
                6, coordinate3.getY());
    }

    @Test
    public void test1getIndex() {
        assertEquals("getIndex() gets the wrong index value",
                0, coordinate1.getIndex());
    }

    @Test
    public void test2getIndex() {
        assertEquals("getIndex() gets the wrong index value",
                78, coordinate2.getIndex());
    }

    @Test
    public void test3getIndex() {
        assertEquals("getIndex() gets the wrong index value",
                64, coordinate3.getIndex());
    }

    @Test
    public void testIsInBounds() {
        assertEquals("isInBounds() cannot determine if the coordinate is in bounds",
                true, coordinate2.isInBounds());

        assertEquals("isInBounds() cannot determine if the coordinate is in bounds",
                true, coordinate3.isInBounds());
    }

    @Test
    public void testNotInBounds() {
        Coordinate coordinate4 = new Coordinate(11, 11);
        assertEquals("isInBounds() cannot determine if the coordinate is in bounds",
                false, coordinate4.isInBounds());

        Coordinate coordinate5 = new Coordinate(101);
        assertEquals("isInBounds() cannot determine if the coordinate is in bounds",
                false, coordinate5.isInBounds());
    }

    @Test
    public void testConvert() {
        assertEquals("convert() cannot convert X and Y to an array index location",
                92, coordinate1.convert(2, 9));
    }

    @Test
    public void test1ToString() {
        assertEquals("ToString() cannot return the string correctly",
                "(0,0)", coordinate1.toString());
    }

    @Test
    public void test2ToString() {
        assertEquals("ToString() cannot return the string correctly",
                "(8,7)", coordinate2.toString());
    }

    @Test
    public void test3ToString() {
        assertEquals("ToString() cannot return the string correctly",
                "(4,6)", coordinate3.toString());
    }
}