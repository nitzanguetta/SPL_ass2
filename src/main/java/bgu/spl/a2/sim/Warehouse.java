package bgu.spl.a2.sim;


import java.util.HashMap;


/**
 * represents a warehouse that holds a finite amount of computers
 * and their suspended mutexes.
 */
public class Warehouse {

    private HashMap<String, SuspendingMutex> computersList = new HashMap<>();

    /**
     * Constructor for Warehouse
     */
    public Warehouse() {
    }

    /**
     * adding new computer to computer list
     *
     * @param computer will be added to list of computers
     */
    public void addComputer(Computer computer) {
        computersList.put(computer.getComputerType(), new SuspendingMutex(computer));
    }

    /**
     * checks if computer is free or not
     *
     * @param computerType specific computer type
     * @return suspending mutex of specific computer
     */
    public SuspendingMutex getSuspendingMutex(String computerType) { //need in action check if null
        return computersList.get(computerType);
    }
}
