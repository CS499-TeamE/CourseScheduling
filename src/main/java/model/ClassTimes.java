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

    /**
     * Overload class construction.
     * @param day String representing the day of the week the class takes place
     */
    public ClassTimes(String day){
        this.day = day;
    }

    /**
     * Overload class construction.
     * @param day String representing the day of the week the class takes place
     * @param times Array list of times available
     */
    public ClassTimes(String day, List<String> times){
        this.day = day;
        this.times = times;
    }

    /**
     * Overload class construction.
     * @param day String representing the day of the week the class takes place
     * @param times Array list of times available
     * @param randomTime String of a random value, used in scheduling algorithm
     */
    public ClassTimes(String day, List<String> times, String randomTime){
        this.day = day;
        this.times = times;
        this.randomTime = randomTime;
    }
    /**
     * Setter for day(s)
     * @param day String representing the day of the week the class takes place
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     * Adding a time to the day
     * @param timeToAdd adds a time slot to the ArrayList within the class object
     */
    public void addTime(String timeToAdd) { this.times.add(timeToAdd) ;  }

    /**
     * Getter for day
     * @return String representing the day of the week the class takes place
     */
    public String getDay()
    {
        return day;
    }

    /**
     * getter for times
     * @return Array list of strings representing times
     */
    public List<String> getTimes()
    {
        return times;
    }

    /**
     * setter for times
     * @param times Array list of strings representing times
     */
    public void setTimes(List<String> times)
    {
        this.times = times;
    }

    /**
     * Getter for random time
     * @return String value for random time
     */
    public String getRandomTime() {
        return randomTime;
    }

    /**
     * Setter for random time
     * @param randomTime String value for random time
     */
    public void setRandomTime(String randomTime) {
        this.randomTime = randomTime;
    }

    /**
     * Used to send information to the console for debugging
     */
    public void printClassTimes(){
        System.out.print("Day(s) of the Week: ");
        System.out.println(this.day);
        System.out.print("\tClass Times: ");
        System.out.println(this.times);
    }

    @Override
    public String toString() {
        return ("Day(s) of the Week: " + this.day + "\tClass Times: " + this.times);
    }

}
