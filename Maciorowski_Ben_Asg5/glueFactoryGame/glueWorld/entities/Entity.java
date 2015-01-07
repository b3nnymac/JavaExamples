package glueFactoryGame.glueWorld.entities;

import glueFactoryGame.glueWorld.GameImage;

/**
 * Represents root of inheritance tree 
 * @author Jordan Kidney
 * @version 1.0
 * Last Modified: Nov 10, 2014 - Created (By Jordan Kidney)
 */

public class Entity 
{
	private int row; // row location of the object in the grid world
	private int col; // column location of the object in the world
	private boolean canMoveOnTo; // used to indicate if another object is allowed to
								 // stand on, or be located at the same location
							     // as the current object
							     // false = no, true = yes
	
	private boolean isSticky;  // used to indicate if this object is sticky or not
							   // false = no, true = yes
	
	private GameImage  displayImg;  // The image used to display the object

	/**
	 * default constructor
	 * @param row the row location of the object
	 * @param col the column location of the object
	 * @param displayImg the image used to display the object
	 */
	public Entity(int row, int col, GameImage  displayImg)
	{
		this(row, col, displayImg, false, false);	  
	}
	/**
	 * Constructor
	 * @param row the row location of the object
	 * @param col the column location of the object
	 * @param displayImg the image used to display the object
	 * @param canMoveOnTo if the object can be moved onto or not
	 * @param isSticky if the object is sticky or not
	 */
	public Entity(int row, int col, GameImage  displayImg, boolean canMoveOnTo, boolean isSticky)
	{
		setLocation(row,col);
		this.canMoveOnTo = canMoveOnTo; 
		this.displayImg = displayImg;
		this.isSticky = isSticky;
	}

	/**
	 * used to indicate if another Entity is allowed to
	 * stand on, or be located at the same location
	 * as the current entity
	 * @return true = yes, false = no
	 */
	public boolean canMoveOnTo() { return canMoveOnTo; }

	/**
	 * Used to indicate if this entity is sticky or not
	 * @return true = yes, false = no
	 */
	public boolean isEntitySticky() { return isSticky; }
	
	public int getRow() { return row; }
	public void setRow(int value) { row = value; }

	public int getCol() { return col; }
	public void setCol(int value) { col = value; }

	public void setLocation(int row, int col)
	{
		this.row = row;
		this.col = col;
	}

	/**
	 * Determines if the current entity is occupying the same location as the given entity
	 * @param entity
	 * @return true of occupy same location, false otherwise
	 */
	public boolean isStandingOn(Entity entity)
	{
		return (row==entity.row) && (col==entity.col);
	}

	/**
	 * Determines the distance between the two entities
	 * @param entity
	 * @return
	 */
	public int distance(Entity entity)
	{
		int xDiff = col - entity.col;
		int yDiff = row - entity.row;
		return (int) Math.sqrt((xDiff*xDiff) + (yDiff*yDiff));
	}

	public GameImage getDisplayImage() { return displayImg; }
	public void setdisplayImg(GameImage displayImg) 
	{ this.displayImg = displayImg; }

	
	@Override
	public String toString() 
	{
		String result = String.format("[row=%2d, col=%2d] type="+displayImg,row,col);
		if(isSticky) result += " (sticky entity)";
		return result;
	}
}
