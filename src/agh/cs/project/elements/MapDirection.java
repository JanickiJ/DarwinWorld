package agh.cs.project.elements;

public enum MapDirection {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;


    public Vector2d toUnitVector() {
        return switch (this) {
            case NORTH -> new Vector2d(0,1);
            case SOUTH -> new Vector2d(0,-1);
            case EAST -> new Vector2d(1,0);
            case WEST -> new Vector2d(-1,0);
            case NORTHEAST -> new Vector2d(1,1);
            case SOUTHEAST -> new Vector2d(1,-1);
            case SOUTHWEST -> new Vector2d(-1,-1);
            case NORTHWEST -> new Vector2d(-1,1);
        };
    }

    public MapDirection next() {
            return switch (this) {
                case NORTH -> NORTHEAST;
                case SOUTH -> SOUTHWEST;
                case EAST -> SOUTHEAST;
                case WEST -> NORTHWEST;
                case NORTHEAST -> EAST;
                case SOUTHEAST -> SOUTH;
                case SOUTHWEST -> WEST;
                case NORTHWEST -> NORTH;

            };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> NORTHWEST;
            case SOUTH -> SOUTHEAST;
            case EAST -> NORTHEAST;
            case WEST -> SOUTHWEST;
            case NORTHWEST -> WEST;
            case SOUTHWEST -> SOUTH;
            case NORTHEAST -> NORTH;
            case SOUTHEAST -> EAST;
        };
    }

}


