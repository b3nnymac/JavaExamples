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
    private double weight;
    private int age;
    private double height;
    private double totalCalories;
    private double activityLevel;
    private int gender;

    /**
     * Constructor for objects of class Person
     */
    public Person()
    {
        fullName = "none";
        weight = 0.00;
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
    public Person(String fullName, double weight,int age, double height, int gender, double activityLevel)
    {
        this.fullName=fullName;
        this.weight=weight;
        this.age=age;
        this.height=height;
        this.gender=gender;
        this.activityLevel=activityLevel;

    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
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
    public String getActivityLevel() {
        if(activityLevel==1.2)return "Little to no exercise";
        else if(activityLevel==1.375) return "Light exercise (1–3 days per week)";
        else if(activityLevel== 1.55) return "Moderate exercise (3–5 days per week)";
        else if(activityLevel== 1.725) return "Heavy exercise (6–7 days per week)";
        else if(activityLevel== 1.9) return "Very heavy exercise (twice per day, extra heavy workouts)";
        return "error";
    }

    public void setActivityLevel(double activityLevel) {
        this.activityLevel = activityLevel;
    }

    /**
     * returns a string of the gender based on the numerical value of the individual's gender
     * @return a description of the gender
     */
    public String getGender() {
        switch(gender) {
            case 0:     return "Male";

            case 1:     return "Female";

            default:     return "Gender Neutral";

        }
    }    

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public double addCalories(double calories) {
        if (calories >= 0) {
            return totalCalories +=  calories;
        } else {

            return totalCalories;
        }
    }

    public double removeCalories(double calories) {
        if (calories >= 0) {
            return totalCalories -=  calories;
        } else {

            return totalCalories;
        }
    }

    public double calculateRMR() {
        switch (gender) {
            case 2: return 9.99 * weight + 6.25 * height - 4.92 * age - 33;

            case 1: return 9.99 * weight + 6.25 * height - 4.92 * age -161;       

            default: return 9.99 * weight + 6.25 * height - 4.92 * age + 5;

        }
    }

    public double minCalorieIntake() {return calculateRMR() * activityLevel;}

    public double calculateBMI() {return weight/Math.pow((height/100), 2);}

    /**
     * returns a string of the BMI categoruy based on the numerical value of the individual's BMI
     * @return a description of the BMI
     */
    public String categoryBMI() {
        if (calculateBMI()<15) { return "Very severely underweight";} 
        else if (calculateBMI() >= 15 && calculateBMI() < 16) {return "Severely underweight";}
        else if (calculateBMI() >= 16 && calculateBMI() < 18.5) {return "Underweight";}
        else if (calculateBMI() >= 18.5 && calculateBMI() < 25) {return "Normal (healthy weight)";}
        else if (calculateBMI() >= 25 && calculateBMI() < 30) {return "Overweight";}
        else if (calculateBMI() >= 30 && calculateBMI() < 35) {return "Obese Class I (Moderately obese)";}
        else if (calculateBMI() >= 35 && calculateBMI() < 40) {return "Obese Class II (Severely obese)";}
        else {return "Obese Class III (Very severely obese)";
        }
    }

    public String toString() {
        return (String.format("%20s","Full Name = ") + fullName + String.format("\n%19s %.2f","Weight =", weight)  + String.format("kg \n%20s","Age = ")
            + age + String.format("\n%20s","Height = ") + height + String.format("cm \n%20s","TotalCalories = ")
            + totalCalories + String.format("\n%20s","ActivityLevel = ") + getActivityLevel()
            + String.format("\n%20s","Gender = ") +  getGender() + String.format("\n%10s","RMR: ") + String.format("%.2f",calculateRMR())
            + String.format("\n%10s","Min Cal: ") +  String.format("%.2f",minCalorieIntake()) + String.format("\n%10s","BMI: ") + String.format("%.2f",calculateBMI()) + " ( " + categoryBMI() + ")");
    }
}
