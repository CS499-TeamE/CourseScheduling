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
     * Parse Course data takes input string and extracts course numbers.
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
            courseData = courseData.substring(parse+2);
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
        String r; //room number of class room preference
        String cells[] = courseInfo.split(delimiter); //split data by delimiter

        //1. Determine course number
        //Should always be first cell
        c = cells[0];

        //2. Determine room
        //Should always be second cell
        r = cells[1];

        //search through list to assign preference
        this.courses.forEach(p-> {
            if((p.getCourseId().equals(c))){
                p.setRoomPreference(r);
                //System.out.println("Preference found " + p.getCourseId()); //debug line
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

        String cells[] = data.split(delimiter); //split data by delimiter
        //debug loop to see what each cell looks like
        //for (String l: cells){
        //    System.out.println(l);
        //}

        //1. Faculty Name
        //Which is always the first cell
        p.setName(cells[0]);

        //2. Courses & Preference
        //Should be subsequent cells after faculty name
        for(int i=1; i<cells.length; i++){
            if (cells[i].length() != 0){ //if its not a blank cell
                int parsePref = cells[i].indexOf(":"); //find index of first ":"
                //if ':' is found then its a preference line
                //System.out.println("Parse number is.." + parsePref); //debug line
                if(parsePref == -1){
                    //add course
                    //go through courses to find a match
                    String courseNumb = cells[i];
                    this.courses.forEach(pair-> {
                        if((pair.getCourseId().equals(courseNumb))){
                            p.addCourse(pair); //if found add course to professors list
                        }
                    });
                }else{
                    //add preference
                    String preference = cells[i].substring(parsePref+1);
                    p.setPreference(preference);
                }
            }
        }

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

        String cells[] = data.split(delimiter); //split data by delimiter

        //1. Days of the week
        //This should always be the first cell
        t.setDay(cells[0]);

        //2. Class Times
        //Class times should occupy the rest of the line
        for(int i=1; i<cells.length; i++){
            if (cells[i].length() != 0) { //if its not a blank cell
                t.addTime(cells[i]); //add time to class object
            }
        }

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
        String roomNum;

        parse = data.indexOf(delimiter); //find first delimiter
        String room = data.substring(0,parse); //string value of first cell read in
        spaceParse = room.indexOf(" "); //find space which separates building from room number
        building = room.substring(0,spaceParse); //separate building
        roomNum = room.substring(spaceParse+1); //separates room
        String roomCap = data.substring(parse+1); //the rest of the line without room number
        parse = roomCap.indexOf(delimiter);

        if(parse>1){ //Do more cells exist on this line?
            roomCap = roomCap.substring(0,parse); //if so truncate string
        }
        roomCapacity = Integer.parseInt(roomCap); //change capacity value to an integer

        Room r = new Room(building,(building + " " + roomNum),roomCapacity); //make new room object
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
        //ReadInputFile rif = new ReadInputFile("src/main/resources/InputData.csv");
        ReadInputFile rif = new ReadInputFile("src/main/resources/InputData.tsv");

        rif.debugPrint();
    }
}
