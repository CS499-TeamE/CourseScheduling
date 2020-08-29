import java.util.ArrayList; //Import the ArrayList class

/**
 * Faculty class. This class stores information about a professor (their name, classes they can teach, and preferences)
 *
 * @author Ed Brown
 * @version 0.1
 * @since 8/29/2020
 *
 * @TODO Add getters
 */
public class Faculty {
    private String name;
    private ArrayList<String> canTeach = new ArrayList<String>();
    private String preference;

    /**
     * Class constructor
     */
    public Faculty(){
        this.name = null;
        this.preference = null;
    }

    /**
     * Setter for faculty name
     * @param name string representing faculty's name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * Setter for adding available courses
     * @param course string representing course faculty can teach
     */
    public void addCourse(String course){
        this.canTeach.add(course);
    }

    /**
     * Setter for faculty's preference
     * @param pref string representing faculty's preference
     */
    public void setPreference(String pref){
        this.preference = pref;
    }

    /**
     * Debugging tool. Print out all information for faculty member.
     */
    public void display(){
        System.out.println(this.name);
        System.out.print("\tCourses: ");
        System.out.println(this.canTeach);
        System.out.println("\tPreference: " + this.preference);
    }
}
