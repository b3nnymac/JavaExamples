import java.util.*;
public class PersonDatabase
{
    private ArrayList<Person> personArrayList;
    String positiveChartArray[][];

    /**
     * Constructor for PersonDatabase; an arrayList to hold all of the trainees is instantiated
     * 
     */
    public PersonDatabase()
    {
        personArrayList = new  ArrayList<Person>();

    }

    /**
     * Adds a trainee to the Person database
     * @param person a person object 
     */
    public void addTrainee(Person person){
        personArrayList.add(person);
    }

    /**
     * Removes a specific trainee from the Person database
     * @param traineeNumToRemove the index number of the trainee to be removed
     */
    public void removeTrainee(int traineeNumToRemove){
        personArrayList.remove(traineeNumToRemove); 
        printDBArray();
    }

    /**
     * Prints to the terminal a formatted table containing all of the trainees in the Health Tracker database
     */
    public void printDBArray(){
        String dottedLine = "----------------------------------------------------------------------------------------------------------------------------------";
        System.out.println(dottedLine + String.format("\n%3s|%18s |%12s |%9s |%9s |%6s |%9s |%17s |%15s" , "#","Full Name","Total Cal", "Weight", "  WChange","Age", "Height", "Gender", "Activity Level\n")
            + dottedLine);
        int num = 0;

        while(num < personArrayList.size() ){
            if(personArrayList.get(num) != null){
                System.out.println(String.format("%3d|%18s |%12.2f |%9.2f |%9.2f |%6d |%9.2f |%17s | %15s" , num+1, personArrayList.get(num).getFullName(),personArrayList.get(num).getTotalCalories(), personArrayList.get(num).getWeight(),personArrayList.get(num).weightChangeDouble(), personArrayList.get(num).getAge(), personArrayList.get(num).getHeight(), personArrayList.get(num).getGender(), personArrayList.get(num).getActivityLevelString()));

            }
            num++;
        }
    }

    /**
     * cycles through the entire personArrayList to determine if the array is empty of not
     * @return true if the array is empty and false if at least one Person object is in the array
     */
    public boolean checkIfEmpty(){
        boolean isEmpty = true; 

        for(Person arrayIndex : personArrayList)
            if(arrayIndex != null) {isEmpty = false; break;}

        if(isEmpty)System.out.println("Your database it empty");
        return         personArrayList.isEmpty();
    }

    /**
     * Provides a specific element held at a given index number in the personArrayList
     * @param index the specific index number for the element wanted in the personArrayList
     * @return the specific element from the given index value
     */
    public Person getArrayIndex(int index){ return personArrayList.get(index);}

    /**
     * Provides the size of the personArrayList
     * @return the size of the personArrayList
     */
    public int getSize(){ return personArrayList.size();}

    /**
     * Determines if a user-enterd trainee name string already exist in the personArrayList by
     * comparing the given string to each fullName string in the personArrayList
     * if a match is determind, the for loops ends
     * Is case INsensitive, capitlizes both the given string and the string it is being compared to
     * @return whether or not a duplicate is found
     */
    public boolean duplicateAssessor(String name){
        boolean duplicate = false;

        for(Person arrayIndex : personArrayList)
        {
            if(name.equals("")){
                System.out.println("\n You have not entered a trainee's name");  
                duplicate = true; break;}

            if(arrayIndex.getFullName().toUpperCase().equals(name.toUpperCase())) {
                System.out.format("\n%s already exists in your database\n", arrayIndex.getFullName());  
                duplicate = true; break;} 
        }
        return duplicate;
    }

    /**
     *Prints a formatted chart of all the trainees in the personArrayList 
     *Chart dynamically changes based on largest weight addition and reduction 
     *and whether or not a weight addition or reduction is present
     */
    public void printWeightChangeChart(){
        ;
        String dottedLine = "    +";

        for(int num = 0; num < personArrayList.size(); num ++){
            dottedLine+="====";
        }
        dottedLine+="+";
        if(largestWeightGain() > 0){
            System.out.println(dottedLine);
            String[][] chart = loadPositiveChart();
            for(int col = 0, yAxis = largestWeightGain(); col < chart[getSize()-1].length; col+=2, yAxis-=2){
                System.out.printf("%3d |", yAxis);
                for(int row = 0; row < chart.length; row++){
                    System.out.printf("  %s ", chart[row][col]);
                }
                System.out.println();
            }

            System.out.println(dottedLine );
        }

        System.out.print("    |");

        for(int num = 1; num <= personArrayList.size(); num ++){
            System.out.printf("%3d%s",  num, "|");
        }

        if(largestWeightloss() < 0){System.out.print("\n"+dottedLine + "\n");

            int largestWeightloss = largestWeightloss();
            int yAxis = largestWeightloss()%2;
            if(yAxis == 0){yAxis-=2;}

            String[][] chart = loadNegativeChart();
            for(int col = chart[getSize()-1].length -1; col >= 0 ; col-=2, yAxis-=2){
                System.out.printf("%3d |", yAxis);
                for(int row = 0; row < chart.length; row++){
                    System.out.printf("  %s ", chart[row][col]);
                }
                System.out.println();
            }

            System.out.println(dottedLine);
        }
        System.out.println();
    }

    /**
     *Determines the largest weight gain in the trainee arrayList
     */
    private int largestWeightGain(){
        int largestWeightGain = 0;

        for(int num = 0; num < personArrayList.size(); num++){
            if(personArrayList.get(num).weightChangeInt() > largestWeightGain){largestWeightGain=personArrayList.get(num).weightChangeInt();}
        }
        return largestWeightGain;
    }

    /**
     *Determines the largest weight loss in the trainee arrayList
     */
    private int largestWeightloss(){
        int largestWeightLoss = 0;

        for(int num = 0; num < personArrayList.size(); num++){
            if(personArrayList.get(num).weightChangeInt() < largestWeightLoss){largestWeightLoss=personArrayList.get(num).weightChangeInt();}
        }
        return largestWeightLoss;
    }

    /**
     * Determines if there is at least one trainee with a history of weight gain and loads an ArrayList with their 
     * information
     */
    private String[][] loadPositiveChart(){

        String positiveChartArray[][] =  new String[getSize()][largestWeightGain()];
        for(int row = 0; row < getSize(); row++)
        {
            int yAxis = largestWeightGain();
            for(int col = 0; col < largestWeightGain(); col++){
                int weightCheck = personArrayList.get(row).weightChangeInt();
                int weightCheck1 = largestWeightGain();
                if(personArrayList.get(row).weightChangeInt() >= yAxis){
                    positiveChartArray[row][col] = "*";}
                else{positiveChartArray[row][col] = " ";}
                yAxis--;
            }
        }

        return positiveChartArray;
    }

    /**
     * Determines if there is at least one trainee with a history of weight loss and loads an ArrayList with their 
     * information
     */
    private String[][] loadNegativeChart(){
        int positiveWeightLoss = largestWeightloss()*-1; //converts the negative weightloss number to a positive integer 

        String negativeChartArray[][] =  new String[getSize()][positiveWeightLoss];
        for(int row = 0; row < getSize(); row++)
        {
            int yAxis=largestWeightloss();
            for(int col = 0; col < positiveWeightLoss; col++){
                int weightCheck = personArrayList.get(row).weightChangeInt();
                int weightCheck1 = largestWeightloss();
                if(personArrayList.get(row).weightChangeInt() <= yAxis){
                    negativeChartArray[row][col] = "*";}
                else{negativeChartArray[row][col] = " ";}
                yAxis++; 
            }
        }

        return negativeChartArray;
    }
}
