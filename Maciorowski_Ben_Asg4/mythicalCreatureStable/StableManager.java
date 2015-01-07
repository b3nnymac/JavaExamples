package mythicalCreatureStable;

import userCommunication.*;
import java.util.*;
import java.io.*;
/**
 * Receives input from a user and provides several dynamic menu options
 * 
 * @author (Ben Mac) 
 * @version (November 9, 2014)
 */
public class StableManager
{
    private UserInteraction comm = new UserInteraction();
    private Menu options;
    private Menu creatureOptions;
    private Menu stallOptions;
    private Menu addRemoveOptions;

    private String creatureStableFile;

    boolean exit = false;
    MenuOption choice = null;

    private Stable creatureStable;
    /**
     * Starts the program
     * @param creatureStable a pre-loaded stable object
     * @param creatureStableFile the name of the input file
     */
    public void run(Stable creatureStable, String creatureStableFile){
        this.creatureStable = creatureStable;
        this.creatureStableFile = creatureStableFile;
        setUpMenus();

        do
        {
            choice = options.getUserChoice();

            System.out.println("The user selected: " + choice.getOption());
            switch(choice.getOption())
            {
                case "a":  //List Current Stable Occupancy
                System.out.println(listCurrentStableOccupancy());
                comm.pause();
                comm.clearBlueJTerminal();
                break;

                case "b": //List All Creatures
                if(!checkIfEmpty()) {System.out.println(listAllCreatures());
                    comm.pause();
                    comm.clearBlueJTerminal();}

                break;

                case "c": //Add New Creature
                if(checkIfFull()){addNewCreature();}
                break;

                case "d": //Remove Creature
                if(!checkIfEmpty()) {removeCreature();}
                break;

                case "e": // Move Creature
                if(!checkIfEmpty()) moveCreature();
                break;

                case "f": //Generate Report
                generateReport();
                break;

                case "g": //Add New Stall
                addNewStall();
                break;

                case "h": //Remove Stall
                if(checkIfFull()){removeStall();}
                break;

                case "q": //Quit Program
                exit = true;
                System.out.println("Bye!");
                break;

            }
        }
        while(!exit);

    }

    /**
     * Sets up menus that have constant variables
     */
    private void setUpMenus(){
        options = new Menu(comm);
        creatureOptions = new Menu(comm);
        stallOptions = new Menu(comm);

        options.addMenuOption("a","List Current Stable Occupancy");
        options.addMenuOption("b","List All Creatures");
        options.addMenuOption("c","Add New Creature");
        options.addMenuOption("d","Remove Creature");
        options.addMenuOption("e","Move Creature");
        options.addMenuOption("f","Generate Report");
        options.addMenuOption("g","Add New Stall");
        options.addMenuOption("h","Remove Stall");
        options.addMenuOption("q","Quit");

        creatureOptions.addMenuOption("1", "Chimera");
        creatureOptions.addMenuOption("2", "Dragon");
        creatureOptions.addMenuOption("3", "Unicorn");
        creatureOptions.addMenuOption("4", "Basilisk");
        creatureOptions.addMenuOption("5", "Centaur");
        creatureOptions.addMenuOption("6", "Djinn");
        creatureOptions.addMenuOption("7", "Giant");

        stallOptions.addMenuOption("f", "Fire");
        stallOptions.addMenuOption("i", "Indestructable");
        stallOptions.addMenuOption("m", "Magic");        
        stallOptions.addMenuOption("c", "Clown");        

    }

