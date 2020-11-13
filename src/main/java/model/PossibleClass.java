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
                + "\t|\t" + this.getCourse().getMaxEnrollment()
                + "\t\t\t\t|\t" + this.getRoom().getRoomNumber()
                + "\t\t|\t" + this.getRoom().getRoomCapacity()
                + "\t\t\t\t|\t";
        String middle = this.getProfessor().getName();
        middle = StringUtils.rightPad(middle, 20, " ");
        String end = "\t|\t" + this.getJustTime();

        return beginning.concat(middle.concat(end));
    }


}
