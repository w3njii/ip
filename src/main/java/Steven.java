import java.util.Scanner;
import java.util.ArrayList;

public class Steven {

    public static void main(String[] args) {
        final String HORIZONTAL_LINE = "____________________________________________________________";
        final String GREETING = "Hello There! I'm Steven\nHow may I help you?";
        final String GOODBYE = "Bye.";

        ArrayList<Task> toDoList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println(GREETING);
        System.out.println(HORIZONTAL_LINE);

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                System.out.println("\t Here are the tasks in your list: ");
                for (int i = 0; i < toDoList.size(); i++) {
                    System.out.println("\t\t" + (i + 1) + ". " + toDoList.get(i).toString());
                }
            } else if (input.startsWith("todo")) {
                String description = input.substring(5);
                Task currentTask = new ToDo(description);
                toDoList.add(currentTask);
                System.out.println("\tOK, I've added this task: " + currentTask);
                System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
            } else if (input.startsWith("deadline")) {
                String descriptionAndDeadline = input.substring(9);
                int byIndex = descriptionAndDeadline.indexOf(" /by ");
                String description = descriptionAndDeadline.substring(0, byIndex);
                String deadline = descriptionAndDeadline.substring(byIndex + 5);
                Task currentTask = new Deadline(description, deadline);
                toDoList.add(currentTask);
                System.out.println("\tOK, I've added this task: " + currentTask);
                System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
            } else if (input.startsWith("event")) {
                String descriptionAndTime = input.substring(6);
                int fromIndex = descriptionAndTime.indexOf(" /from ");
                int toIndex = descriptionAndTime.indexOf(" /to ");
                String description = descriptionAndTime.substring(0, fromIndex);
                String from = descriptionAndTime.substring(fromIndex + 7, toIndex);
                String to = descriptionAndTime.substring(toIndex + 5);
                Task currentTask = new Event(description, from, to);
                toDoList.add(currentTask);
                System.out.println("\tOK, I've added this task: " + currentTask);
                System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
            } else if (input.startsWith("mark ")) {
                int number;
                try {
                    number = Integer.parseInt(input.substring(5));
                    System.out.println("\tOK, I will mark this task as done");
                    toDoList.get(number - 1).markAsDone();
                    System.out.println("\t" + toDoList.get(number - 1).toString());
                } catch (NumberFormatException e) {
                    // Intentionally ignored because it is possible that the substring after "mark" is not a number
                }
            } else if (input.startsWith("unmark ")) {
                int number;
                try {
                    number = Integer.parseInt(input.substring(7));
                    System.out.println("\tOK, I will mark this task as not done");
                    toDoList.get(number - 1).markAsNotDone();
                    System.out.println("\t" + toDoList.get(number - 1).toString());
                } catch (NumberFormatException e) {
                    // Intentionally ignored because it is possible that the substring after "unmark" is not a number
                }
            } else {
                Task currentTask = new Task(input);
                toDoList.add(currentTask);
                System.out.println("\tOK, I've added this task: " +  currentTask);
                System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
            }
        }

        scanner.close();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(GOODBYE);
    }
}
