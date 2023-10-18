package researchsim.map;

import org.junit.Before;
import org.junit.Test;
import researchsim.entities.Fauna;
import researchsim.entities.Size;
import researchsim.util.NoSuchEntityException;

import static org.junit.Assert.assertEquals;

public class TileTest {

    /** Land Tile to be tested without contents */
    private Tile landTile;

    /** Ocean Tile to be tested with contents (crab) */
    private Tile oceanTile;

    /** A default coordinate to be tested for oceanTile */
    private Coordinate coordinate;

    /** A sample fauna to be tested for oceanTile */
    private Fauna crab;

    @Before
    public void setup() {
        landTile = new Tile(TileType.LAND);
        oceanTile = new Tile(TileType.OCEAN);
        coordinate = new Coordinate();
        crab = new Fauna(Size.SMALL, coordinate, TileType.OCEAN);
        oceanTile.setContents(crab);
    }

    @Test
    public void testGetLandType() {
        assertEquals("getType() gets the wrong type",
                TileType.LAND, landTile.getType());
    }

    @Test
    public void testGetOceanType() {
        assertEquals("getType() gets the wrong type",
                TileType.OCEAN, oceanTile.getType());
    }

    @Test(expected = NoSuchEntityException.class)
    public void testGetContents() throws NoSuchEntityException {
        landTile.getContents();
    }

    @Test
    public void testSetContents() throws NoSuchEntityException {
        assertEquals("setContents() sets the wrong content",
                "Crab", oceanTile.getContents().getName());
    }

    @Test
    public void testHasNoContents() {
        assertEquals("Method hasContents not returning true for tile with entity",
                false, landTile.hasContents());
    }

    @Test
    public void testHasContents() {
        assertEquals("Method hasContents not returning true for tile with entity",
                true, oceanTile.hasContents());
    }
}