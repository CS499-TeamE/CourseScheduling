package model;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * Mutations mean creating a schedule similar to the one we started with and randomly picking whether or not to bring
 * back in that "Class" into the already changed schedule or to leave the changed schedule
 *
 * Crossovers are similar but instead of grabbing from a new list of schedules they take two of the schedules with the
 * highest fitness and swap "class" variables between those based on chance
 * chance
 */


public class Algorithm //crossover, mutation
{
    private Department dept;
    Random random =new Random();

    /**
     * Constructor for the Genetic model.Algorithm
     * @param dept
     */
    public Algorithm(Department dept)
    {
        this.dept = dept;
    }

    /**
     * Very low chance to happen but basically mixes everything up
     * Basically calls all the other methods since mutatePopulation calls mutateSchedule
     * and crossoverPopulation calls crossoverSchedule
     *
     * @param population
     * @return
     */
    public Population evolvePopulation(Population population)
    {
        return mutatePopulation(crossoverPopulation(population));
    }

    /**
     *  Determines random choices for the two parents that will be used in the crossover
     * @param population
     * @return selectedParents
     */
    private Population parentsSelection(Population population)
    {
        Population selectedParents = new Population(dept, Application.PARENT_SELECTION_SIZE);
        for(int i = 0; i < Application.PARENT_SELECTION_SIZE; i++)
        {
            selectedParents.getScheduleList().set(i, population.getScheduleList().get(random.nextInt(population.getScheduleList().size())));
        }
        return selectedParents;
    }

    /**
     * Performs the actual crossover between two schedules, swaps two possible "classes"
     * if the Math.random is greater than 0.5
     * @param parent1
     * @param parent2
     * @return resultingSchedule
     */
    private Schedule crossoverSchedule(Schedule parent1, Schedule parent2)
    {
        Schedule resultingSchedule = new Schedule(dept).createSchedule();
        for(int i = 0; i  < resultingSchedule.getClassList().size(); i++)
        {
            if(Math.random() > 0.5)  resultingSchedule.getClassList().set(i, parent1.getClassList().get(i));
            else resultingSchedule.getClassList().set(i, parent2.getClassList().get(i));
        }
        return resultingSchedule;
    }

    /**
     *  For each schedule, other than elite schedules, if the Rate of crossover is greater than Math.random() then call
     *  crossover schedule to perform a crossover
     * @param population
     * @return resultingPopulation
     */
    private Population crossoverPopulation(Population population)
    {
        Population resultingPopulation = new Population(dept, population.getScheduleList().size());
        // First get the best schedules as elite schedules
        for(int i =0; i < Application.ELITE_SCHEDULE_NUM; i++)
        {
            resultingPopulation.getScheduleList().set(i,population.getScheduleList().get(i));
        }
        // Fill the rest of the population with crossed over schedules or the current ones
        IntStream.range(Application.ELITE_SCHEDULE_NUM, population.getScheduleList().size()).forEach(n ->
        {
            // If conditions are met select parents for crossover crossover
            if(Application.RATE_OF_CROSSOVER > Math.random())
            {
                Schedule parent1 = parentsSelection(population).sortScheduleList().getScheduleList().get(0);
                Schedule parent2 = parentsSelection(population).sortScheduleList().getScheduleList().get(0);
                resultingPopulation.getScheduleList().set(n, crossoverSchedule(parent1,parent2));
            }
            else // do nothing if rate of crossover is less and leave the current population
            {
                resultingPopulation.getScheduleList().set(n,population.getScheduleList().get(n));
            }
        });
        return resultingPopulation;
    }

    /**
     * For each schedule, other than elite schedules, if the Rate of mutation is greater than Math.random()
     * then call mutateSchedule to perform the mutation
     * @param population
     * @return mutatedPopulation
     */
    private Population mutatePopulation(Population population)
    {
        Population mutatedPopulation = new Population(dept, population.getScheduleList().size());
        ArrayList<Schedule> mutatedScheduleList = mutatedPopulation.getScheduleList();
        for(int i = 0; i < Application.ELITE_SCHEDULE_NUM; i++)
        {
            mutatedScheduleList.set(i, population.getScheduleList().get(i));
        }
        IntStream.range(Application.ELITE_SCHEDULE_NUM, population.getScheduleList().size()).forEach(n ->
        {
            mutatedScheduleList.set(n, mutateSchedule(population.getScheduleList().get(n)));
        });

        return mutatedPopulation;
    }

    /**
     * For each class in the schedule that is passed in by mutatePopulation, determine whether to mutate or not
     * and then perfom mutation
     * @param mutatedSchedule
     * @return mutatedSchedule
     */
    private Schedule mutateSchedule(Schedule mutatedSchedule)
    {
        Schedule classPool = new Schedule(dept).createSchedule();
        for( int i =0; i < mutatedSchedule.getClassList().size(); i++)
        {
            if(Application.RATE_OF_MUTATION > Math.random())
                mutatedSchedule.getClassList().set(i, classPool.getClassList().get(i));
        }
        return mutatedSchedule;
    }

}