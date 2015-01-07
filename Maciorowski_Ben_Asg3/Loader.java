/**
 * Main startup point for the programL
 * @author Jordan Kidney
 * @version 1.0
 * 
 * Last Modified: Aug 19, 2014 - Created (Jordan Kidney)
 */
public class Loader 
{
    public static void main(String[] args)
    {
        FileLoader loader = new FileLoader();

        if(args.length == 1){
            loader.openFile(args[0]);
        }
        else if (args.length == 0) {
            System.out.println("Missing command line argument. \nProgram will now terminate.");}
        else
            System.out.println("Too many file names entered. \nProgram will now terminate.");	

    }        
}

