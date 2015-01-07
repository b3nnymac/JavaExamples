package glueFactoryGame;
/**
 * Random number maker for the game
 * @author Jordan Kidney
 * @version 1.0
 * Last Modified: March 14, 2014 - Created (By Jordan Kidney)
 *                  Nov 10, 2014 - added next(int range)
 */

import java.util.Random;

public class GameRandomNumber 
{
	//uses singleton design pattern
	private static GameRandomNumber rngMaker = null;
	private static boolean debug;

	public static void setDebug(boolean value) { debug = value; } 

	public static GameRandomNumber getInstance()
	{
		if(rngMaker == null) rngMaker = new GameRandomNumber();
		return rngMaker;
	}

	private Random rng;

	//for debug
	private int current_number;
	private int[] debugNumbers = 
		{ 0,3,7,6,8,9,1,2,4,7,0,9,6,7,1,4,6,8,2,6,2,2,4,5,3,3,1,6,9,
			4,8,9,7,7,7,5,3,2,1,5,7,8,9,0,7,2,3,7,2,8,4,9,3,8,7,9,8,9,9,
			4,6,8,2,2
		};

	private GameRandomNumber() 
	{
		rng = new Random();
		current_number = 0;
	}

	/**
	 * returns the next random number between 0 and 9 inclusive
	 * @return random number
	 */
	public int next()
	{
		int number = 0;
		if(debug)
		{
			number = debugNumbers[current_number];
			current_number++;
			current_number %= debugNumbers.length;
		}
		else
			number = rng.nextInt(10);

		return number;
	}

	
	/**
	 * returns the next random number between 0 and Range exclusive
	 * @return random number
	 */
	public int next(int range)
	{
		return next() % range;
	}
}
