package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

import java.util.LinkedList;

public class Unregister extends Action { //maybe this is course and the subAction is student

    private String studentId;
    private String course;

    /**
     *
     * @param studentId student's ID
     * @param course course's name
     */
    public Unregister(String studentId, String course) {
        setActionName("Unregister");
        this.course = course;
        this.studentId = studentId;
    }

    /**
     * executing operation
     */
    public void start() {
        if (((CoursePrivateState) this.getActorState()).getAvailableSpots() != -1) {
            LinkedList<Action> actionsBefore = new LinkedList<>();
            Action removeGradeFromStudentCourseAction = new RemoveGradeFromStudentCourseAction(course);
            sendMessage(removeGradeFromStudentCourseAction, studentId, ((RemoveGradeFromStudentCourseAction) removeGradeFromStudentCourseAction).getActorState());
            actionsBefore.add(removeGradeFromStudentCourseAction);

            then(actionsBefore, () -> {
                if(((CoursePrivateState) this.getActorState()).getAvailableSpots() != -1) {
                    int i = 0;
                    boolean found = false;
                    CoursePrivateState coursePrivateState = ((CoursePrivateState) this.getActorState());
                    boolean enteredWhile = false;
                    while (!found && i < coursePrivateState.getRegStudents().size()) {
                        enteredWhile = true;
                        if (coursePrivateState.getRegStudents().get(i).equals(studentId)) {
                            found = true;
                            coursePrivateState.getRegStudents().remove(i);
                            coursePrivateState.setRegistered(coursePrivateState.getRegistered() - 1);
                            coursePrivateState.setAvailableSpots(coursePrivateState.getAvailableSpots() + 1);

                            LinkedList<Action> actionsBefore2 = new LinkedList<>();
                            Action dummyAction = new Action() {
                                @Override
                                protected void start() {
                                    complete(true);
                                }
                            };
                            actionsBefore2.add(dummyAction);
                            sendMessage(dummyAction, studentId, new StudentPrivateState());
                            then(actionsBefore2, () -> complete(true));
                        }
                        i++;
                    }
                    if (enteredWhile == false){
                        complete(false);
                    }
                }else {
                    complete(false);
                }
            });
        } else {
            complete(false);
        }
    }

    /**
     *
     * @return actor's private state
     */
    public PrivateState getActorState() {
        return this.actorState;
    }

}
