package mythicalCreatureStable;
/**
 * Write a description of class Creature here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Creature
{

    String name;
    String type;
    int age = 0;
    int weight = 0;
    int specialAttributeValue = 0;

    /**
     * Constructor for objects of class Creature
     */
    public Creature(String name, String type, int age, int weight, int specialAttributeValue)
    {
        this.name = name;
        this.age = age;
        this. weight = weight;
        this.specialAttributeValue = specialAttributeValue;
        this.type = type;

    }

    public Creature(){}

    public String getCreatureName(){return name;}

    public String getType(){return type;}

    public int getAge() {return age;}

    public int getWeight(){return weight;}

    public int getSpecialAttributeValue(){return specialAttributeValue;}

    public String getName(){return name;}
}