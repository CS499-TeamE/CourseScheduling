package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Test for Course
 * @author Ed Brown
 */
class CourseTest {

    @Test
    void getCourseId() {
        String[] classes = {"Tech Hall", "Test", "1234", "RandomPlace", "CS121", "CS499", "!CS_@"};
        for (String course : classes){
            Course c = new Course(course);
            assertEquals(course,c.getCourseId());
        }
    }

    @Test
    void getRoomPreference() {
        for(int i=-10;i<1000;i++){
            Course c = new Course("TestCourse",i);
            assertEquals(i, c.getRoomPreference());
        }
    }

    @Test
    void getMaxEnrollment() {
        for(int i=-10;i<1000;i++){
            Course c = new Course("TestCourse",121, i);
            assertEquals(i, c.getMaxEnrollment());
        }
    }

    @Test
    void setCourseId() {
        Course c = new Course();
        String[] classes = {"Tech Hall", "Test", "1234", "RandomPlace", "CS121", "CS499", "!CS_@"};
        for (String course : classes){
            c.setCourseId(course);
            assertEquals(course,c.getCourseId());
        }
    }

    @Test
    void setRoomPreference() {
        Course c = new Course();
        for(int i=-10;i<1000;i++){
            c.setRoomPreference(i);
            assertEquals(i, c.getRoomPreference());
        }
    }

    @Test
    void setMaxStudents() {
        Course c = new Course();
        for(int i=-10;i<1000;i++){
            c.setMaxStudents(i);
            assertEquals(i, c.getMaxEnrollment());
        }
    }
}