    /**
     * Creates a string of the stable's stalls and occupants
     * @return a formatted string of the stable's contents
     */
    private String listCurrentStableOccupancy(){
        boolean indent;
        String creatureOccupancyString;
        creatureOccupancyString = (String.format("| %s | %20s | %s | %20s | %10s | %s | \n" , "Row" , "Row Name", "Stall" ,"Stall Name", "Stall Type", "Vacancy"));
        for(int row = 0; row < creatureStable.getRowsArray().length; row++){
            indent = false;
            creatureOccupancyString+=(String.format("| %3d | %20.20s |" , row+1, creatureStable.getSpecificRow(row).getDescription())); 
            for(int stall = 0; stall < creatureStable.getRowsArray()[row].getStalls().size(); stall++){
                if(indent == false){
                    creatureOccupancyString+=(String.format(" %5d | %20.20s | %10c | %7c |\n" , stall+1,
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getName(),
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getType().charAt(0), 
                            charcaterCharForPrint(row, stall)));
                    indent = true;
                }
                else creatureOccupancyString+=(String.format("                               %5d | %20.20s | %10c | %7c |\n" , stall+1,
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getName(),
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getType().charAt(0), 
                            charcaterCharForPrint(row, stall)));

            }
            if(creatureStable.getRowsArray()[row].getStalls().size() == 0){creatureOccupancyString+=("   *************Row Contains Zero Stalls************\n");  }
        }

        return creatureOccupancyString;
    }

    /**
     * Determines if a stall contains a creature and returns the char of their type
     * @param row a specific row
     * @param stall a specific stall
     * @return a char type or # if unoccupied
     */
    private char charcaterCharForPrint(int row, int stall){
        char characterChar;
        if(creatureStable.getSpecificRow(row).getStalls().get(stall).getOccupied())
            characterChar=creatureStable.getSpecificRow(row).getStalls().get(stall).getCreature().getType().charAt(0);
        else characterChar = '#';
        return characterChar;
    }

    /**
     * Creates a string of the stable's occupants
     * @return a formatted string of the stable's occupants
     */
    private String listAllCreatures(){
        String allCreaturesString;
        allCreaturesString=(String.format("| %20s | %s | %s | %s | %s | %5s | %s | %s |\n" , "Stall Name", "Row Number", "Stall Number", "Creature Name" , "Creature Type" , "Age", "Weight", "Special Attribute Value"));
        for(int row = 0; row < creatureStable.getRowsArray().length; row++){

            for(int stall = 0; stall < creatureStable.getRowsArray()[row].getStalls().size(); stall++){

                if(creatureStable.getRowsArray()[row].getStalls().get(stall).getOccupied()){
                    allCreaturesString+=(String.format("| %20.20s | %10d | %12d | %13.13s | %13.13s | %5d | %6d | %23d |\n" , 
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getName(),
                            row+1, 
                            stall+1, 
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getCreature().getCreatureName(),
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getCreature().getType(),
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getCreature().getAge(),
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getCreature().getWeight(),
                            creatureStable.getSpecificRow(row).getStalls().get(stall).getCreature().getSpecialAttributeValue()));
                }

            }
        }

        return allCreaturesString;
    }

    /**
     * A menu options which receives input from a user to add a creature to the stable
     */
    private void addNewCreature(){
        boolean addNewCreaturePossible = false;
        choice = creatureOptions.getUserChoice("What Type of Character Would You Like to Add?");

        String name;
        int age = 0;
        int weight = 0;
        int specialAttributeValue = 0;
        String type;

        name = comm.getInput_String("\nPlease enter the new " + choice.getdescription() + "'s name: ");
        age = comm.getInput_IntGreaterThan("\nPlease enter " +  name + "'s age: ", 1);
        weight = comm.getInput_IntGreaterThan("\nPlease enter " +  name + "'s weight: ", 1);

        switch(choice.getOption())
        {
            case "1":  //Chimera
            System.out.println("\n"+choice.getdescription()+"s have the special property of a fire power.\n");
            specialAttributeValue = comm.getInput_IntBetween("Please enter " + name + "'s special attribute value: ", 1, 10);
            break;
            case "2":  //Dragon
            System.out.println("\n"+choice.getdescription()+"s have the special property of a fire power.\n");
            specialAttributeValue = comm.getInput_IntBetween("Please enter " + name + "'s special attribute value: ", 5, 12);
            break;
            case "3":  //Unicorn
            System.out.println("\n"+choice.getdescription()+"s do not have any special properties.\n");
            specialAttributeValue = 0;
            break;
            case "4":  //Basilisk
            System.out.println("\n"+choice.getdescription()+"s have the special property of a magic power.\n");
            specialAttributeValue = comm.getInput_IntBetween("Please enter " + name + "'s special attribute value: ", 4, 14);
            break;
            case "5":  //Centaur
            System.out.println("\n"+choice.getdescription()+"s do not have any special properties.\n");
            specialAttributeValue = 0;
            break;
            case "6":  //Djinn
            System.out.println("\n"+choice.getdescription()+"s have the special property of a magic power.\n");
            specialAttributeValue = comm.getInput_IntBetween("Please enter " + name + "'s special attribute value: ", 3, 11);
            break;
            case "7":  //Giant
            System.out.println("\n"+choice.getdescription()+"s have the special property of a smash power.\n");
            specialAttributeValue = comm.getInput_IntBetween("Please enter " + name + "'s special attribute value: ", 10, 25);
            break;
        }
        Creature creature = new Creature(name, choice.getdescription().toUpperCase(),  age,  weight,  specialAttributeValue);
        type = choice.getdescription();

        addNewCreaturePossible = findStallThatMatchesCriteria(loadAnArrayWithCompatibleStallTypes(choice.getdescription()), creature);
        comm.pause();
        if(addNewCreaturePossible){choice = addRemoveOptions.getUserChoice("Please select which stall you'd like to place your new creature");
            for(Row row : creatureStable.getRowsArray()){
                for(Stall stall : row.getStalls()){
                    if(stall.getName().equals(choice.getdescription())){stall.addCreature(creature); }

                }
            }
            System.out.println("Congrats! " + name + " the " + type + " was added to " + choice.getdescription());
            comm.pause();
            comm.clearBlueJTerminal();
        }

    }

