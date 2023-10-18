package researchsim.map;

/**
 * This enum represents the type of terrain that is at a tile.
 * The terrain type restricts the entities that are allowed in inhabit a tile.
 */
public enum TileType {

    /** Generic land area for ground based entities */
    LAND,

    /** A mountain, a rough terrain to inhabit, a good boundary tile */
    MOUNTAIN,

    /** Generic sea area for water based entities */
    OCEAN,

    /** Sandy terrain to transition between LAND and SEA or represent a desert */
    SAND
}

