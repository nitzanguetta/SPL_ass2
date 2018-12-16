package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class RemoveGradeFromStudentCourseAction extends Action {

    private String course;

    /**
     *
     * @param course course's name
     */
    public RemoveGradeFromStudentCourseAction(String course) {
        setActionName("Remove Grade From Student Course Action");

        this.course = course;
    }

    /**
     * executing operation
     */
    public void start() {
        ((StudentPrivateState) this.getActorState()).getGrades().remove(course);
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
