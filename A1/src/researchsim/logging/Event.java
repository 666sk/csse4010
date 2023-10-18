package researchsim.logging;

import researchsim.entities.Entity;
import researchsim.map.Coordinate;

/**
 * An event occurs in the Scenario and between an Entity and a Coordinate
 */
public abstract class Event {

    /** The target coordinate of the event */
    private Coordinate targetCoordinate;

    /** The coordinate that the event was initiated from */
    private Coordinate initialCoordinate;

    /** The entity completing some action */
    private Entity initialEntity;

    /**
     * Create a new event of an entity completing some action on a coordinate
     * @param entity The entity completing some action
     * @param coordinate The coordinate that is being actioned (the target of the event)
     */
    public Event(Entity entity, Coordinate coordinate) {
        initialEntity = entity;
        targetCoordinate = coordinate;
        initialCoordinate = initialEntity.getCoordinate();
    }

    /**
     * Return the target coordinate of the event
     * @return targetCoordinate Target coordinate of the event
     */
    public Coordinate getCoordinate() {
        return targetCoordinate;
    }

    /**
     * Return the coordinate that the event was initiated from
     * @return initialCoordinate The initial coordinate of the event
     */
    public Coordinate getInitialCoordinate() {
        return initialCoordinate;
    }

    /**
     * Return the entity that initiated the event
     * @return initialEntity The entity completing the action
     */
    public Entity getEntity() {
        return initialEntity;
    }

    /**
     * Return the string representation of the Event
     * @return string form of the event
     */
    public abstract String toString();
}

