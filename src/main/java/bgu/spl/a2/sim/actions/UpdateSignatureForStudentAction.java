package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class UpdateSignatureForStudentAction extends Action {

    private Computer computer;
    private LinkedList<String> conditions;

    /**
     *
     * @param computer computer
     * @param conditions list of conditions
     */
    public UpdateSignatureForStudentAction(Computer computer, LinkedList<String> conditions) {
        setActionName("Update Signature For Student Action");
        this.computer = computer;
        this.conditions = conditions;
    }

     /**
     * executing operation
     */
    protected void start() {
        StudentPrivateState thisStudentPrivateState = (StudentPrivateState) this.getActorState();
        thisStudentPrivateState.setSignature(computer.checkAndSign(conditions, thisStudentPrivateState.getGrades()));
        complete(true);
    }

    /**
     *
     * @return actor's private state
     */
    public PrivateState getActorState() {
        return this.actorState;
    }

}
