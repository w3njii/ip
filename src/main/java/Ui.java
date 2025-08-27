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

    public void printToDoList(ArrayList<Task> toDoList) {
        System.out.println("\tHere are the tasks in your list: ");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println("\t\t" + (i + 1) + ". " + toDoList.get(i).toString());
        }
    }
}