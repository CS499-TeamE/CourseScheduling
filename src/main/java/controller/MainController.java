package controller;

import com.sun.tools.javac.Main;
import model.Department;
import model.ReadInputFile;
import model.Schedule;

import java.util.ArrayList;

public class MainController
{

    private static ArrayList<Department> data = new ArrayList<>();
    private static MainController instance;

    /**
     * create a heap instance if there is not one already, otherwise return heap
     * @return MinHeap
     */
    public static MainController getInstance()
    {
        if(instance == null)
        {
            instance = new MainController();
        }
        return instance;
    }

    public void initializeData(ArrayList<String> files)
    {
        for(String file: files)
        {
            ReadInputFile parser = new ReadInputFile(file);
            data.add(parser.getDepartment());
        }

        for(Department dept : data)
        {
            Schedule schedule = new Schedule(dept);
            schedule.createSchedule();
        }

    }


    public static ArrayList<Department> getData()
    {
        return data;
    }

    public static void setData(ArrayList<Department> data)
    {
        MainController.data = data;
    }
}
