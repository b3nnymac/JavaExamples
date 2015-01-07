package glueFactoryGame;
import glueFactoryGame.Direction;
import glueFactoryGame.StudentGameController;
import glueFactoryGame.glueWorld.*;
import glueFactoryGame.glueWorld.entities.*;
import java.io.*;
import java.util.*;

/**
 * Write a description of class BenMacGameController here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BenMacGameController implements StudentGameController
{
    private String gameFile = "";
    private Scanner inputFile; 
    private GlueWorld world;
    private EntityFactory factory;
    private int numberBombsUserHas = 0;
    private ArrayList<MovingEntity> movingEntities;
    private User user;
    private int bugCount = 0;

    public BenMacGameController()
    {

    }

    /**
     * Returns the current number of bobs the user has
     * @return the number of bombs the user currently has
     */
    public int numBombsUserHas(){return user.getNumberGlueBombs();}

    /**
     * Called to determine if the user has won the game or not
     * @return true if they have won, false otherwise
     */
    public boolean hasUserWonGame(){
        boolean wonGame=false;
        if(bugCount ==  0){
            wonGame=true;}

        return wonGame;}

    /**
     * Deals with a request from the user to move the avatar in the desired direction
     * @param where the direction to move
     */
    public void userMoveRequest(Direction where){
        Direction.getDirectionOffsets(where);
        user.move(where);

    }

    /**
     * Deals with a request from the user to place glue in a location where the avatar currently is
     * @param where the direction to place the glue in
     */
    public void placeGlue(Direction where){
        Direction.getDirectionOffsets(where);
        user.placeGlueBomb(where);
    }

    /**
     * Called to request an update to  the world state including moving NPC's.
     * Should properly move all NPC's in the world and update basic game statistics 
     * if there are any
     */
    public void updateWorldState(){
        for(int num = 0; num < movingEntities.size(); num++) {
            if(!movingEntities.get(num).isStuck()){ movingEntities.get(num).move();}
            else {movingEntities.remove(num); bugCount--;}
        }

    }             

    /**
     * Called to load a game file into the system
     * @param fileName the path and name of the file to load
     * @throws Exception thrown if any major errors occur while loading the file 
     */
    public void loadFile(String fileName) throws Exception{
        try{
            world = GlueWorld.getInstance(); // gets single instance of the Glue world shared by the entire program
            inputFile = new Scanner(new File(fileName));
            System.out.println("Load file: " + fileName);
            factory = new EntityFactory();
            gameFile = fileName;
            readFile();

        }
        catch(Exception error){
            System.out.println("Could not load file");

        }

    }

    public void readFile() throws UnkownEntity, LocationOccupiedException
    {
        movingEntities = new ArrayList<MovingEntity>();

        MovingEntity newMovingEntity;
        Entity newEntity;

        String line = inputFile.nextLine();
        String[] userDetails = line.split(","); 
        int entityStartingRowNumber = Integer.parseInt(userDetails[0]);
        int entityStartingColNumber = Integer.parseInt(userDetails[1]);
        String entityImage = userDetails[2];
        int userImageNum = Integer.parseInt(userDetails[3]);
        numberBombsUserHas = Integer.parseInt(userDetails[4]);

        user = new User(entityStartingRowNumber, entityStartingColNumber, getUserGameImage(userImageNum), numberBombsUserHas);

        world.place(entityStartingRowNumber, entityStartingColNumber, user);

        while(inputFile.hasNext()){
            int speed = 0;

            line = inputFile.nextLine();
            String[] entityDetails = line.split(","); 
            entityStartingRowNumber = Integer.parseInt(entityDetails[0]);
            entityStartingColNumber = Integer.parseInt(entityDetails[1]);
            entityImage = entityDetails[2];

            GameImage displayImg;

            if(entityDetails.length == 4){
                speed = Integer.parseInt(entityDetails[3]);}
            switch(entityImage){
                case "SOLVENT_BOT" : 
                newEntity = new SolventBot(entityStartingRowNumber, entityStartingColNumber, GameImage.SOLVENT, speed);
                movingEntities.add((SolventBot)newEntity);
                break;
                case "SPEEDY" : 
                newEntity =  new Speedy(entityStartingRowNumber, entityStartingColNumber, GameImage.ENEMY2, GameImage.ENEMY2_STUCK, speed);
                movingEntities.add((Speedy)newEntity);
                bugCount++;
                break;                    
                case "SLOWPOKE" : 
                newEntity =  new SlowPoke(entityStartingRowNumber, entityStartingColNumber, GameImage.ENEMY1, GameImage.ENEMY1_STUCK);
                movingEntities.add((SlowPoke)newEntity);
                bugCount++;
                break;
                case "GLUE" : 
                newEntity =  new Entity(entityStartingRowNumber, entityStartingColNumber, GameImage.GLUE, true, true);                                               
                break; 
                case "GHOST" : 
                newEntity =  new Ghost(entityStartingRowNumber, entityStartingColNumber, GameImage.ENEMY3, GameImage.ENEMY3_STUCK);
                movingEntities.add((Ghost)newEntity);
                bugCount++;
                break; 
                case "WALL" : 
                newEntity =  new Entity(entityStartingRowNumber, entityStartingColNumber, GameImage.WALL, false, false);
                break;
                case "ROCK" : 
                newEntity =  new Entity(entityStartingRowNumber, entityStartingColNumber, GameImage.ROCK, false, false);
                break;
                case "PLANT" : 
                newEntity =  new Entity(entityStartingRowNumber, entityStartingColNumber, GameImage.PLANT, false, false);
                break;
                default  :
                throw new UnkownEntity("This entity type does not exist");
            }

            if(world.place(entityStartingRowNumber, entityStartingColNumber, newEntity) != null){throw new LocationOccupiedException();};
        }

    }

    private GameImage getUserGameImage(int userImageNumber){
        GameImage userImage = null;
        switch(userImageNumber){
            case 1 :
            userImage = userImage.USER1;            
            break;
            case 2 :
            userImage = userImage.USER2;            
            break;
            case 3 :
            userImage = userImage.USER3;            
            break;
            case 4 :
            userImage = userImage.USER4;            
            break;

        }

        return userImage; 
    }
}
