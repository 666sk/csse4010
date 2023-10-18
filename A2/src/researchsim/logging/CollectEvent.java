package researchsim.logging;

import researchsim.entities.User;
import researchsim.entities.Fauna;
import researchsim.scenario.ScenarioManager;
import researchsim.entities.Entity;

/**
 * The collection of an entity that implemented {@link researchsim.util.Collectable} by a
 * {@link User}.
 *
 * @ass2
 */
public class CollectEvent extends Event {

    /**
     * The target entity that was collected
     */
    private Entity target;

    /**
     * The user collected a target
     */
    private User user;

    /**
     * Creates a new collect event,
     * this is an event where a user collects research on an entity
     * @param user - the user collecting a target
     * @param target - the target entity that is being collected
     */
    public CollectEvent(User user, Entity target) {
        super(user, target.getCoordinate());
        this.target = target;
        this.user = user;
    }

    /**
     * Returns the target that was collected.
     * @return event target
     */
    public Entity getTarget() {
        return this.target;
    }

    /**
     * Returns the string representation of the collect event.
     * @specified by toString in class Event
     * @return human-readable string representation of this collect event
     */
    @Override
    public String toString() {
        return String.format("%s%sCOLLECTED%s%s%s%s",
                user, System.lineSeparator(),
                System.lineSeparator(), target,
                System.lineSeparator(), "-----");
    }
}
