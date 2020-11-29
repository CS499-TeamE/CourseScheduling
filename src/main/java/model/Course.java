package model;

/**
 * Course Class that contains the information of the Course, such as Course ID and Room Preference.
 *
 * @author Youssef Sleiman
 */
public class Course {

    /**
     * Class variables
     */
    private String courseId;
    private int RoomPreference =0;
    private int maxEnrollment = 0;

    /**
     * Default constructor. Sets Variables to null
     */
    public Course() {
        this.courseId = null;
    }

    /**
     * Overloaded constructor for if the Course ID is known.
     * @param courseId string that represents Course ID
     */
    public Course(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Overloaded constructor for if the Course ID and Room Preference is Known
     * @param courseId string that represents Course ID
     * @param RoomPreference int that represents Room Preference
     */
    public Course(String courseId, int RoomPreference) {
        this.courseId = courseId;
        this.RoomPreference = RoomPreference;
    }

    /**
     * Overloaded constructor for if the Course ID, Room Preference, and max enrollment is Known
     * @param courseId string that represents Course ID
     * @param RoomPreference int that represents Room Preference
     * @param maxEnrollment int that represent max enrollment of students allowed for course
     */
    public Course(String courseId, int RoomPreference, int maxEnrollment) {
        this.courseId = courseId;
        this.RoomPreference = RoomPreference;
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * Returns the Course ID as a string
     * @return string that represents Course ID
     */
    public String getCourseId() {
        return this.courseId;
    }

    /**
     * Returns the room preference of this course object as an int
     * @return int that represents room preference
     */
    public int getRoomPreference() {
        return this.RoomPreference;
    }

    /**
     * Updates value of courseId
     * @param courseId string to represent new courseId
     */
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    /**
     * Updates value of roomPreference
     * @param RoomPreference int to represent new roomPreference
     */
    public void setRoomPreference(int RoomPreference) {
        this.RoomPreference = RoomPreference;
    }

    /**
     * Returns value of maxEnrollment
     * @return int representing maximum number of students
     */
    public int getMaxEnrollment() {
        return maxEnrollment ;
    }

    /**
     * Updates value of maxEnrollment
     * @param maxEnrollment int representing maximum number of students
     */
    public void setMaxStudents(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    /**
     * Debugging function to print contents of course to console.
     */
    public void printCourse(){
        System.out.print("\t"+this.getCourseId()); //print course ID
        if(this.RoomPreference==0){
            System.out.print(": No Room Preference");
        }else{
            System.out.print(": prefers room " + this.getRoomPreference());
        }
        System.out.println(". Max enrollment of " + this.getMaxEnrollment());
    }

    @Override
    public String toString() {
        return this.getCourseId();
    }
}