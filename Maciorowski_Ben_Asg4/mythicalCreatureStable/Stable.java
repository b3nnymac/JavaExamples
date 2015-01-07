package mythicalCreatureStable;

import java.util.*;

public class Stable
{

    int numberOfCreatures = 0;

    Row rows[];

    /**
     * Constructor for objects of class Stable
     */
    public Stable(int numberOfRows)
    {
        rows = new Row[numberOfRows];
    }

    public int getRowSize(){return rows.length;}

    public Row[] getRowsArray(){return rows;}  

    public Row getSpecificRow(int index){return rows[index];}  

    /**
     * Counts the number of creatures in the stable
     * @retrun the total number of creatures in the stables
     */
    public int getNumberOfCreaturesInStable(){
        int count = 0;
        for(Row row: rows){
            for(Stall stall : row.getStalls()){
                if(stall.getOccupied()){count++;}
            }
        }
        return count;
    }

    /**
     * Checks to see if the sables is empty
     * @retrun whether or not the stable is completely empty
     */
    public boolean detrminesIfEmpty(){
        boolean isEmpty = true;

        for(Row currentRow : rows){
            for(Stall stall : currentRow.getStalls()){
                if(stall.getOccupied()){isEmpty = false; break;}
            }
        }

        return isEmpty;
    }

    /**
     * adds a named row to a specific index in the rows array
     * @param rowNumber the specific index name where the row is being added
     * @param rowName the name of the row to be added
     */
    public void addRow(int rowNumber, String rowName){
        rows[rowNumber] = new Row(rowName);
    }

    public void getRowName(int rowNumber)
    {	
        rows[rowNumber].getDescription();
    }

    /**
     * counts the number of creatures in the stable
     * @return the number of current creatues in the stable
     */
    public int countCreaturesInStable(){
        int count = 0;
        for(Row row : rows){
            for(Stall stall : row.getStalls()){
                if(stall.getOccupied()){count++;}

            } 
        }
        return count;
    }

    /**
     * counts the number of creatures of type X in the stable
     * @return the number of current creatuesof type X in the stable
     */
    public int totalNumberOfXTypeOfCreature(String type){
        int count = 0;
        for(Row row : rows){
            for(Stall stall : row.getStalls()){
                if(stall.getOccupied() && stall.getCreature().getType().equals(type)){count++;}

            } 
        }
        return count;

    }

}