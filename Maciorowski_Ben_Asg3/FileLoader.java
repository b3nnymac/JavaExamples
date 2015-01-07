import java.io.*;
import userCommunication.*;
import java.util.*;

public class FileLoader {
    private UserInteraction comm = new UserInteraction();
    private Scanner inputFile;   
    private HealthTracker tracker = new HealthTracker();

    private PersonDatabase personArrayList;
    private FoodDatabase foods;
    private ExcerciseDatabase excercises;
    String databaseFile ="";
    private int count = 0;

    /**
     * Opens a file and determines if it is valid witha track catch statement
     * @param fileName the name of the given file 
     */
    public  void  openFile(String fileName){
        try{
            databaseFile = fileName;
            inputFile = new Scanner(new File(fileName));
            readFile();
            foodExcerciseLoader();
            tracker.run(personArrayList, foods, excercises);
            saveToFile();
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
        personArrayList = new PersonDatabase();
        while (inputFile.hasNext()){ 
            String line = inputFile.nextLine();
            String[] info = line.split("#");
            String healthInfo = info[0];
            String weightHistory = info[1];
            String[] weights = weightHistory.split(",");
            ArrayList<Double> weightList = new ArrayList<Double>();
            for(int index = 0; index < weights.length; index++){
                weightList.add(Double.parseDouble(weights[index]));
            }

            String[] parts = healthInfo.split(",");
            String fullName = parts[0];
            int age = Integer.parseInt(parts[1]);
            double weight = Double.parseDouble(parts[2]);
            double height = Double.parseDouble(parts[3]);
            double totalCalories = Double.parseDouble(parts[4]);
            double activityLevel = Double.parseDouble(parts[5]);
            String gender = parts[6];

            Person person = new Person(fullName, weight, age, height, gender, activityLevel, weightList);

            personArrayList.addTrainee(person);
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
        foods = new FoodDatabase();
        excercises = new ExcerciseDatabase();

        for (int foodCount = 0; foodCount<numOfFoods; foodCount++){
            line = inputFile.nextLine();
            String[] foodParts = line.split(",");
            String foodName = foodParts[0];
            int calories = Integer.parseInt(foodParts[1]);
            foods.addFoodToArray(new Food(foodName, calories));
        }
        comm.print(""+ numOfFoods + " foods were added to the database\n");

        for (int excerciseCount = 0; excerciseCount< numOfExcercises; excerciseCount++){
            line = inputFile.nextLine();
            String[] excerciseParts = line.split(",");
            String excerciseName = excerciseParts[0];
            int calories = Integer.parseInt(excerciseParts[1]);
            excercises.addExcerciseToArray(new Excercise(excerciseName, calories));
        }
        comm.print(""+ numOfExcercises + " excercise were added to the database\n");
        personArrayList.printDBArray();
        comm.pause();
    }

    /**
     * Overwrites the contents of the given file name with the updated trainee database info
     * 
     */
    public void saveToFile()
    {
        try
        {
            PrintStream outFile = new PrintStream(new FileOutputStream(databaseFile));    

            for(int index=0;index < personArrayList.getSize(); index++)
            {
                outFile.println(personArrayList.getArrayIndex(index).outputFileFormat());
            }
            outFile.println();

            outFile.close();
            System.out.println("The file has been saved: you can view it by opening " + databaseFile);
        }
        catch(Exception error)
        {
            System.out.println("There has been an error while writing to the file");
        }   
    }
}
