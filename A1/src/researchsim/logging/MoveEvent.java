package researchsim.logging;

import researchsim.entities.Entity;
import researchsim.map.Coordinate;

/**
 * The movement of an entity from one coordinate to another (new) coordinate
 */
public class MoveEvent extends Event {

    /**
     * Creates a new move event.
     * This is an event where an entity moves to another coordinate
     * @param entity The entity that is moving
     * @param coordinate The coordinate that the entity is moving to
     */
    public MoveEvent(Entity entity, Coordinate coordinate) {
        super(entity, coordinate);
    }

    /**
     * Return the string representation of the move event
     * @return Human-readable string representation of the move event
     */
    @Override
    public String toString() {
        super.getEntity().setCoordinate(super.getInitialCoordinate());

        return super.getEntity().toString() + System.lineSeparator() + "MOVED TO ("
                + String.valueOf(super.getCoordinate().getX()) + ","
                + String.valueOf(super.getCoordinate().getY()) + ")"
                + System.lineSeparator() + "-----";
    }
}
