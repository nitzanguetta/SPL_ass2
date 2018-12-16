package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;

public class Computer {

    String computerType;
    long failSig;
    long successSig;
    private SuspendingMutex suspendingMutex;

    /**
     * Constractor for computer
     *
     * @param computerType returns the type of specific computer
     */
    public Computer(String computerType) { //THREAD SAFE?!?!?@??!@#!@?!@?@?!@?@?!@?!??
        this.computerType = new String(computerType);
    }

    /**
     * this method checks if the courses' grades fulfill the conditions
     *
     * @param courses       courses that should be pass
     * @param coursesGrades courses' grade
     * @return a signature if couersesGrades grades meet the conditions
     */
    public long checkAndSign(List<String> courses, Map<String, Integer> coursesGrades) { //wanna to take any course in list and check if passed
        if (courses == null || coursesGrades == null) {
            return failSig;
        }
        for (int i = 0; i < courses.size(); i++) {
            if (coursesGrades.get(courses.get(i)) == null) {
                return failSig;
            } else if (coursesGrades.get(courses.get(i)) < 56) {
                return failSig;
            }
        }
        return successSig;
    }

    /**
     * @param failSig sets parameter to be fail sig
     */
    public void setFailSig(long failSig) {
        this.failSig = failSig;
    }

    /**
     * @param successSig sets parameter to be success sig
     */
    public void setSuccessSig(long successSig) {
        this.successSig = successSig;
    }

    /**
     * @return computer type
     */
    public String getComputerType() {
        return computerType;
    }

    /**
     * @return suspending mutex field
     */
    public SuspendingMutex getSuspendingMutex() {
        return suspendingMutex;
    }

    /**
     * @param suspendingMutex sets suspending mutex to be suspending mutex field
     */
    public void setSuspendingMutex(SuspendingMutex suspendingMutex) {
        this.suspendingMutex = suspendingMutex;
    }
}
