package glueFactoryGame.glueWorld;

import glueFactoryGame.glueWorld.entities.Entity;

/**
 * Represents the Glue Factory World used in the game
 * @author Jordan Kidney
 * @version 1.0
 * Last Modified: Nov 10, 2014 - Created (By Jordan Kidney)
 */
//YOU SOULD NOT HAVE TO ADD ANY CODE TO THIS CLASS	

public class GlueWorld 
{  
    public static final int MAX_ROWS = 15;
    public static final int MAX_COLS = 15;
    private int stickyCount = 0;

    //instance variables
    private Entity[][] grid;
    private int numCols;
    private int numRows;

    //Singleton pattern
    private static GlueWorld world = null;
    public static GlueWorld getInstance()
    {
        if(world == null) world = new GlueWorld();

        return world;
    }

    /**
     * Constructor, sets up the blank world
     */
    private GlueWorld()
    {
        grid = new Entity[MAX_ROWS][MAX_COLS];
        this.numRows = MAX_ROWS;
        this.numCols = MAX_COLS;
    }

    /**
     * Determines if the given row and col value are within bounds
     * of the grid world
     * @param row
     * @param col
     * @return true for pass, false for fail
     */
    public boolean inBounds(int row, int col)
    {
        return (row >= 0 && row < grid.length) && (col >= 0 && col < grid[row].length); 
    }

    /**
     * Determines if the given Entity is within bounds
     * @param thing
     * @return
     */
    public boolean inBounds(Entity thing)
    {
        return inBounds(thing.getRow(), thing.getCol());
    } 

    /**
     * Determines in the given location is within bounds and can be moved
     * on to. There could be an entity at the location with that has the ability to be 
     * Stepped onto. 
     * @param row row location to check
     * @param col column location to check
     * @return boolean if it is open, false otherwise
     */
    public boolean canLocationbeMovedOnto(int row, int col)
    {
        boolean result = false;

        if(inBounds(row, col))
        {
            Entity entity = grid[row][col];	
            if(entity == null) result = true;
            else if(entity.canMoveOnTo() ) result = true;
        }

        return result;
    }

    /**
     * Places the entity at the location it is marked to appear at. If any
     * other entity is at the location already it will be returned. 
     * @param thing the entity to place in the world
     * @return null for no past value or the previous entity is returned
     */
    public Entity place(Entity thing)
    {
        return place(thing.getRow(),thing.getCol(),thing);
    }

    /**
     * Places the entity at the given location. If any
     * other entity is at the location already it will be returned. 
     * @param row
     * @param col
     * @param thing the entity to place in the world
     * @return null for no past value or the previous entity is returned
     */
    public Entity place(int row, int col,Entity thing)
    {
        Entity oldObjectAtLocation = null;

        if(inBounds(row,col))
        {
            oldObjectAtLocation = grid[row][col];
            grid[row][col] = thing;
            if(thing != null && thing.isEntitySticky()) stickyCount++;

            if(oldObjectAtLocation != null && oldObjectAtLocation.isEntitySticky()) stickyCount--;
        }
        return oldObjectAtLocation;
    }

    /**
     * places a null at the location of the given entity to indicate it has been removed
     * @param entity
     */
    public void remove(Entity entity)
    {
        if(inBounds(entity))
        {
            if(entity.isEntitySticky()) stickyCount--;
            grid[entity.getRow()][entity.getCol()] = null;
        }
    }

    /**
     * clears out any entity at the given location (makes it an open location)
     * @param row 
     * @param col
     */
    public void clearLocation(int row, int col)
    {
        if(inBounds(row,col))
        {
            if(grid[row][col] != null && grid[row][col].isEntitySticky())
                stickyCount--;

            grid[row][col] = null;
        }
    }

    public int getNumCols() { return numCols; }

    public int getNumRows() { return numRows; }

    /**
     * Returns the entity at the given location
     * @param row row location
     * @param col column location
     * @return the entity found at the location
     * @throws IndexOutOfBoundsException if the row or col are out of bounds
     */
    public Entity get(int row, int col) throws IndexOutOfBoundsException
    {
        Entity objectAtLocation = null;

        if(inBounds(row,col))
        {
            objectAtLocation = grid[row][col];
        }
        else
            throw new IndexOutOfBoundsException();

        return objectAtLocation;
    }

    /**
     * returns a count of the number of sticky items current;y placed into the world
     * @return
     */
    public int numStickyItemsInWorkd()
    {
        return stickyCount;
    }
}
