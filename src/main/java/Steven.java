import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.StringBuilder;

public class Steven {

    private static final ArrayList<Task> toDoList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

    private static void addToDoTask(String input) throws StevenException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        Task currentTask = new ToDo(description);
        toDoList.add(currentTask);
        System.out.println("\tOK, I've added this task: " + currentTask);
        System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
    }

    private static void addDeadlineTask(String input) throws StevenException {
        String descriptionAndDeadline = input.substring(8).trim();
        if (descriptionAndDeadline.isEmpty()) {
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
        String descriptionAndTime = input.substring(5).trim();
        if (descriptionAndTime.isEmpty()) {
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
            System.out.println("\tNow ur list got " + toDoList.size() + " task");
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

    public static void saveTasks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : toDoList) {
            stringBuilder.append(task.convertToSaveFormat()).append("\n");
        }
        try {
            FileWriter fw = new FileWriter("data/tasklist.txt");
            fw.write(stringBuilder.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void fetchTasks() {
        File f = new File("data/tasklist.txt");
        try {
            Scanner taskScanner = new Scanner(f);
            while (taskScanner.hasNextLine()) {
                String currentTask = taskScanner.nextLine();
                loadTask(currentTask);
            }
            taskScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void loadTask(String task) {
        try {
            if (task.startsWith("[T]")) {
                toDoList.add(new ToDo(task.substring(7)));
            } else if (task.startsWith("[D]")) {
                int byIndex = task.indexOf(" (by:");
                String description = task.substring(7, byIndex);
                String by = task.substring(byIndex + 6, task.indexOf(")"));
                toDoList.add(new Deadline(description, by));
            } else if (task.startsWith("[E]")) {
                int fromIndex = task.indexOf(" (from:");
                int toIndex = task.indexOf(" to:");
                String description = task.substring(7, fromIndex);
                String from = task.substring(fromIndex + 8, toIndex);
                String to = task.substring(toIndex + 5, task.indexOf(")"));
                toDoList.add(new Event(description, from, to));
            }
            if (task.startsWith("[X]", 3)) {
                toDoList.get(toDoList.size() - 1).markAsDone();
            }
        } catch (InvalidDateAndTimeFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.printGreeting();
        ui.printHorizontalLine();
        fetchTasks();

        while (true) {
            String input = scanner.nextLine();
            Command command = Command.convert(input.split(" ")[0]);

            switch (command) {
            case BYE:
                scanner.close();
                ui.printGoodbye();
                saveTasks();
                return;

            case LIST:
                ui.printToDoList(toDoList);
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
            ui.printHorizontalLine();
        }
    }
}
