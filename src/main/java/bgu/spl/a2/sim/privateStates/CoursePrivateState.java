package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.LinkedList;
import java.util.List;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState {

    private Integer availableSpots;
    private Integer registered;
    private List<String> regStudents;
    private List<String> prequisites;

    /**
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     */
    public CoursePrivateState() {
        availableSpots = new Integer(0);
        registered = new Integer(0);
        regStudents = new LinkedList<>();
        prequisites = new LinkedList<>();
    }

    /**
     * @return available spots field
     */
    public Integer getAvailableSpots() {
        return availableSpots;
    }

    /**
     * @param availableSpots sets available spot field
     */
    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
    }

    /**
     * @return registered field
     */
    public Integer getRegistered() {
        return registered;
    }

    /**
     * @param registered sets registered value field
     */
    public void setRegistered(Integer registered) {
        this.registered = new Integer(registered.intValue());
    }

    /**
     * @return registered students list
     */
    public List<String> getRegStudents() {
        return regStudents;
    }

    /**
     * @return prequisites list
     */
    public List<String> getPrequisites() {
        return prequisites;
    }

    /**
     * @param prequisites sets prequisites field
     */
    public void setPrequisites(LinkedList<String> prequisites) {
        this.prequisites.addAll(prequisites);
    }
}