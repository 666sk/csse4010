package researchsim.entities;

import researchsim.map.Coordinate;

/**
 * A class involving all the plant life present in a particular region or time,
 * generally the naturally occurring (indigenous) native plants.
 */
public class Flora extends Entity {

    /**
     * Constructs a flora (Plant) with a given size and coordinate.
     * @param size Size associated with the plant
     * @param coordinate Coordinate associated with the plant
     */
    public Flora(Size size, Coordinate coordinate) {
        super(size, coordinate);
    }

    /**
     * Returns the human-readable name of this plant.
     * Specified by: getName in class Entity
     * @return human-readable name
     */
    @Override
    public String getName() {
        String floraName = "";
        Size floraSize = getSize();

        //Determine what the plant is
        if (floraSize == Size.SMALL) {

            floraName = "Flower";
        } else if (floraSize == Size.MEDIUM) {

            floraName = "Shrub";
        } else if (floraSize == Size.LARGE) {

            floraName = "Sapling";
        } else if (floraSize == Size.GIANT) {

            floraName = "Tree";
        }

        return floraName;
    }
}

