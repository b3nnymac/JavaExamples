package glueFactoryGame.glueWorld.entities;
import glueFactoryGame.*;
import glueFactoryGame.glueWorld.GameImage;
import glueFactoryGame.glueWorld.GlueWorld;

/**
 * Write a description of class SolventBot here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class SlowPoke extends MovingEntity
{
    private boolean stuck;
    private boolean stickable; // used to indicate if the moving entity can be stuck by glue 
    private GameImage stuckImage;
    private GameImage unStuckImage;
    private GameRandomNumber randomNumber;
    private Direction direction;
    private GlueWorld world;

    /**
     * Constructor for objects of class User
     */
    public SlowPoke(int row, int col,  GameImage unStuckImage,  GameImage stuckImage)
    {
        super (row,  col, unStuckImage, stuckImage);
        world = GlueWorld.getInstance();
    }   

    public void move() {
        int moveNum = randomNumber.getInstance().next(4);
        int[] move = Direction.getDirectionOffsets(Direction.ALL_DIRECTIONS[moveNum]);
        int newRow = getRow() + move[0];
        int newCol = getCol() + move[1];

        if (world.canLocationbeMovedOnto(newRow, newCol) && world.inBounds(newRow, newCol)){
            world.remove(this);
            if(world.get(newRow, newCol) != null && world.get(newRow, newCol).isEntitySticky()){
                setEntityStuck();
            }
            world.place(newRow, newCol, this);  
            setLocation(newRow, newCol);

        }

    }
}