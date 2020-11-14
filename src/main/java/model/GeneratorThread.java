package model;

import controller.FinalizeInputController;
import controller.MainController;

public class GeneratorThread implements Runnable
{
    private Department datum;
    private Population population;
    private Schedule schedule;
    int run = 1;
    int evolutions = 0;
    private int id;



    public GeneratorThread(Department data, Population population, int i)
    {
        this.datum = data;
        this.id = i;
        this.population = population;
    }

    public void run()
    {
        startAlgorithm();
    }

    private String getIndent()
    {
        String indent = "";
        for(int i = 0; i <= id; i++)
            indent += "\t";
        return indent;
    }

    public void startAlgorithm()
    {

            run = 1;
            while (!population.getScheduleList().get(0).isPerfect() && run <= 4) {
                population = new Population(datum, Application.INITIAL_POP_SIZE);
                evolutions = 0;
                Algorithm alg = new Algorithm(datum);
                simulateEvolution(alg);
                run++;
            }
        schedule.setId(id);
        MainController.getInstance().appendScheduleList(schedule);
    }

    private void simulateEvolution(Algorithm alg)
    {

        while (!population.getScheduleList().get(0).isPerfect() && evolutions <= Application.MAX_EVOLUTIONS) {
            population = alg.evolvePopulation(population.sortScheduleList());
            evolutions++;
            System.out.println(getIndent() + "evolution: " + evolutions + " with fitness high of "
                    + population.getScheduleList().get(0).getFitness());

        }
        if(population.getScheduleList().get(0).isPerfect())
        {
            schedule = population.getScheduleList().get(0);
        }
        else if(run == 4)
        {
            schedule = population.getScheduleList().get(0);
        }
    }
}
