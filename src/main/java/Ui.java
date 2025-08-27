import java.util.ArrayList;

public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String GREETING = "Hello There! I'm Steven\nHow may I help you?";
    private static final String GOODBYE = "\tBye.";

    public void printHorizontalLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    public void printGreeting() {
        System.out.println(GREETING);
    }

    public void printGoodbye() {
        System.out.println(GOODBYE);
    }
}