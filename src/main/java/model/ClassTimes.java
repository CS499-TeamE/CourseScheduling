package model;

import java.util.ArrayList;
import java.util.List;

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
    private String randomTime;
    private List<String> times = new ArrayList<String>();

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
    public String getDay()
    {
        return day;
    }

    /**
     * getter for times
     * @return
     */
    public List<String> getTimes()
    {
        return times;
    }

    /**
     * setter for times
     * @param times
     */
    public void setTimes(List<String> times)
    {
        this.times = times;
    }

    public String getRandomTime() {
        return randomTime;
    }

    public void setRandomTime(String randomTime) {
        this.randomTime = randomTime;
    }

    public void printClassTimes(){
        System.out.print("Day(s) of the Week: ");
        System.out.println(this.day);
        System.out.print("\tClass Times: ");
        System.out.println(this.times);
    }

}
