package researchsim.entities;

import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.scenario.ScenarioManager;
import researchsim.logging.CollectEvent;
import researchsim.logging.MoveEvent;
import researchsim.util.Movable;
import researchsim.util.Collectable;
import researchsim.util.CoordinateOutOfBoundsException;

import java.util.Iterator;
import java.util.Objects;
import java.util.List;
import java.util.ArrayList;



/**
 * Fauna is all the animal life present in a particular region or time.
 * Fauna can move around the scenario and be collected by the {@link User}.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 * @ass1_test
 */
public class Fauna extends Entity implements Movable, Collectable {

    /**
     * The habitat associated with the animal.
     * That is, what tiles an animal can exist in.
     */
    private final TileType habitat;

    /**
     * Creates a fauna (Animal) with a given size, coordinate and habitat.
     *
     * @param size       size associated with the animal
     * @param coordinate coordinate associated with the animal
     * @param habitat    habitat tiles associated with the animal
     * @throws IllegalArgumentException if habitat is not {@link TileType#LAND} or
     *                                  {@link TileType#OCEAN}
     * @ass1
     */
    public Fauna(Size size, Coordinate coordinate, TileType habitat)
        throws IllegalArgumentException {
        super(size, coordinate);
        if (habitat != TileType.LAND && habitat != TileType.OCEAN) {
            throw new IllegalArgumentException("Animal was created with a bad habitat: " + habitat);
        }
        this.habitat = habitat;
    }

    /**
     * Returns the animal's habitat.
     *
     * @return animal's habitat
     * @ass1
     */
    public TileType getHabitat() {
        return habitat;
    }

    /**
     * Returns the human-readable name of this animal.
     * The name is determined by the following table.
     * <p>
     * <table border="1">
     *     <caption>Human-readable names</caption>
     *     <tr>
     *         <td rowspan="2" colspan="2" style="background-color:#808080">&nbsp;</td>
     *         <td colspan="3">Habitat</td>
     *     </tr>
     *     <tr>
     *         <td>LAND</td>
     *         <td>OCEAN</td>
     *     </tr>
     *     <tr>
     *         <td rowspan="4">Size</td>
     *         <td>SMALL</td>
     *         <td>Mouse</td>
     *         <td>Crab</td>
     *     </tr>
     *     <tr>
     *         <td>MEDIUM</td>
     *         <td>Dog</td>
     *         <td>Fish</td>
     *     </tr>
     *     <tr>
     *         <td>LARGE</td>
     *         <td>Horse</td>
     *         <td>Shark</td>
     *     </tr>
     *     <tr>
     *         <td>GIANT</td>
     *         <td>Elephant</td>
     *         <td>Whale</td>
     *     </tr>
     * </table>
     * <p>
     * e.g. if this animal is {@code MEDIUM} in size and has a habitat of {@code LAND} then its
     * name would be {@code "Dog"}
     *
     * @return human-readable name
     * @ass1
     */
    @Override
    public String getName() {
        String name;
        switch (getSize()) {
            case SMALL:
                name = habitat == TileType.LAND ? "Mouse" : "Crab";
                break;
            case MEDIUM:
                name = habitat == TileType.LAND ? "Dog" : "Fish";
                break;
            case LARGE:
                name = habitat == TileType.LAND ? "Horse" : "Shark";
                break;
            case GIANT:
            default:
                name = habitat == TileType.LAND ? "Elephant" : "Whale";
        }
        return name;
    }

    /**
     * Return the machine-readable string representation of this animal.
     * @specified by encode in interface Encodable
     * @return encoded string representation of this animal
     */
    @Override
    public String encode() {
        return String.format("Fauna-%s-%d,%d-%s",
                this.getSize(),
                this.getCoordinate().getX(),
                this.getCoordinate().getY(),
                this.habitat);
    }

    /**
     * Return the hash code of this animal.
     * @return hash code of this animal.
     */
    @Override
    public int hashCode() {
        return Objects.hash(this.habitat, this.getSize(), this.getCoordinate());
    }

