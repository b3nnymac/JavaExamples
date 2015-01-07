package glueFactoryGame;
/**
 * Thread used to periodically signal updates to the world
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Nov 10, 2014 - Created (Jordan Kidney)
 */
public class UpdateThread extends Thread 
{
	private GameController controller;
	private Boolean shutDown;
	private long updateDelay;

	/**
	 * Constructor
	 * @param controller reference  to the main game controller
	 * @param updateDelay the amount of time to delay between updates (in mil sec)
	 */
	public UpdateThread(GameController controller,long updateDelay )
	{
		this.controller = controller;
		this.updateDelay = updateDelay;
		shutDown = new Boolean(false);	
	}


	/**
	 * Request for the thread to shut down
	 */
	public synchronized  void shutDown()
	{
		synchronized (shutDown)
		{
			shutDown = new Boolean(true);
		}	
	}

	/**
	 * check for shut down
	 */
	private boolean shouldShutDown()
	{
		boolean res = false;

		synchronized (shutDown)
		{
			res = (boolean) shutDown;	
		}

		return res;
	}
	
	/**
	 * checks to see if the thread is still running 
	 */
	public boolean isRunning()
	{
		boolean res = false;

		synchronized (shutDown)
		{
			res = (boolean) shutDown;	
		}

		return res;
	}

	/**
	 * Main method for execution of the updated thread
	 */
	public void run()
	{
		try
		{
			while(!shouldShutDown())
			{
				synchronized (controller)
				{
					controller.updateWorldState();
					if(controller.checkForGameEnd()) break;
				}   

				sleep(updateDelay);
				

			}
		}
		catch(Exception error)
		{
			error.printStackTrace();
		}

	}

}
