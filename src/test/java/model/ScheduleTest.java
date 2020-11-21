package model;

import javafx.scene.DepthTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {
    private Schedule schedule;
    private Department dept;

    @BeforeEach
    void setUp()
    {
        String inputFileName = "src/main/resources/InputData.csv";
        ReadInputFile parser = new ReadInputFile(inputFileName);
        dept = parser.getDepartment();
        schedule = new Schedule(dept);
    }

    @Test
    void getConflicts()
    {
        List<String> conflicts = schedule.getConflicts();
        assert (conflicts.isEmpty());
    }

    @Test
    void getId()
    {
        Integer id = schedule.getId();
        assert id == null;
    }

    @Test
    void setId()
    {
        schedule.setId(1);
        Integer id = schedule.getId();
        assert id.equals(1);
    }

    @Test
    void createSchedule()
    {
        schedule.createSchedule();
        assert !schedule.getClassList().isEmpty();
    }

    @Test
    void getFitness()
    {
        double fitness = schedule.getFitness();
        assert fitness == 1.0;
    }

    @Test
    void isPerfect()
    {
        Boolean perfection = schedule.isPerfect();
        assert perfection == false;
    }

    @Test
    void getClassList()
    {
        assert schedule.getClassList().isEmpty();
    }

    @Test
    void getConflictAmount()
    {
        int conflicts = schedule.getConflictAmount();
        assert (conflicts <= 1);
    }

    @Test
    void compareTo()
    {
        Schedule schedule2 = new Schedule(dept);
        schedule2.setId(1);
        schedule.setId(0);
        int result = schedule.compareTo(schedule2);
        assert(result == -1);
    }
}