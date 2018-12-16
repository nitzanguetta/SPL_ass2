package bgu.spl.a2.sim.privateStates;

import bgu.spl.a2.PrivateState;

import java.util.LinkedList;
import java.util.List;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState {

    private List<String> courseList;
    private List<String> studentList;

    /**
     * Implementors note: you may not add other constructors to this class nor
     * you allowed to add any other parameter to this constructor - changing
     * this may cause automatic tests to fail..
     */
    public DepartmentPrivateState() {
        courseList = new LinkedList<>();
        studentList = new LinkedList<>();
    }

    /**
     * @return courses linked list
     */
    public List<String> getCourseList() {
        return courseList;
    }

    /**
     * @return students linked list
     */
    public List<String> getStudentList() {
        return studentList;
    }

    /**
     * @param course will be added to courses list
     */
    public void addToCourseList(String course) {
        this.courseList.add(course);
    }

    /**
     * @param student will be added to students list
     */
    public void addToStudentList(String student) {
        this.studentList.add(student);
    }
}
