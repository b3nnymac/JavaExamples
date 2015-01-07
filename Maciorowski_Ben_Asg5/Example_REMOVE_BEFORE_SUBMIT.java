import glueFactoryGame.*;
import glueFactoryGame.glueWorld.*;

public class Example_REMOVE_BEFORE_SUBMIT
{
    public static void main(String[] args)
    {
        //get access to the single instance of a random number generator 
        //used for the program 
        GameRandomNumber rng = GameRandomNumber.getInstance();
        System.out.println(rng.next(10));
        
        //get access to the single instance of the worl object
        GlueWorld world = GlueWorld.getInstance();
        
    }
}
