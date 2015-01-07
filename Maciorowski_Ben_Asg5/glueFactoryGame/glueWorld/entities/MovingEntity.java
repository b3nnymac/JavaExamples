package glueFactoryGame.glueWorld.entities;

import glueFactoryGame.Direction;
import glueFactoryGame.glueWorld.GameImage;
import glueFactoryGame.glueWorld.GlueWorld;

/**
 * Represents all functionality for entities that can move
 *  
 * @author Jordan Kidney
 * @version 1.0
 * Last Modified: Nov 10, 2014 - Created (By Jordan Kidney)
 */
public class MovingEntity extends Entity
{	
	private boolean stuck;
	private boolean stickable; // used to indicate if the moving entity can be stuck by glue 
	private GameImage stuckImage;
	private GameImage unStuckImage;

	/**
	 * Sets up a moving entity that can be stuck by glue
	 * @param row the row location of the object
	 * @param col the column location of the object
	 * @param displayImg the image used to display the object
	 * @param stuckImage the image to use when the entity is stuck by glue
	 */
	public MovingEntity(int row, int col,GameImage displayImg,GameImage stuckImage)
	{
		super(row,col,displayImg);
		stuck = false;
		this.stuckImage = stuckImage;
		this.unStuckImage = displayImg;
		stickable = true;	
	}
	
	/**
	 * Sets up a moving entity that can not be stuck by glue
	 * @param row the row location of the object
	 * @param col the column location of the object
	 * @param displayImg the image used to display the object
	 */
	public MovingEntity(int row, int col, GameImage displayImg)
	{
		super(row,col,displayImg);
		stuck = false;
		this.stuckImage = stuckImage;
		this.unStuckImage = displayImg;
		stickable = false;
	}

	public boolean isStuck() {  return stuck; }
	public boolean canBeStuckByGlue() { return stickable; }
	
	/**
	 * Sets the entities state to being stuck and updates the display image to be the stuck image
	 */
	public void setEntityStuck() 
	{ 
		stuck = true; 
		setdisplayImg(stuckImage);
	}
	/**
	 * Sets the entities state to being unstuck and updates the display image to be the orignal image
	 */
	public void setEntityUnStuck() 
	{
		stuck = false; 
		setdisplayImg(unStuckImage);
	}

	
	/**
	 * When called this method executes the move
	 * operation of the entity. Once completed
	 * the entity's location and state should be updated
	 * 
	 * This method should be overridden in children classes 
	 */
	public void move() { }
}
