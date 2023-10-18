package researchsim.entities;

import researchsim.map.Coordinate;
import researchsim.map.TileType;
import java.util.*;

/**
 * A class involving all the animal life present in a particular region or time.
 */
public class Fauna extends Entity {

    /** The type of terrain that is at a tile */
    private TileType habitat;

    /**
     * Constructs a fauna (Animal) with a given size, coordinate and habitat.
     * @param size Size associated with the animal
     * @param coordinate Coordinate associated with the animal
     * @param habitat Habitat tiles associated with the animal
     * @throw IllegalArgumentException - if habitat is not LAND or OCEAN
     */
    public Fauna(Size size, Coordinate coordinate, TileType habitat) {
        super(size, coordinate);

        if (!(habitat == TileType.LAND || habitat == TileType.OCEAN)) {

            throw new IllegalArgumentException();
        }

        this.habitat = habitat;
    }

    /**
     * Returns the animal's habitat.
     * @return animal's habitat
     */
    public TileType getHabitat() {
        return this.habitat;
    }

    /**
     * Returns the human-readable name of this animal.
     * Specified by: getName in class Entity
     * @return human-readable name
     */
    @Override
    public String getName() {
        String animalName = "";
        Size animalSize = getSize();

        //Determine what the animal is
        if (habitat == TileType.LAND) {
            if (animalSize == Size.SMALL) {

                animalName = "Mouse";
            } else if (animalSize == Size.MEDIUM) {

                animalName = "Dog";
            } else if (animalSize == Size.LARGE) {

                animalName = "Horse";
            } else if (animalSize == Size.GIANT) {

                animalName = "Elephant";
            }
        } else if (habitat == TileType.OCEAN) {

            if (animalSize == Size.SMALL) {

                animalName = "Crab";
            } else if (animalSize == Size.MEDIUM) {

                animalName = "Fish";
            } else if (animalSize == Size.LARGE) {

                animalName = "Shark";
            } else if (animalSize == Size.GIANT) {

                animalName = "Whale";
            }
        }

        return animalName;
    }

    /**
     * Returns the human-readable string representation of this animal.
     * Format: name [Fauna] at coordinate [habitat]
     * @return human-readable string representation of this animal
     */
    @Override
    public String toString() {
        return String.format(super.toString() + " [%s]", getHabitat().toString());
    }
}
