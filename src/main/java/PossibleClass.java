package CourseScheduling.src.main.java;

public class PossibleClass
{

    private Course course;
    private int room;
    private Professor professor;
    private String timeslot;

    public PossibleClass(int i, Professor professor, Department dept)
    {

    }
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
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


}
