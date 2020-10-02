package model;

import java.util.ArrayList; //imports the ArrayList class
import java.util.List;
import java.util.Random;

/**
 * Department class that contains the list of courses, rooms, and Professors for that department.
 *
 * @author Youssef Sleiman
 */
public class Department {
    /**
     * Class variables
     */
    private String departmentName;
    private String departmentLocation;
    private ArrayList<Course> coursesList;
    private ArrayList<Room> roomsList;
    private ArrayList<Professor> ProfessorsList;
    private ArrayList<ClassTimes> meetingTimes;
    Random random = new Random();

    /**
     * Default constructor
     */
    public Department() {
    }

    /**
     * Returns String of department's name
     * @return String of department's name
     */
    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * Sets the department's name
     * @param departmentName String of department's name
     */
    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * Returns String of department's location
     * @return String of department's location
     */
    public String getDepartmentLocation() {
        return departmentLocation;
    }

    /**
     * Sets the department's location
     * @param departmentLocation string of the department's location
     */
    public void setDepartmentLocation(String departmentLocation) {
        this.departmentLocation = departmentLocation;
    }

    /**
     * Sets CourseList
     * @param coursesList An ArrayList of Courses
     */
    public void setCoursesList(ArrayList<Course> coursesList) {
        this.coursesList = coursesList;
    }

    /**
     * Sets the available list of rooms for the department
     * @param roomsList ArrayList of rooms
     */
    public void setRoomsList(ArrayList<Room> roomsList) {
        this.roomsList = roomsList;
    }

    /**
     * Sets the available professors for the department
     * @param professorsList ArrayList of professors
     */
    public void setProfessorsList(ArrayList<Professor> professorsList) {
        ProfessorsList = professorsList;
    }

    /**
     * Returns an ArrayList of Course objects
     * @return ArrayList of Course objects
     */
    public ArrayList<Course> getCoursesList() {
        return this.coursesList;
    }

    /**
     * Returns an ArrayList of Room objects
     * @return ArrayList of Rooom objects
     */
    public ArrayList<Room> getRoomsList() {
        return this.roomsList;
    }

    /**
     * Returns an ArrayList of Professor objects
     * @return ArrayList of Professor objects
     */
    public ArrayList<Professor> getProfessorList() {
        return this.ProfessorsList;
    }



    public ArrayList<ClassTimes> getMeetingTimes()
    {
        return meetingTimes;
    }

    public void setMeetingTimes(ArrayList<ClassTimes> meetingTimes) {
        this.meetingTimes = meetingTimes;
    }

    /**
     * Looks at the possible class times and sets a random combination
     * @return randomTime
     */
    public ClassTimes getRandomMeetingTime(int day, List<String> times)
    {
        ClassTimes randomTime = new ClassTimes();

        randomTime.setRandomTime(this.getMeetingTimes().get(day).getDay() + ": " +
                                 times.get(random.nextInt(times.size())));
        return randomTime;
    }

    public void printDepartmentInfo(){
        System.out.println("Department: " + this.getDepartmentName());
        System.out.println("Location: " + this.getDepartmentLocation());
        System.out.println("Courses:");
        this.coursesList.forEach(p-> p.printCourse());
        System.out.println("Faculty:");
        this.ProfessorsList.forEach(p->p.displayProf());
        this.meetingTimes.forEach(p->p.printClassTimes());
        System.out.println("Available Classrooms:");
        this.roomsList.forEach(p->p.printRoom());
    }
}
