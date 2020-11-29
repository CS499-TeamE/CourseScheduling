package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for professor class
 * @author Ed Brown
 */
class ProfessorTest {

    @Test
    void getName() {
        String[] faculty= {"Mr. Smith", "Test", "1234", "RandomTeacher", "Dr. Delugach", "!CS_@"};
        for (String fac : faculty){
            Professor p = new Professor(fac);
            assertEquals(fac, p.getName());
        }
    }

    @Test
    void getPreference() {
        String[] preference = {"Morning", "Afternoon", "Evening", "None"};
        for(String pref: preference){
            Professor p = new Professor("Dr. D", pref);
            assertEquals(pref, p.getPreference());
        }
    }

    @Test
    void getTaughtCourses() {
        //create a professor and add some courses
        //test should pass if loop is set from i=0; i<10; i++
        //test should fail if these are changed
        Professor p = new Professor("Dr. Ed");
        for(int i = 0; i<10; i++){
            Course c = new Course("CS" + i);
            p.addCourse(c);
        }

        ArrayList<Course> taughtCourses = p.getTaughtCourses();

        //loop through taught courses and check that they match the ones added
        boolean flag = true;
        int k = 0;
        for( Course course: taughtCourses){
            if( !((course.getCourseId()).equals("CS" + k++)) ){
                flag = false;
            }
        }

        //test condition
        assertEquals(true, flag);
    }

    @Test
    void setName() {
        String[] faculty= {"Mr. Smith", "Test", "1234", "RandomTeacher", "Dr. Delugach", "!CS_@"};
        Professor p = new Professor();
        for (String fac : faculty){
            p.setName(fac);
            assertEquals(fac, p.getName());
        }
    }

    @Test
    void setPreference() {
        String[] preference = {"Morning", "Afternoon", "Evening", "None"};
        Professor p = new Professor();
        for(String pref: preference){
            p.setPreference(pref);
            assertEquals(pref, p.getPreference());
        }
    }

    @Test
    void addCourse() {
        //Testing if we can add course CS499
        //Step 1: Create a new professor and add some course
        Professor p = new Professor("Dr. Ed");
        for(int i = 0; i<10; i++){
            Course c = new Course("CS" + i);
            p.addCourse(c);
        }

        //p.addCourse(new Course("CS499")); //uncomment to make the first half of the test fail

        //See if CS499 is in list, it should not be
        ArrayList<Course> taughtCourses = p.getTaughtCourses();

        //loop through taught courses and check that they match the ones added
        boolean flag = true;
        for( Course course: taughtCourses){
            if(((course.getCourseId()).equals("CS499"))){
                flag = false;
            }
        }
        //test condition
        assertEquals(true, flag);
        //-----------------------------------------------------------------------

        flag = false; //set flag to fals

        p.addCourse(new Course("CS499")); //add course we are checking for to professor
        //add some more classes for good measure
        for(int i = 15; i<20; i++){
            Course c = new Course("CS" + i);
            p.addCourse(c);
        }

        ArrayList<Course> taughtCourses2 = p.getTaughtCourses();
        for( Course course2: taughtCourses){
            if(((course2.getCourseId()).equals("CS499"))){
                flag = true;
            }
        }

        //test condition
        assertEquals(true, flag);
    }
}