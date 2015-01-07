import java.util.*;
/**
 * Write a description of class ExcerciseDatabase here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ExcerciseDatabase
{
    private ArrayList<Excercise> excercises;
    private int count;

    /**
     * Constructor for objects of class ExcerciseDatabase
     */
    public ExcerciseDatabase()
    {
        this.excercises = new ArrayList<Excercise>();
    }

    /**
     * Adds excercise items and their calories to the excercises array
     * @param a excercise object
     * @param the index number of the excercises array where the newly created excercise object is added to
     */
    public void addExcerciseToArray(Excercise excercise){excercises.add(excercise);}

    /**
     * Provides a specific emelent in the excercises arrayList from a given index value
     * @param index 
     * @param Excercise element
     */
    public Excercise getArray(int index){ return excercises.get(index);}

    /**
     * Provides the size of the excercises arrayList 
     * @return excercises size
     */
    public int getSize(){ return excercises.size();}

}