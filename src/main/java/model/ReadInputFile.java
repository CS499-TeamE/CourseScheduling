package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ReadInputFile is responsible for parsing the input file
 * and making the appropriate class objects
 *
 * @author Ed Brown
 * @version 0.1
 * @since 9/29/2020
 */
public class ReadInputFile
{
    //variables
    private Department dept; //department variable to store all the information
    private ArrayList<Course> courses = new ArrayList<>();; //list of available courses
    private ArrayList<Professor> professors = new ArrayList<>(); //list of available faculty members
    private ArrayList<ClassTimes> times = new ArrayList<>(); //list of class times
    private ArrayList<Room> rooms = new ArrayList<>(); //list of rooms

    /**
     * Class constructor. Asks the user for the file name through the console
     */
    public ReadInputFile(){
        //instantiate classes
        dept = new Department(); //make a new department

        // Scanner for user input
        Scanner user = new Scanner( System.in );
        String  inputFileName;

        // prepare the input file
        System.out.print("Input File Name: ");
        inputFileName = user.nextLine().trim();

        this.determineFileType(inputFileName);
    }

    /**
     * Overload class constructor. Used when the file name is already known.
     * @param inputFileName string of file name
     */
    public ReadInputFile(String inputFileName){
        //instantiate classes
        dept = new Department(); //make a new department

        this.determineFileType(inputFileName);
    }

    /**
     * Returns department info
     * @return a department class object
     */
    public Department getDepartment()
    {
        return dept;
    }

    /**
     * This function determines the file type of input file
     * @param fileName
     */
    private void determineFileType(String fileName){
        int parse = fileName.indexOf("."); //find index of "." which separates file type
        String sub = fileName.substring(parse+1); //this string will be the file extension
        //System.out.println(sub); //debug line
        if (sub.equals("csv")){
            this.parseData(fileName, ",");
        }else if(sub.equals("tsv")){
            this.parseData(fileName, "\t");
        }else{
            System.out.println("Invalid file type");
        }
    }

    /**
     * parseData function is the start of the parsing of input. As needed, other functions
     * will be called to gather detailed data
     * @param filename String of the input file name
     * @param delimiter String of the delimiter  value used in the input file
     */
    private void parseData(String filename, String delimiter){
        // enclose reading file inside a try/catch block
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            //----------------------------------------------------------------------
            //gather department info
            //----------------------------------------------------------------------
            String data = myReader.nextLine();
            int parse = data.indexOf(":"); //find index of ":" which separates string for useful info
            int parse2 = data.indexOf(delimiter); //find index of delimiter which separates string for useful info
            String sub = data.substring(parse+2,parse2); //string of department name
            //System.out.println("Department is " + sub); //debug line
            this.dept.setDepartmentName(sub); //set name

            //----------------------------------------------------------------------
            //gather location info
            //----------------------------------------------------------------------
            data = myReader.nextLine();
            parse = data.indexOf(":"); //find index of ":" which separates string for useful info
            parse2 = data.indexOf(delimiter); //find index of delimiter which separates string for useful info
            sub = data.substring(parse+2,parse2); //string of department location
            //System.out.println("Location is " + sub); //debug line
            this.dept.setDepartmentLocation(sub); //set location

            //read the rest of the input file
            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                parse2 = data.indexOf(delimiter); //find index of delimiter which separates cells for useful info
                //----------------------------------------------------------------------
                //gather course data
                //----------------------------------------------------------------------
                if(data.substring(0,parse2).equals("Courses Offered")){
                    data=myReader.nextLine(); //read line after Courses Offered, which should be blank
                    data=myReader.nextLine(); //read next line, this should be the first line with class numbers
                    while (!data.equals("")){
                        this.parseCourseData(data,delimiter);
                        data=myReader.nextLine();
                        parse2 = data.indexOf(delimiter); //find index of delimiter which separates cells for useful info
                        if(parse2<1){ //if this row is a blank line
                            break;
                        }
                    }

                }

                //----------------------------------------------------------------------
                //gather classroom preference data
                //----------------------------------------------------------------------
                if(data.substring(0,parse2).equals("Classroom Preferences:")){
                    data=myReader.nextLine(); //read next line, this should be the start of classroom preferences
                    while (!data.equals("")){
                        this.parseCoursePreferences(data,delimiter);
                        data=myReader.nextLine();
                        parse2 = data.indexOf(delimiter); //find index of delimiter which separates cells for useful info
                        if(parse2<1){ //if this row is a blank line
                            break;
                        }
                    }
                }

                //----------------------------------------------------------------------
                //gather professor data
                //----------------------------------------------------------------------
                if(data.substring(0,parse2).equals("Faculty Assignments:")){
                    data=myReader.nextLine(); //read next line, this should be the start of professors
                    while (!data.equals("")){
                        this.parseProfInfo(data,delimiter);
                        data=myReader.nextLine();
                        parse2 = data.indexOf(delimiter); //find index of delimiter which separates cells for useful info
                        if(parse2<1){ //if this row is a blank line
                            break;
                        }
                    }
                }

                //----------------------------------------------------------------------
                //gather class times
                //----------------------------------------------------------------------
                if(data.substring(0,parse2).equals("Class Times:")){
                    data=myReader.nextLine(); //read next line, this should be the start of class times
                    while (!data.equals("")){
                        this.parseClassTimes(data,delimiter);
                        data=myReader.nextLine();
                        parse2 = data.indexOf(delimiter); //find index of delimiter which separates cells for useful info
                        if(parse2<1){ //if this row is a blank line
                            break;
                        }
                    }
                }

                //----------------------------------------------------------------------
                //gather classrooms
                //----------------------------------------------------------------------
                //if(delimiter.equals(",")) {
                    if (data.substring(0, parse2).equals("Classroom")) {
                        data = myReader.nextLine(); //read next line, this should be the start of classroom information
                        while (!data.equals("")) {
                            this.parseClassRooms(data, delimiter);

                            //if there is a next line continue to read
                            if (myReader.hasNextLine()) {
                                data = myReader.nextLine(); //read next line;
                            } else {
                                break;
                            }

                            parse2 = data.indexOf(delimiter); //find index of delimiter which separates cells for useful info
                            if (parse2 < 1) { //if this row is a blank line
                                break;
                            }
                        }
                    }
               /* }else if(delimiter.equals("\t")){
                    System.out.println(data);
                }*/
            }
            myReader.close(); //close file reader

