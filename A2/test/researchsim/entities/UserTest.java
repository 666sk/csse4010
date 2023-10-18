package researchsim.entities;

import researchsim.logging.CollectEvent;
import researchsim.logging.MoveEvent;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.BadSaveException;
import researchsim.util.CoordinateOutOfBoundsException;
import researchsim.util.NoSuchEntityException;


import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User Dave;
    private User Matt;
    private Coordinate initCoord;
    private Coordinate destCoord;

    @Before
    public void setUp() {
        initCoord = new Coordinate(0, 0);
        destCoord = new Coordinate(2, 3);
        Dave = new User(initCoord, "Dave");
        Matt = new User(destCoord, "Dr Matthew D'Souza");
    }

    @After
    public void tearDown() throws Exception {
        ScenarioManager.getInstance().reset();
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

    @Test
    public void testGetName() {
        assertEquals("Incorrect name value is returned",
                "Dave", Dave.getName());
        assertEquals("Incorrect name value is returned",
                "Dr Matthew D'Souza", Matt.getName());
    }

    @Test
    public void testEncode() {
        assertEquals("Incorrect encode value is returned",
                "User-0,0-Dave", Dave.encode());
        assertEquals("Incorrect encode value is returned",
                "User-2,3-Dr Matthew D'Souza", Matt.encode());
    }

    @Test
    public void testHashCode() {
        assertEquals("Incorrect hashcode value is returned", true, Matt.hashCode()
                == Matt.hashCode());
        assertEquals("Incorrect hashcode value is returned", false, Matt.hashCode()
                == Dave.hashCode());
    }

    @Test
    public void testEquals() {
        User drMatt = new User(new Coordinate(2, 3),
                "Dr Matthew D'Souza");
        assertEquals("Incorrect boolean value is returned", true, drMatt
                .equals(Matt));
        assertEquals("Incorrect boolean value is returned", false, drMatt
                .equals(Dave));
        assertEquals("Incorrect boolean value is returned",
                false, Dave.equals("Dave"));
    }

    @Test
    public void testGetPossibleMoves() {
        Scenario safeScenario = createSafeTestScenario("safeScenario");
        List<Coordinate> PossibleMoves = new ArrayList<Coordinate>();
        List<Coordinate> DavesPossibleMoves = Dave.getPossibleMoves();

        for (int i = 1; i <= 3; i++) {
            PossibleMoves.add(new Coordinate(i, 0));
        }
        for (int j = 1; j <= 3; j++) {
            PossibleMoves.add(new Coordinate(0, j));
        }
        PossibleMoves.add(new Coordinate(1,1));
        PossibleMoves.add(new Coordinate(2,1));
        PossibleMoves.add(new Coordinate(1,2));

        assertEquals("Incorrect possible move value is returned",
                new HashSet<>(PossibleMoves),
                new HashSet<>(DavesPossibleMoves));
    }

    @Test
    public void testMove() {
        Scenario safeScenario = createSafeTestScenario("safeScenario");
        Tile[] safeMapGrid = ScenarioManager.getInstance().getScenario().getMapGrid();
        Coordinate moveCoord = new Coordinate(2,1);
        Coordinate collectCoord = new Coordinate(2,2);
        Fauna horse = new Fauna(Size.LARGE, collectCoord, TileType.LAND);
        int cIndex = collectCoord.getIndex();
        int mIndex = moveCoord.getIndex();
        safeMapGrid[cIndex].setContents(horse);
        try {
            ScenarioManager.getInstance().getScenario().setMapGrid(safeMapGrid);
        } catch (CoordinateOutOfBoundsException e) {
        }
        MoveEvent move = new MoveEvent(Dave, moveCoord);
        Dave.move(moveCoord);
        Tile[] safeMapGrid2 = ScenarioManager.getInstance().getScenario().getMapGrid();
        assertEquals("Incorrect move event", false, safeMapGrid2[0].hasContents());
        assertEquals("Incorrect move event", true, safeMapGrid2[mIndex].hasContents());
        assertEquals("moveEvent is incorrect", true, ScenarioManager.getInstance().getScenario()
                .getLog().getTilesTraversed() ==3);
        try {
            assertEquals("Incorrect move event", Dave, safeMapGrid2[mIndex].getContents());
        } catch (NoSuchEntityException e) {

        }
        Dave.move(collectCoord);
        CollectEvent collect = new CollectEvent(Dave, horse);
        assertEquals("collect is incorrect", true, ScenarioManager.getInstance().getScenario()
                .getLog().getEntitiesCollected() == 1);
        assertEquals("collect is incorrect", true, ScenarioManager.getInstance().getScenario()
                .getLog().getPointsEarned() == 3);

    }

    @Test
    public void testCanMove() throws CoordinateOutOfBoundsException {

        TileType[] tiles = new TileType[] {
                TileType.LAND, TileType.LAND, TileType.OCEAN, TileType.LAND, TileType.LAND,
                TileType.MOUNTAIN, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND
        };
        Scenario safeScenario = createSafeTestScenario("safeScenario", tiles);
        Coordinate maryCoord = new Coordinate(0,2);
        User Mary = new User(maryCoord,"Mary");
        assertEquals("Incorrect can move info", true, Dave.canMove(new Coordinate(1, 0)));
        assertEquals("Incorrect can move info", true, Mary.canMove(new Coordinate(4, 2)));
        assertEquals("Incorrect can move info", false, Dave.canMove(new Coordinate(2, 0)));
        assertEquals("Incorrect can move info", true, Dave.canMove(new Coordinate(1, 1)));
        assertEquals("Incorrect can move info", false, Dave.canMove(new Coordinate(0, 1)));
        assertEquals("Incorrect can move info", false, Dave.canMove(new Coordinate(2, 2)));
        assertEquals("Incorrect can move info", false, Dave.canMove(new Coordinate(3, 1)));
        assertEquals("Incorrect can move info", false, Matt.canMove(new Coordinate(2, 0)));
        assertEquals("Incorrect can move info", true, Matt.canMove(new Coordinate(4, 3)));
    }

    @Test (expected = CoordinateOutOfBoundsException.class)
    public void testCanMoveException() throws CoordinateOutOfBoundsException {
        Scenario safeScenario = createSafeTestScenario("safeScenario");
        Dave.canMove(new Coordinate(10, 0));
    }

    @Test
    public void testGetPossibleCollection() {
        TileType[] tiles = new TileType[] {
                TileType.LAND, TileType.LAND, TileType.OCEAN, TileType.LAND, TileType.LAND,
                TileType.MOUNTAIN, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND
        };
        Scenario safeScenario = createSafeTestScenario("safeScenario", tiles);
        Tile[] mapGrid = ScenarioManager.getInstance().getScenario().getMapGrid();
        Coordinate horseCoord = new Coordinate(3,3);
        Coordinate userCoord = new Coordinate(2,4);
        Fauna horse = new Fauna(Size.LARGE, horseCoord, TileType.LAND);
        User farmer = new User(userCoord,"Fred");
        mapGrid[horseCoord.getIndex()].setContents(horse);
        mapGrid[userCoord.getIndex()].setContents(farmer);
        try {
            ScenarioManager.getInstance().getScenario().setMapGrid(mapGrid);
        } catch (CoordinateOutOfBoundsException e) {
            ;
        }

        List<Coordinate> possibleCollect = new ArrayList<Coordinate>();
        possibleCollect.add(horseCoord);
        //System.out.println(Matt.getPossibleCollection());
        assertEquals("incorrect possible collection", true, new HashSet<>(possibleCollect)
                .equals(new HashSet<>(Matt.getPossibleCollection())));
        assertEquals("incorrect possible collection", false, new HashSet<>(possibleCollect)
                .equals(new HashSet<>(Dave.getPossibleCollection())));
    }

    @Test
    public void testCollect() {
        TileType[] tiles = new TileType[] {
                TileType.LAND, TileType.LAND, TileType.OCEAN, TileType.LAND, TileType.LAND,
                TileType.MOUNTAIN, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.OCEAN, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
                TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND
        };
        Scenario safeScenario = createSafeTestScenario("safeScenario", tiles);
        Tile[] mapGrid = ScenarioManager.getInstance().getScenario().getMapGrid();
        Coordinate horseCoord = new Coordinate(3,3);
        Coordinate crabCoord = new Coordinate(2,2);
        Coordinate userCoord = new Coordinate(2,4);
        Fauna horse = new Fauna(Size.LARGE, horseCoord, TileType.LAND);
        Fauna crab = new Fauna(Size.SMALL, crabCoord, TileType.OCEAN);
        User farmer = new User(userCoord,"Fred");
        mapGrid[horseCoord.getIndex()].setContents(horse);
        mapGrid[crabCoord.getIndex()].setContents(crab);
        mapGrid[userCoord.getIndex()].setContents(farmer);
        try {
            ScenarioManager.getInstance().getScenario().setMapGrid(mapGrid);
        } catch (CoordinateOutOfBoundsException e) {
            ;
        }

        CollectEvent collectEvent = new CollectEvent(Matt, horse);
        try {
            Matt.collect(horseCoord);
        } catch (Exception e) {

        }

        try {
            Matt.collect(crabCoord);
        } catch (Exception e) {

        }
        assertEquals("Invalid collect event", false, ScenarioManager.getInstance().getScenario()
                .getMapGrid()[horseCoord.getIndex()].hasContents());
        assertEquals("Invalid collect event", true, ScenarioManager.getInstance().getScenario()
                .getLog().getEvents().get(0).toString().equals(collectEvent.toString()));
        assertEquals("Invalid collect event", true, ScenarioManager.getInstance().getScenario()
                .getLog().getEntitiesCollected() == 2);
        assertEquals("Invalid collect event", true, ScenarioManager.getInstance().getScenario()
                .getLog().getPointsEarned() == 4);
    }

    @Test (expected = NoSuchEntityException.class)
    public void testCollectNoSuchEntityException() throws
            CoordinateOutOfBoundsException, NoSuchEntityException {
        Scenario safeScenario = createSafeTestScenario("safeScenario");
        Dave.collect(new Coordinate(1, 0));
    }

    @Test (expected = CoordinateOutOfBoundsException.class)
    public void testCollectCoordinateOutOfBoundsException() throws
            CoordinateOutOfBoundsException, NoSuchEntityException {
        Scenario safeScenario = createSafeTestScenario("safeScenario");
        Dave.collect(new Coordinate(10, 0));
    }
}