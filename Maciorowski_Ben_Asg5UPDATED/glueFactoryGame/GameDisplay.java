package glueFactoryGame;
/**
 * Graphical Display area for game. Created using the Processing library www.processing.org
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Nov 9, 2014 - Created (Jordan Kidney)
 */

import glueFactoryGame.glueWorld.GameImage;

import java.awt.Color;
import java.awt.Dimension;

import java.util.*;
import processing.core.*;


public class GameDisplay extends PApplet
{
	private Hashtable<String, PImage> preloadedImages;
	private PImage[][] backGroundlayer = null; // base layer images for entire background
	private PImage[][] foreGroundLayer = null; // all images to appear in the foreground 

	/** the number of rows in the map */
	private int rows;
	/** the number of columns in the map */
	private int cols;
	/** the size of each cell when it is drawn*/
	private int cellWidth;
	private int cellHeight;
	private GameController gameController;


	/**
	 * used to store buffered image of map for fast smooth rendering
	 */
	private PGraphics buffer = null;

	public GameDisplay(GameController gameController)
	{
		this.gameController = gameController;
		setBackground(Color.BLACK);
		preloadedImages = new Hashtable<String, PImage>();
		preLoadAllImages();
		init();
	}

	/**
	 * PreLoads all images into the program
	 */
	private void preLoadAllImages()
	{
		GameImage[] all = GameImage.values();
		for(GameImage img: all)
			preloadedImages.put(img.getPath(), loadImage(img.getPath()));
	}

	/**
	 * returns the size of the display area for drawing graphics @see Dimension
	 * @return a Dimension object
	 */
	public Dimension getDimensions()
	{
		return new Dimension(cols,rows);
	}

	/**
	 * Builds the draw area to be used
	 * @param rows the number of rows in the map
	 * @param cols the number of columns in the map
	 * @param cellWidth the width of each cell in the map
	 * @param cellHeight the height of each cell in the map
	 */
	public void buildDrawArea(int rows, int cols, int cellWidth, int cellHeight)
	{
		backGroundlayer = new PImage[rows][cols];
		foreGroundLayer = new PImage[rows][cols];
		this.rows = rows;
		this.cols = cols;
		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		buffer = createGraphics(cols*cellWidth,rows*cellHeight, JAVA2D);

		for(int row=0; row < rows; row++)
			for(int col = 0; col < cols; col++)
			{
				backGroundlayer[row][col] = null;
				foreGroundLayer[row][col] = null;
			}
	}

	/**
	 * Sets the Image for a cell in the background. Can be null to indicate the cell should
	 * not be drawn
	 * @param row the row location of the cell
	 * @param col the column location of the cell
	 * @param img the image to draw at the given location
	 */
	public void setBackgroundImageAt(int row, int col, GameImage img)
	{
		backGroundlayer[row][col] = preloadedImages.get(img.getPath());
	}

	/**
	 * Sets the image used for all background cells
	 * @param image the image to use
	 */
	public void setBackgroundImageForAll(GameImage img)
	{
		PImage background = preloadedImages.get(img.getPath());

		for(int row=0; row < backGroundlayer.length; row++)
			for(int col = 0; col < backGroundlayer[row].length; col++)
				backGroundlayer[row][col] = background;
	}

	/**
	 *Sets the Image for a cell in the foreground. 
	 * not be drawn
	 * @param row the row location of the cell
	 * @param col the column location of the cell
	 * @param img the image to draw at the given location
	 */
	public void setForegroundCellImageAt(int row, int col, GameImage img)
	{
		foreGroundLayer[row][col] = preloadedImages.get(img.getPath());
	}
	/**
	 * clears the Image for a cell in the foreground.
	 * not be drawn
	 * @param row the row location of the cell
	 * @param col the column location of the cell
	 * @param img the image to draw at the given location
	 */
	public void clearForegroundImageAt(int row, int col)
	{
		foreGroundLayer[row][col] = null;
	}

	/**
	 * Clears all the background cells
	 */
	public void clearBackGround()
	{
		clearCells(backGroundlayer);
		drawGraphics();
	}
	/**
	 * Clears all the foreground cells
	 */
	public void clearForeGround()
	{
		clearCells(foreGroundLayer);
		drawGraphics();
	}
	/**
	 * sets all cells in the given2d array to null to clear them	
	 * @param cells the 2d array to clear
	 */
	private void clearCells(PImage[][] cells)
	{	
		for(int row=0; row < cells.length; row++)
			for(int col = 0; col < cells[row].length; col++)
				cells[row][col] = null;
	}
	/**
	 * Draws the current cells to a buffered image. If the cells changes this 
	 * method should be called to update the displayed map
	 */
	public void drawGraphics()
	{
		int currX = 0;
		int currY = 0;


		synchronized (buffer)
		{
			buffer.beginDraw();
			buffer.background(Color.BLACK.getRGB());

			buffer.fill(Color.BLACK.getRGB());

			buffer.noStroke();

			for(int row=0; row < rows; row++)
			{
				currX = 0;
				for(int col = 0; col < cols; col++)
				{

					if(backGroundlayer[row][col] != null)
					 buffer.image(backGroundlayer[row][col], (float)currX, (float)currY, (float)cellWidth,(float)cellHeight);

					if(foreGroundLayer[row][col] != null)
						buffer.image(foreGroundLayer[row][col], (float)currX, (float)currY, (float)cellWidth,(float)cellHeight);

					currX+=cellWidth;
				}

				currY += cellHeight;
			}

			buffer.noFill();
			buffer.stroke(255);
			buffer.rect(0,0,(cols*cellWidth)-1,(rows*cellHeight)-1);

			buffer.endDraw();
		}
	}


/**
 * Overrides draw method from processing library
 */
public void draw()
{
	background(0);

	synchronized (buffer)
	{
		Dimension size = getSize();
		//place drawn map at the center of the display area
		image( buffer.get(0, 0, buffer.width, buffer.height) ,
				(int)((size.getWidth()/2)-(buffer.width/2)),
				(int)((size.getHeight()/2)-(buffer.height/2))
				);  
	}

}

public void keyPressed()
{
	gameController.keyPressed(key, keyCode);
}
}
