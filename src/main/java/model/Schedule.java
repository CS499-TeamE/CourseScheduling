package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Schedule
{
    private int classAmount = 0;
    private int conflictAmount = 0;
    private double fitness = -1;
    private boolean fitnessChange = true;

    private List<PossibleClass> possibleClassList;
    private List<PossibleClass> filteredPossibleClassList;
    private Department dept;
    Random random = new Random();

    /**
     * Constructor for model.Schedule
     * @param dept
     */
    public Schedule(Department dept)
    {
        this.dept = dept;
        possibleClassList = new ArrayList<PossibleClass>(dept.getCoursesList().size());
    }

    /**
     * Method creates a randomly generated schedule
     * schedule observes the soft constraints (model.Room and Time preferences)
     * @return schedule
     */
    public Schedule createSchedule()
    {
            for (Professor professor : dept.getProfessorList())
            {
                for (Course course : professor.getTaughtCourses())
                {
                    PossibleClass n = new PossibleClass(classAmount++, professor, dept);
                    handleCourse(n,course); // handles the course information assignment
                    handleProfessor(n,professor); // handles the professor information
                    handleRoom(n); // handles the room information
                    handleMeetingTime(n); // Handles the meeting time information
                    possibleClassList.add(n);
                }
            }
      return this;
    }


    private void handleCourse(PossibleClass n, Course course)
    {
        n.setCourse(course);
    }

    private void handleProfessor(PossibleClass n, Professor professor)
    {
        n.setProfessor(professor);
    }

    private void handleRoom(PossibleClass n)
    {
        if(n.getCourse().getRoomPreference() == 0)
        {
            n.setRoom(dept.getRoomsList().get(random.nextInt(dept.getRoomsList().size()))); // Get a random room if there is no preference
        }
        else
        {
            int searchTarget = n.getCourse().getRoomPreference();
            for (Room room : dept.getRoomsList())
            {
                if(room.getRoomNumber() == searchTarget);
                {
                    n.setRoom(room);
                }
            }
        }
    }

    private void handleMeetingTime(PossibleClass n)
    {
        handleTimePref(n);
    }

    /**
     * Add a "random" meeting time based on teacher preference
     * @param n
     *
     */
    private void handleTimePref(PossibleClass n)
    {

        switch(n.getProfessor().getPreference())
        {
            case ("All Tues-Thurs classes") :
            {
                n.setMeetingTime(dept.getRandomMeetingTime(1,dept.getMeetingTimes().get(1).getTimes()).getRandomTime()); // Get a random time from T/Th
                break;
            }
            case ("All Mon-Wed classes") :
            {
                n.setMeetingTime(dept.getRandomMeetingTime(0,dept.getMeetingTimes().get(0).getTimes()).getRandomTime()); // Get a random time from M/W
                break;
            }
            case ("Morning classes only") :
            {
                int day = random.nextInt(dept.getMeetingTimes().size());
                List<String> times = dept.getMeetingTimes().get(day).getTimes();
                List<String> filteredTimes = times.subList(0,2);

                n.setMeetingTime(dept.getRandomMeetingTime(day, filteredTimes).getRandomTime());
                break;
            }
            case ("Afternoon classes only") :
            {
                int day = random.nextInt(dept.getMeetingTimes().size());
                List<String> times = dept.getMeetingTimes().get(day).getTimes();
                List<String> filteredTimes = times.subList(3,5);

                n.setMeetingTime(dept.getRandomMeetingTime(day, filteredTimes).getRandomTime());
                break;
            }
            case ("Evening classes only") :
            {
                int day = random.nextInt(dept.getMeetingTimes().size());
                List<String> times = dept.getMeetingTimes().get(day).getTimes();
                List<String> filteredTimes = times.subList(5,7);

                n.setMeetingTime(dept.getRandomMeetingTime(day, filteredTimes).getRandomTime());
                break;
            }
            default:
            {
                int day = random.nextInt(dept.getMeetingTimes().size());
                n.setMeetingTime(dept.getRandomMeetingTime(day,dept.getMeetingTimes().get(day).getTimes()).getRandomTime());
            }
        }
    }

    /**
     * Determine how good a schedule is in order to sort
     * @return double fitnessAmount
     */
    private double calcFitness()
    {
        conflictAmount = 0;
        for (PossibleClass n  : possibleClassList)
        {
            if(n.getRoom().getRoomCapacity() < n.getCourse().getMaxEnrollment()) conflictAmount++;
            for(PossibleClass m : possibleClassList)
            {
                // If two classes share a meeting time and are not the same class, check the room number and instructor to see if there are conflicts
                if(n.getMeetingTime() == m.getMeetingTime() && n.getCourse() != m.getCourse())
                {
                    if(n.getRoom() == m.getRoom()) conflictAmount++;
                    if(n.getProfessor() == m.getProfessor()) conflictAmount++;
                }
            }
        }
        return 1/(double)(conflictAmount + 1); // a schedule with a fitness of 1 has zero conflicts
    }
    /**
     * Calls the calculate method if it has not already been calculated, gets fitness value
     * @return double fitness value
     */
    public double getFitness()
    {
        if(fitnessChange == true)
        {
            fitness = calcFitness();
            fitnessChange = false;
        }
        return fitness;
    }

    /**
     * Gets the list of class combinations that have been made in an example schedule
     * @return
     */
    public List<PossibleClass> getClassList()
    {
        fitnessChange = true;
        return possibleClassList;
    }

    /**
     * Gets the amount of conflicts for this current schedule
     * @return amount of conflicts
     */
    public int getConflictAmount()
    {
        return conflictAmount;
    }

    public void printScheduleInfo(){ ;

     for(PossibleClass combo : possibleClassList)
     {
         System.out.println("Course:");
         System.out.println(combo.getCourse().getCourseId());
         System.out.println("Professor:");
         System.out.println(combo.getProfessor().getName());
         System.out.println("Classroom:");
         System.out.println(combo.getRoom().getRoomNumber());
         System.out.println("Time:");
         System.out.println(combo.getMeetingTime());
     }
    }
}
