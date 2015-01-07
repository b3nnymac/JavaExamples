import  mythicalCreatureStable.*;
import java.io.*;
import userCommunication.*;
import java.util.*;

public class FileLoader {
    private UserInteraction comm = new UserInteraction();
    private Scanner inputFile; 
    private Stable creatureStable;
    private String creatureStableFile ="";
    private StableManager manager;

    /**
     * Opens a file and determines if it is valid witha track catch statement
     * @param fileName the name of the given file 
     */
    public  void  openFile(String fileName){
        try{
            creatureStableFile = fileName;
            inputFile = new Scanner(new File(fileName));
            readFile();
            manager = new StableManager();
            manager.run(creatureStable, creatureStableFile);
            saveToFile();
        }
        catch(Exception error){
            System.out.println("Could not load file");

        }
    }

    /**
     * reads the contents of a file, parsing specific portions, and instantiate variables to create Person objects, and 
     * add them to an array
     */
    public void readFile(){

        while (inputFile.hasNext()){ 
            String line = inputFile.nextLine();
            String[] info = line.split(",");
            int numberOfRows = Integer.parseInt(info[0]);
            creatureStable = new Stable(numberOfRows);
            int numberOfCreatures = Integer.parseInt(info[1]);

            for(int count = 0; count < numberOfRows; count++) 
            {
                line = inputFile.nextLine();
                String[] rowDetails = line.split(","); 
                String rowName = rowDetails[0];
                int numberOfStalls = Integer.parseInt(rowDetails[1]);
                creatureStable.addRow(count, rowName);

                for(int stallCount = 0;   numberOfStalls != 0;  numberOfStalls--) {
                    line = inputFile.nextLine();
                    String[] stallDetails = line.split(","); 
                    String stallName = stallDetails[0];
                    String stallType = stallDetails[1];
                    String occupantInformation = stallDetails[2];
                    creatureStable.getSpecificRow(count).addStall(stallName, stallType, occupantInformation);

                }
            }

            while(inputFile.hasNext()){
                line = inputFile.nextLine();
                String[] creatureDetails = line.split(","); 
                String creatureName = creatureDetails[0];
                String creatureType = creatureDetails[1];
                int age = Integer.parseInt(creatureDetails[2]);
                int weight = Integer.parseInt(creatureDetails[3]);
                int specialAttributeValue = Integer.parseInt(creatureDetails[4]);
                for(Row row : creatureStable.getRowsArray()){
                    for(Stall stall : row.getStalls()){
                        if(creatureName.equals(stall.getOccupant())){
                            stall.addCreature(creatureName, creatureType, age, weight, specialAttributeValue);

                        }
                    }
                }

            }
        }

    }

    /**
     * Overwrites the contents of the given file name with the updated trainee database info
     * 
     */
    public void saveToFile()
    {
        try
        {
            PrintStream outFile = new PrintStream(new FileOutputStream(creatureStableFile));    
            outFile.println(creatureStable.getRowSize() +","+ creatureStable.getNumberOfCreaturesInStable()); //deleted ""
            for(Row row : creatureStable.getRowsArray()){
                outFile.println(row.getDescription()+","+row.getStalls().size());
                for(Stall stall : row.getStalls()){
                    outFile.println(stall.getName()+","+stall.getType()+","+stall.getOccupant());
                }
            }
            for(Row row : creatureStable.getRowsArray()){

                for(Stall stall : row.getStalls()){
                    if(stall.getOccupied()){outFile.println(stall.getCreature().getName()+","+stall.getCreature().getType()+","+
                            stall.getCreature().getAge()+","+stall.getCreature().getWeight()+","+stall.getCreature().getSpecialAttributeValue());}
                }
            }
            outFile.close();
            System.out.println("The file has been saved: you can view it by opening " + creatureStableFile);
        }
        catch(Exception error)
        {
            System.out.println("There has been an error while writing to the file");
        }   
    }
}

