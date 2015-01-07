package mythicalCreatureStable;
/**
 * Write a description of class Stall here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Stall
{
    String type;
    String stallName;
    String occupant;
    int typeNumerized = 0;
    Creature creature;
    Boolean occupied = false;

    public static final int FIRE = 0;
    public static final int MAGIC = 1;
    public static final int INDESTRUCTABLE = 2;
    public static final int CLOWN = 3;
    public static final int OPEN = 4;
    /**
     * Constructor for objects of class Stall
     */
    public Stall(String stallName, String type, String occupant)
    {
        this.type = type;
        typeNumerized = setType(type);
        this.stallName = stallName;
        this.occupant = occupant;
        this.occupied = false;

    }

    /**
     *  adds a creatues to the stall with parameters
     */
    public void addCreature(String name, String type, int age, int weight, int strength){
        creature = new Creature (name,  type, age,  weight, strength);
        occupied = true;
        occupant = creature.getCreatureName();

    }

    /**
     *  adds a complete creatues to the stall 
     */
    public void addCreature(Creature creature){
        this.creature = creature;
        occupied = true;
        occupant = creature.getCreatureName();

    }

    /**
     *  Numerizes the type
     *  @return the stall types in numeral form
     */
    private int setType(String type) { 
        int typeNum = 0;
        switch(type) {
            case "FIRE":     typeNum = FIRE; break;

            case "MAGIC":     typeNum = MAGIC; break;

            case "INDESTRUCTABLE":     typeNum = INDESTRUCTABLE; break;

            case "CLOWN":     typeNum = CLOWN; break;

            case "OPEN":     typeNum = OPEN; break;

        }
        return typeNum;
    }

    public String getOccupant(){return occupant;}

    public Creature getCreature(){return creature;}

    public String getType(){return type;}

    public String getName(){return stallName;}

    public Boolean getOccupied(){return occupied;}

    /**
     *  reomves the creature from the stall and marks it as open
     */
    public void removeCreature(){
        creature = null;
        occupied = false;
        occupant = "OPEN";}

}