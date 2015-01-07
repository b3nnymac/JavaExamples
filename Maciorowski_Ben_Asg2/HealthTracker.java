/**
 *Set up Health Tracker Menus to Receive User Input
 * @BenMac
 * @version 1.0
 * Last Modified: September 14, 2014 - Created By Ben Maciorowski
 */

import userCommunication.*;
import java.util.*;

public class HealthTracker 
{
    private UserInteraction comm = new UserInteraction();
    private Menu options;
    private Menu genderOptions;
    private Menu activityOptions;
    private Menu foodOptions;
    private Menu excerciseOptions;

    public static final int DATABASE_SIZE = 20;
    private Person[] personArray= new Person[DATABASE_SIZE];
    private Food[] foods;
    private Excercise[] excercises;
    private int numOfFoods = 0;
    private int numOfExcercises = 0;

    private int count = 0;
    /**
     * Sets up the main user interaction menu and presents the user with a list of choices.
     * Each choice is applied to a switch case statement which checks if the database is empty if appropriate
     * to the user selection then calls upon the appropriate method.
     * Terminates program by exiting a while loop if the user selects to quit from the menu
     */
    public void run()
    {
        setUpMenu();
        boolean exit = false;
        MenuOption choice = null;
        do
        {
            choice = options.getUserChoice();

            System.out.println("The user selected: " + choice.getOption());
            switch(choice.getOption())
            {
                case "a":  //Add Trainee
                if(checkIfAnyLocationEmpty()){ addTrainee(getHealthInfo());}
                else{System.out.println("There are currently no free spaces available in your health tracker database");}
                printDBArray();
                comm.pause();
                comm.clearBlueJTerminal();
                break;

                case "b": //Remove Trainee
                if(checkIfEmpty()){}
                else {
                    int availableSpace = 0;
                    printDBArray();

                    availableSpace = comm.getInput_IntBetween("\nPlease enter the # of the trainee you'd like to remove from the database: ", 1, DATABASE_SIZE);

                    if(checkIfSpecificSpaceAvailable(availableSpace-1)){removeTrainee(availableSpace-1);}
                    else{System.out.println("There is not a trainee at that database location"); }
                }
                comm.pause();
                comm.clearBlueJTerminal();
                break;

                case "c": //Edit Trainee Info
                if(checkIfEmpty()){}
                else{
                    printDBArray();
                    int traineeToEdit = (comm.getInput_IntBetween("\nPlease enter the # of the trainee you'd like to edit: ", 1, DATABASE_SIZE));
                    if(checkIfSpecificSpaceAvailable(traineeToEdit-1)){editMenuOptions(traineeToEdit-1);
                        comm.clearBlueJTerminal();
                        System.out.println(personArray[traineeToEdit-1].toString());}
                    else{System.out.println("There is not a trainee at that database location"); }

                }
                comm.pause();
                comm.clearBlueJTerminal(); 
                break;

                case "d": //List Trainee Info
                if(checkIfEmpty()){}
                else{
                    printDBArray();
                    int listChoice = comm.getInput_IntBetween("Please enter the # of the trainee whose information you'd like to list: ", 1, DATABASE_SIZE)-1;
                    if(personArray[listChoice]!=null){System.out.println(personArray[listChoice].toString());}
                    else{System.out.println("There is not a trainee at your selected database location");}
                }
                comm.pause();
                comm.clearBlueJTerminal();
                break;

                case "e": // Add Food
                if(checkIfEmpty()){}
                else{addFood();}
                comm.pause();
                comm.clearBlueJTerminal();
                break;

                case "f": //Add Excercise
                if(checkIfEmpty()){}
                else{addExcercise();}
                comm.pause();
                comm.clearBlueJTerminal();
                break;

                case "q":
                exit = true;
                System.out.println("Bye!");
                break;

            }
        }
        while(!exit);

    }

    /**
     * Adds a trainee to the first free spot in the health tracker database array
     * @param a newly created person object
     */
    public void addTrainee(Person person){
        for(int num = 0; num < personArray.length; num++){
            if(personArray[num] == null) 
            {personArray[num] = person; count++; break;}
        }
    }

