package researchsim.entities;

import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.CoordinateOutOfBoundsException;
import researchsim.util.Movable;
import researchsim.util.NoSuchEntityException;
import researchsim.logging.MoveEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * User is the player controlled character in the simulation.
 * A user can {@code collect} any class that implements the {@link researchsim.util.Collectable}
 * interface.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass2
 * @ass2_test
 */
public class User extends Entity implements Movable {

    /**
     * The name of the user
     */
    private String name;

    /**
     * Creates a user with a given coordinate and name.
     * @param coordinate - coordinate associated with the user
     * @param name - the name of this user
     */
    public User(Coordinate coordinate, String name) {
        super(Size.MEDIUM, coordinate);
        this.name = name;
    }

    /**
     * Returns the name of this user.
     * @specified by getName in class Entity
     * @return human-readable name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the machine-readable string representation of this user.
     * @specified by encode in interface Encodable
     * @return encoded string representation of this user
     */
    @Override
    public String encode() {
        return String.format("User-%s-%s", this.getCoordinate().encode(), this.name);
    }

    /**
     * Returns the hash code of this user.
     * @return hash code of this user.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.getCoordinate(), this.name);
    }

    /**
     * Returns true if and only if this user is equal to the other given object.
     * @param other the reference object with which to compare
     * @return true if this user is the same as the other argument; false otherwise
     */
    public boolean equals(Object other) {
        return other instanceof User
                && ((User) other).getName().equals(this.name)
                && ((User) other).getCoordinate().equals(this.getCoordinate());
    }

    /**
     * Returns a List of all the possible coordinates that this user can move to.
     * @specified by getPossibleMoves in interface Movable
     * @return list of possible movements
     */
    public List<Coordinate> getPossibleMoves() {

        List<Coordinate> inRange = checkRange(3, getCoordinate());
        List<Coordinate> possibleMoves = new ArrayList<Coordinate>();
        for (Coordinate i : inRange) {
            try {
                if (this.canMove(i)) {
                    possibleMoves.add(i);
                }
            } catch (CoordinateOutOfBoundsException e) {
                ;
            }
        }
        return possibleMoves;
    }

    /**
     * Moves the user to the new coordinate.
     * @specified by move in interface Movable
     * @param coordinate The new coordinate to move to
     * @requires canMove(Coordinate) == true
     * @ensures the state of the tile that the user is inhabiting,
     * the tile that the user is going to are both updated, the event is logged
     */
    @Override
    public void move(Coordinate coordinate) {
        ScenarioManager.getInstance().getScenario().getLog().add(
                new MoveEvent(this, coordinate));

        //Obtain the current map grid
        Tile[] curMap = ScenarioManager.getInstance().getScenario().getMapGrid();
        int prevIndex = this.getCoordinate().getIndex();
        curMap[prevIndex].setContents(null);
        try {
            ScenarioManager.getInstance().getScenario().setMapGrid(curMap);
        } catch (CoordinateOutOfBoundsException e) {
            ;
        }

        try {
            this.collect(coordinate);
        } catch (CoordinateOutOfBoundsException e) {
            ;
        } catch (NoSuchEntityException e) {
            ;
        }

        this.setCoordinate(coordinate);
        curMap = ScenarioManager.getInstance().getScenario().getMapGrid();
        int curIndex = this.getCoordinate().getIndex();
        curMap[curIndex].setContents(this);

        try {
            ScenarioManager.getInstance().getScenario().setMapGrid(curMap);
        } catch (CoordinateOutOfBoundsException e) {
            ;
        }

    }

    /**
     * Determines if the user can move to the specified coordinate.
     * @specified by canMove in interface Movable
     * @param coordinate coordinate to check
     * @return true if the instance can move to the specified coordinate else false
     * @throws CoordinateOutOfBoundsException - if the coordinate given is out of bounds
     */
    @Override
    public boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException {
        //Coordinate is in the current scenario map and is a different coordinate
        if (!coordinate.isInBounds()) {
            throw new CoordinateOutOfBoundsException();
        } else if (coordinate.equals(this.getCoordinate())) {
            return false;
        } else if ((this.getCoordinate().distance(coordinate).getAbsX()
                + this.getCoordinate().distance(coordinate).getAbsY()) > 4) {
            return false;
        }

        //x, y coordinates of the initial coordinate
        int initX = this.getCoordinate().getX();
        int initY = this.getCoordinate().getY();

        //x,y coordinates of the destination coordinate
        int destX = coordinate.getX();
        int destY = coordinate.getY();

        if ((initX == destX) || (initY == destY)) {
            return noTurningCanMove(initX, destX, initY, destY);
        } else {
            return turningOnceCanMove(initX, destX, initY, destY);
        }
    }


