package CourseScheduling.src.main.java;

import java.util.ArrayList;

/**
 * ClassTimes class. This class stores information about a times classes are offered
 *
 * @author Ed Brown
 * @version 0.1
 * @since 9/20/2020
 */
public class ClassTimes {
    //Class Variables
    private String day;
    private ArrayList<String> times = new ArrayList<String>();

    /**
     * Class constructor
     */
    public ClassTimes(){
        this.day = null;
    }

    public ClassTimes(String day){
        this.day = day;
    }

    /**
     * Setter for day(s)
     * @param day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Adding a time to the day
     * @param timeToAdd
     */
    public void addTime(String timeToAdd) { this.times.add(timeToAdd) ;  }

    /**
     * Getter for day(s)
     * @return
     */
    public String getDay() {
        return day;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public void printClassTimes(){
        System.out.print("Day(s) of the Week: ");
        System.out.println(this.day);
        System.out.print("\tClass Times: ");
        System.out.println(this.times);
    }


}
