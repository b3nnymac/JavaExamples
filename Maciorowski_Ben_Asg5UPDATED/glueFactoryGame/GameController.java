package glueFactoryGame;
/**
 * Main game controller for all important actions
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Nov 9, 2014 - Created (Jordan Kidney)
 */

import glueFactoryGame.glueWorld.GlueWorld;
import glueFactoryGame.glueWorld.entities.Entity;

import java.awt.event.KeyEvent;

public class GameController 
{
	private GlueFactoryGameWindow mainGameWindow;
	private StudentGameController studentControl;
	private UpdateThread worldStateUpdateThread;

    
	/**
	 * Constructor
	 * @param mainGameWindow main game window reference
	 */
	public GameController(GlueFactoryGameWindow mainGameWindow,StudentGameController studentControl)
	{
		this.mainGameWindow = mainGameWindow;
		this.studentControl = studentControl;
		worldStateUpdateThread = new UpdateThread(this, 500);
	}

	/**
	 * Called to deal with specific key input for the game 
	 * @param key the character key pressed
	 * @param keyCode the key code number
	 */
	public void keyPressed(char key, int keyCode)
	{
		synchronized (this) // used to lock access when user moves ( if an update to the world state is occurring ) 
		{
			switch(keyCode)
			{
				//help command
				case KeyEvent.VK_H: mainGameWindow.showHelpWindow(); break;
				case KeyEvent.VK_SPACE: showWorldStateInfo(); break;

				//User Move Requests
				case KeyEvent.VK_W: studentControl.userMoveRequest(Direction.UP); break;
				case KeyEvent.VK_A: studentControl.userMoveRequest(Direction.LEFT); break;
				case KeyEvent.VK_S: studentControl.userMoveRequest(Direction.DOWN); break;
				case KeyEvent.VK_D: studentControl.userMoveRequest(Direction.RIGHT); break;

				//User Place Glue Requests
				case KeyEvent.VK_UP   : studentControl.placeGlue(Direction.UP); break;
				case KeyEvent.VK_LEFT : studentControl.placeGlue(Direction.LEFT); break;
				case KeyEvent.VK_DOWN : studentControl.placeGlue(Direction.DOWN); break;
				case KeyEvent.VK_RIGHT: studentControl.placeGlue(Direction.RIGHT); break;
			}


			mainGameWindow.drawWorld();
			mainGameWindow.setNumberOfUserBombs(studentControl.numBombsUserHas());

			checkForGameEnd();
		}


	}

	/**
	 * Shows text based information about the current world state
	 */
	private void showWorldStateInfo()
	{
	  GlueWorld world = GlueWorld.getInstance();
	  String state = "";
	 
	  for(int row = 0; row < world.getNumRows(); row++)
		  for(int col=0; col < world.getNumCols(); col++)
		  {
			  Entity curr = world.get(row, col);
			  
			  if(curr != null) state += curr.toString() +"\n";
		  }
	  
	  
	  mainGameWindow.showMessage(state);
	}
	
	
	/**
	 * Checks to see if the user has won or lost the game. This will shutdown down all code
	 * if either case has occurred.
	 * @return true if the program should shut down, false otherwise
	 */
	public boolean checkForGameEnd()
	{
		boolean result = false;
		synchronized(studentControl)
		{
			result = checkForUserWin();
			if( !result )
				result = checkForUserLost();

			if(result)
			{
				worldStateUpdateThread.shutDown();
				mainGameWindow.closeWindow();
				System.exit(0); // cheap hack put in place for now, should be fixed in the future
			}
		}

		return result;
	}


	/**
	 * Determines if the user has lost the game or not. If the user has lost, the game will
	 * display a message
	 */
	private  boolean checkForUserLost()
	{
		boolean result = false;

		GlueWorld world = GlueWorld.getInstance();
		int glueInWorldCount = world.numStickyItemsInWorkd();
		int userGlueCount = studentControl.numBombsUserHas();
		result = ( glueInWorldCount == 0 && userGlueCount == 0);

		if(result) 
			mainGameWindow.showMessage("You are out of glue bombs and have lost the game");

		return result;
	}


	/**
	 * Determines if the user has won the game or not. If the user has won, the game will
	 * display a message
	 */
	private boolean checkForUserWin()
	{
		boolean result = studentControl.hasUserWonGame();
		if(result) 
			mainGameWindow.showMessage("You Win!!!!!!!!!!!!!!!");

		return result;
	}


	/**
	 * Called to request an update to  the world state including moving NPC's.
	 * Should properly move all NPC's in the world and update basic game statistics 
	 * if there are any
	 */
	public void updateWorldState()
	{
		studentControl.updateWorldState();
		mainGameWindow.drawWorld();
	}

	/**
	 * Called to shut down the game
	 */
	public void shutDown()
	{
		if(worldStateUpdateThread.isRunning())
		{
			worldStateUpdateThread.shutDown();
			try
			{
				worldStateUpdateThread.wait();
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}

	/**
	 * Loads the given game file into the system
	 * @param fileName the absolute path of the file to open
	 * @throws Exception if any errors occur while loading the file
	 */
	public void loadFile(String fileName) throws Exception
	{  
		try
		{
			studentControl.loadFile(fileName);
			mainGameWindow.setNumberOfUserBombs(studentControl.numBombsUserHas());
			worldStateUpdateThread.start();
		}
		catch(Exception err)
		{
			String errorMsg = "Student code raised an exception: " + err.getClass().getName() + " : Message = " + err.getMessage(); 
			throw new Exception(errorMsg);
		}
	}
}
