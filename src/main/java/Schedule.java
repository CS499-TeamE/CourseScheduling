
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Schedule
{
    private int classAmount = 0;
    private int conflictAmount = 0;
    private double fitness = -1;
    private boolean fitnessChange = true;

    private ArrayList<PossibleClass> possibleClassList;
    private List<PossibleClass> filteredPossibleClassList;
    private Data info;

    /**
     * Constructor for Schedule
     * @param info
     */
    public Schedule(Data info)
    {
        this.info = info;
        possibleClassList = new ArrayList<PossibleClass>(info.getNumberOfClasses());
    }

    /**
     * Method creates a randomly generated schedule
     * schedule observes the soft constraints (Room and Time preferences)
     * @return schedule
     */
    public Schedule createSchedule()
    {
        for (Department dept : info.getDeptList())
        {
            for (Professor professor : dept.getProfessorList())
            {
                for (Course course : professor.getTaughtCourses())
                {
                    PossibleClass n = new PossibleClass(classAmount++, professor, dept);
                    n.setCourse(course); // assigns a course
                    if (n.getCourse().getRoomPreference() != 0) n.setRoom(n.getCourse().getRoomPreference()); // set room to the course preference
                    else n.setRoom(info.getRoomList().get((int) Math.random() * info.getRoomList().size())); // randomly assigns a room
                    if (n.getProfessor().getPreference() != "") handleTimePref(n, n.getProfessor().getPreference()); // set time to professor's preference
                    else n.setMeetingTime(info.getMeetingTimeList().get((int) Math.random() * info.getMeetingTimeList().size())); // randomly assigns a meeting time
                    possibleClassList.add(n);
                }
            }
        }
        return this;
    }

    /**
     * Add a "random" meeting time based on teacher preference
     * @param n
     * @param preference
     */
    private void handleTimePref(PossibleClass n, String preference)
    {
        switch(preference) //TODO Make this switch actually assign the correct times
        {
            case ("All Tues-Thurs classes") :
            {
                n.setMeetingTime("T/H: times");
                break;
            }
            case ("All Mon-Wed classes") :
            {
                n.setMeetingTime("M/W: times");
                break;
            }
            case ("Morning classes only") :
            {
                n.setMeetingTime("Morning classes only");
                break;
            }
            case ("Afternoon classes only") :
            {
                n.setMeetingTime("Afternoon classes only");
                break;
            }
            case ("Evening classes only") :
            {
                n.setMeetingTime("Evening classes only");
                break;
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
            //if(n.getRoom().) will be if room capacity is less than course capacity //TODO once inputs are there for the course/room capacity
            for(PossibleClass m : possibleClassList.stream().filter(m -> possibleClassList.indexOf(m) >= possibleClassList.indexOf(n)).collect(Collectors.toList()))
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
    public ArrayList<PossibleClass> getClassList()
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

    /**
     *  Gets the available information from Data class
     * @return all the available data
     */
    public Data getInfo()
    {
        return info;
    }
}
