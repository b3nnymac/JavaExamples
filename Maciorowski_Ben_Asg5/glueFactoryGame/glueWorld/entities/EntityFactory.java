package glueFactoryGame.glueWorld.entities;

import glueFactoryGame.glueWorld.GameImage;
import glueFactoryGame.glueWorld.GlueWorld;

import java.util.Scanner;

/**
 * Used to build each specific Entity for the game
 * @author Jordan Kidney
 * @version 1.0
 * Last Modified: Nov 10, 2014 - Created (By Jordan Kidney)
 */
public class EntityFactory 
{

    private Scanner inputFile; 
    /**
     * Builds a single entity based upon information read in from the scanner and adds it to
     * the world
     * @param info the scanner to use
     * @param world the world object used to validate and set up information
     * @throws UnknownEntity if the data could not be interpreted as a proper entity
     * @return returns the entity that was made
     */
    public static Entity buildFromStream(Scanner info, GlueWorld world)
    {	
        Entity entity = null;

        return entity;
    }

//     public void readFile(String line){
//         Entity newEntity = null;
// 
//         String line = inputFile.nextLine();
//         String[] userDetails = line.split(","); 
//         int userStartingRowNumber = Integer.parseInt(userDetails[0]);
//         int userStartingColNumber = Integer.parseInt(userDetails[1]);
//         String userName = userDetails[2];
//         String userImageNum = userName+userDetails[3];
//         numberBombsUserHas = Integer.parseInt(userDetails[4]);
//         newEntity = new User(userStartingRowNumber, userStartingRowNumber, GameImage.USER3, numberBombsUserHas);
// 
//         world.place(userStartingRowNumber, userStartingRowNumber, newEntity);
// 
//         while(inputFile.hasNext()){
// 
//             line = inputFile.nextLine();
//             String[] entityDetails = line.split(","); 
//             int entityStartingRowNumber = Integer.parseInt(entityDetails[0]);
//             int entityStartingColNumber = Integer.parseInt(entityDetails[1]);
//             String entityImage = entityDetails[2];
//             int solventBotSpeed = 0;
//             GameImage displayImg;
// 
//             int DELETE1 = entityDetails.length;;
// 
//             if(entityDetails.length == 4){
//                 solventBotSpeed = Integer.parseInt(entityDetails[3]);}
//             switch(entityImage){
//                 case "SOLVENT_BOT" : 
//                 newEntity =  new SolventBot(entityStartingRowNumber, entityStartingColNumber, GameImage.SOLVENT, solventBotSpeed);                       
//                 break;
//                 case "SPEEDY" : 
//                 newEntity =  new Speedy(entityStartingRowNumber, entityStartingColNumber, GameImage.ENEMY2, GameImage.ENEMY2_STUCK);                       
//                 break;                    
//                 case "SLOWPOKE" : 
//                 newEntity =  new Speedy(entityStartingRowNumber, entityStartingColNumber, GameImage.ENEMY1, GameImage.ENEMY1_STUCK);                       
//                 break;
//                 case "GLUE" : 
//                 newEntity =  new Speedy(entityStartingRowNumber, entityStartingColNumber, GameImage.GLUE, GameImage.GLUE);                                               
//                 break; 
//                 case "GHOST" : 
//                 newEntity =  new Ghost(entityStartingRowNumber, entityStartingColNumber, GameImage.ENEMY3, GameImage.ENEMY3_STUCK);                                               
//                 break; 
//                 case "WALL" : 
//                 newEntity =  new Entity( entityStartingRowNumber, entityStartingColNumber, GameImage.WALL);
//                 break;
//                 case "ROCK" : 
//                 newEntity =  new Entity( entityStartingRowNumber, entityStartingColNumber, GameImage.ROCK);
//                 break;
//                 case "PLANT" : 
//                 newEntity =  new Entity( entityStartingRowNumber, entityStartingColNumber, GameImage.PLANT);
//                 break;
//             }
//             world.place(entityStartingRowNumber, entityStartingColNumber, newEntity);
//         }
//     }

}
