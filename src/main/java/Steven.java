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
                continue;
            } else if (input.startsWith("mark ")) {
                int number;
                try {
                    number = Integer.parseInt(input.substring(5));
                    System.out.println("\tOK, I will mark this task as done");
                    toDoList.get(number - 1).markAsDone();
                    System.out.println("\t" + toDoList.get(number - 1).toString());
                    continue;
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
                    continue;
                } catch (NumberFormatException e) {
                    // Intentionally ignored because it is possible that the substring after "unmark" is not a number
                }
            }

            toDoList.add(new Task(input));
            System.out.println("\t added: " + input);
        }

        scanner.close();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(GOODBYE);
    }
}
