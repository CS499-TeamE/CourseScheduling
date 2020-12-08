package model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class PossibleClass
{

    private Course course;
    private Room room;
    private Professor professor;
    private String timeslot ="";

    public boolean isHasConflict() {
        return hasConflict;
    }

    public void setHasConflict(boolean hasConflict) {
        this.hasConflict = hasConflict;
    }

    private boolean hasConflict;

    public PossibleClass(int i, Professor professor, Department dept)
    {

    }
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getMeetingTime() {
        return timeslot;
    }

    public void setMeetingTime(String timeslot) {
        this.timeslot = timeslot;
    }

    public Professor getProfessor()
    {
        return professor;
    }

    public void setProfessor(Professor professor)
    {
        this.professor = professor;
    }

    public String getJustTime()
    {
        String[] justTime = timeslot.split(" ");
        String time = justTime[1] + justTime[2] + justTime[3];
        return time;
    }
    public String getClassInfo()
    {
        String beginning = this.getCourse().getCourseId()
                + "\t|\t\t\t\t" + this.getCourse().getMaxEnrollment()
                + "\t|\t " + this.getRoom().getRoomNumber()
                + "\t\t|\t\t\t\t" + this.getRoom().getRoomCapacity()
                + "\t|\t\t" + this.getMeetingTime()
                + "\t\t|\t" + this.getProfessor().getName();
        //String middle =
        //middle = StringUtils.rightPad(middle, 20, " ");
        //String end =

        return beginning;
    }

    public String getCSVInfo()
    {
        String beginning = this.getCourse().getCourseId()
                + "   | " + this.getCourse().getMaxEnrollment()
                + "                                |  " + this.getRoom().getRoomNumber()
                + "         |  " + this.getRoom().getRoomCapacity()
                + "                             |   ";
        String middle = this.getJustTime();
        middle = StringUtils.rightPad(middle, 25, " ");
        String end = "|   " + this.getProfessor().getName();

        return beginning.concat(middle.concat(end));
    }
    public static class CourseCompare implements Comparator<PossibleClass>
    {
        public int compare(PossibleClass m1, PossibleClass m2)
        {
            return m1.getCourse().getCourseId().compareTo(m2.getCourse().getCourseId());
        }
    }
    public static class RoomCompare implements Comparator<PossibleClass>
    {
        public int compare(PossibleClass m1, PossibleClass m2)
        {
            return m1.getRoom().getRoomNumber().compareTo(m2.getRoom().getRoomNumber());
        }
    }
    public static class ProfessorCompare implements Comparator<PossibleClass>
    {
        public int compare(PossibleClass m1, PossibleClass m2)
        {
            return m1.getProfessor().getName().compareTo(m2.getProfessor().getName());
        }
    }
    public static class MeetingTimeCompare implements Comparator<PossibleClass>
    {
        public int compare(PossibleClass m1, PossibleClass m2)
        {
            return m1.getMeetingTime().compareTo(m2.getMeetingTime());
        }
    }
    public static class EnrollmentCompare implements Comparator<PossibleClass>
    {
        public int compare(PossibleClass m1, PossibleClass m2)
        {
            return ((Integer) m1.getCourse().getMaxEnrollment()).compareTo((Integer) m2.getCourse().getMaxEnrollment());
        }
    }
    public static class CapacityCompare implements Comparator<PossibleClass>
    {
        public int compare(PossibleClass m1, PossibleClass m2)
        {
            return ((Integer) m1.getRoom().getRoomCapacity()).compareTo((Integer) m2.getRoom().getRoomCapacity());
        }
    }

}
