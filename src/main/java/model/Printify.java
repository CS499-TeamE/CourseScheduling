package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        String lines[] = this.origData.split("\\n"); //split data by lines

        //parse data into finalClass object
        for(int i=1; i< lines.length; i++){
            FinalClass fClass = new FinalClass();
            String courseInfo[] = lines[i].split("\\|");

            //Set course ID
            //Course ID is found before the first tab
            int parse = courseInfo[0].indexOf("\t"); //find first delimiter
            fClass.setCourseID(courseInfo[0].substring(0,parse)); //filter out the first tab and assign courseID

            //Set max attendance
            //Max attendance is found between two tabs
            parse = courseInfo[1].indexOf("\t"); //find first delimiter
            String maxAttendance = courseInfo[1].substring(parse+1); //filter out the first tab
            parse = maxAttendance.indexOf("\t"); //find next delimiter
            fClass.setMax(maxAttendance.substring(0,parse)); //filter out anything after the second tab and assign max attendance

            //Set room number
            //Room number is found between two tabs
            parse = courseInfo[2].indexOf("\t"); //find first delimiter
            String room = courseInfo[2].substring(parse+1); //filter out the first tab
            parse = room.indexOf("\t"); //find next delimiter
            fClass.setRoom(room.substring(0,parse)); //filter out anything after the second tab and assign max attendance

            //Set Room Capacity
            //Room capacity is found between two tabs
            parse = courseInfo[3].indexOf("\t"); //find first delimiter
            String roomCap = courseInfo[3].substring(parse+1); //filter out the first tab
            parse = roomCap.indexOf("\t"); //find next delimiter
            fClass.setRoomCap(roomCap.substring(0,parse));

            //Set Professor
            //Professor is found between a tab and the second space (first space is after the first name)
            parse = courseInfo[4].indexOf("\t"); //find first delimiter
            String prof = courseInfo[4].substring(parse+1); //filter out the first tab
            parse = prof.indexOf(" "); //find next delimiter
            String temp = prof.substring(parse+1);
            parse += temp.indexOf(" "); //find next delimiter
            fClass.setProf(prof.substring(0,parse+1));

            //Set Meeting Times
            //Meeting time is found after the first tab
            parse = courseInfo[5].indexOf("\t"); //find first delimiter
            fClass.setTime(courseInfo[5].substring(parse+1)); //filter and assign value

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
            this.printData += fc.getCourseID() + "\t " + fc.getMax() + "       " + fc.getRoom() + "\t    " + "x/x" + "\t" + fc.getTime() + "\t  " + fc.getProf() + "\n";
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
