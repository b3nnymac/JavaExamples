/**
 * @author unknown1
 *
 */
public class Food {
    private String name = "";
    private int calories = 0;

    /**
     * Contructor for a food object
     */
    public Food(String name, int calories) {
        this.name=name;
        this.calories=calories;
    }

    public String getName() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public String toString(){
        return String.format("%s, %d", name, calories);

    }
}

