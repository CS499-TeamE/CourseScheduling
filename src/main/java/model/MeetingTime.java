package model;

/**
 * Class that represents the meeting time blocks for classes.
 *
 * @author Youssef Sleiman
 */
@Deprecated
public class MeetingTime {
    /**
     * Class Variable
     */
    private String time;

    /**
     * Default constructor
     */
    public MeetingTime() {

    }

    /**
     * Overloaded constructor for when time is known
     * @param time String that represents time
     */
    public MeetingTime(String time) {
        this.time = time;
    }

    /**
     * Returns the time as a String
     * @return a String that represents time
     */
    public String getTime() {
        return this.time;
    }

    /**
     * Updates the time
     * @param time a String that represents time
     */
    public void setTime(String time) {
        this.time = time;
    }
}
