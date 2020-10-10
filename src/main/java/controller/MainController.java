package controller;

import com.sun.tools.javac.Main;
import model.*;

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
        //System.out.println(data.get(0).getCoursesList());
        for(Department dept : data)
        {
            Population population = new Population(dept, Application.INITIAL_POP_SIZE);

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
