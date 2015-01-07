package mythicalCreatureStable;

import java.util.*;
/**
 * Manages a stable's rows.
 * 
 * @author (Ben Maciorowksi) 
 * @version (November 9, 2014)
 */
public class Row
{
    String description;
    int number = 0;
    int numberOfStalls = 0;

    ArrayList<Stall> stalls;

    /**
     * Constructor for objects of class Row
     */
    public Row(String description)//, int NumberOfCreatures)
    {
        stalls = new ArrayList<Stall>();
        this.description = description;

    }

    /**
     * adds a stall to the row
     * @param stallName the name of the stall
     * @param stallType the type of the stall
     * @param occupancyInfo the occupancy Info of the stall
     */
    public void addStall(String stallName, String stallType, String occupancyInfo){
        stalls.add(new Stall(stallName, stallType, occupancyInfo));

    }

    /**
     * removes a stall from the row
     * @param stallToRemove the stall to remove
     */
    public void removeStall(Stall stallToRemove){
        int DELETE = stalls.indexOf(stallToRemove);	
        stalls.remove(stalls.indexOf(stallToRemove));

    }

    public String getDescription(){return description;}

    public ArrayList<Stall> getStalls(){return stalls;} 

    /**
     * Determines if a specific stall is filled by a specific creature
     */
    public boolean findStallNumber(String creatureName, String creatureType, int age, int weight, int specialAttributeValue){
        boolean filled = false;

        for(int index = 0; index < stalls.size(); index++)
        {

            if(stalls.get(index).getCreature().getName().equals(creatureName)){
                stalls.get(index).addCreature(creatureName, creatureType, age, weight, specialAttributeValue); 
                filled = true; break;
            }

        }
        return filled;
    }

    /**
     * check to see if there is at least one empty stall
     * @return whether or not there is an empty stall
     */
    public boolean findEmptyStall(){
        boolean isFull = false;

        for(Stall currentStall : stalls){
            if(currentStall.getOccupied()){isFull = true;}
            else{isFull = false; break; }
        }

        return isFull;
    }

}
