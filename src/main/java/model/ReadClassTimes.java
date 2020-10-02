package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * ReadClassTimes class. This class takes the class time data input and converts into an object.
 *
 * @author Ed Brown
 * @version 0.1
 * @since 9/26/2020
 * @deprecated All input readers have been combined into ReadInputFile class
 */
@Deprecated
public class ReadClassTimes {
    //Class Variables
    private ArrayList<ClassTimes> times = new ArrayList<>();

    /**
     * Class constructor. Asks the user for the file name
     */
    public ReadClassTimes(){
        // Scanner for user input
        Scanner user = new Scanner( System.in );
        String  inputFileName;

        // prepare the input file
        System.out.print("Input File Name: ");
        inputFileName = user.nextLine().trim();

        this.determineDataType(inputFileName);
    }

    /**
     * Overload class constructor. Used when the file name is already known
     * @param inputFileName string of file name
     */
    public ReadClassTimes(String inputFileName){
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

    /**
     * Takes a file name and collects input data
     * This function assumes that input data is in correct format
     * @param filename string of filename containing Department Class data
     */
    private void gatherData(String filename,String delimiter){
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
                if(sub.equals("Class Time Slots")){
                    data=myReader.nextLine(); //read next line, this should be the first line with class times
                    while(!data.equals("")){ //as long as next line is not blank continue
                        ClassTimes t = new ClassTimes(); //create new class times object
                        parse = data.indexOf(delimiter); //find first delimiter to determine days
                        t.setDay(data.substring(0,parse-1)); //set class day
                        //System.out.println(t.getDay()); //debug line
                        //find next delimiter
                        sub = data.substring(parse+1);
                        parse = sub.indexOf(delimiter); //find next delimiter
                        while (parse != -1){ //as long as there is still a delimiter
                            String newTime = sub.substring(0,parse);
                            //System.out.println(newTime); //debug line
                            t.addTime(newTime);

                            sub = sub.substring(parse+1);
                            //System.out.println(sub); //debug line
                            parse = sub.indexOf(delimiter); //find next delimiter
                        }
                        //save in last class time
                        t.addTime(sub);
                        //System.out.println(sub); //debug line
                        //t.printClassTimes(); //debug line
                        this.times.add(t); //add to collection list
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

    public ArrayList<ClassTimes> getClassTimes() {
        return times;
    }

    /**
     * Print values stored in class. This function is mostly for debugging purposes.
     */
    public void printClass(){
        this.times.forEach(p-> p.printClassTimes());
    }

    /**
     * Driver code
     * @param args input read from command line
     */
    public static void main(String[] args) {
        //ReadClassTimes t = new ReadClassTimes();
        ReadClassTimes t = new ReadClassTimes("D:\\Temp\\ClassTimes.csv");
        //ReadClassTimes t = new ReadClassTimes("D:\\Temp\\ClassTimes.tsv");
        t.printClass();
    }

}
