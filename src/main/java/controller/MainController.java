package controller;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class MainController
{

    private static List<Department> data = new ArrayList<>();
    private List<Population> populationList = new ArrayList<>();
    private static MainController instance;
    private int evolutions = 0;
    boolean perfect = false;

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
        for(String file: files)
        {
            ReadInputFile parser = new ReadInputFile(file);
            data.add(parser.getDepartment());
        }

        for(Department dept : data)
        {
            Population population = new Population(dept, Application.INITIAL_POP_SIZE);
            populationList.add(population);
        }
        startAlgorithm();
    }

    public void startAlgorithm()
    {
        for(int i = 0; i< data.size(); i++)
        {
            if(i < populationList.size())
            {
                Algorithm alg = new Algorithm(data.get(i));
                simulateEvolution(alg, i);
            }
        }
    }

    synchronized private void simulateEvolution(Algorithm alg, int i)
    {
        while(populationList.get(i).getScheduleList().get(0).getFitness() != 1 && evolutions <= Application.MAX_EVOLUTIONS)
        {
            populationList.set(i,alg.evolvePopulation(populationList.get(i).sortScheduleList()));
            evolutions++;
            System.out.println("evolution: " + evolutions + " with fitness high of "
                    + populationList.get(i).getScheduleList().get(0).getFitness());

        }
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
}
