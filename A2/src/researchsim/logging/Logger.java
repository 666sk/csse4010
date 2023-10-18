package researchsim.logging;

import researchsim.map.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * A detailed log that contains a record of {@link Event}s and contains some event statistics.
 *
 * @ass2
 */
public class Logger {

    /**
     * The number of entities that have been collected
     */
    private int entityCounts;

    /**
     * The number of tiles that have been travelled
     */
    private int tileCounts;

    /**
     * The number of points that have been earned
     */
    private int pointCounts;

    /**
     * The list of events logged
     */
    private List<Event> eventsLogged;

    /**
     * Creates a new logger to maintain a list of events that occur in a scenario.
     * All starting at 0
     */
    public Logger() {
        entityCounts = 0;
        tileCounts = 0;
        pointCounts = 0;
        eventsLogged = new ArrayList<Event>();
    }

    /**
     * Returns how many tiles have been traversed by entities.
     * @return tiles traversed
     */
    public int getTilesTraversed() {
        return tileCounts;
    }

    /**
     * Returns how many entities have been collected by a user.
     * @return entities collected
     */
    public int getEntitiesCollected() {
        return entityCounts;
    }

    /**
     * Returns the number of points earned in a scenario.
     * @return points earned
     */
    public int getPointsEarned() {
        return pointCounts;
    }

    /**
     * Returns all the events that have been logged.
     * Adding or removing elements from the returned list
     * should not affect the original list.
     * @return all events that have been logged
     */
    public List<Event> getEvents() {
        return new ArrayList<Event>(eventsLogged);
    }

    /**
     * Adds an event to the log.
     * Count depends on the type of the event
     * @param event - the new event
     */
    public void add(Event event) {
        eventsLogged.add(event);

        if (event instanceof CollectEvent) {
            entityCounts++;
            int pointsEarned = ((CollectEvent) event).getTarget().getSize().points;
            pointCounts += pointsEarned;
        } else if (event instanceof MoveEvent) {
            Coordinate distance = event.getInitialCoordinate()
                    .distance(event.getCoordinate());
            tileCounts += (distance.getAbsX() + distance.getAbsY());
        }
    }

    /**
     * Returns the string representation of the event log
     * @return human-readable string representation of log
     */
    @Override
    public String toString() {
        String logger = new String();

        for (Event i : eventsLogged) {
            logger += i;
            logger += System.lineSeparator();
        }

        //Note that there is no trailing newline.
        logger = logger.substring(0, logger.length() - 1);
        return logger;
    }
}
