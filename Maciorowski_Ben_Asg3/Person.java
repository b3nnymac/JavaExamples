import java.util.ArrayList;
import java.text.*;
/**
 *
Set up Person Object and Restore Vital Information to calculate various health and fitness calucations
 * @BenMac
 * @version 1.0
 * Last Modified: September 14, 2014 - Created By Ben Maciorowski
 */
public class Person
{

    private String fullName;
    private ArrayList<Double> weights = new ArrayList<Double>();
    private double currentWeight;
    private int age;
    private double height;
    private double totalCalories;
    private double activityLevel;
    private int gender;

    public static final int MALE = 0;
    public static final int FEMALE = 1;
    public static final int GENDER_NEUTRAL = 2;

    public static final double LITTLE_TO_NO_EXCERCISE = 1.20;
    public static final double LIGHT_EXCERCISE = 1.38;
    public static final double MODERATE_EXCERCISE = 1.55;
    public static final double HEAVY_EXCERCISE = 1.73;
    public static final double VERY_HEAVY_EXCERCISE = 1.90;

    /**
     * Constructor for objects of class Person
     */
    public Person()
    {
        fullName = "none";
        currentWeight = 0.00;
        age = 0;
        height = 0;
        totalCalories = 0;
        activityLevel = 0;
        gender = 0;
    }

