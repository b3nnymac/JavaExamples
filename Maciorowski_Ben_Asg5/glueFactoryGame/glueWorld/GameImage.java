package glueFactoryGame.glueWorld;

/**
 * Constants used for all images in the system
 * @author Jordan Kidney
 * @version 1.0
 * Last Modified: Nov 10, 2014 - Created (By Jordan Kidney)
 */
public enum GameImage 
{
	GROUND("images/Wood Block.png"),
	 WALL("images/Stone Block.png"),
	 ROCK("images/Rock.png"),
	PLANT("images/Plant.png"),
	
    USER1("images/User1.png"),
    USER2("images/User2.png"),
    USER3("images/User3.png"),
    USER4("images/User4.png"),
	
    SOLVENT("images/Solvent.png"),
    GLUE("images/Glue.png"),
	
    
    ENEMY1("images/Enemy1.png"),
    ENEMY2("images/Enemy2.png"),
    ENEMY3("images/Enemy3.png"),

    
    ENEMY1_STUCK("images/Enemy1Stuck.png"),
    ENEMY2_STUCK("images/Enemy2Stuck.png"),
    ENEMY3_STUCK("images/Enemy3Stuck.png");
  
    
	GameImage(String path) { filePath = path; }
	public String getPath() { return filePath; }
	private String filePath;
}
