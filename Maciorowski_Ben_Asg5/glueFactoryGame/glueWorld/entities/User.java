package glueFactoryGame.glueWorld.entities;
import glueFactoryGame.Direction;
import glueFactoryGame.glueWorld.GameImage;
import glueFactoryGame.glueWorld.GlueWorld;

/**
 * Write a description of class User here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class User extends MovingEntity
{

    private GameImage displayImg;
    private GameImage unStuckImage;
    private GlueWorld world;
    private int numberGlueBombs;
    private Entity newGlueBomb;

    /**
     * Constructor for objects of class User
     */
    public User(int row, int col, GameImage displayImg, int numberGlueBombs)
    {

        super (row,  col, displayImg);
        world = GlueWorld.getInstance(); 
        this.numberGlueBombs = numberGlueBombs;
    }

    public void move(Direction where) { 
        int[] move = Direction.getDirectionOffsets(where);
        int newRow = getRow() + move[0];
        int newCol = getCol() + move[1];

        if (world.canLocationbeMovedOnto(newRow, newCol) && world.inBounds(newRow, newCol)){
            if(world.get(newRow, newCol) != null && world.get(newRow, newCol).isEntitySticky()){
                world.remove(world.get(newRow, newCol));
                numberGlueBombs++;

            }
            world.remove(this);

            world.place(newRow, newCol, this);  
            setLocation(newRow, newCol);
        }

    }

    public int getNumberGlueBombs(){return numberGlueBombs; };

    public void placeGlueBomb(Direction where){
        int[] coordsWhereBombToBePlace = Direction.getDirectionOffsets(where);
        int bombRow = getRow() + coordsWhereBombToBePlace[0];
        int bombCol = getCol() + coordsWhereBombToBePlace[1];

        if (world.canLocationbeMovedOnto(bombRow, bombCol) && world.get(bombRow, bombCol)==null && numberGlueBombs > 0){
            newGlueBomb =  new Entity(bombRow, bombCol, GameImage.GLUE, true, true);
            world.place(bombRow, bombCol, newGlueBomb);
            numberGlueBombs--;
        }
    }
}
