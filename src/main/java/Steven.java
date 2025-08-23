import java.util.Scanner;
import java.util.ArrayList;

public class Steven {
    private static final String HORIZONTAL_LINE = "____________________________________________________________";
    private static final String GREETING = "Hello There! I'm Steven\nHow may I help you?";
    private static final String GOODBYE = "Bye.";

    private static final ArrayList<Task> toDoList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static void printList() {
        System.out.println("\tHere are the tasks in your list: ");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println("\t\t" + (i + 1) + ". " + toDoList.get(i).toString());
        }
    }

    private static void addToDoTask(String input) throws StevenException {
        String description = input.substring(4);
        if (description.trim().isEmpty()) {
            throw new EmptyDescriptionException();
        }
        Task currentTask = new ToDo(description);
        toDoList.add(currentTask);
        System.out.println("\tOK, I've added this task: " + currentTask);
        System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
    }

    private static void addDeadlineTask(String input) throws StevenException {
        String descriptionAndDeadline = input.substring(8);
        if (descriptionAndDeadline.trim().isEmpty()) {
            throw new EmptyDescriptionException();
        }
        int byIndex = descriptionAndDeadline.indexOf(" /by ");
        if (byIndex == -1) {
            throw new InvalidTaskFormatException("deadline");
        }
        String description = descriptionAndDeadline.substring(0, byIndex);
        String deadline = descriptionAndDeadline.substring(byIndex + 5);
        if (deadline.trim().isEmpty()) {
            throw new MissingDeadlineException();
        }
        Task currentTask = new Deadline(description, deadline);
        toDoList.add(currentTask);
        System.out.println("\tOK, I've added this task: " + currentTask);
        System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
    }

    public static void addEventTask(String input) throws StevenException {
        String descriptionAndTime = input.substring(5);
        if (descriptionAndTime.trim().isEmpty()) {
            throw new EmptyDescriptionException();
        }
        int fromIndex = descriptionAndTime.indexOf(" /from ");
        int toIndex = descriptionAndTime.indexOf(" /to ");
        if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
            throw new InvalidTaskFormatException("event");
        }
        String description = descriptionAndTime.substring(0, fromIndex);
        String from = descriptionAndTime.substring(fromIndex + 7, toIndex);
        String to = descriptionAndTime.substring(toIndex + 5);
        if (from.trim().isEmpty() || to.trim().isEmpty()) {
            throw new MissingStartAndEndTimeException();
        }
        Task currentTask = new Event(description, from, to);
        toDoList.add(currentTask);
        System.out.println("\tOK, I've added this task: " + currentTask);
        System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
    }

    public static void deleteTask(String input) {
        if (!input.startsWith("delete ")) {
            System.out.println("Invalid input");
            return;
        }
        try {
            int number = Integer.parseInt(input.substring(7));
            if (number < 1 || number > toDoList.size()) {
                System.out.println("\tYou only have " + toDoList.size() + " tasks in your list");
                return;
            }
            Task deletedTask = toDoList.remove(number - 1);
            System.out.println("\tOK, DELETE THIS ONE ALR:\n\t" + deletedTask);
            System.out.println("Now ur list got " + toDoList.size() + " task");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input");
        }
    }

    public static void markTask(String input) throws InvalidMarkFormatException {
        if (!input.startsWith("mark ")) {
            throw new InvalidMarkFormatException();
        }
        try {
            int number = Integer.parseInt(input.substring(5));
            if (number > toDoList.size() || number < 1) {
                System.out.println("\tYou only have " + toDoList.size() + " tasks in your list");
                return;
            }
            System.out.println("\tOK, I will mark this task as done");
            toDoList.get(number - 1).markAsDone();
            System.out.println("\t" + toDoList.get(number - 1).toString());
        } catch (NumberFormatException e) {
            throw new InvalidMarkFormatException();
        }
    }

    public static void unmarkTask(String input) throws InvalidMarkFormatException {
        if (!input.startsWith("unmark ")) {
            throw new InvalidMarkFormatException();
        }
        try {
            int number = Integer.parseInt(input.substring(7));
            if (number > toDoList.size() || number < 1) {
                System.out.println("\tYou only have " + toDoList.size() + " tasks in your list");
                return;
            }
            System.out.println("\tOK, I will mark this task as not done");
            toDoList.get(number - 1).markAsNotDone();
            System.out.println("\t" + toDoList.get(number - 1).toString());
        } catch (NumberFormatException e) {
            throw new InvalidMarkFormatException();
        }
    }

    public static void main(String[] args) {
        System.out.println(GREETING);
        System.out.println(HORIZONTAL_LINE);

        while (true) {
            String input = scanner.nextLine();
            Command command = Command.convert(input.split(" ")[0]);

            switch (command) {
                case BYE:
                    scanner.close();
                    System.out.println("\t" + GOODBYE);
                    return;

                case LIST:
                    printList();
                    break;

                case TODO:
                    try {
                        addToDoTask(input);
                    } catch (StevenException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case DEADLINE:
                    try {
                        addDeadlineTask(input);
                    } catch (StevenException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case EVENT:
                    try {
                        addEventTask(input);
                    } catch (StevenException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case MARK:
                    try {
                        markTask(input);
                    } catch (InvalidMarkFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case UNMARK:
                    try {
                        unmarkTask(input);
                    } catch (InvalidMarkFormatException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case DELETE:
                    deleteTask(input);
                    break;

                case UNKNOWN:
                    System.out.println("\t?????");
                    break;
            }
            System.out.println(HORIZONTAL_LINE);
        }
    }
}
