package researchsim.util;

import researchsim.map.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Denotes a specific type of entity that can move around in the simulation.
 * <p>
 * <b>NOTE:</b> <br> Read the documentation for these methods well.
 * This is one of the harder parts of A2.
 * <br> It is recommended that you create some private helper methods to assist with these
 * functions. <br> Some example methods might be: <br> checkTile - see if a Tile can be moved to
 * <br> checkTraversal - see if all tiles along a path can be moved to
 *
 * @ass2
 */
public interface Movable {
    /**
     * Returns a List of all the possible coordinates that the entity can move to.
     * @return list of possible movements
     */
    List<Coordinate> getPossibleMoves();

    /**
     * Move the entity from its current Coordinate to the given coordinate.
     * A MoveEvent should be created and added to the current scenario logger.
     * @param coordinate The new coordinate to move to
     * @requires the entity canMove(Coordinate) == true
     * @ensures the state of the tile that the entity is inhabiting,
     * the tile that the entity is going to are both updated, the event is logged
     */
    void move(Coordinate coordinate);

    /**
     * Determine if the entity can move to the specified coordinate.
     * @param coordinate coordinate to check
     * @return true if the instance can move to the specified coordinate else false
     * @throws CoordinateOutOfBoundsException if the coordinate given is out of bounds
     */
    boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException;

    /**
     * Return a list of coordinates that fall into the radius (range) of the specified coordinate.
     * @param radius the number of tiles that the entity can move
     * @param initialCoordinate the starting coordinate of the entity
     * @return a List of coordinates that the entity can move to.
     */
    default List<Coordinate> checkRange(int radius, Coordinate initialCoordinate) {

        List<Coordinate> inRange = new ArrayList<Coordinate>();
        for (int i = - radius; i <= radius; i++) {
            for (int j = - (radius - Math.abs(i)); j <= radius - Math.abs(i); j++) {

                inRange.add(new Coordinate(initialCoordinate.getX() + i,
                        initialCoordinate.getY() + j));
            }
        }
        return inRange;
    }
}
