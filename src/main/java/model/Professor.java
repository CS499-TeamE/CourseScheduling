package model;

import java.util.ArrayList; //Import the ArrayList class

/**
 * Professor class, contains information such as Professor name, preference, and courses they teach
 *
 * @author Youssef Sleiman
 */
public class Professor {
    /**
     * Class variables
     */
    private String name;
    private String preference;
    private ArrayList<Course> taughtCourses = new ArrayList<Course>();

    /**
     * Default Constructor
     */
    public Professor() {
        this.name = null;
        this.preference = null;
    }

    /**
     * Overloaded constructor. Use if only name is known
     * @param name String that represents the Professor's name
     */
    public Professor(String name) {
        this.name = name;
    }

    /**
     * Overloaded constructor. Use if name and preference is known
     * @param name String that represents the Professor's name
     * @param preference String that represents the Professor's preference
     */
    public Professor(String name, String preference) {
        this.name = name;
        this.preference = preference;
    }

    /**
     * Returns the name of the Professor
     * @return string that is the name of the Professor
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the Professor's preference.
     * @return preference as a string
     */
    public String getPreference() {
        return this.preference;
    }

    /**
     * Returns the list of courses that is taught by the Professor
     * @return an ArrayList of strings
     */
    public ArrayList<Course> getTaughtCourses() {
        return this.taughtCourses;
    }

    /**
     * Updates the name of the Professor
     * @param name a string that is the name of the Professor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the preference of the Professor
     * @param preference a string that is the preference of the Professor
     */
    public void setPreference(String preference) {
        this.preference = preference;
    }

    /**
     *
     * @param c
     */
    public void addCourse(Course c){
        this.taughtCourses.add(c);
    }

    /**
     * Debugging tool. Print out all information for faculty member.
     */
    public void displayProf(){
        System.out.print("\t" + this.getName());
        System.out.print("\tCan teach:");
        this.taughtCourses.forEach(p->System.out.print(" "+p.getCourseId()+","));
        System.out.print("\tPreference: "+ this.getPreference());
        System.out.print("\n");
    }

    @Override
    public String toString() {
        return this.getName();
    }
}
