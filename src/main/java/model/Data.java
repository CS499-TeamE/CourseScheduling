package model;

import java.util.ArrayList;

@Deprecated
/**
 * @deprecated use department instead
 */
public class Data
{

    private ArrayList<Room> rooms; //collection of class room
    private ArrayList<ClassTimes> times;
    private ArrayList<Course> courses;
    private ArrayList<String> classroomPreferences;
    private ArrayList<Professor> professors;
    private ArrayList<Department> departments;
    private String location;


    public Data(String filename)
    {

    }
}
