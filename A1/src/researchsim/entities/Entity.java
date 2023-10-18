package researchsim.entities;

import researchsim.map.Coordinate;
import java.util.*;

/**
 * An abstract class representing entities that can exist as an inhabitant of a tile.
 * See Also: Tile.
 */
public abstract class Entity {

    /** The size of an entity */
    private Size size;

    /** The coordinate of an entity */
    private Coordinate coordinate;

    /**
     * Constructs an entity with a given size and coordinate.
     * @param size Size associated with the entity
     * @param coordinate Coordinate associated with the entity
     */
    public Entity(Size size, Coordinate coordinate) {
        this.size = size;
        this.coordinate = coordinate;
    }

    /**
     * Returns this entity's size.
     * @return Associated size
     */
    public Size getSize() {
        return this.size;
    }

    /**
     * Returns this entity's scenario grid coordinate.
     * @return Associated coordinate
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * Update this entity's scenario grid coordinate.
     * @param coordinate The new coordinate
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Returns the human-readable name of this entity.
     * @return Human-readable name
     */
    public abstract String getName();

    /**
     * Returns the human-readable string representation of this entity.
     * Format: name [EntityClass] at coordinate
     * @return Human-readable string representation of this entity
     */
    public String toString() {
        String className = this.getClass().getSimpleName();
        int coordinateX = this.coordinate.getX();
        int coordinateY = this.coordinate.getY();

        return String.format("%s [%s] at (%d,%d)",
                getName(), className, coordinateX, coordinateY);
    }
}
