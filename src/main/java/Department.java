import java.util.ArrayList; //imports the ArrayList class

/**
 * Department class that contains the list of courses, rooms, and Professors for that department.
 *
 * @author Youssef Sleiman
 */
public class Department {
    /**
     * Class variables
     */
    private ArrayList<Course> coursesList;
    private ArrayList<Room> roomsList;
    private ArrayList<Professor> ProfessorsList;

    /**
     * Default constructor
     */
    public Department() {
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
}
