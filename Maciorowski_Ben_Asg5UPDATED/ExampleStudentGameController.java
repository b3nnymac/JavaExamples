import glueFactoryGame.Direction;
import glueFactoryGame.StudentGameController;
import glueFactoryGame.glueWorld.*;
import glueFactoryGame.glueWorld.entities.*;

/**
 * Example of a Student implementation of the studentGameController interface
 * 
 * NOTE: Your controller should be properly putinto one of the packages 
 * 
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Nov 9, 2014 - Created (Jordan Kidney)
 */
public class ExampleStudentGameController implements StudentGameController 
{
    /**
     * Returns the current number of bobs the user has
     * @return the number of bombs the user currently has
     */
    public int numBombsUserHas()
    {
        return 10;
    }

    /**
     * Called to determine if the user has won the game or not
     * @return true if they have won, false otherwise
     */
    public boolean hasUserWonGame()
    {
        return false;
    }

    /**
     * Deals with a request from the user to move the avatar in the desired direction
     * @param where the direction to move
     */
    public void userMoveRequest(Direction where)
    {
        System.out.println("User Move: " + where);
    }

    /**
     * Deals with a request from the user to place glue in a location where the avatar currently is
     * @param where the direction to place the glue in
     */
    public void placeGlue(Direction where)
    {
        System.out.println("User place glue: " + where);
    }

    /**
     * Called to request an update to  the world state including moving NPC's.
     * Should properly move all NPC's in the world and update basic game statistics 
     * if there are any
     */
    public void updateWorldState()
    {
        System.out.println("Call to update world state");
    }

    /**
     * Called to load a game file into the system
     * @param fileName the path and name of the file to load
     * @throws Exception thrown if any major errors occur while loading the file 
     */
    public void loadFile(String fileName) throws Exception
    {
        try{
            GlueWorld world = GlueWorld.getInstance(); // gets single instance of the Glue world shared by the entire program
            System.out.println("Load file: " + fileName);
            EntityFactory factory = new EntityFactory();
            readFile();
            world.place (new Entity(0,0, GameImage.USER4));
        }
        catch(Exception error){
            System.out.println("Could not load file");

        }
        // factory.buildFromStream(info, world) <-- possible method for you to complete
        //                                          used to build a single entity from an input
        //                                          file source

        // to add an Entity into the Game world you must first properly set the row and col location in the
        //object and then you must call world.place
    }

    public void readFile(){}
    // 
    //         while (inputFile.hasNext()){ 
    //             String line = inputFile.nextLine();
    //             String[] info = line.split(",");
    //             int numberOfRows = Integer.parseInt(info[0]);
    //             creatureStable = new Stable(numberOfRows);
    //             int numberOfCreatures = Integer.parseInt(info[1]);
    // 
    //             for(int count = 0; count < numberOfRows; count++) 
    //             {
    //                 line = inputFile.nextLine();
    //                 String[] rowDetails = line.split(","); 
    //                 String rowName = rowDetails[0];
    //                 int numberOfStalls = Integer.parseInt(rowDetails[1]);
    //                 creatureStable.addRow(count, rowName);
    // 
    //                 for(int stallCount = 0;   numberOfStalls != 0;  numberOfStalls--) {
    //                     line = inputFile.nextLine();
    //                     String[] stallDetails = line.split(","); 
    //                     String stallName = stallDetails[0];
    //                     String stallType = stallDetails[1];
    //                     String occupantInformation = stallDetails[2];
    //                     creatureStable.getSpecificRow(count).addStall(stallName, stallType, occupantInformation);
    // 
    //                 }
    //             }
    // 
    //             while(inputFile.hasNext()){
    //                 line = inputFile.nextLine();
    //                 String[] creatureDetails = line.split(","); 
    //                 String creatureName = creatureDetails[0];
    //                 String creatureType = creatureDetails[1];
    //                 int age = Integer.parseInt(creatureDetails[2]);
    //                 int weight = Integer.parseInt(creatureDetails[3]);
    //                 int specialAttributeValue = Integer.parseInt(creatureDetails[4]);
    //                 for(Row row : creatureStable.getRowsArray()){
    //                     for(Stall stall : row.getStalls()){
    //                         if(creatureName.equals(stall.getOccupant())){
    //                             stall.addCreature(creatureName, creatureType, age, weight, specialAttributeValue);
    // 
    //                         }
    //                     }
    //                 }
    // 
    //             }
    //         }

}

