package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * This class takes output data and formats into a
 * printer friendly layout
 *
 * @author Ed Brown
 */
public class Printify {
    String origData; //stores the original data
    String department; //value for department
    String printData; //reformatted data that is printer friendly
    List<FinalClass> courses = new ArrayList<>(); //list to store courses

    /**
     * Class constructor.
     * After creating the class only need to call the getter for printData to
     * receive the print friendly version
     * @param inData String of data to transform
     * @param dept String of department name
     */
    public Printify(String inData, String dept){
        this.origData = inData;
        System.out.println(inData);
        this.department = dept;
        collect(); //parse data
        Collections.sort(courses); //sort data
        mutate(); //change data
    }

    /**
     * Takes the input data and turns them into objects
     * and stores into courses list. This function
     * will assist with sorting and printing
     */
    private void collect(){
       // Pattern pattern = Pattern.compile(".*[0-9].*");
        String lines[] = this.origData.split("\\n"); //split data by lines

        //parse data into finalClass object
        for(int i=1; i< lines.length; i++){
            FinalClass fClass = new FinalClass();
            String courseInfo[] = lines[i].split("\\|");

            //debug loop to see whats in course info array
            /*for (String course: courseInfo){
                System.out.println(course);
            }*/

            //Set course ID
            //Course ID is found before the first tab
            int parse = courseInfo[0].indexOf("\t"); //find first delimiter
            fClass.setCourseID(courseInfo[0].substring(0,parse)); //filter out the first tab and assign courseID

            //Set max enrollment
            //Max attendance is found between after the 4th tab and between the last tab
            //System.out.println("Orig info..." + courseInfo[1]); //debug line
            String maxAttendance = courseInfo[1];
            for (int a=0; a<4;a++){
                parse = maxAttendance.indexOf("\t"); //find first delimiter
                //System.out.println("Index of Parse " +parse); //debug line
                maxAttendance = maxAttendance.substring(parse+1); //filter out the first tab
               // System.out.println("After " + a + " parsing max info is..." + maxAttendance); //debug line
            }

            parse = maxAttendance.indexOf("\t"); //find next delimiter
            //System.out.println("Max is:..." + maxAttendance.substring(0,parse)); //debug line
            fClass.setMax(maxAttendance.substring(0,parse)); //filter out anything after the second tab and assign max attendance

            //Set room number
            //Room number is found between two tabs
            parse = courseInfo[2].indexOf("\t"); //find first delimiter
            String room = courseInfo[2].substring(parse+1); //filter out the first tab
            parse = room.indexOf("\t"); //find next delimiter
            fClass.setRoom(room.substring(0,parse)); //filter out anything after the second tab and assign max attendance

            //Set Room Capacity
            //Room capacity is found between 4 tabs and the last tab
            String roomCap = courseInfo[3];
            for (int a=0; a<4;a++){
                parse = roomCap.indexOf("\t"); //find first delimiter
                //System.out.println("Index of Parse " +parse); //debug line
                roomCap = roomCap.substring(parse+1); //filter out the first tab
               // System.out.println("After " + a + " parsing info is roomCap..." + roomCap); //debug line
            }
            parse = roomCap.indexOf("\t"); //find next delimiter
            fClass.setRoomCap(roomCap.substring(0,parse));

            //Set Meeting Times
            //Meeting time is found after the second tab
            String dayAndTime = courseInfo[4];
            for (int a=0; a<2; a++){
                parse = dayAndTime.indexOf("\t"); //find first delimiter
                dayAndTime = dayAndTime.substring(parse+1);
            }

            //parse out day and times
            parse = dayAndTime.indexOf(":");
            fClass.setDay(dayAndTime.substring(0,parse));

            String classTime = dayAndTime.substring(parse+2);
            //System.out.println(classTime); //debug line
            int parseTab = classTime.indexOf("\t");
            classTime = classTime.substring(0,parseTab);
            //System.out.println(classTime); //debug line
            fClass.setTime(classTime); //filter and assign value

            //Set Professor
            //Professor is found between a tab and the second space (first space is after the first name)
            parse = courseInfo[5].indexOf("\t"); //find first delimiter
            String prof = courseInfo[5].substring(parse+1); //filter out the first tab
            //System.out.println("Prof after first parse.."+prof); //debug line
            fClass.setProf(prof);

            this.courses.add(fClass); //add course to list

            //Debug line
            //System.out.println("Max course attendance for.." + fClass.getCourseID() +  "..is.." + fClass.getMax() + "..in.." + fClass.getRoom()+"..which has max desks of.." + fClass.getRoomCap() + "..taught by.." + fClass.getProf() + "..at.." + fClass.getTime() +".."); //debug line
        }
    }


    /**
     * Takes data and makes a string that is printer friendly
     */
    private void mutate(){
        this.printData = this.department + " Course Listing\n\n"; //first line shows department

        //set headers
        this.printData += "      \tMax\n";
        this.printData += "Course\tEnrl     Room\t    Days\tTimes\t  Instructor\n";
        this.printData += "----------\t------    --------\t    ---------\t-------------\t  --------------\n";

        //print data for each class
        for(FinalClass fc: this.courses){
            this.printData += fc.getCourseID() + "\t " + fc.getMax() + "       " + fc.getRoom() + "\t    " + fc.getDay() + "\t" + fc.getTime() + "\t  " + fc.getProf() + "\n";
        }
    }

    /**
     * Getter for print friendly string of classes
     * @return String of class information in a print friendly format
     */
    public String getPrintData(){
        return this.printData;
    }
}
