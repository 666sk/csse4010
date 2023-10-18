package researchsim.map;

import researchsim.entities.Entity;
import researchsim.util.NoSuchEntityException;
import java.util.Objects;

/**
 * A tile on the scenario map that entities' items operate on.
 *
 * @ass1_partial
 * @ass1_test
 */
public class Tile {

    /**
     * The type of the tile
     */
    private final TileType type;
    /**
     * The entity that is occupying the tile (if any)
     */
    private Entity contents;

    /**
     * Creates a new tile with a given type.
     * Every new tile is initialised as unoccupied (empty).
     *
     * @param type tile type
     * @ass1
     */
    public Tile(TileType type) {
        this.type = type;
        this.contents = null;
    }

    /**
     * Returns the type of the tile.
     *
     * @return tile type
     * @ass1
     */
    public TileType getType() {
        return type;
    }

    /**
     * Returns the contents of the tile.
     *
     * @return tile contents
     * @throws NoSuchEntityException if the tile is empty
     * @ass1
     */
    public Entity getContents() throws NoSuchEntityException {
        if (!hasContents()) {
            throw new NoSuchEntityException("Attempted to get the entity at an empty Tile.");
        }
        return contents;
    }

    /**
     * Updates the contents of the tile.
     *
     * @param item new tile contents
     * @ass1
     */
    public void setContents(Entity item) {
        this.contents = item;
    }

    /**
     * Checks if the tile is currently occupied.
     *
     * @return true if occupied (has contents), else false
     * @ass1
     */
    public boolean hasContents() {
        return contents != null;
    }

    /**
     * Returns the hash code of this tile.
     * @return hash code of this tile.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.contents);
    }

    /**
     * Returns true if and only if this tile is equal to the other given tile.
     * @param other - the reference object with which to compare
     * @return true if this tile is the same as the other argument; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (! (other instanceof Tile)) {
            return false;
        }

        Boolean sameTile = true;
        try {
            sameTile = ((Tile) other).getContents().equals(this.getContents());
        } catch (NoSuchEntityException e) {
            ;
        }
        if (sameTile) {
            sameTile = ((Tile) other).getType().equals(this.getType());
        }

        return sameTile;
    }

}
