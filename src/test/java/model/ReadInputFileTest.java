package model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for reading input files
 *
 * This test automatically compares the values between a tsv file and a csv file that
 * should be identical. This ensures both the csv and tsv files are being processed the
 * same
 *
 * For verification of actual data, one should manually compare console output of
 * displayCSVdata and displayTSVdata tests to actual input file
 *
 * @author Ed Brown
 */
class ReadInputFileTest {
    //read in the two file types
    ReadInputFile csvInputs = new ReadInputFile("src/main/resources/InputData.csv");
    ReadInputFile tsvInputs = new ReadInputFile("src/main/resources/InputData.tsv");

    //pull the department information out of the read input file object
    //this contains all information read in from the file
    Department csvDept = csvInputs.getDepartment();
    Department tsvDept = tsvInputs.getDepartment();

    /**
     * This function will compare two courses to see they are the same
     * in all aspects
     * @param a first course to compare
     * @param b second course to compare
     * @return boolean value stating if the two courses are equal
     */
    private boolean compareCourses(Course a, Course b){
        boolean flag = true;
        //if course IDs to not equal change flag
        if( ! (a.getCourseId()).equals(b.getCourseId()) ){
            System.out.println(a.getCourseId());
            flag = false;
        }

        //if course preferences doesn't match change flag
        if( (a.getRoomPreference()) != (b.getRoomPreference()) ){
            System.out.println(a.getCourseId());
            flag = false;
        }

        //if max enrollments are different, change flag
        if ( a.getMaxEnrollment() != b.getMaxEnrollment()){
            System.out.println(a.getCourseId());
            flag = false;
        }

        return flag;
    }

    @Test
    void displayCSVdata(){
        System.out.println("---------------------------");
        System.out.println("Showing CSV information");
        System.out.println("---------------------------");
        csvDept.printDepartmentInfo();
        System.out.println("------------------------------------------------------");
        System.out.println("End of CSV information");
        System.out.println("------------------------------------------------------\n");
    }

    @Test
    void displayTSVdata(){
        System.out.println("---------------------------");
        System.out.println("Showing TSV information");
        System.out.println("---------------------------");
        tsvDept.printDepartmentInfo();
        System.out.println("------------------------------------------------------");
        System.out.println("End of TSV information");
        System.out.println("------------------------------------------------------\n");
    }

    @Test
    void compareDeptValues(){
        boolean flag = false; //flag for comparison

        //compare department information
        if( ((csvDept.getDepartmentName()).equals(tsvDept.getDepartmentName())) ){
            flag = true;
        }
        assertEquals(true, flag);
    }

    @Test
    void compareLocValues() {
        boolean flag = false; //flag for comparison

        //compare department information
        if( ((csvDept.getDepartmentLocation()).equals(tsvDept.getDepartmentLocation())) ){
            flag = true;
        }
        assertEquals(true, flag);
    }

    @Test
    void compareCourses(){
        ArrayList<Course> csvCoursesList = csvDept.getCoursesList();
        ArrayList<Course> tsvCoursesList = tsvDept.getCoursesList();

        //make sure both lists have the same number of courses
        boolean flag = false;
        if( csvCoursesList.size()==tsvCoursesList.size() ){
            flag = true;
        }
        assertEquals(true, flag);

        //go through each course and make sure they are the same
        flag = true; //rest flag
        for(int i = 0; i<csvCoursesList.size(); i++){
            if(!(compareCourses(csvCoursesList.get(i),tsvCoursesList.get(i)))){
                flag=false;
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void compareFaculty(){
        ArrayList<Professor> csvProfessors = csvDept.getProfessorList();
        ArrayList<Professor> tsvProfessors = tsvDept.getProfessorList();

        //make sure both lists have the same number of professors
        boolean flag = false;
        if(csvProfessors.size() == tsvProfessors.size()){
            flag = true;
        }
        assertEquals(true, flag);

        //go through each professor and make sure they are the same
        flag = true; //rest flag
        for(int i = 0; i<csvProfessors.size(); i++){
            //compare names
            if(! (csvProfessors.get(i).getName()).equals(tsvProfessors.get(i).getName()) ){
                flag = false;
            }

            //compare courses
            ArrayList<Course> csvCoursesList = csvProfessors.get(i).getTaughtCourses();
            ArrayList<Course> tsvCoursesList = tsvProfessors.get(i).getTaughtCourses();
            //make sure both lists have the same number of courses
            if( csvCoursesList.size()!=tsvCoursesList.size() ){
                flag = false;
            }
            //go through each course and make sure they are the same
            for(int ii = 0; ii<csvCoursesList.size(); ii++){
                if(!(compareCourses(csvCoursesList.get(ii),tsvCoursesList.get(ii)))){
                    flag=false;
                }
            }

            //compare preferences
            if(! ((csvProfessors.get(i).getPreference()).equals(tsvProfessors.get(i).getPreference()))){
                System.out.println(csvProfessors.get(i).getName() + "\t" + csvProfessors.get(i).getPreference() + " vs " + tsvProfessors.get(i).getPreference());
                flag = false;
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void compareClassTimes(){
        ArrayList<ClassTimes> csvMeetingTimes = csvDept.getMeetingTimes();
        ArrayList<ClassTimes> tsvMeetingTimes = tsvDept.getMeetingTimes();

        //make sure list are same size
        boolean flag=false;
        if(csvMeetingTimes.size()==tsvMeetingTimes.size()){
            flag = true;
        }
        assertEquals(true, flag);

        //make sure class times are the same
        flag = true; //reset flag
        for( int j = 0; j< csvMeetingTimes.size(); j++){
            //compare days
            if( !(csvMeetingTimes.get(j).getDay()).equals(tsvMeetingTimes.get(j).getDay())){
                flag = false;
            }

            //compare size of times
            if( csvMeetingTimes.get(j).getTimes().size() != tsvMeetingTimes.get(j).getTimes().size()){
                System.out.println(csvMeetingTimes.get(j).getDay()+":\t"+csvMeetingTimes.get(j).getTimes().size()+" vs "+ tsvMeetingTimes.get(j).getTimes().size());
                flag = false;
            }

            //compare actual times
            for(int k=0; k<csvMeetingTimes.get(j).getTimes().size(); k++){
                if( !(csvMeetingTimes.get(j).getTimes().get(k)).equals(tsvMeetingTimes.get(j).getTimes().get(k))){
                    flag = false;
                }
            }
        }
        assertEquals(true, flag);
    }

    @Test
    void compareClassrooms(){
        ArrayList<Room> csvRoomsList = csvDept.getRoomsList();
        ArrayList<Room> tsvRoomsList = tsvDept.getRoomsList();

        //make sure list are same size
        boolean flag=false;
        if(csvRoomsList.size()==tsvRoomsList.size()){
            flag = true;
        }
        assertEquals(true, flag);

        //make sure class rooms are the same
        flag = true; //reset flag
        for( int j = 0; j< csvRoomsList.size(); j++){
            //compare building
            if( !(csvRoomsList.get(j).getBuilding()).equals(tsvRoomsList.get(j).getBuilding())){
                flag = false;
            }
            //compare room number
            if ((csvRoomsList.get(j).getRoomNumber())!=(tsvRoomsList.get(j).getRoomNumber())){
                flag = false;
            }
            //compare capacity
            if((csvRoomsList.get(j).getRoomCapacity())!=(csvRoomsList.get(j).getRoomCapacity())){
                flag = false;
            }
        }
        assertEquals(true, flag);
    }
}