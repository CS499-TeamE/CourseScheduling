package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for department class
 * @author Ed Brown
 */
class DepartmentTest {
    private String[] names = {"CS", "EE", "COM", "BS"};
    private String faculty[] = { "Dr. Able", "Dr. Brovo", "Dr. Charlie", "Dr. Dog", "Dr. Echo", "Dr. Foxtrot", "Dr. Golf", "Dr. Hotel", "Dr. India", "Dr. Juliet" };
    private String[] locations = {"Tech Hall", "BFE", "Parking Lot", "Charger Station", "Out Back"};
    private String[] times = {"8:00 - 9:20","9:40 - 11:00","11:20 - 12:40","1:00 - 2:20","2:40 - 4:00","4:20 - 5:40","6:00 - 7:20"};

    @Test
    void getDepartmentName() {
        for(String name:names){
            Department d = new Department(name);
            assertEquals(name, d.getDepartmentName());
        }
    }

    @Test
    void setDepartmentName() {
        for(String name:names){
            Department d = new Department();
            d.setDepartmentName(name);
            assertEquals(name, d.getDepartmentName());
        }
    }

    @Test
    void getDepartmentLocation() {
        for(String location: locations){
            Department d = new Department("CS", location);
            assertEquals(location, d.getDepartmentLocation());
        }
    }

    @Test
    void setDepartmentLocation() {
        for(String location: locations){
            Department d = new Department();
            d.setDepartmentLocation(location);
            assertEquals(location, d.getDepartmentLocation());
        }
    }

