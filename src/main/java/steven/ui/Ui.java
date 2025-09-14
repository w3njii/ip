package steven.ui;

/**
 * Handles the user interface and interactions for the Steven chatbot.
 * Provides methods to display greetings, separators, and exit messages.
 */
public class Ui {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String GREETING = "Hello There! I'm Steven\nHow may I help you?";
    private static final String GOODBYE = "\tBye.";

    /**
     * Prints a horizontal line separator.
     */
    public void printHorizontalLine() {
        System.out.println(HORIZONTAL_LINE);
    }

    /**
     * Prints the greeting message.
     */
    public void printGreeting() {
        System.out.println(GREETING);
    }

    /**
     * Prints the farewell message.
     */
    public void printGoodbye() {
        System.out.println(GOODBYE);
    }

    public String getGreeting() {
        return GREETING;
    }
}