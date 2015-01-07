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
    private MenuOption choice = null;
    private Person person;

    private final double ACTIVITY_LEVEL_NO_EXCERCISE = 1.2;
    private final double ACTIVITY_LEVEL_LIGHT_EXCERCISE = 1.375;
    private final double ACTIVITY_LEVEL_MODERATE_EXCERCISE = 1.55;
    private final double ACTIVITY_LEVEL_HEAVY_EXCERCISE = 1.725;
    private final double ACTIVITY_LEVEL_VERY_HEAVY_EXCERCISE = 1.9;

    public void run()
    {
        person = getHealthInfo();

        setUpMenu();
        boolean exit = false;
        do
        {
            choice = options.getUserChoice();

            System.out.println("The user selected: " + choice.getOption());
            switch(choice.getOption())
            {
                case "a": 
                System.out.println(person.toString());
                comm.pause();
                break;

                case "b":
                person.addCalories(comm.getInput_DoubleGreaterThan("Please enter calories to add: ", 0));
                comm.pause();
                break;

                case "c":
                person.removeCalories(comm.getInput_DoubleGreaterThan("Please enter calories to remove: ", 0));
                comm.pause();
                break;

                case "d":
                System.out.println(String.format("RMR: %.2f", person.calculateRMR()));
                comm.pause();
                break;

                case "e":
                System.out.println(String.format("BMI: %.2f", person.calculateBMI()));
                comm.pause();
                break;

                case "f":
                editMenuOptions();
                comm.pause();
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

        options.addMenuOption("a","Print");
        options.addMenuOption("b","Add Calories");
        options.addMenuOption("c","Remove Calories");
        options.addMenuOption("d","Print RMR");
        options.addMenuOption("e","Print BMI");
        options.addMenuOption("f","Edit");
        options.addMenuOption("q","Quit");   

    }

    /**
     * Asks the user to input their required health info
     * @return person object
     */
    private Person getHealthInfo() {
        Person person;
        Scanner keyboard = new Scanner(System.in);

        String fullName = comm.getInput_String("Hello, Welcome to your personalized Health Tracker! \nPlease enter your first and last name: ");
        while (fullName.equals("")){fullName = comm.getInput_String("You did not enter a value. \nPlease try again: ");}
        double weight = comm.getInput_DoubleBetween("\nPlease enter your weight in kilograms: ", 10, 907);   
        int age = comm.getInput_IntBetween("\nPlease enter your age: ", 1, 170);
        double height = comm.getInput_DoubleBetween("\nPlease enter your height in centimetres: ", 20, 300);
        System.out.println();
        int gender = setUpGenderMenu();
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
    private int setUpGenderMenu(){
        options = new Menu(comm);

        options.addMenuOption("m", "Male");
        options.addMenuOption("f","Female");
        options.addMenuOption("n","Gender Neutral");

        choice = options.getUserChoice();

        comm.println("The user selected: " + choice.getOption());

        switch (choice.getOption()) {
            case "m":
            return 0;

            case "f":
            return 1;

            default:
            return 2;
        }
    }

    /**
     * Sets up an activity level option menu for the user and waits to receive their selection
     * @retun the numerical representation of the user's activity level
     */
    private double setUpActivityMenu(){
        options = new Menu(comm);

        options.addMenuOption("1", "Little to no exercise");
        options.addMenuOption("2","Light exercise (1–3 days per week)");
        options.addMenuOption("3","Moderate exercise (3–5 days per week)");
        options.addMenuOption("4","Heavy exercise (6–7 days per week)");
        options.addMenuOption("5","Very heavy exercise (twice per day, extra heavy workouts)");

        choice = options.getUserChoice();

        comm.println("The user selected: " + choice.getOption());

        switch (choice.getOption()) {
            case "1":
            return ACTIVITY_LEVEL_NO_EXCERCISE;

            case "2":
            return ACTIVITY_LEVEL_LIGHT_EXCERCISE;

            case "3":
            return ACTIVITY_LEVEL_MODERATE_EXCERCISE;

            case "4":
            return ACTIVITY_LEVEL_HEAVY_EXCERCISE;

            case "5":
            return ACTIVITY_LEVEL_VERY_HEAVY_EXCERCISE;

            default: 
            return 0;
        }
    }

    /**
     * The edit options from the main menu; asks the user which informationy the would like to edit
     */
    private void editMenuOptions(){
        if (comm.getInput_char("Do you want to edit weight? y/n: ", "y n") == 'Y'){
            person.setWeight(comm.getInput_DoubleBetween("\nPlease enter new weight in kilograms: ", 10, 907));}
        if (comm.getInput_char("Do you want to edit age? y/n: ", "y n") == 'Y'){
            person.setAge(comm.getInput_IntBetween("\nPlease enter new age: ", 1, 170));}
        if (comm.getInput_char("Do you want to edit height? y/n: ", "y n") == 'Y'){
            person.setHeight(comm.getInput_DoubleBetween("\nPlease enter your height in centimetres: ", 20, 300));}
        if (comm.getInput_char("Do you want to edit activity level? y/n: ", "y n") == 'Y'){
            person.setActivityLevel(setUpActivityMenu());
            setUpMenu();}
    }

}
