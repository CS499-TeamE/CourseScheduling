package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmTest {
    Department dept;
    Population pop;

    @BeforeEach
    void setUp()
    {
        String inputFileName = "src/main/resources/InputData.csv";
        ReadInputFile parser = new ReadInputFile(inputFileName);
        dept = parser.getDepartment();
        pop = new Population(dept, 4);
    }

    @Test
    void evolvePopulation()
    {
        Algorithm algorithm = new Algorithm(dept);
        Population newPop = algorithm.evolvePopulation(pop);
        Boolean result = pop.getScheduleList().equals(newPop.getScheduleList());
        assert result == false;
    }
}