    /**
     * Takes in vital parameters for the person object     
     * @param fullName the name of the individual
     * @param weight the individual's weight
     * @param height the individual's height
     * @param age the individual's age
     * @param weight the individual's gender 0=male,1=female,2=gender neutral
     * @param activityLevel the  activity level of the individual represented by a double
     */
    public Person(String fullName, double currentWeight,int age, double height, String gender, double activityLevel, ArrayList weights)
    {
        this.fullName=fullName;
        this.currentWeight=currentWeight;
        this.age=age;
        this.height=height;
        this.gender=setGender(gender);
        this.activityLevel= roundTwoDecimalPlaces(activityLevel);

        this.weights = weights;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getWeight() {
        return currentWeight;
    }

    public void setWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    /**
     * returns a string of the activity level based on the numerical value of the individual's activity level
     * @return a description of the activity level 
     */
    public String getActivityLevelString() {
        String activityLevelString = "";
        if (activityLevel == LITTLE_TO_NO_EXCERCISE) activityLevelString = "Little to no exercise";
        else if (activityLevel== LIGHT_EXCERCISE) activityLevelString=  "Light exercise (1–3 days per week)";
        else if (activityLevel == MODERATE_EXCERCISE) activityLevelString = "Moderate exercise (3–5 days per week)";
        else if (activityLevel == HEAVY_EXCERCISE) activityLevelString = "Heavy exercise (6–7 days per week)";
        else if (activityLevel == VERY_HEAVY_EXCERCISE) activityLevelString = "Very heavy exercise (twice per day, extra heavy workouts)";

        return activityLevelString;
    }

    public void setActivityLevel(double activityLevel) {this.activityLevel = activityLevel;}

    /**
     * returns a string of the gender based on the numerical value of the individual's gender
     * @return a description of the gender
     */
    public String getGender() {
        String genderString = "";
        switch(gender) {
            case MALE:     genderString = "Male"; break;

            case FEMALE:     genderString = "Female"; break;

            case GENDER_NEUTRAL:     genderString = "Gender Neutral"; break;

        }
        return genderString;
    }    

    /**
     * Convers a gender string to an constant int value
     * @param String the gender
     * @return the constant gender int value
     */
    public int setGender(String gender) { 
        int genderNo = 0;
        switch(gender) {
            case "Male":     genderNo = MALE; break;

            case "Female":     genderNo = FEMALE; break;

            case "Gender Neutral":     genderNo = GENDER_NEUTRAL; break;

        }
        return genderNo;

    }

    public double getTotalCalories() { return totalCalories;}

    public double getActivityLevel() { return activityLevel;}

    /**
     * adds calories to the total calories instance variable if total calories are greater than 0
     * @param the calories to add to total Calories
     * @return total calories
     */
    public double addCalories(double calories) {
        if (calories >= 0) {
            return totalCalories +=  calories;
        } else {
            return totalCalories;
        }
    }

    /**
     * subtracts calories from the total calories instance variable if calories are greater than total calories
     * @param the calories to add to total Calories
     * @return total calories
     */
    public double removeCalories(double calories) {
        if (calories <= totalCalories) {
            totalCalories -=  calories;
        } else {
            System.out.println("Invalid Calorie Amount");            
        }
        return totalCalories;
    }

    /**
     * calculatrs the users RMR based on their gender
     * @return rmr
     */
    public double calculateRMR() {
        double rmr = 0;	

        switch (gender) {
            case GENDER_NEUTRAL: rmr = 9.99 * currentWeight + 6.25 * height - 4.92 * age - 33;

            case FEMALE: rmr = 9.99 * currentWeight + 6.25 * height - 4.92 * age -161;       

            case MALE: rmr = 9.99 * currentWeight + 6.25 * height - 4.92 * age + 5;

        }

        return rmr;
    }

    /**
     * calories the minimum calories neccessary based on the rmr and activity level
     * @return Minimum Calorie Intake
     */
    public double minCalorieIntake() {return calculateRMR() * activityLevel;}

    /**
     * calories the BMI based on their weigt
     * @return BMI
     */
    public double calculateBMI() {return currentWeight/Math.pow((height/100), 2);}

    /**
     * returns a string of the BMI categoruy based on the numerical value of the individual's BMI
     * @return a description of the BMI
     */
    public String categoryBMI() {
        double bmi = calculateBMI();
        String bmiCategory = "";

        if (bmi<15) { bmiCategory = "Very severely underweight";} 
        else if (bmi >= 15 && bmi < 16) {bmiCategory = "Severely underweight";}
        else if (bmi >= 16 && bmi < 18.5) {bmiCategory = "Underweight";}
        else if (bmi >= 18.5 && bmi < 25) {bmiCategory = "Normal (healthy weight)";}
        else if (bmi >= 25 && bmi < 30) {bmiCategory = "Overweight";}
        else if (bmi >= 30 && bmi < 35) {bmiCategory = "Obese Class I (Moderately obese)";}
        else if (bmi >= 35 && bmi < 40) {bmiCategory = "Obese Class II (Severely obese)";}
        else if (bmi > 40) {bmiCategory = "Obese Class III (Very severely obese)";}

        return bmiCategory;
    }

    public String toString() {     
        return (String.format("%20s","Full Name = ") + fullName + String.format("\n%20s %.2f","Weight =",currentWeight)  + String.format("kg \n%20s","Age = ")
            + age + String.format("\n%20s","Height = ") + height + String.format("cm \n%20s","TotalCalories = ")
            + totalCalories + String.format("\n%20s","ActivityLevel = ") + getActivityLevelString()
            + String.format("\n%20s","Gender = ") +  getGender() + String.format("\n%20s","RMR = ") + String.format("%.2f",calculateRMR())
            + String.format("\n%20s","Min Cal = ") +  String.format("%.2f",minCalorieIntake()) + String.format("\n%20s","BMI = ") + String.format("%.2f",calculateBMI()) + " ( " + categoryBMI() + ")" 
            + String.format("\n%20s %.2f","Weight Change =",weightChangeDouble()) + String.format("\n%20s" , "\nWeight History:"));
    }

    /**
     * Properly formats a string to be output to a file
     * @return a formatted string
     */
    public String outputFileFormat(){

        return String.format("%s,%d,%.1f,%.1f,%.1f,%.2f,%s#%s" , getFullName() , getAge(), getWeight(), getHeight(), getTotalCalories(), getActivityLevel(), getGender(), weightHistoryString());

    }

    /**
     * Reduces weight from the trainee's current weight
     * @param weightToTakeOff the given amount of weight to remove
     */
    public void reduceWeight(double weightToTakeOff){
        currentWeight = currentWeight - weightToTakeOff;
        weights.add(currentWeight);
    }

    /**
     * Adds weight to the trainee's current weight
     * @param weightToAdd the given amount of weight to add
     */
    public void addWeight(double weightToAdd){
        currentWeight = currentWeight + weightToAdd;
        weights.add(currentWeight);
    }

    /**
     * Casts the Person's weight change to an int value 
     * @return the Person's weight change as an int
     */
    public int weightChangeInt(){return (int)Math.round(currentWeight - weights.get(0));}

    /**
     * Determines the Person's weight change from their first entered weight to their current weight
     * @return the Person's weight change
     */
    public double weightChangeDouble(){return currentWeight - weights.get(0);}

    /**
     * Formats a person's weight history and prints it as a bar chart to the terminal
     */
    public void weightChart(){
        for(int index = 0; index < weights.size(); index++)
        {System.out.printf("\t (%2d)  %.2f : ", index, weights.get(index) );
            for(int bar = 0; bar < weights.get(index); bar+=2){
                System.out.print("*");}
            System.out.println();
        }

    }

    /**
     * Formats a person's weight history to be used to print to a file
     * @return the person's formatted weight history
     */
    private String weightHistoryString(){
        String weightHistory = "";
        for(int index = 0; index < weights.size(); index++){
            weightHistory+= weights.get(index) + ",";

        }
        return weightHistory;
    }

    /**
     * Rounds a trainee's activity level to 2 decimal places 
     * @param a trainee's activity level which may be to 3 decimal places
     * @return double the trainee's activity level round to 2 decimal places
     */
    private double roundTwoDecimalPlaces(double activityLevel) { return Math.round(activityLevel*100.0)/100.0;}
}