    /**
     * Determine if the user can move to the destination without turning
     * @param initX Initial x Coordinate
     * @param destX Destination x Coordinate
     * @param initY Initial y Coordinate
     * @param destY Destination y Coordinate
     * @return true if all path tiles can move, false otherwise
     */
    private boolean noTurningCanMove(int initX, int destX, int initY, int destY) {
        int i = 0;

        if (initX == destX) {
            if (destY < initY) {
                for (i = initY - 1; i >= destY; i--) {
                    if (!(pathCanMove(destX, i))) {
                        return false;
                    }
                }
            } else if (destY > initY) {
                for (i = initY + 1; i <= destY; i++) {
                    if (!(pathCanMove(destX, i))) {
                        return false;
                    }
                }
            }
        } else if (initY == destY) {
            if (destX < initX) {
                for (i = initX - 1; i >= destX; i--) {
                    if (!(pathCanMove(i, destY))) {
                        return false;
                    }
                }

            } else if (destX > initX) {
                for (i = initX + 1; i <= destX; i++) {
                    if (!(pathCanMove(i, destY))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /**
     * Determine if the user can move to the destination with one turning
     * There are two possible pathways, one of them can move is fine
     * @param initX Initial x Coordinate
     * @param destX Destination x Coordinate
     * @param initY Initial y Coordinate
     * @param destY Destination y Coordinate
     * @return true if all path tiles can move, false otherwise
     */
    private boolean turningOnceCanMove(int initX, int destX, int initY, int destY) {
        int i = 0;
        int j = 0;
        boolean pathOne = true;
        boolean pathTwo = true;

        if (destX < initX) {
            for (i = initX - 1; i >= destX; i--) {
                if (!(pathCanMove(i, initY))) {
                    pathOne = false;
                }
                if (!(pathCanMove(i, destY))) {
                    pathTwo = false;
                }
            }
        } else if (destX > initX) {
            for (i = initX + 1; i <= destX; i++) {
                if (!(pathCanMove(i, initY))) {
                    pathOne = false;
                }
                if (!(pathCanMove(i, destY))) {
                    pathTwo = false;
                }
            }
        }

        if (destY < initY) {
            for (j = initY - 1; j >= destY; j--) {
                if (!(pathCanMove(destX, j))) {
                    pathOne = false;
                }
                if (!(pathCanMove(initX, j))) {
                    pathTwo = false;
                }
            }
        } else if (destY > initY) {
            for (j = initY + 1; j <= destY; j++) {
                if (!(pathCanMove(destX, j))) {
                    pathOne = false;
                }
                if (!(pathCanMove(initX, j))) {
                    pathTwo = false;
                }
            }
        }

        //Either pathway can move to the destination is OK
        return (pathOne || pathTwo);
    }


    /**
     * Determine if the user can pass through the tile
     * @param x x Coordinate of the tile on pathway
     * @param y y Coordinate of the tile on pathway
     * @return true if this tile can move to, false otherwise
     */
    private boolean pathCanMove(int x, int y) {
        Coordinate pathCoord = new Coordinate(x, y);
        int index = pathCoord.getIndex();
        TileType curType = ScenarioManager.getInstance()
                .getScenario().getMapGrid()[index].getType();
        if ((curType == TileType.OCEAN) || (curType == TileType.MOUNTAIN)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns a List of all the possible coordinates that this user can collect from.
     * @return list of possible collections
     */
    public List<Coordinate> getPossibleCollection() {
        List<Coordinate> inRange = checkRange(1, getCoordinate());

        //If cannot collect, delete the coordinate
        Iterator<Coordinate> inRangeItr = inRange.iterator();
        while (inRangeItr.hasNext()) {
            Coordinate nextCoord = inRangeItr.next();
            if (!(nextCoord.isInBounds())) {
                inRangeItr.remove();
            } else if (!(ScenarioManager.getInstance().getScenario().getMapGrid()[nextCoord
                    .getIndex()].hasContents())) {
                inRangeItr.remove();
            } else {
                try {
                    if (! ((ScenarioManager.getInstance().getScenario().getMapGrid()
                            [nextCoord.getIndex()].getContents() instanceof Fauna)
                            || ScenarioManager.getInstance().getScenario().getMapGrid()
                            [nextCoord.getIndex()].getContents() instanceof Flora)) {
                        inRangeItr.remove();
                    }
                } catch (NoSuchEntityException e) {
                    ;
                }
            }
        }
        return inRange;
    }

    /**
     * Collects an entity from the specified coordinate.
     * @param coordinate - the coordinate we are collecting from
     * @throws NoSuchEntityException - if the given coordinate is empty
     * @throws CoordinateOutOfBoundsException - if the given coordinate is not in the map bounds.
     */
    public void collect(Coordinate coordinate)
            throws NoSuchEntityException, CoordinateOutOfBoundsException {

        if (! coordinate.isInBounds()) {
            throw new CoordinateOutOfBoundsException();
        }

        int index = coordinate.getIndex();
        Entity collected = ScenarioManager.getInstance().getScenario().getMapGrid()
                [index].getContents();
        if (collected instanceof Flora) {
            ((Flora) collected).collect(this);
        } else if (collected instanceof Fauna) {
            ((Fauna) collected).collect(this);
        }

    }
}
