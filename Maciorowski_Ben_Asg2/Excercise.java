/**
 * @author unknown1
 *
 */
public class Excercise {
    private String name = "";
    private int calories = 0;

    /**
     * Contructor for an excercise object
     */
    public Excercise(String name, int calories) {
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

