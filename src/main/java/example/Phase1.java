
package example;

import java.util.LinkedList;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Phase1 {

    @SerializedName("Action")
    @Expose
    private String action;
    @SerializedName("Department")
    @Expose
    private String department;
    @SerializedName("Course")
    @Expose
    private String course;
    @SerializedName("Space")
    @Expose
    private int space;
    @SerializedName("Prerequisites")
    @Expose
    private LinkedList<String> prerequisites = null;
    @SerializedName("Student")
    @Expose
    private String student;
    @SerializedName("Grade")
    @Expose
    private LinkedList<String> grade = null;
    @SerializedName("Number")
    @Expose
    private Integer number;
    @SerializedName("Preferences")
    @Expose
    private LinkedList<String> preferences = null;
    @SerializedName("Students")
    @Expose
    private LinkedList<String> students = null;
    @SerializedName("Computer")
    @Expose
    private String computer;
    @SerializedName("Conditions")
    @Expose
    private LinkedList<String> conditions = null;



    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public LinkedList<String> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(LinkedList<String> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public String getStudent() {
        return student;
    }

    public void setStudent(String student) {
        this.student = student;
    }

    public LinkedList<String> getGrade() {
        return grade;
    }

    public void setGrade(LinkedList<String> grade) {
        this.grade = grade;
    }

    public Integer getNumber() { return number; }

    public void setNumber(int number) {
        this.number = number;
    }

    public LinkedList<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(LinkedList<String> preferences) {
        this.preferences = preferences;
    }

    public LinkedList<String> getStudents() {
        return students;
    }

    public void setStudents(LinkedList<String> students) {
        this.students = students;
    }

    public String getComputer() {
        return computer;
    }

    public void setComputer(String computer) {
        this.computer = computer;
    }

    public LinkedList<String> getConditions() {
        return conditions;
    }

    public void setConditions(LinkedList<String> conditions) {
        this.conditions = conditions;
    }

}