    public void removeTrainee(int traineeNumToRemove){
        if(traineeNumToRemove == DATABASE_SIZE - 1){ personArray[traineeNumToRemove] = null; } 
        else{
            personArray[traineeNumToRemove] = personArray[traineeNumToRemove+1];

            for(int num = traineeNumToRemove+1; personArray[traineeNumToRemove] != null; num++){
                if(num == DATABASE_SIZE - 1){ personArray[num] = null; break;} 
                personArray[num] = personArray[num+1];
            }
        }
        count--;
        printDBArray();
    }

    /**
     * sets up the main menus   
     */
    private void setUpMenu(){
        options = new Menu(comm);

        options.addMenuOption("a","Add Trainee");
        options.addMenuOption("b","Remove Trainee");
        options.addMenuOption("c","Edit Trainee");
        options.addMenuOption("d","List");
        options.addMenuOption("e","Add Food");
        options.addMenuOption("f","Add Excercise");
        options.addMenuOption("q","Quit"); 

        genderOptions = new Menu(comm);

        genderOptions.addMenuOption("M", "Male", personArray[DATABASE_SIZE-1].MALE);
        genderOptions.addMenuOption("F","Female", personArray[DATABASE_SIZE-1].FEMALE);
        genderOptions.addMenuOption("G","Gender Neutral", personArray[DATABASE_SIZE-1].GENDER_NEUTRAL);

        activityOptions = new Menu(comm);

        activityOptions.addMenuOption("1", "Little to no exercise", personArray[DATABASE_SIZE-1].LITTLE_TO_NO_EXCERCISE);
        activityOptions.addMenuOption("2","Light exercise (1–3 days per week)", personArray[DATABASE_SIZE-1].LIGHT_EXCERCISE);
        activityOptions.addMenuOption("3","Moderate exercise (3–5 days per week)", personArray[DATABASE_SIZE-1].MODERATE_EXCERCISE);
        activityOptions.addMenuOption("4","Heavy exercise (6–7 days per week)", personArray[DATABASE_SIZE-1].HEAVY_EXCERCISE);
        activityOptions.addMenuOption("5","Very heavy exercise (twice per day, extra heavy workouts)", personArray[DATABASE_SIZE-1].VERY_HEAVY_EXCERCISE);

        foodOptions = new Menu(comm);

        for(int num = 0; num < numOfFoods; num++){
            foodOptions.addMenuOption("" + num, foods[num].toString(), num);
        }

        excerciseOptions = new Menu(comm);

        for(int num = 0; num < numOfExcercises; num++){
            excerciseOptions.addMenuOption("" + num, excercises[num].toString(), num);
        }
    }

    /**
     * Asks the user to input their required health info
     * @return person object
     */
    private Person getHealthInfo() {
        Person person;
        Scanner keyboard = new Scanner(System.in); 

        String fullName = comm.getInput_String("\nPlease enter the new trainee's name: ");
        double weight = comm.getInput_DoubleBetween("\nPlease enter the trainee's weight in kilograms: ", 10, 907);   
        int age = comm.getInput_IntBetween("\nPlease enter their age: ", 1, 170);
        double height = comm.getInput_DoubleBetween("\nPlease enter their height in centimetres: ", 20, 300);
        System.out.println();
        String gender = setUpGenderMenu();
        System.out.println();
        double activityLevel = setUpActivityMenu();
        person = new Person(fullName,  weight, age,  height,  gender,  activityLevel);

        return person; 

    }

    /**
     * Sets up a menu with 3 gender options and waits for user to enter gender option, coverts option to numerical 
     * representation 0=male, 1=female, 2=gender neutral
     * @return the numerical representation of the user's gender
     */
    private String setUpGenderMenu(){
        MenuOption choice = null;
        String gender = "";
        choice = genderOptions.getUserChoice("Enter Gender: ");

        comm.println("The user selected: " + choice.getdescription());

        gender = choice.getdescription();

        return gender;
    }

