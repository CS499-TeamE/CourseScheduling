package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for ClassTimes
 * @author Ed Brown
 */
class ClassTimesTest {

    @Test
    void getDay() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "!123asdf@"};
        for(String day: days){
            ClassTimes ct = new ClassTimes(day);
            assertEquals(day, ct.getDay());
        }
    }

    @Test
    void setDay() {
        String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", "!123asdf@"};
        for(String day: days){
            ClassTimes ct = new ClassTimes();
            ct.setDay(day);
            assertEquals(day, ct.getDay());
        }
    }

    @Test
    void getTimes() {
        List<String> times = new ArrayList<String>();
        times.add("2:30");
        times.add("12:00");
        ClassTimes ct = new ClassTimes("Tuesday", times);
        assertEquals(times, ct.getTimes());
    }

    @Test
    void addTime() {
        String testTime = "12:30";

        //make a dummy list of times
        List<String> times = new ArrayList<String>();
        times.add("2:30");
        times.add("12:00");
        ClassTimes ct = new ClassTimes("Tuesday", times); //create class object

        //make sure time is not already there
        boolean flag = false;
        for(String time: ct.getTimes()){
            if(testTime.equals(time)){
                flag = true; //if time is found set to true
            }
        }
        assertEquals(false, flag);

        //add test time and make sure it is there
        ct.addTime(testTime);
        for(String time: ct.getTimes()){
            if(testTime.equals(time)){
                flag = true; //if time is found set to true
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void setTimes() {
        //make a dummy list of times
        List<String> times = new ArrayList<String>();
        times.add("2:30");
        times.add("12:00");

        //create a class and manually add the same times
        ClassTimes ct = new ClassTimes();
        ct.setTimes(times);

        //test
        assertEquals(times, ct.getTimes());
    }

    @Test
    void getRandomTime() {
        String random = "random thingy";

        //make a dummy list of times
        List<String> times = new ArrayList<String>();
        times.add("2:30");
        times.add("12:00");

        ClassTimes ct = new ClassTimes("Tuesday", times, random); //create class

        assertEquals(random, ct.getRandomTime());
    }

    @Test
    void setRandomTime() {
        String random = "random thingy";
        ClassTimes ct = new ClassTimes();
        ct.setRandomTime(random);

        assertEquals(random, ct.getRandomTime());
    }
}