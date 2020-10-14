package model;

import java.util.ArrayList;


public class Population
{
    private ArrayList<Schedule> scheduleList;

    /**
     * Constructor for the Population class
     * @param dept
     * @param size
     */
    public Population(Department dept, int size)
    {
        scheduleList = new ArrayList<Schedule>(size);

        // Initialize the data for the whole population of schedules
        for(int i=0; i<size; i++)
        {
            scheduleList.add(new Schedule(dept).createSchedule());
        }
    }

    /**
     *  Get the best of schedules by sorting with respect to fitness
     * @return Population of schedules
     */
    public Population sortScheduleList()
    {
        scheduleList.sort((scheduleN, scheduleM) -> {
            int compareValue =0;
            if(scheduleN.getFitness() > scheduleM.getFitness()) compareValue = -1;
            else if(scheduleN.getFitness() < scheduleM.getFitness()) compareValue = 1;
            return compareValue;
        });
        return this;
    }

    /**
     * Gets the list of schedules
     * @return ArrayList<Schedule> scheduleList
     */
    public ArrayList<Schedule> getScheduleList()
    {
        return scheduleList;
    }
}
