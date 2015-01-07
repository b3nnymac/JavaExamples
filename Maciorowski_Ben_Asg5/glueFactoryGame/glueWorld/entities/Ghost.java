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
public class Ghost extends MovingEntity
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
    public Ghost(int row, int col,GameImage displayImg,GameImage stuckImage)
    {
        super (row,  col, displayImg, stuckImage);
        world = GlueWorld.getInstance();
    }

    public void move() {
        int moveNum = randomNumber.getInstance().next(4);
        Direction direction = Direction.ALL_DIRECTIONS[moveNum];
        int[] move = Direction.getDirectionOffsets(direction);

        int newRow = getRow() + move[0];
        int newCol = getCol() + move[1];

        if(world.inBounds(newRow, newCol)){

            while (world.inBounds(newRow, newCol)&&!world.canLocationbeMovedOnto(newRow, newCol) &&  !world.get(newRow, newCol).isEntitySticky()){

                switch (direction){
                    case UP :    newRow--; break;
                    case LEFT :    newCol--; break;
                    case DOWN : newRow++; break;
                    case RIGHT : newCol++; break;

                }

            }
            world.remove(this);
            if(world.get(newRow, newCol) != null && world.get(newRow, newCol).isEntitySticky()){
                setEntityStuck();
            }
            world.place(newRow, newCol, this);  
            setLocation(newRow, newCol);
        }
    }
}