/**
 *Set up Health Tracker Menus to Receive User Input
 * @BenMac
 * @version 1.0
 * Last Modified: September 14, 2014 - Created By Ben Maciorowski
 */

import userCommunication.*;
import java.util.*;
import java.io.*;

public class HealthTracker 
{
    private UserInteraction comm = new UserInteraction();
    private Menu options;
    private Menu genderOptions;
    private Menu activityOptions;
    private Menu foodOptions;
    private Menu excerciseOptions;

    private PersonDatabase database;
    private FoodDatabase foods;
    private ExcerciseDatabase excercises;

    private int numOfFoods = 0;
    private int numOfExcercises = 0;

    private int count = 0;
    /**
     * Sets up the main user interaction menu and presents the user with a list of choices.
     * Each choice is applied to a switch case statement which checks if the database is empty if appropriate
     * to the user selection then calls upon the appropriate method.
     * Terminates program by exiting a while loop if the user selects to quit from the menu
     */
    public void run(PersonDatabase database, FoodDatabase foods, ExcerciseDatabase excercises)
    {
        numOfFoods = foods.getSize();
        numOfExcercises = excercises.getSize();
        this.foods = foods;
        this.excercises = excercises;
        setUpMenu();
        boolean exit = false;
        MenuOption choice = null;
        this.database = database;

        do
        {
            choice = options.getUserChoice();

            System.out.println("The user selected: " + choice.getOption());
            switch(choice.getOption())
            {
                case "a":  //Add Trainee
                addTrainee();
                break;

                case "b": //Remove Trainee
                removeTrainee();
                break;

                case "c": //Edit Trainee Info
                editTrainee();
                break;

                case "d": //List Trainee Info
                listTraineeInfo();
                break;

                case "e": // Add Food
                addFood();
                break;

                case "f": //Add Excercise
                addExcercise();
                break;

                case "g":
                removeWeight();
                break;

                case "h":
                addWeight();
                break;

                case "i" :
                showWeightChangesChart(); 

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
        options.addMenuOption("g","Reduce Weight");
        options.addMenuOption("h","Increase Weight");
        options.addMenuOption("i","Show Weight Change Chart");
        options.addMenuOption("q","Quit"); 

        genderOptions = new Menu(comm);
        genderOptions.addMenuOption("M", "Male", Person.MALE);
        genderOptions.addMenuOption("F","Female", Person.FEMALE);
        genderOptions.addMenuOption("G","Gender Neutral", Person.GENDER_NEUTRAL);

        activityOptions = new Menu(comm);

        activityOptions.addMenuOption("1", "Little to no exercise", Person.LITTLE_TO_NO_EXCERCISE);
        activityOptions.addMenuOption("2","Light exercise (1–3 days per week)", Person.LIGHT_EXCERCISE);
        activityOptions.addMenuOption("3","Moderate exercise (3–5 days per week)", Person.MODERATE_EXCERCISE);
        activityOptions.addMenuOption("4","Heavy exercise (6–7 days per week)", Person.HEAVY_EXCERCISE);
        activityOptions.addMenuOption("5","Very heavy exercise (twice per day, extra heavy workouts)", Person.VERY_HEAVY_EXCERCISE);

        foodOptions = new Menu(comm);

        for(int num = 0; num < numOfFoods; num++){
            foodOptions.addMenuOption ("" + num, foods.getArray(num).toString(), num);
        }

        excerciseOptions = new Menu(comm);

        for(int num = 0; num < numOfExcercises; num++){
            excerciseOptions.addMenuOption("" + num, excercises.getArray(num).toString(), num);
        }
    }

    /**
     * Asks the user to input their required health info
     * @return person object
     */
    private Person getHealthInfo() {
        Person person; 
        String fullName;
        do{
            fullName = comm.getInput_String("\nPlease enter the new trainee's name: ");

        }while(database.duplicateAssessor(fullName));

        double weight = comm.getInput_DoubleBetween("\nPlease enter the trainee's weight in kilograms: ", 10, 907);   
        int age = comm.getInput_IntBetween("\nPlease enter their age: ", 1, 170);
        double height = comm.getInput_DoubleBetween("\nPlease enter their height in centimetres: ", 20, 300);
        System.out.println();
        String gender = setUpGenderMenu();
        System.out.println();
        double activityLevel = setUpActivityMenu();
        ArrayList<Double> weightList = new ArrayList<Double>();
        weightList.add(weight);
        person = new Person(fullName,  weight, age,  height,  gender,  activityLevel, weightList);

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
            case "1": activityLevelChoice = Person.LITTLE_TO_NO_EXCERCISE;
            break;
            case "2": activityLevelChoice = Person.LIGHT_EXCERCISE;
            break;
            case "3": activityLevelChoice = Person.MODERATE_EXCERCISE;
            break;
            case "4": activityLevelChoice = Person.HEAVY_EXCERCISE;
            break;
            case "5": activityLevelChoice = Person.VERY_HEAVY_EXCERCISE;
            break;
        }

        return activityLevelChoice;
    }

    /**
     * The edit options from the main menu; asks the user which informationy the would like to edit
     * @param traineeNumberToEdit the trainee number selected by the user the given by a printed table containing all
     * trainees in the daabase
     */
    private void editMenuOptions(int traineeNumberToEdit){
        if (comm.getInput_char("Do you want to edit "+database.getArrayIndex(traineeNumberToEdit).getFullName()+"'s weight? y/n: ", "y n") == 'Y'){
            database.getArrayIndex(traineeNumberToEdit).setWeight(comm.getInput_DoubleBetween("\nPlease enter "+database.getArrayIndex(traineeNumberToEdit).getFullName()+"'s new weight in kilograms: ", 10, 907));}
        if (comm.getInput_char("Do you want to edit "+database.getArrayIndex(traineeNumberToEdit).getFullName()+"'s age? y/n: ", "y n") == 'Y'){
            database.getArrayIndex(traineeNumberToEdit).setAge(comm.getInput_IntBetween("\nPlease enter "+database.getArrayIndex(traineeNumberToEdit).getFullName()+"'s new age: ", 1, 170));}
        if (comm.getInput_char("Do you want to edit "+database.getArrayIndex(traineeNumberToEdit).getFullName()+"'s height? y/n: ", "y n") == 'Y'){
            database.getArrayIndex(traineeNumberToEdit).setHeight(comm.getInput_DoubleBetween("\nPlease enter "+database.getArrayIndex(traineeNumberToEdit).getFullName()+"'s new height in centimetres: ", 20, 300));}
        if (comm.getInput_char("Do you want to edit "+database.getArrayIndex(traineeNumberToEdit).getFullName()+"'s activity level? y/n: ", "y n") == 'Y'){
            database.getArrayIndex(traineeNumberToEdit).setActivityLevel(setUpActivityMenu());
            setUpMenu();}

    }

    /**
     * Creates a new menu populated by the food items loaded from a food and excercise text file. 
     * Asks user to enter the table number of the trainee they would like to add calories to
     * Adds calories based on the type of food and amount of servings to the trainee total calories
     */
    private void addFood(){
        if(database.checkIfEmpty()){}
        else{
            database.printDBArray();
            int traineeChoice = comm.getInput_IntBetween("Which trainee would you like to add calories to: ", 1, database.getSize());
            int foodChoice = 0;
            foodChoice = foodOptions.getUserChoiceInt();
            double servings = comm.getInput_DoubleGreaterThan(String.format("How many servings of %s did %s consume: " , foods.getArray(foodChoice).getName() , database.getArrayIndex(traineeChoice -1).getFullName()) , 0);
            database.getArrayIndex(traineeChoice -1).addCalories(foods.getArray(foodChoice).getCalories() * servings);
            System.out.printf("%s's total calories are now %.2f\n", database.getArrayIndex(traineeChoice -1).getFullName(), database.getArrayIndex(traineeChoice -1).getTotalCalories());
        }
        comm.pause();
        comm.clearBlueJTerminal();
    }

    /**
     * Creates a new menu populated by the excercise items loaded from a food and excercise text file. 
     * Asks user to enter the table number of the trainee they would like to subtract calories to
     * Subtracts calories based on the type of excercice and amount of minutes performed from the trainee's total calories
     */
    private void addExcercise(){
        if(database.checkIfEmpty()){}
        else{
            database.printDBArray();
            int traineeChoice = comm.getInput_IntBetween("Which trainee would you like to add an excercise to: ", 1, database.getSize()-1);

            int excerciseChoice = 0;
            excerciseChoice = excerciseOptions.getUserChoiceInt();
            double minutes = comm.getInput_DoubleGreaterThan(String.format("How many minutes of %s did %s perform: " , excercises.getArray(excerciseChoice).getName() , database.getArrayIndex(traineeChoice -1).getFullName())  , 0);
            database.getArrayIndex(traineeChoice -1).removeCalories(excercises.getArray(excerciseChoice).getCalories()*minutes);
            System.out.printf("%s's total calories are now %.2f\n", database.getArrayIndex(traineeChoice -1).getFullName(), database.getArrayIndex(traineeChoice -1).getTotalCalories());

        } 
        comm.pause();
        comm.clearBlueJTerminal();   
    }

    /**
     * Adds a trainee to the last element in  health tracker database arrayList
     */
    public void addTrainee()
    {
        database.addTrainee(getHealthInfo());
        database.printDBArray();
        comm.pause();
        comm.clearBlueJTerminal();
    }

    /**
     *A main menu option to remove a trainee from the database
     */
    private void removeTrainee(){
        if(database.checkIfEmpty()){}
        else {
            int traineeToRemove = 0;
            database.printDBArray();
            traineeToRemove = comm.getInput_IntBetween("\nPlease enter the # of the trainee you'd like to remove from the database: ", 1, database.getSize());
            database.removeTrainee(traineeToRemove-1);
        }
        comm.pause();
        comm.clearBlueJTerminal();
    }

    /**
     *A main menu option to edit a specific trainee in the database
     */
    private void editTrainee(){
        if(database.checkIfEmpty()){}
        else{
            int traineeToEdit = 0;
            database.printDBArray();
            traineeToEdit = comm.getInput_IntBetween("\nPlease enter the # of the trainee you'd like to edit: ", 1, database.getSize());
            editMenuOptions(traineeToEdit-1);
            comm.clearBlueJTerminal();
            System.out.println(database.getArrayIndex(traineeToEdit-1).toString());
            database.getArrayIndex(traineeToEdit-1).weightChart();
        }
        comm.pause();
        comm.clearBlueJTerminal(); 
    }

    /**
     *A main menu option to print to the terminal a formatted summary of a specific trainee
     */
    private void listTraineeInfo(){
        if(database.checkIfEmpty()){}
        else{
            database.printDBArray();
            int listChoice = 0;
            listChoice = comm.getInput_IntBetween("Please enter the # of the trainee whose information you'd like to list: ", 1, database.getSize());
            System.out.println(database.getArrayIndex(listChoice-1).toString());
            database.getArrayIndex(listChoice-1).weightChart();
        }
        comm.pause();
        comm.clearBlueJTerminal();
    }

    /**
     *A main menu option to reduce weight from a specific trainee
     */
    private void removeWeight(){
        if(database.checkIfEmpty()){}
        else{
            database.printDBArray();
            int traineeToRemoveWeight = 0;
            traineeToRemoveWeight = comm.getInput_IntBetween("Please enter the # of the trainee whose weight you'd like to reduce: ", 1, database.getSize());

            double weight = comm.getInput_DoubleBetween(String.format("How much weight did %s lose: " , database.getArrayIndex(traineeToRemoveWeight -1).getFullName()), 0, database.getArrayIndex(traineeToRemoveWeight -1).getWeight());
            database.getArrayIndex(traineeToRemoveWeight -1).reduceWeight(weight);
            System.out.printf("%s's current weight is now %.2f\n", database.getArrayIndex(traineeToRemoveWeight -1).getFullName(), database.getArrayIndex(traineeToRemoveWeight -1).getWeight());

        }
        comm.pause();
        comm.clearBlueJTerminal();
    }

    /**
     *A main menu option to add weight to a specific trainee
     */
    private void addWeight(){
        if(database.checkIfEmpty()){}
        else{
            database.printDBArray();
            int traineeToaddWeight = 0;
            traineeToaddWeight = comm.getInput_IntBetween("Please enter the # of the trainee whose weight you'd like to increase: ", 1, database.getSize());

            double weight = comm.getInput_DoubleGreaterThan(String.format("How much weight did %s gain: " , database.getArrayIndex(traineeToaddWeight -1).getFullName())  , 0);
            database.getArrayIndex(traineeToaddWeight -1).addWeight(weight);
            System.out.printf("%s's current weight is now %.2f\n", database.getArrayIndex(traineeToaddWeight -1).getFullName(), database.getArrayIndex(traineeToaddWeight -1).getWeight());

        }
        comm.pause();
        comm.clearBlueJTerminal();
    }

    /**
     *A main menu option to print a formatted weight change chart for each trainee in the database
     */
    private void showWeightChangesChart(){
        if(database.checkIfEmpty()){}
        else{database.printWeightChangeChart();
        }
        comm.pause();
        comm.clearBlueJTerminal();
    }

}
