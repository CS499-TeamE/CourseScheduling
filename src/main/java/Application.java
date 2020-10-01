package CourseScheduling.src.main.java;

public class Application {

    public static final int INITIAL_POP_SIZE = 45;
    public static final double RATE_OF_MUTATION = 0.1;
    public static final double RATE_OF_CROSSOVER = 0.9;
    public static final int PARENT_SELECTION_SIZE = 3;
    public static final int ELITE_SCHEDULE_NUM = 3;
    private Data info;

    public static void main(String[] args) {
        ChooseFile.main(args);
    }


}
