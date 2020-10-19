package model;

import java.util.ArrayList;
import java.util.List;

public class PossibleClass
{

    private Course course;
    private Room room;
    private Professor professor;
    private String timeslot ="";

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
        String info = " Course: " + this.getCourse().getCourseId()
                + " Max Attendance: " + this.getCourse().getMaxEnrollment()
                + " Room: " + this.getRoom().getRoomNumber()
                + " Room Capacity: " + this.getRoom().getRoomCapacity()
                + " Professor: " + this.getProfessor()
                + " Meeting Time: " + this.getJustTime();

        return info;
    }
}