    @Test
    void getCoursesList() {
        //make a dummy array list of courses
        ArrayList<Course> coursesList = new ArrayList<>();
        for(int i = 0; i<10; i++){
            String course = "Course"+i;
            Course c = new Course(course);
            coursesList.add(c);
        }

        Department d = new Department(coursesList); //create department with courses

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<Course> coursesList2 = new ArrayList<>();
        for(int i = 0; i<10; i++){
            String course = "Course"+i;
            Course c = new Course(course);
            coursesList2.add(c);
        }

        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<coursesList2.toArray().length; i++){
            //System.out.println(coursesList2.get(i).getCourseId()); //debug line
            //System.out.println(d.getCoursesList().get(i).getCourseId()); //debug line
            if (!(coursesList2.get(i).getCourseId().equals(d.getCoursesList().get(i).getCourseId()))){

                flag = false;
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void setCoursesList() {
        Department d = new Department(); //create department
        //make a dummy array list of courses
        ArrayList<Course> coursesList = new ArrayList<>();
        for(int i = 0; i<10; i++){
            String course = "Course"+i;
            Course c = new Course(course);
            coursesList.add(c);
        }

        d.setCoursesList(coursesList);

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<Course> coursesList2 = new ArrayList<>();
        for(int i = 0; i<10; i++){
            String course = "Course"+i;
            Course c = new Course(course);
            coursesList2.add(c);
        }

        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<coursesList2.toArray().length; i++){
            //System.out.println(coursesList2.get(i).getCourseId()); //debug line
            //System.out.println(d.getCoursesList().get(i).getCourseId()); //debug line
            if (!(coursesList2.get(i).getCourseId().equals(d.getCoursesList().get(i).getCourseId()))){

                flag = false;
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void getRoomsList() {
        //make a dummy array list of Rooms
        ArrayList<Room> roomsList = new ArrayList<>();
        for(int i = 0; i<10; i++){
            Room r = new Room();
            r.setRoomNumber(i);
            roomsList.add(r);
        }

        Department d = new Department("Computer Science", roomsList); //create department with rooms

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<Room> roomsList2 = new ArrayList<>();
        for(int i = 0; i<10; i++){
            Room r = new Room();
            r.setRoomNumber(i);
            roomsList2.add(r);
        }

        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<roomsList2.toArray().length; i++){
            if( (roomsList2.get(i).getRoomNumber()) != (roomsList.get(i).getRoomNumber()) ){
                flag = false;
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void setRoomsList() {
        Department d = new Department(); //create department
        //make a dummy array list of Rooms
        ArrayList<Room> roomsList = new ArrayList<>();
        for(int i = 0; i<10; i++){
            Room r = new Room();
            r.setRoomNumber(i);
            roomsList.add(r);
        }

        d.setRoomsList(roomsList); //set rooms

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<Room> roomsList2 = new ArrayList<>();
        for(int i = 0; i<10; i++){
            Room r = new Room();
            r.setRoomNumber(i);
            roomsList2.add(r);
        }

        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<roomsList2.toArray().length; i++){
            if( (roomsList2.get(i).getRoomNumber()) != (roomsList.get(i).getRoomNumber()) ){
                flag = false;
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void getProfessorList() {
        //make a dummy list of professors
        ArrayList<Professor> pl = new ArrayList<>();
        for(String teacher: faculty){
            Professor p = new Professor(teacher);
            pl.add(p);
        }

        Department d = new Department(pl, "Tech Hall"); //create department with list of professors

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<Professor> pl2 = new ArrayList<>();
        for(String teacher: faculty){
            Professor p = new Professor((teacher));
            pl2.add(p);
        }
        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<pl2.toArray().length; i++) {
            if (!(pl2.get(i).getName().equals(d.getProfessorList().get(i).getName()))) {
                flag = false;
            }
        }

        assertEquals(true, flag);
    }

    @Test
    void setProfessorsList() {
        Department d = new Department();

        //make a dummy list of professors
        ArrayList<Professor> pl = new ArrayList<>();
        for(String teacher: faculty){
            Professor p = new Professor(teacher);
            pl.add(p);
        }

        d.setProfessorsList(pl); //set professors

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<Professor> pl2 = new ArrayList<>();
        for(String teacher: faculty){
            Professor p = new Professor((teacher));
            pl2.add(p);
        }
        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<pl2.toArray().length; i++) {
            if (!(pl2.get(i).getName().equals(d.getProfessorList().get(i).getName()))) {
                flag = false;
            }
        }

        assertEquals(true, flag);
    }

    @Test
    void getMeetingTimes() {
        //make dummy data
        ArrayList<ClassTimes> meetingTimes = new ArrayList<>(); //make a dummy list of class times
        List<String> classTime = new ArrayList<String>(); //make a dummy list of times
        for(String time: times){
            classTime.add(time);
        }
        ClassTimes ct = new ClassTimes("M/W", classTime);
        meetingTimes.add(ct);
        ClassTimes ct2 = new ClassTimes("T/R", classTime);
        meetingTimes.add(ct2);

        Department d = new Department("Computer Science", "Tech Hall", meetingTimes); //create department with meeting times

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<ClassTimes> meetingTimes2 = new ArrayList<>(); //make a dummy list of class times
        List<String> classTime2 = new ArrayList<String>(); //make a dummy list of times
        for(String time: times){
            classTime2.add((time));
        }
        ClassTimes ct3 = new ClassTimes("M/W", classTime2);
        meetingTimes2.add(ct3);
        ClassTimes ct4 = new ClassTimes("T/R", classTime2);
        meetingTimes2.add(ct4);

        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<meetingTimes2.toArray().length; i++) {
            //test for day value
            if(!(d.getMeetingTimes().get(i).getDay().equals(meetingTimes2.get(i).getDay()))){
                flag = false;
            }
            //test for class time values
            for(int j=0; j<((meetingTimes2.get(i)).getTimes()).size(); j++){
                if(!((d.getMeetingTimes().get(i).getTimes().get(j)).equals(meetingTimes2.get(i).getTimes().get(j)))){
                    flag = false;
                }
            }
        }

        assertEquals(true, flag);
    }

    @Test
    void setMeetingTimes() {
        Department d = new Department();

        //make dummy data
        ArrayList<ClassTimes> meetingTimes = new ArrayList<>(); //make a dummy list of class times
        List<String> classTime = new ArrayList<String>(); //make a dummy list of times
        for(String time: times){
            classTime.add(time);
        }
        ClassTimes ct = new ClassTimes("M/W", classTime);
        meetingTimes.add(ct);
        ClassTimes ct2 = new ClassTimes("T/R", classTime);
        meetingTimes.add(ct2);

        d.setMeetingTimes(meetingTimes);

        //Make another list to compare too. Copy loop above to pass, change loop parameters to make test fail
        ArrayList<ClassTimes> meetingTimes2 = new ArrayList<>(); //make a dummy list of class times
        List<String> classTime2 = new ArrayList<String>(); //make a dummy list of times
        for(String time: times){
            classTime2.add((time));
        }
        ClassTimes ct3 = new ClassTimes("M/W", classTime2);
        meetingTimes2.add(ct3);
        ClassTimes ct4 = new ClassTimes("T/R", classTime2);
        meetingTimes2.add(ct4);

        //go through and verify courses are the same
        boolean flag = true;
        for(int i=0; i<meetingTimes2.toArray().length; i++) {
            //test for day value
            if(!(d.getMeetingTimes().get(i).getDay().equals(meetingTimes2.get(i).getDay()))){
                flag = false;
            }
            //test for class time values
            for(int j=0; j<((meetingTimes2.get(i)).getTimes()).size(); j++){
                if(!((d.getMeetingTimes().get(i).getTimes().get(j)).equals(meetingTimes2.get(i).getTimes().get(j)))){
                    flag = false;
                }
            }
        }

        assertEquals(true, flag);
    }
}