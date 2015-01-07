import glueFactoryGame.*;

import java.awt.EventQueue;

/**
 * Startup point for GlueFactory Game
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Nov 9, 2014 - Created (Jordan Kidney)
 */
public class Loader 
{
	/**
	 * Launch the application.
	 */
	public static void main(String[] args)
	{
		try
		{
			String fileName = "hell.txt";
			if(args.length > 0) fileName = args[0];


			StudentGameController control = new BenMacGameController();
			GlueFactoryGameWindow window = new GlueFactoryGameWindow(control);

			if(fileName != null)
				window.run(fileName);
			else
				window.run(fileName);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