            //add items to department object
            this.dept.setCoursesList(this.courses);
            this.dept.setMeetingTimes(this.times);
            this.dept.setRoomsList(this.rooms);
            this.dept.setProfessorsList(this.professors);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    /**
     * Parse Course Data takes input string and extracts course numbers.
     * This assumes that commas are not used as values
     * @param courseInfo line from the input stream and parses around commas to get course data
     * @param delimiter delimiter character from file type
     */
    private void parseCourseData(String courseInfo,String delimiter){
        int parse = courseInfo.indexOf(delimiter);
        while(parse > 1){
            Course c = new Course(); //make a new course

            // 1. Extract info for a single course
            String courseData = courseInfo.substring(0,parse);

            // 2. Extract class number
            parse = courseData.indexOf(":");
            c.setCourseId(courseData.substring(0,parse)); //set course ID

            // 3. Extract max enrollment
            courseData = courseData.substring(parse+1);
            parse = courseData.indexOf("people");
            courseData = courseData.substring(1,parse-1);
            c.setMaxStudents(Integer.parseInt(courseData));

            // 4. Add course to ArrayList and create next substring for processing
            this.courses.add(c); //add course to the array list
            parse = courseInfo.indexOf(delimiter); //find delimiter value
            courseInfo = courseInfo.substring(parse+1); //remove newly added course from the string
            parse = courseInfo.indexOf(delimiter); //recalculate parsing position
        }
    }

    /**
     * Parses the course preferences and stores them into the class object
     * @param courseInfo String to be analyzed to get course information
     * @param delimiter String of the delimiter  value used
     */
    private void parseCoursePreferences(String courseInfo,String delimiter){
        //variables
        String c; //course number with preference
        int r; //room number of class room preference

        //determine course number
        int parse = courseInfo.indexOf(" must");
        c = courseInfo.substring(0,parse);

        //determine room
        parse = courseInfo.indexOf(delimiter);
        courseInfo = courseInfo.substring(parse+1);
        parse = courseInfo.indexOf("room");
        courseInfo = courseInfo.substring(parse+5);
        parse = courseInfo.indexOf(delimiter);
        if(parse==-1){
            r = Integer.parseInt(courseInfo);
        }else {
            r = Integer.parseInt(courseInfo.substring(0,parse));
        }
        //System.out.println(c + " prefers room " + r); //debug line

        //search through list to assign preference
        this.courses.forEach(p-> {
            if((p.getCourseId().equals(c))){
                p.setRoomPreference(r);
            }
        });
    }