    /**
     * Return true if and only if this animal is equal to the other given object.
     * @param other the reference object with which to compare
     * @return true if this animal is the same as the other argument; false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other instanceof Fauna
                && ((Fauna) other).getSize() == this.getSize()
                && ((Fauna) other).getCoordinate().equals(this.getCoordinate())
                && ((Fauna) other).habitat == this.habitat;
    }

    /**
     * Returns the human-readable string representation of this animal.
     * <p>
     * The format of the string to return is:
     * <pre>name [Fauna] at coordinate [habitat]</pre>
     * Where:
     * <ul>
     *   <li>{@code name} is the animal's human-readable name according to {@link #getName()}</li>
     *   <li>{@code coordinate} is the animal's associated coordinate in human-readable form</li>
     *   <li>{@code habitat} is the animal's associated habitat</li>
     *
     * </ul>
     * For example:
     *
     * <pre>Dog [Fauna] at (2,5) [LAND]</pre>
     *
     * @return human-readable string representation of this animal
     * @ass1
     */
    @Override
    public String toString() {
        return String.format("%s [%s]",
            super.toString(),
            this.habitat);
    }

    /**
     * Return a List of all the possible coordinates that this animal can move to
     * @specified by getPossibleMoves in interface Movable
     * @return list of possible movements in coordinates
     */
    @Override
    public List<Coordinate> getPossibleMoves() {

        List<Coordinate> inRange = checkRange(getSize().moveDistance, getCoordinate());
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
     * Moves the animal to the new coordinate.
     * The Tile that the animal moves to should now be occupied by this animal.
     * The tile that the animal moves from (the existing coordinate) should now have no occupant.
     * @specified by move in interface Movable
     * @param coordinate The new coordinate to move to
     * @requires canMove(Coordinate) == true
     * @ensures the state of the tile that the animal is inhabiting,
     * the tile that the animal is going to are both updated, the event is logged
     */
    @Override
    public void move(Coordinate coordinate) {

        //Obtain the current map grid
        Tile[] curMap = ScenarioManager.getInstance().getScenario().getMapGrid();
        int prevIndex = this.getCoordinate().getIndex();
        curMap[prevIndex].setContents(null);
        this.setCoordinate(coordinate);
        int curIndex = this.getCoordinate().getIndex();
        curMap[curIndex].setContents(this);

        try {
            ScenarioManager.getInstance().getScenario().setMapGrid(curMap);
        } catch (CoordinateOutOfBoundsException e) {
            ;
        }

        ScenarioManager.getInstance().getScenario().getLog().add(
                new MoveEvent(this, coordinate));
    }

    /**
     * Determines if the animal can move to the new coordinate.
     * @specified by canMove in interface Movable
     * @param coordinate coordinate to check
     * @return true if the above conditions are satisfied else false
     * @throws CoordinateOutOfBoundsException - if the coordinate given is out of bounds
     */
    @Override
    public boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException {

        if (!coordinate.isInBounds()) {
            throw new CoordinateOutOfBoundsException();
        } else if (coordinate.equals(this.getCoordinate())) {
            return false;
        } else if ((this.getCoordinate().distance(coordinate).getAbsX()
                + this.getCoordinate().distance(coordinate).getAbsY())
                > getSize().moveDistance) {
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
     * Determine if the animal can move to the destination without turning
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
     * Determine if the animal can move to the destination with one turning
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
     * Determine if the animal can pass through the tile
     * @param x x Coordinate of the tile on pathway
     * @param y y Coordinate of the tile on pathway
     * @return true if this tile can move to, false otherwise
     */
    private boolean pathCanMove(int x, int y) {
        Coordinate pathCoord = new Coordinate(x, y);
        int index = pathCoord.getIndex();
        if (ScenarioManager.getInstance().getScenario().getMapGrid()[index]
                .hasContents()) {
            return false;
        } else if (getHabitat() == TileType.OCEAN && ScenarioManager.getInstance()
                .getScenario().getMapGrid()[index].getType() != TileType.OCEAN) {
            return false;
        } else if (getHabitat() == TileType.LAND && ScenarioManager.getInstance()
                .getScenario().getMapGrid()[index].getType() == TileType.OCEAN) {
            return false;
        }
        return true;
    }

    /**
     * A User interacts and collects this animal.
     * @specified by collect in interface Collectable
     * @param user - the user that collects the entity.
     * @return points earned
     */
    @Override
    public int collect(User user) {
        int curIndex = this.getCoordinate().getIndex();
        Tile[] curMap = ScenarioManager.getInstance().getScenario().getMapGrid();

        curMap[curIndex].setContents(null);
        try {
            ScenarioManager.getInstance().getScenario().setMapGrid(curMap);
        } catch (CoordinateOutOfBoundsException e) {
            ;
        }
        ScenarioManager.getInstance().getScenario().getController().removeAnimal(this);
        CollectEvent faunaCollected = new CollectEvent(user, this);
        ScenarioManager.getInstance().getScenario().getLog().add(faunaCollected);

        return this.getSize().points;
    }

}
