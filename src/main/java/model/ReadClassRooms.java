package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ReadRooms class. This class takes the classroom data input and converts into an object.
 *
 * @author Ed Brown
 * @version 0.1
 * @since 9/26/2020
 * @deprecated All input readers have been combined into ReadInputFile class
 */

@Deprecated
public class ReadClassRooms {
    //Class Variables
    private ArrayList<Room> rooms = new ArrayList<>(); //collection of class rooms

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

        this.determineFileType(inputFileName);
    }

    /**
     * Overload class constructor. Used when the file name is already known
     * @param inputFileName string of file name
     */
    public ReadClassRooms(String inputFileName){
        this.determineFileType(inputFileName);
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
            this.gatherData(fileName, ",");
        }else if(sub.equals("tsv")){
            this.gatherData(fileName, "\t");
        }else{
            System.out.println("Invalid file type");
        }

    }

    /**
     *
     * @param filename
     * @param delimiter
     */
    private void gatherData(String filename, String delimiter){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);

            String data;

            while (myReader.hasNextLine()) {
                data = myReader.nextLine();
                int parse = data.indexOf(delimiter); //find index of delimiter which separates string for useful info
                String sub = data.substring(0,parse);
                //System.out.println(sub); //debug line

                //collect time data
                if(sub.equals("Classroom")){
                    data=myReader.nextLine(); //read next line, this should be the first line with a classroom
                    while(!data.equals("")){ //as long as next line is not blank continue
                        //System.out.println(data); //debug line
                        parse = data.indexOf(delimiter); //find first delimiter
                        String room = data.substring(0,parse); //string value of room number
                        int spaceParse = room.indexOf(" ");
                        String building = room.substring(0,spaceParse);
                        String roomNumber = room.substring(spaceParse+1);
                        int roomNum = Integer.parseInt(roomNumber);
                        String roomCap = data.substring(parse+1); //the rest of the line without room number
                        int roomCapacity = Integer.parseInt(roomCap);
                        Room r = new Room(building,roomNum,roomCapacity);
                        this.rooms.add(r);

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


    public ArrayList<Room> getRooms() {
        return rooms;
    }

    /**
     * Print values stored in class. This function is mostly for debugging purposes.
     */
    public void printClass(){
        this.rooms.forEach(p-> p.printRoom());
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
