import java.io.*;
import userCommunication.*;
import java.util.*;

public class FileLoader {
    private UserInteraction comm = new UserInteraction();
    private Scanner inputFile;   
    private HealthTracker tracker = new HealthTracker();

    private int count = 0;

    /**
     * Opens a file and determines if it is valid witha track catch statement
     * @param a file name
     */
    public  void  openFile(String fileName){
        try{
            inputFile = new Scanner(new File(fileName));
            readFile();
            foodExcerciseLoader();
            tracker.run();
        }
        catch(Exception error){
            System.out.println("Could not load file");

        }
    }

    /**
     * reads the contents of a file, parsing specific portions, and instantiate variables to create Person objects, and 
     * add them to an array
     */
    public void readFile(){

        while (inputFile.hasNext() && count <= HealthTracker.DATABASE_SIZE){
            String line = inputFile.nextLine();
            String[] parts = line.split(",");
            String fullName = parts[0];
            int age = Integer.parseInt(parts[1]);
            double weight = Double.parseDouble(parts[2]);
            double height = Double.parseDouble(parts[3]);
            double totalCalories = Double.parseDouble(parts[4]);
            double activityLevel = Double.parseDouble(parts[5]);
            String gender = parts[6];

            tracker.addTrainee(new Person(fullName, weight, age, height, gender, activityLevel));
            count++;
        }

        comm.print(count + " Trainees were added to the database\n");
    }

    /**
     * Opens a pre-determined text file containing a pre-formatted list of foods and excercises
     * creates and adds foods and excercises to arrays to be accessed by the health tracker class
     */
    public void foodExcerciseLoader(){
        try{inputFile = new Scanner(new File("foodExDb.txt"));}
        catch(Exception error){System.out.println("Could not open file");}

        String line = inputFile.nextLine();
        String[] parts = line.split(",");
        int numOfFoods = Integer.parseInt(parts[0]);
        int numOfExcercises = Integer.parseInt(parts[1]);

        tracker.instantiateFoodArray(numOfFoods);

        for (int foodCount = 0; foodCount<numOfFoods; foodCount++){
            line = inputFile.nextLine();
            String[] foodParts = line.split(",");
            String foodName = foodParts[0];
            int calories = Integer.parseInt(foodParts[1]);
            tracker.addFoodToArray(new Food(foodName, calories), foodCount);
        }
        comm.print(""+ numOfFoods + " foods were added to the database\n");

        tracker.instantiateExcerciseArray(numOfExcercises);
        for (int excerciseCount = 0; excerciseCount< numOfExcercises; excerciseCount++){
            line = inputFile.nextLine();
            String[] excerciseParts = line.split(",");
            String excerciseName = excerciseParts[0];
            int calories = Integer.parseInt(excerciseParts[1]);
            tracker.addExcerciseToArray(new Excercise(excerciseName, calories),excerciseCount);
        }
        comm.print(""+ numOfExcercises + " excercise were added to the database\n");
        tracker.printDBArray();
        comm.pause();
    }

}
