package model;

import org.jetbrains.annotations.NotNull;

/**
 * Object representing the final information for a course.
 * These objects will mainly be used for printing final
 * schedules
 *
 * @author Ed Brown
 */
public class FinalClass implements Comparable<FinalClass> {
    private String courseID; //String representing the course number
    private String max; //String for the max enrollment amount in the course
    private String room; //String representing room number
    private String roomCap; //String representing room capacity
    private String prof; //String representing the professor
    private String time; //String representing the class time
    private String day; //String representing the class days

    /**
     * Default Constructor
     */
    public FinalClass(){ }

    /**
     * Overload class constructor
     * @param courseNumber //String representing the course number
     */
    public FinalClass(String courseNumber){
        this.courseID = courseNumber;
    }

    /**
     * Overload constructor
     * @param courseNumber String representing the course number
     * @param maxEnrollment String for the max enrollment amount in the course
     */
    public FinalClass(String courseNumber, String maxEnrollment){
        this.courseID = courseNumber;
        this.max = maxEnrollment;
    }

    /**
     * Overload constructor
     * @param courseNumber String representing the course number
     * @param maxEnrollment String for the max enrollment amount in the course
     * @param room String representing room number
     */
    public FinalClass(String courseNumber, String maxEnrollment, String room){
        this.courseID = courseNumber;
        this.max = maxEnrollment;
        this.room = room;
    }

    /**
     * Overload constructor
     * @param courseNumber String representing the course number
     * @param maxEnrollment String for the max enrollment amount in the course
     * @param room String representing room number
     * @param professor String representing the professor
     */
    public FinalClass(String courseNumber, String maxEnrollment, String room, String professor){
        this.courseID = courseNumber;
        this.max = maxEnrollment;
        this.room = room;
        this.prof = professor;
    }

    /**
     * Overload constructor
     * @param courseNumber String representing the course number
     * @param maxEnrollment String for the max enrollment amount in the course
     * @param room String representing room number
     * @param professor String representing the professor
     * @param time String representing the class time
     */
    public FinalClass(String courseNumber, String maxEnrollment, String room, String professor, String time){
        this.courseID = courseNumber;
        this.max = maxEnrollment;
        this.room = room;
        this.prof = professor;
        this.time = time;
    }

    /**
     * Overload constructor
     * @param courseNumber String representing the course number
     * @param maxEnrollment String for the max enrollment amount in the course
     * @param room String representing room number
     * @param professor String representing the professor
     * @param time String representing the class time
     * @param day String representing the class days
     */
    public FinalClass(String courseNumber, String maxEnrollment, String room, String professor, String time, String day){
        this.courseID = courseNumber;
        this.max = maxEnrollment;
        this.room = room;
        this.prof = professor;
        this.time = time;
        this.day = day;
    }

    /**
     * Overload constructor
     * @param courseNumber String representing the course number
     * @param maxEnrollment String for the max enrollment amount in the course
     * @param room String representing room number
     * @param professor String representing the professor
     * @param time String representing the class time
     * @param day String representing the class days
     * @param roomCap String representing room capacity
     */
    public FinalClass(String courseNumber, String maxEnrollment, String room, String professor, String time, String day, String roomCap){
        this.courseID = courseNumber;
        this.max = maxEnrollment;
        this.room = room;
        this.prof = professor;
        this.time = time;
        this.day = day;
        this.roomCap = roomCap;
    }

    /**
     * Getter for the course number
     * @return String representing the course number
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * Setter for the course number
     * @param courseID String representing the course number
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     * Getter for the max course enrollment
     * @return String for the max enrollment amount in the course
     */
    public String getMax() {
        return max;
    }

    /**
     * Setter for the max course enrollment
     * @param max String for the max enrollment amount in the course
     */
    public void setMax(String max) {
        this.max = max;
    }

    /**
     * Getter for room number
     * @return String representing room number
     */
    public String getRoom() {
        return room;
    }

    /**
     * Setter for room number
     * @param room String representing room number
     */
    public void setRoom(String room) {
        this.room = room;
    }

    /**
     * Getter for professor teaching the course
     * @return String representing the professor
     */
    public String getProf() {
        return prof;
    }

    /**
     * Setter for professor teaching the course
     * @param prof String representing the professor
     */
    public void setProf(String prof) {
        this.prof = prof;
    }

    /**
     * Getter for class time
     * @return String representing the class time
     */
    public String getTime() {
        return time;
    }

    /**
     * Setter for class time
     * @param time String representing the class time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Getter for class day
     * @return String representing the class days
     */
    public String getDay() {
        return day;
    }

    /**
     * Setter for class day
     * @param day String representing the class days
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Getter for room capacity
     * @return String representing room capacity
     */
    public String getRoomCap() {
        return roomCap;
    }

    /**
     * Setter for room capacity
     * @param roomCap String representing room capacity
     */
    public void setRoomCap(String roomCap) {
        this.roomCap = roomCap;
    }

    /**
     * Override function to enable sorting by course ID
     * @param o FinalClass object to compare wit
     * @return int value representing comparison value
     */
    @Override
    public int compareTo(@NotNull FinalClass o) {
        if(getCourseID() == null || o.getCourseID() == null){
            return 0;
        }
        return getCourseID().compareTo(o.getCourseID());
    }
}
