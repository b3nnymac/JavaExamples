package glueFactoryGame;

public enum Direction 
{
    UP,
    LEFT,
    DOWN,
    RIGHT;

    public static Direction[] ALL_DIRECTIONS = 
        { 
            UP,
            LEFT,
            DOWN,
            RIGHT 
        };

    //offsets from current location to desired location
    //the row = direction matching numbers from above
    //col = 0 ( row offset)
    //col = 1 ( col offset)
    public static int[][] DIRECTION_OFFSET =
        {
            {-1, 0},  //UP
            { 0,-1},  //LEFT
            { 1, 0},  //DOWN 
            { 0, 1}   //RIGHT 
        };

    /**
     * Returns offsets that can be used in relation to a direction
     * @param where the direction to get the offsets for
     * @return the offsets and an array. col = 0 ( row offset), col = 1 ( col offset)
     */
    public static int[] getDirectionOffsets(Direction where)
    {
        int offsets = -1;
        switch(where)
        {
            case UP: offsets = 0; break;
            case LEFT: offsets = 1; break;
            case DOWN: offsets = 2; break;
            case RIGHT: offsets = 3; break;
        }

        return Direction.DIRECTION_OFFSET[offsets]; 		
    }

}