    /**
     * Determines if at least one stall is available and compatible based on an array of stall types
     * @return the verifiation if a stall is open and matches criteria
     */
    public boolean findStallThatMatchesCriteria(ArrayList<String>  possibleStallTypes, Creature newCreature){
        addRemoveOptions = new Menu(comm);

        boolean atLeastOneOpenStall = false;
        System.out.println("Found Open Compatible Stalls: ");
        char menuOption = 'a';
        for(Row row : creatureStable.getRowsArray()){
            for(Stall stall : row.getStalls()){
                for(String creatureType: possibleStallTypes){

                    if(creatureType.equals(stall.getType()) && !stall.getOccupied()){
                        System.out.println("Row Name: " + row.getDescription() + " \nStall Name: "  + stall.getName() + " \nStall Type: " + stall.getType()); 
                        String menuOptionString = "" +menuOption;
                        String name = stall.getName();
                        addRemoveOptions.addMenuOption ("" +menuOption, stall.getName());
                        menuOption++;
                        atLeastOneOpenStall = true;
                    }
                }

            }

        }
        if(!atLeastOneOpenStall){System.out.println("None");
        }
        return atLeastOneOpenStall;
    }

    /**
     * A menu options which receives input from a user to removes a creature to the stable
     */
    private void removeCreature(){
        loadMenuWithCurrentCreatures();

        choice = addRemoveOptions.getUserChoice("Please select which creature you would like to remove: ");

        char confirmation = comm.getInput_char ("Are you sure you would like to remove " + choice.getdescription()  + " from your stable? y/n ", "y n");
        if(confirmation == 'Y'){
            for(Row row : creatureStable.getRowsArray()){

                for(Stall stall : row.getStalls()){
                    String[] info = choice.getdescription().split("the");
                    String name = info[0].trim();
                    if(stall.getOccupied() && name.equals(stall.getCreature().getCreatureName())){
                        System.out.println("" + choice.getdescription() + " age of " + stall.getCreature().getAge() + ", weight of " + stall.getCreature().getWeight()
                            + ", with a special attribute of " + stall.getCreature().getSpecialAttributeValue() + " haseth beeneth removedeth");
                        stall.removeCreature();
                        comm.pause();

                        break;

                    }

                }
            }

        }
        comm.clearBlueJTerminal();
    }

    /**
     * verifies is the stable is completely empty
     * @return verification if the stable is empty
     */
    private boolean checkIfEmpty(){
        if(creatureStable.detrminesIfEmpty()){
            System.out.println("Your Stable is Empty");
            comm.pause();
            comm.clearBlueJTerminal();
        }
        return creatureStable.detrminesIfEmpty();}