    /**
     * Sets up an activity level option menu for the user and waits to receive their selection
     * @retun the numerical representation of the user's activity level
     */
    private double setUpActivityMenu(){
        MenuOption choice = null;

        double activityLevelChoice = 0;

        choice = activityOptions.getUserChoice("Enter Choice: ");
        comm.println("The user selected: " + choice.getdescription());
        switch(choice.getOption()){
            case "1": activityLevelChoice = personArray[DATABASE_SIZE-1].LITTLE_TO_NO_EXCERCISE;
            break;
            case "2": activityLevelChoice = personArray[DATABASE_SIZE-1].LIGHT_EXCERCISE;
            break;
            case "3": activityLevelChoice = personArray[DATABASE_SIZE-1].MODERATE_EXCERCISE;
            break;
            case "4": activityLevelChoice = personArray[DATABASE_SIZE-1].HEAVY_EXCERCISE;
            break;
            case "5": activityLevelChoice = personArray[DATABASE_SIZE-1].VERY_HEAVY_EXCERCISE;
            break;
        }

        return activityLevelChoice;
    }

    /**
     * The edit options from the main menu; asks the user which informationy the would like to edit
     * @param the trainee number selected by the user the given by a printed table containing all
     * trainees in the daabase
     */
    private void editMenuOptions(int traineeNumberToEdit){
        if(checkIfSpecificSpaceAvailable(traineeNumberToEdit)){
            if (comm.getInput_char("Do you want to edit "+personArray[traineeNumberToEdit].getFullName()+"'s weight? y/n: ", "y n") == 'Y'){
                personArray[traineeNumberToEdit].setWeight(comm.getInput_DoubleBetween("\nPlease enter "+personArray[traineeNumberToEdit].getFullName()+"'s new weight in kilograms: ", 10, 907));}
            if (comm.getInput_char("Do you want to edit "+personArray[traineeNumberToEdit].getFullName()+"'s age? y/n: ", "y n") == 'Y'){
                personArray[traineeNumberToEdit].setAge(comm.getInput_IntBetween("\nPlease enter "+personArray[traineeNumberToEdit].getFullName()+"'s new age: ", 1, 170));}
            if (comm.getInput_char("Do you want to edit "+personArray[traineeNumberToEdit].getFullName()+"'s height? y/n: ", "y n") == 'Y'){
                personArray[traineeNumberToEdit].setHeight(comm.getInput_DoubleBetween("\nPlease enter "+personArray[traineeNumberToEdit].getFullName()+"'s new height in centimetres: ", 20, 300));}
            if (comm.getInput_char("Do you want to edit "+personArray[traineeNumberToEdit].getFullName()+"'s activity level? y/n: ", "y n") == 'Y'){
                personArray[traineeNumberToEdit].setActivityLevel(setUpActivityMenu());
                setUpMenu();}
        }
        else{System.out.println("There is not a trainee at your selected database location");}
    }

    /**
     * Creates a new menu populated by the food items loaded from a food and excercise text file. 
     * Asks user to enter the table number of the trainee they would like to add calories to
     * Adds calories based on the type of food and amount of servings to the trainee total calories
     */
    private void addFood(){

        printDBArray();
        int traineeChoice = comm.getInput_IntBetween("Which trainee would you like to add calories to: ", 1, DATABASE_SIZE);

        if(checkIfSpecificSpaceAvailable(traineeChoice-1)){
            int foodChoice = 0;

            foodChoice = foodOptions.getUserChoiceInt();

            double servings = comm.getInput_DoubleGreaterThan(String.format("How many servings of %s did %s consume: " , foods[foodChoice].getName() , personArray[traineeChoice -1].getFullName()) , 0);
            personArray[traineeChoice-1].addCalories(foods[foodChoice].getCalories() * servings);
            System.out.printf("%s's total calories are now %.2f\n", personArray[traineeChoice-1].getFullName(), personArray[traineeChoice-1].getTotalCalories());
        }

        else System.out.println("There is not a trainee at the selected database location");
    }

