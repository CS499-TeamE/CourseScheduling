package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ReadCourses class. This class takes the department class data input and converts into an object.
 *
 * @author Ed Brown
 * @version 0.1
 * @since 8/29/2020
 * @deprecated All input readers have been combined into ReadInputFile class
 */
@Deprecated
public class ReadCourses {
    //PossibleClass Variables
    private String department;
    private String location;
    private ArrayList<String> courses = new ArrayList<String>();
    private ArrayList<String> classroomPreferences = new ArrayList<String>();
    private ArrayList<Faculty> professors = new ArrayList<>();

    /**
     * PossibleClass constructor. Asks the user for the file name
     */
    public ReadCourses(){
        // Scanner for user input
        Scanner user = new Scanner( System.in );
        String  inputFileName;

        // prepare the input file
        System.out.print("Input File Name: ");
        inputFileName = user.nextLine().trim();

        this.gatherData(inputFileName);
    }

    /**
     * Overload class constructor. Used when the file name is already known
     * @param inputFileName string of file name
     */
    public ReadCourses(String inputFileName){
        this.gatherData(inputFileName);
    }

    /**
     * Parse Course Data takes input string and extracts course numbers.
     * This assumes that commas are not used as values
     * @param courseInfo line from the input stream and parses around commas to get course data
     */
    private void parseCourseData(String courseInfo){
        try (Scanner rowScanner = new Scanner(courseInfo)) {
           // System.out.println(courseInfo);
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                this.courses.add(rowScanner.next());
            }
        }
    }

    /**
     * Takes input from string and extracts faculty information
     * @param facData line from input stream that gets parsed for faculty data
     */
    private void parseFacultyData(String facData){
        Faculty f = new Faculty(); //create new faculty object

        //parse into three strings (1. Name   2. Courses    3. Preferences)
        int parseLeft = facData.indexOf(","); //find index of first ","
        int parseRight = facData.indexOf("-"); //find index of first "-"
        int parsePref = facData.indexOf(":"); //find index of first ":"

        // 1. Faculty name
        String name = facData.substring(0, parseLeft);
        f.setName(name);

        // 2. Courses
        String courses = facData.substring(parseLeft+1, parseRight);
        try (Scanner rowScanner = new Scanner(courses)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                f.addCourse(rowScanner.next());
            }
        }

        // 3. Preferences
        String preference = facData.substring(parsePref+1);
        f.setPreference(preference);
        //System.out.println(name + " :: " + courses + " :: " + preference);

        professors.add(f); //add professor to list
    }

    /**
     * Takes a file name and collects input data
     * This function assumes that input data is in correct format
     * @param filename string of filename containing Department PossibleClass data
     */
    private void gatherData(String filename){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            //gather department info
            String data = myReader.nextLine();
            int parse = data.indexOf(":"); //find index of ":" which separates string for useful info
            String sub = data.substring(parse+2);
            this.department = sub;
            //gather location info
            data = myReader.nextLine();
            parse = data.indexOf(":"); //find index of ":" which separates string for useful info
            sub = data.substring(parse+2);
            this.location = sub;

            while (myReader.hasNextLine()) {
                data = myReader.nextLine();

                //collect course data
                if(data.equals("Courses Offered")){
                    data=myReader.nextLine(); //read line after Courses Offered, which should be blank
                    data=myReader.nextLine(); //read next line, this should be the first line with class numbers
                    while (!data.equals("")){
                        this.parseCourseData(data);
                        data=myReader.nextLine();
                    }
                }

                //collect classroom preference data
                if(data.equals("Classroom Preferences:")){
                    data=myReader.nextLine(); //read next line, this should be the start of classroom preferences
                    while (!data.equals("")){
                        this.classroomPreferences.add(data);
                        data=myReader.nextLine();
                    }
                }

                //collect professor data
                if(data.equals("Faculty Assignments:")){
                    data = myReader.nextLine();
                    //System.out.println("Read for fac: " + data);
                    while (!data.equals("") && myReader.hasNextLine()){
                        this.parseFacultyData(data);
                        data=myReader.nextLine();
                    }
                }
                //System.out.println(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public String getDepartment() {
        return department;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<String> getCourses() {
        return courses;
    }

    public ArrayList<String> getClassroomPreferences() {
        return classroomPreferences;
    }

    public ArrayList<Faculty> getProfessors() {
        return professors;
    }

    /**
     * Print values stored in class. This function is mostly for debugging purposes.
     */
    public void printClass(){
        System.out.print("Department: ");
        System.out.println(this.department);
        System.out.print("Location: ");
        System.out.println(this.location);
        System.out.println("Courses Offered: ");
        System.out.println(this.courses);
        System.out.println("Classroom Preferences: ");
        System.out.println("\t" + this.classroomPreferences);
        this.professors.forEach(p-> p.display());
    }

    /**
     * Driver code
     * @param args input read from command line
     */
    public static void main(String[] args) {
        ReadCourses s = new ReadCourses("D:\\Temp\\Dept1ClassData.csv");
        s.printClass();
    }
}
