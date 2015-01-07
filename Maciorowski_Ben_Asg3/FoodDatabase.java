import java.util.*;
/**
 * Write a description of class FoodDatabase here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class FoodDatabase
{
    private ArrayList<Food> foods;
    private int count;

    /**
     * Constructor for objects of class FoodDatabase
     */
    public FoodDatabase()
    {
        this.foods = new ArrayList<Food>();
    }

    /**
     * Adds food items and their calories to the foods array
     * @param a food object
     * @param the index number of the foods array where the newly created food object is added to
     */
    public void addFoodToArray(Food food){foods.add(food);}

    /**
     * Provides a specific emelent in the foods arrayList from a given index value
     * @param index 
     * @return Food element
     */
    public Food getArray(int index){ return foods.get(index);}

    /**
     * Provides the size of the foods arrayList 
     * @return Foods size
     */
    public int getSize(){ return foods.size();}

}