    /**
     * verifies is the stable is completely full
     * @return verification if the stable is full
     */
    private boolean checkIfFull(){
        boolean isFull = false;
        for(Row row : creatureStable.getRowsArray()){
            for(Stall stall : row.getStalls()){
                if(!stall.getOccupied()){isFull=true; break;}

            }

        }

        if(!isFull){
            System.out.println("Your Stable is Full");
            comm.pause();
            comm.clearBlueJTerminal();
        }

        return isFull;
    }

    /**
     * A menu options which receives input from a user to move a creature within the stable
     */
    private void  moveCreature(){
        loadMenuWithCurrentCreatures();
        char menuOption = 'a';
        Creature creatureToBeMoved = new Creature();
        ArrayList<String>  possibleStallTypes = new ArrayList<String>();
        boolean movePossible = false;

        choice = addRemoveOptions.getUserChoice("Please select which creature you would like to move: ");

        char confirmation = comm.getInput_char ("Are you sure you would like to move " + choice.getdescription()  + "? y/n ", "y n");
        if(confirmation == 'Y'){
            for(Row row : creatureStable.getRowsArray()){

                for(Stall stall : row.getStalls()){
                    String[] info = choice.getdescription().split("the");
                    String name = info[0].trim();
                    String type = info[1].trim(); 

                    if(name.equals(stall.getOccupant())){
                        movePossible = findStallThatMatchesCriteria(loadAnArrayWithCompatibleStallTypes(type),  stall.getCreature());
                        if(movePossible){
                            creatureToBeMoved = stall.getCreature();
                            stall.removeCreature();}

                    }

                }
            }

        }

        if(movePossible){choice = addRemoveOptions.getUserChoice("Please select which stall you'd like to move " + choice.getdescription());
            for(Row row : creatureStable.getRowsArray()){
                for(Stall stall : row.getStalls()){

                    if(stall.getName().equals(choice.getdescription())){
                        stall.addCreature(creatureToBeMoved);
                    }

                }
            }
            System.out.println("" + creatureToBeMoved.getName() +" the " + creatureToBeMoved.getType() + " was successfully moved to " + choice.getdescription());

        }
        comm.pause();
        comm.clearBlueJTerminal();
    }

    /**
     * A menu options which receives input from a user to add a stall to the stable
     */
    private void addNewStall(){
        addRemoveOptions = new Menu(comm);
        char menuChoice = 'a';
        String stallName = "";

        for(Row row : creatureStable.getRowsArray()){
            addRemoveOptions.addMenuOption(""+menuChoice, row.getDescription());
            menuChoice++;
        }
        choice = addRemoveOptions.getUserChoice("To which row would thou like to addeth a stall?");

        for(Row row : creatureStable.getRowsArray()){
            if(choice.getdescription().equals(row.getDescription())){
                stallName = comm.getInput_String("Name the stall: ");
                choice = stallOptions.getUserChoice("And of what type is " +stallName + "?");
                row.addStall(stallName, choice.getdescription().toUpperCase(), "OPEN");
                System.out.println("Hazah! You have added "+stallName+" of type "+choice.getdescription()+" to row "+row.getDescription());
                comm.pause();
                comm.clearBlueJTerminal();
            }

        }

    }

    /**
     * A menu options which receives input from a user to remove a stall to the stable
     */
    private void removeStall(){
        char confirmation;
        loadMenuWithOpenStalls();
        choice = addRemoveOptions.getUserChoice("Which stall we be removing then, hey?");

        confirmation = comm.getInput_char ("Are you sure you would like to remove the stall \"" + choice.getdescription()  + "\" from your stable? y/n ", "y n");
        if(confirmation == 'Y'){
            for(Row row : creatureStable.getRowsArray()){

                for(Stall stall : row.getStalls()){
                    String DELETE = choice.getdescription();
                    if(choice.getdescription().equals(stall.getName())) {
                        row.removeStall(stall);
                        System.out.println("Excelsior! You have successfully removed \"" + choice.getdescription() + "\" from your stable" );

                        comm.pause();
                        break;
                    }
                }
            }

        }
        comm.clearBlueJTerminal();
    }