    /**
     * Parses professor information and stores them into an object
     * @param data stringing containing faculty information
     * @param delimiter String of the delimiter  value used
     */
    private void parseProfInfo(String data, String delimiter){
        Professor p = new Professor(); //create new faculty object

        //parse into three strings (1. Name   2. Courses    3. Preferences)
        int parseLeft = data.indexOf(delimiter); //find index of first delimiter
        int parseRight = data.indexOf("-"); //find index of first "-"
        int parsePref = data.indexOf(":"); //find index of first ":"

        // 1. Faculty name
        String name = data.substring(0, parseLeft);
        p.setName(name);

        // 2. Courses
        String courses = data.substring(parseLeft+1, parseRight); //make a string of just the courses
        //System.out.println(courses); //debug line

        int parseCourses;
        do{
            String courseNumber;
            parseCourses = courses.indexOf(delimiter);
            //System.out.println("parsing value: " + parseCourses); //debug line

            if(parseCourses!=-1){
                courseNumber = courses.substring(1,parseCourses); //make string of just course number
                //System.out.println("course number: " + courseNumber); //debug line
            }else{ //last course number in the list
                courseNumber = courses.substring(1,courses.length()-1); //make string of just course number
                //System.out.println("last course number: " + courseNumber); //debug line
            }

            //go through courses to find a match
            this.courses.forEach(pair-> {
                if((pair.getCourseId().equals(courseNumber))){
                    p.addCourse(pair); //if found add course to professors list
                }
            });

            //Filter out class that was just added and recalculate parse position
            courses = courses.substring(parseCourses+1);
            //System.out.println(courses +" + parsing value: " + parseCourses); //debug line
        }while(parseCourses!=-1);

        // 3. Preferences
        String preference = data.substring(parsePref+1);
        parseCourses = preference.indexOf(delimiter);
        if (parseCourses != -1) {
            preference = preference.substring(0, parseCourses);
        }
        p.setPreference(preference);
        //System.out.println(p.getPreference()); //debug line

        // 4. Add professor to list
        professors.add(p);
    }

    /**
     * Parses the class times and stores them into an object
     * @param data string containing information about class times
     * @param delimiter String of the delimiter  value used
     */
    private void parseClassTimes(String data, String delimiter){
        ClassTimes t = new ClassTimes(); //create new class times object

        //Determine Days of the Week
        int parse = data.indexOf(delimiter); //find first delimiter
        t.setDay(data.substring(0,parse-1)); //set class days
        //System.out.println(t.getDay()); //debug line

        String sub = data.substring(parse+1); //filter days of week out of string

        //Add class times
        do{ //as long as there is still a delimiter
            parse = sub.indexOf(delimiter); //find next delimiter

            if (parse<1){//save in last class time
                t.addTime(sub);
                //System.out.println("Adding " + sub); //debug line
            }else{
                String newTime = sub.substring(0,parse); //make a string of the class time
                //System.out.println("Adding " + newTime); //debug line
                t.addTime(newTime); //add time to class object
                sub = sub.substring(parse+1); //filter out the class time that was just added
            }
           // System.out.println("Processing " + sub); //debug line
        }while (parse != -1);
        //t.printClassTimes(); //debug line
        times.add(t); //add ClassTimes object to ArrayList
    }

    /**
     * Parses the classroom data strings into objects
     * @param data string containing information about classrooms
     * @param delimiter String of the delimiter  value used
     */
    private void parseClassRooms(String data, String delimiter){
        //variables
        int parse;
        int spaceParse;
        int roomCapacity;
        String building;
        int roomNum;

        parse = data.indexOf(delimiter); //find first delimiter
        String room = data.substring(0,parse); //string value of first cell read in
        spaceParse = room.indexOf(" "); //find space which separates building from room number
        building = room.substring(0,spaceParse); //separate building
        String roomNumber = room.substring(spaceParse+1); //separates room
        roomNum = Integer.parseInt(roomNumber); //change room to an integer
        String roomCap = data.substring(parse+1); //the rest of the line without room number
        parse = roomCap.indexOf(delimiter);

        if(parse>1){ //Do more cells exist on this line?
            roomCap = roomCap.substring(0,parse); //if so truncate string
        }
        roomCapacity = Integer.parseInt(roomCap); //change capacity value to an integer

        Room r = new Room(building,roomNum,roomCapacity); //make new room object
        rooms.add(r); //add to ArrayList
    }

    /**
     * Debug function that allows the printing of the department information
     */
    public void debugPrint(){
        this.dept.printDepartmentInfo();
    }

    /**
     * Driver code
     * @param args input read from command line
     */
    public static void main(String[] args) {
         //ReadInputFile rif = new ReadInputFile("D:\\Temp\\DeptData.csv");
       // ReadInputFile rif = new ReadInputFile("src/main/resources/InputData.csv");
        ReadInputFile rif = new ReadInputFile("src/main/resources/InputData.tsv");
        //ReadInputFile rif = new ReadInputFile("D:\\Temp\\DeptData.tsv");
        rif.debugPrint();
    }
}
