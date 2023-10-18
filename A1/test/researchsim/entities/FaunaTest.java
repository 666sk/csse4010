package researchsim.entities;

import org.junit.Before;
import org.junit.Test;
import researchsim.map.*;

import static org.junit.Assert.assertEquals;

public class FaunaTest {

    private Coordinate coordinate;

    private Fauna mouse;
    private Fauna dog;
    private Fauna horse;
    private Fauna elephant;

    private Fauna crab;
    private Fauna fish;
    private Fauna shark;
    private Fauna whale;

    @Before
    public void setup() {
        coordinate = new Coordinate();

        mouse = new Fauna(Size.SMALL, coordinate, TileType.LAND);
        dog = new Fauna(Size.MEDIUM, coordinate, TileType.LAND);
        horse = new Fauna(Size.LARGE, coordinate, TileType.LAND);
        elephant = new Fauna(Size.GIANT, coordinate, TileType.LAND);

        crab = new Fauna(Size.SMALL, coordinate, TileType.OCEAN);
        fish = new Fauna(Size.MEDIUM, coordinate, TileType.OCEAN);
        shark = new Fauna(Size.LARGE, coordinate, TileType.OCEAN);
        whale = new Fauna(Size.GIANT, coordinate, TileType.OCEAN);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSandFauna() {
        Fauna sandFauna = new Fauna(Size.SMALL, coordinate, TileType.SAND);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMountainFauna(){
        Fauna mountainFauna = new Fauna(Size.SMALL, coordinate, TileType.MOUNTAIN);
    }

    @Test
    public void testOceanHabitat() {
        assertEquals("getHabitat() gets the wrong tile type!", TileType.OCEAN,
                crab.getHabitat());
    }

    @Test
    public void testLandHabitat() {
        assertEquals("getHabitat() gets the wrong tile type!", TileType.LAND,
                mouse.getHabitat());
    }

    @Test
    public void testGetNameMouse() {
        assertEquals("getName() gets the wrong name!", "Mouse",
                mouse.getName());
    }

    @Test
    public void testGetNameDog() {
        assertEquals("getName() gets the wrong name!", "Dog",
                dog.getName());
    }

    @Test
    public void testGetNameHorse() {
        assertEquals("getName() gets the wrong name!", "Horse",
                horse.getName());
    }

    @Test
    public void testGetNameElephant() {
        assertEquals("getName() gets the wrong name!", "Elephant",
                elephant.getName());
    }

    @Test
    public void testGetNameCrab() {
        //Fauna crab = new Fauna(Size.SMALL, coordinate, TileType.OCEAN);
        assertEquals("getName() gets the wrong name!", "Crab",
                crab.getName());
    }

    @Test
    public void testGetNameFish() {
        assertEquals("getName() gets the wrong name!", "Fish",
                fish.getName());
    }

    @Test
    public void testGetNameShark() {
        assertEquals("getName() gets the wrong name!", "Shark",
                shark.getName());
    }

    @Test
    public void testGetNameWhale() {
        assertEquals("getName() gets the wrong name!", "Whale",
                whale.getName());
    }

    @Test
    public void testToStringMouse() {
        assertEquals("toString() gets the wrong string!", "Mouse [Fauna] at (0,0) [LAND]",
                mouse.toString());
    }

    @Test
    public void testToStringCrab() {
        assertEquals("toString() gets the wrong string!", "Crab [Fauna] at (0,0) [OCEAN]",
                crab.toString());
    }
}