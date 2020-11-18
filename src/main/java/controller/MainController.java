package controller;

import javafx.concurrent.Task;
import model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainController
{

    private static List<Department> data = new ArrayList<>();
    private List<Population> populationList = new ArrayList<>();
    private List<Schedule> scheduleList = new ArrayList<>();
    private List<String> files = new ArrayList<>();
    private List<Thread> genList = new ArrayList<>();
    private static MainController instance;
    private int evolutions = 0;
    boolean perfect = false;
    private int run = 1;


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

    /**
     *  Takes in the files from the user and passes that to the parser to obtain the data
     *  Data is then used to create a new population of sudo random schedules
     * @param files
     */
    public void initializeData(List<String> files)
    {
        data.clear();
        for(String file: files)
        {
            if(!this.files.contains(file)) {
                this.files.add(file);
            }
            ReadInputFile parser = new ReadInputFile(file);
            data.add(parser.getDepartment());
        }

    }
    public void initializePopulation(List<Department> departments) {
        ExecutorService es = Executors.newCachedThreadPool();

        for(Department dept : data)
        {
            Population population = new Population(dept, Application.INITIAL_POP_SIZE);
            GeneratorThread gen = new GeneratorThread(dept, population, data.indexOf(dept));
            Thread thread = new Thread(gen);
            genList.add(thread);
        }
        for(Thread thread : genList)
            es.execute(thread);
        es.shutdown();

        try
        {
            boolean finished = es.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        Collections.sort(scheduleList);
        genList.clear();
    }


    private void printSchedule(Schedule schedule)
    {
        schedule.printScheduleInfo();
    }

    public static List<Department> getData()
    {
        return data;
    }

    public static void setData(List<Department> data)
    {
        MainController.data = data;
    }

    public void appendScheduleList(Schedule schedule)
    {
        this.scheduleList.add(schedule);
    }
    public void addPopulationList(Population p)
    {
        populationList.add(p);
    }
    public List<Schedule> getScheduleList() {
        return scheduleList;
    }

    public List<String> getFiles() {
        return files;
    }
}