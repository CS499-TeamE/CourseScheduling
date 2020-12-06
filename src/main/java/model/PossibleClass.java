package model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
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

}
