package glueFactoryGame;

public interface StudentGameController 
{
	/**
	 * Returns the current number of bobs the user has
	 * @return the number of bombs the user currently has
	 */
	public int numBombsUserHas();
	
	/**
	 * Called to determine if the user has won the game or not
	 * @return true if they have won, false otherwise
	 */
	public boolean hasUserWonGame();
	
	/**
	 * Deals with a request from the user to move the avatar in the desired direction
	 * @param where the direction to move
	 */
	public void userMoveRequest(Direction where);
	/**
	 * Deals with a request from the user to place glue in a location where the avatar currently is
	 * @param where the direction to place the glue in
	 */
	public void placeGlue(Direction where);
	
	/**
	 * Called to request an update to  the world state including moving NPC's.
	 * Should properly move all NPC's in the world and update basic game statistics 
	 * if there are any
	 */
	public void updateWorldState();
	
	/**
	 * Called to load a game file into the system
	 * @param fileName the path and name of the file to load
	 * @throws Exception thrown if any major errors occur while loading the file 
	 */
	public void loadFile(String fileName) throws Exception;
}
