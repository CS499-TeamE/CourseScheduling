package model;

import view.StartGUI;

import java.util.ArrayList;

public class Application {

    public static final int INITIAL_POP_SIZE = 45;
    public static final double RATE_OF_MUTATION = 0.5;
    public static final double RATE_OF_CROSSOVER = 0.5;
    public static final int PARENT_SELECTION_SIZE = 15;
    public static final int ELITE_SCHEDULE_NUM = 1;
    public static final int MAX_EVOLUTIONS = 1000;

    public static void main(String[] args)
    {
        StartGUI.main(args);
    }


}
