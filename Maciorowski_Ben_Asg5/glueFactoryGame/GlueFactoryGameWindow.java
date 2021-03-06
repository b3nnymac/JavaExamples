package glueFactoryGame;
/**
 * Graphical user interface Window for GlueFactoryGame
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Nov 9, 2014 - Created (Jordan Kidney)
 */

import glueFactoryGame.glueWorld.GameImage;
import glueFactoryGame.glueWorld.GlueWorld;
import glueFactoryGame.glueWorld.entities.Entity;

import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.awt.BorderLayout;


public class GlueFactoryGameWindow 
{
	private JFrame	frame;
	private JMenuItem mntmLoadNewGame;
	private GameDisplay gDisplay;
	private GameController gameController;
	private JLabel numUserBombsLbl;

	/**
	 * Constructor
	 */
	public GlueFactoryGameWindow(StudentGameController studentControl)
	{
		gameController = new GameController(this,studentControl);
		initialize();
	}

	/**
	 * Start method to show and run window
	 */
	public void run()
	{
		//setup file Chooser
		String fileName = "";
		String workingDir = System.getProperty("user.dir");
		JFileChooser fileChooser = new JFileChooser(workingDir);
		fileChooser.setDialogTitle("Choose a game file to load");
		frame.setVisible(true);
		gDisplay.requestFocusInWindow();

		int result = fileChooser.showOpenDialog(frame);


		if ( result == JFileChooser.APPROVE_OPTION )
		{
			fileName = fileChooser.getSelectedFile().getAbsolutePath();
			try
			{
				gameController.loadFile(fileName);
				drawWorld();
			}
			catch (Exception e)
			{
				frame.setVisible(false);
				frame.dispose();
				e.printStackTrace();
			}
		}
		else
		{
			frame.setVisible(false);
			frame.dispose();
		}
	}

	/**
	 * Start method to show and run window
	 */
	public void run(String fileName)
	{
		frame.setVisible(true);
		gDisplay.requestFocusInWindow();
		try
		{
			gameController.loadFile(fileName);
			drawWorld();
		}
		catch (Exception e)
		{
			frame.setVisible(false);
			frame.dispose();
			e.printStackTrace();
		}
	}

	
	/**
	 * Called to shutdown the main game window
	 */
	public void closeWindow()
	{
		frame.setVisible(false);
		frame.dispose();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize()
	{
		int windowWidth = 725;
		int windowHeight = 725;


		frame = new JFrame("Glue Factory");


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(windowWidth,windowHeight);
		frame.setResizable(false);

		//set window to center of the screen
		//Set Location to center of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		
		frame.setLocation((int)((screenSize.getWidth()/2)-(windowWidth/2)),
				(int)((screenSize.getHeight()/2)-(windowHeight/2)));


		//set up main game display
		gDisplay = new GameDisplay(gameController);
		gDisplay.buildDrawArea(GlueWorld.MAX_ROWS,GlueWorld.MAX_COLS,45,40);
		gDisplay.setBackgroundImageForAll(GameImage.GROUND);
		gDisplay.drawGraphics();

		frame.getContentPane().add(BorderLayout.CENTER, gDisplay);


		numUserBombsLbl = new JLabel("",JLabel.LEFT);
		frame.getContentPane().add(BorderLayout.NORTH, numUserBombsLbl );

		frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                gameController.shutDown();
                e.getWindow().dispose();
            }
        });
		
		
		setNumberOfUserBombs(0);
	}

	/**
	 * draws the world based upon the current configuration in the world
	 */
	public void drawWorld()
	{
		GlueWorld world = GlueWorld.getInstance();
		for(int row=0; row < world.getNumRows(); row++ )
		{
			for(int col=0; col < world.getNumCols(); col++)
			{
				Entity e = world.get(row, col);
				if(e == null) gDisplay.clearForegroundImageAt(row, col);
				else
				{
					gDisplay.setForegroundCellImageAt(row, col, e.getDisplayImage());
				}
			}
		}

		gDisplay.drawGraphics();
	}

	/**
	 * Sets the display amount of how many bombs the user currently has
	 * @param num the number of bombs the user has
	 */
	public void setNumberOfUserBombs(int num)
	{
		GlueWorld world = GlueWorld.getInstance();
		numUserBombsLbl.setText(String.format("(Type H for help) NUM BOMBS: %3d    Num Bombs in World: %3d",num,world.numStickyItemsInWorkd()));
	}

	/**
	 * Pops up the help window showing the basic commands
	 */
	public void showHelpWindow()
	{
		String commands ="";

		commands += "W = move up\n";
		commands += "A = move left\n";
		commands += "S = move down\n";
		commands += "D = move right\n\n";
		

		commands += "   Up arrow = drop glue bomb up\n";
		commands += " Left arrow = drop glue bomb left\n";
		commands += "Right arrow = drop glue bomb right\n";
		commands += " Down arrow = drop glue bomb down\n\n";
		
		commands += "Space bar = show world state information\n";
		commands += "ESC = Exit Game\n";
		
		JOptionPane.showMessageDialog(frame,commands,"Help",JOptionPane.OK_OPTION);
	}
	
	/**
	 * Displays the message in a popup window
	 * @param message
	 */
	public void showMessage(String message)
	{
		JOptionPane.showMessageDialog(frame,message,"Message",JOptionPane.OK_OPTION);
	}
}
