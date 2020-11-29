package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for FinalClass
 * @author Ed Brown
 */
class FinalClassTest {
    private String courseIDs[] = {"CS 102", "CS207", "@@Ddafa", "! CS 307"};
    private String maximums[] = {"1", "0", "100", "_32", "  @  405", "002"};
    private String[] faculty= {"Mr. Smith", "Test", "1234", "RandomTeacher", "Dr. Delugach", "!CS_@"};
    private String[] times = {"8:00 - 9:20","9:40 - 11:00","11:20 - 12:40","1:00 - 2:20","2:40 - 4:00","4:20 - 5:40","6:00 - 7:20"};
    private String[] days = {"M/W", "T/R"};


    @Test
    void getCourseID() {
        for(String course: courseIDs){
            FinalClass fc = new FinalClass(course);
            assertEquals(course, fc.getCourseID());
        }
    }

    @Test
    void setCourseID() {
        FinalClass fc = new FinalClass();
        for(String course: courseIDs){
            fc.setCourseID(course);
            assertEquals(course, fc.getCourseID());
        }
    }

    @Test
    void getMax() {
        for(String max: maximums){
            FinalClass fc = new FinalClass("CS121", max);
            assertEquals(max, fc.getMax());
        }
    }

    @Test
    void setMax() {
        FinalClass fc = new FinalClass();
        for(String max: maximums){
            fc.setMax(max);
            assertEquals(max, fc.getMax());
        }
    }

    @Test
    void getRoom() {
        for(String max: maximums){
            FinalClass fc = new FinalClass("CS121", "30", max);
            assertEquals(max, fc.getRoom());
        }
    }

    @Test
    void setRoom() {
        FinalClass fc = new FinalClass();
        for(String max: maximums){
            fc.setRoom(max);
            assertEquals(max, fc.getRoom());
        }
    }

    @Test
    void getProf() {
        for(String teacher: faculty){
            FinalClass fc = new FinalClass("CS121", "30", "TH308N", teacher);
            assertEquals(teacher, fc.getProf());
        }
    }

    @Test
    void setProf() {
        FinalClass fc = new FinalClass();
        for(String teacher: faculty){
            fc.setProf(teacher);
            assertEquals(teacher, fc.getProf());
        }
    }

    @Test
    void getTime() {
        for(String time: times){
            FinalClass fc = new FinalClass("CS121", "30", "TH308N", "Dr. Teacher", time);
            assertEquals(time, fc.getTime());
        }
    }

    @Test
    void setTime() {
        FinalClass fc = new FinalClass();
        for(String time: times){
            fc.setTime(time);
            assertEquals(time, fc.getTime());
        }
    }

    @Test
    void getDay() {
        for(String day: days){
            FinalClass fc = new FinalClass("CS121", "30", "TH308N", "Dr. Teacher", "8:30", day);
            assertEquals(day, fc.getDay());
        }
    }

    @Test
    void setDay() {
        FinalClass fc = new FinalClass();
        for(String day: days){
            fc.setDay(day);
            assertEquals(day, fc.getDay());
        }
    }

    @Test
    void getRoomCap() {
        for(String max: maximums){
            FinalClass fc = new FinalClass("CS121", "30", "TH308N", "Dr. Teacher", "8:30", "M/W", max);
            assertEquals(max, fc.getRoomCap());
        }
    }

    @Test
    void setRoomCap() {
        FinalClass fc = new FinalClass();
        for(String max: maximums){
            fc.setRoomCap(max);
            assertEquals(max, fc.getRoomCap());
        }
    }
}