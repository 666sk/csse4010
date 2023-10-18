package researchsim.map;

import researchsim.util.NoSuchEntityException;
import researchsim.entities.Entity;

/**
 * A class of tile on the scenario map that entities' items operate on
 */
public class Tile {

    /** The type of the tile */
    private TileType tileType;

    /** The contents of the tile */
    private Entity tileEntity;


    /**
     * Creates a new tile with a given type
     * @param type Tile type
     */
    public Tile(TileType type) {
        tileType = type;
        tileEntity = null;
    }

    /**
     * Return the type of the tile.
     * @return The type of the tile
     */
    public TileType getType() {
        return tileType;
    }

    /**
     * Returns the contents of the tile
     * @return tileEntity Tile contents
     * @throws NoSuchEntityException The exception to no such entity to be thrown
     */
    public Entity getContents() throws NoSuchEntityException {
        if (!hasContents()) {

            throw new NoSuchEntityException();
        } else {

            return tileEntity;
        }
    }

    /**
     * Update the contents of the tile
     * @param item New tile contents (entity)
     */
    public void setContents(Entity item) {
        tileEntity = item;
    }

    /**
     * Checks if the tile is currently occupied
     * @return boolean True if occupied (has contents), else false
     */
    public boolean hasContents() {
        if (tileEntity == null) {

            return false;
        } else {

            return true;
        }
    }
}
