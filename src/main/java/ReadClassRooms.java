package CourseScheduling.src.main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;


public class ReadClassRooms {
    //Class Variables
    private ArrayList<String> rooms = new ArrayList<>();

    /**
     * Class constructor. Asks the user for the file name
     */
    public ReadClassRooms(){
        // Scanner for user input
        Scanner user = new Scanner( System.in );
        String  inputFileName;

        // prepare the input file
        System.out.print("Input File Name For Class Rooms: ");
        inputFileName = user.nextLine().trim();

        this.determineDataType(inputFileName);
    }

    /**
     * Overload class constructor. Used when the file name is already known
     * @param inputFileName string of file name
     */
    public ReadClassRooms(String inputFileName){
        this.determineDataType(inputFileName);
    }

    /**
     *
     * @param fileName
     */
    private void determineDataType(String fileName){
        int parse = fileName.indexOf("."); //find index of "." which separates file type
        String sub = fileName.substring(parse+1);
        //System.out.println(sub); //debug line
        if (sub.equals("csv")){
            this.gatherData(fileName, ",");
        }else if(sub.equals("tsv")){
            this.gatherData(fileName, "/t");
        }else{
            System.out.println("Invalid file type");
        }

    }

    private void gatherData(String filename, String delimiter){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            String data;

            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                int parse = data.indexOf(":"); //find index of delimiter which separates string for useful info
                String sub = data.substring(0,parse);
                //System.out.println(sub); //debug line

                //collect time data
                if(sub.equals("Available Classrooms")){
                    data=myReader.nextLine(); //read next line, this should be the first line with class rooms
                    while(!data.equals("")){ //as long as next line is not blank continue
                        //System.out.println(data); //debug line
                        parse = data.indexOf(delimiter); //find first delimiter
                        sub = data;
                        while (parse != -1){ //as long as there is still a delimiter
                            String newTime = sub.substring(0,parse);
                            //System.out.println(newTime); //debug line
                            this.rooms.add(newTime);
                            sub = sub.substring(parse+1);
                            //System.out.println(sub); //debug line
                            parse = sub.indexOf(delimiter); //find next delimiter
                        }
                        //save in last room on the current row
                        this.rooms.add(sub);
                        //System.out.println(sub); //debug line
                        //t.printClassTimes(); //debug line

                        //if there is a next line continue to read
                        if (myReader.hasNextLine()){
                            data=myReader.nextLine(); //read next line;
                        }else{
                            break;
                        }
                    }
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }


    public ArrayList<String> getRooms() {
        return rooms;
    }

    /**
     * Print values stored in class. This function is mostly for debugging purposes.
     */
    public void printClass(){
        System.out.println(this.rooms);
    }


    /**
     * Driver code
     * @param args input read from command line
     */
    public static void main(String[] args) {
        ReadClassRooms r = new ReadClassRooms();
        //ReadClassRooms r = new ReadClassRooms("D:\\Temp\\ClassRooms.csv");
        r.printClass();
    }
}