    /**
     * Creates a new menu populated by the excercise items loaded from a food and excercise text file. 
     * Asks user to enter the table number of the trainee they would like to subtract calories to
     * Subtracts calories based on the type of excercice and amount of minutes performed from the trainee's total calories
     */
    private void addExcercise(){

        printDBArray();
        int traineeChoice = comm.getInput_IntBetween("Which trainee would you like to add an excercise to: ", 1, DATABASE_SIZE);

        if(checkIfSpecificSpaceAvailable(traineeChoice-1)){
            int excerciseChoice = 0;

            excerciseChoice = excerciseOptions.getUserChoiceInt();

            double minutes = comm.getInput_DoubleGreaterThan(String.format("How many minutes of %s did %s perform: " , excercises[excerciseChoice].getName() , personArray[traineeChoice -1].getFullName())  , 0);
            personArray[traineeChoice-1].removeCalories(excercises[excerciseChoice].getCalories()*minutes);
            System.out.printf("%s's total calories are now %.2f\n", personArray[traineeChoice-1].getFullName(), personArray[traineeChoice-1].getTotalCalories());
        }

        else System.out.println("There is not a trainee at the selected database location");

    }    

    /**
     * Instantiates the food database array from the FileLoader 
     * @param the number of foods in the food and excercise
     * database text file
     */
    public void instantiateFoodArray(int numOfFoods){
        foods = new Food[this.numOfFoods=numOfFoods];    
    }

    /**
     * Instantiates the excercise database array from the FileLoader 
     * @param the number of excercises in the food and excercise
     * database text file
     */
    public void instantiateExcerciseArray(int numOfExcercises){
        excercises = new Excercise[this.numOfExcercises=numOfExcercises];    
    }

    /**
     * Adds food items and their calories to the foods array
     * @param a food object
     * @param the index number of the foods array where the newly created food object is added to
     */
    public void addFoodToArray(Food food, int index){
        foods[index] = food;}

    /**
     * Adds excercise items and their calories to the excercises array
     * @param an excercise object
     * @param the index number of the excercises array where the newly created excercise object is added to
     */
    public void addExcerciseToArray(Excercise excercise, int index){
        excercises[index] = excercise;}

    /**
     * Prints to the terminal a formatted table containing all of the trainees in the Health Tracker database
     */
    public void printDBArray(){
        String dottedLine = "----------------------------------------------------------------------------------------------------------------------------------";
        System.out.println((dottedLine + String.format("\n%3s|%18s |%12s |%9s |%6s |%9s |%17s | %15s" , "#","Full Name","Total Cal", "Weight","Age", "Height", "Gender", "Activity Level\n")
                + dottedLine));
        int num = 0;

        while(num < personArray.length ){
            if(personArray[num] != null){
                System.out.println(String.format("%3d|%18s |%12.2f |%9.2f |%6d |%9.2f |%17s | %15s" , num+1, personArray[num].getFullName(),personArray[num].getTotalCalories(), personArray[num].getWeight(),personArray[num].getAge(), personArray[num].getHeight(), personArray[num].getGender(), personArray[num].getActivityLevel()));

            }
            num++;
        }
    }

    /**
     * cycles through the entire personArray to determine if the array is empty of not
     * @return true if the array is empty and false if at least one Person object is in the array
     */
    public boolean checkIfEmpty(){
        boolean isEmpty = true; 
        for(int num = 0; num < personArray.length; num++){
            if(personArray[num] != null) {isEmpty = false; break;}
        }
        if(isEmpty)System.out.println("Your database it empty");
        return isEmpty;
    }

    /**
     * Checks a specific index locatin in the personArray to determine if the specfic index location is empty or not
     * @return true if the specfic index location is empty and false if specfic index location contains a Person object
     */
    public boolean checkIfSpecificSpaceAvailable(int specificSpace)
    {
        boolean personAtLocation = true;
        if(personArray[specificSpace] == null) personAtLocation = false;
        return personAtLocation;

    }

    public boolean checkIfAnyLocationEmpty(){
        boolean spaceAvaiable = false; 

        for(int num = 0; num < personArray.length; num++){

            if(personArray[num] == null) {spaceAvaiable = true; break;}
        }
        return spaceAvaiable;
    }

}