    /**
     * Populates a user menu with stalls that are open
     */
    private void loadMenuWithOpenStalls(){
        addRemoveOptions = new Menu(comm);
        char menuOption = 'a';

        for(Row row : creatureStable.getRowsArray()){
            for(Stall stall : row.getStalls()){
                if(!stall.getOccupied()){
                    addRemoveOptions.addMenuOption(""+menuOption, stall.getName());
                    menuOption++;
                }

            }
        }

    }

    /**
     * Populates a user menu with all current creatures in the stall
     */
    private void loadMenuWithCurrentCreatures(){
        addRemoveOptions = new Menu(comm);
        char menuOption = 'a';

        for(Row row : creatureStable.getRowsArray()){

            for(Stall stall : row.getStalls()){

                if(stall.getOccupied()){
                    addRemoveOptions.addMenuOption(""+ menuOption , stall.getCreature().getCreatureName() + " the " + stall.getCreature().getType());
                    menuOption++;
                }

            }
        }

    }

    /**
     * Populates an array list with possible stall types based on a specific creature type
     * @param type a specific stall type 
     * @return an arraylist of stall types
     */
    private ArrayList<String> loadAnArrayWithCompatibleStallTypes(String type){
        ArrayList<String>  possibleStallTypes = new ArrayList<String>();
        String test = type.toUpperCase();

        switch(type.toUpperCase())
        {
            case "CHIMERA":  //Chimera
            possibleStallTypes.add("FIRE");
            possibleStallTypes.add("INDESTRUCTABLE");

            break;
            case "DRAGON":  //Dragon
            possibleStallTypes.add("INDESTRUCTABLE");
            break;
            case "UNICORN":  //Unicorn
            possibleStallTypes.add("MAGIC");
            possibleStallTypes.add("CLOWN");
            break;
            case "BASILISK":  //Basilisk
            possibleStallTypes.add("MAGIC");
            break;
            case "CENTAUR":  //Centaur
            possibleStallTypes.add("MAGIC");
            possibleStallTypes.add("CLOWN");
            possibleStallTypes.add("FIRE");
            possibleStallTypes.add("INDESTRUCTABLE");
            break;
            case "DJINN":  //Djinn
            possibleStallTypes.add("MAGIC");
            break;
            case "GIANT":  //Giant
            possibleStallTypes.add("INDESTRUCTABLE");
            break; 

        }
        return possibleStallTypes;
    }

    /**
     * a menu option which generates a user report prefixed with “REPORT_” followed by the original file name 
     */
    private void generateReport()
    {
        String creatureStableeReportFile = "REPORT_"+creatureStableFile;

        try
        {
            PrintStream outFile = new PrintStream(new FileOutputStream(creatureStableeReportFile));  

            outFile.println("Total Creatures in The Stable: " + creatureStable.countCreaturesInStable());
            outFile.println("Total number of giants in The Stable: " + creatureStable.totalNumberOfXTypeOfCreature("GIANT"));
            outFile.println("Total number of centaurs in The Stable: " + creatureStable.totalNumberOfXTypeOfCreature("CENTAUR"));
            outFile.println("Total number of dragons in The Stable: " + creatureStable.totalNumberOfXTypeOfCreature("DRAGON"));
            outFile.println("Total number of unicorns in The Stable: " + creatureStable.totalNumberOfXTypeOfCreature("UNICORN"));
            outFile.println("Total number of basilisks in The Stable: " + creatureStable.totalNumberOfXTypeOfCreature("BASILISK"));
            outFile.println("Total number of djinns in The Stable: " + creatureStable.totalNumberOfXTypeOfCreature("DJINN"));
            outFile.println("Total number of chimeras in The Stable: " + creatureStable.totalNumberOfXTypeOfCreature("CHIMERA"));
            outFile.println("\n**************Current Occupancy Information**************");
            outFile.println(listCurrentStableOccupancy());
            outFile.println("\n************All Creatues in the Stable****************");
            outFile.println(listAllCreatures());

            outFile.close();
            System.out.println("The file has been saved: you can view it by opening " + creatureStableeReportFile);
        }
        catch(Exception error)
        {
            System.out.println("There has been an error while writing to the file");
        } 
        comm.pause();
        comm.clearBlueJTerminal();
    }
}                