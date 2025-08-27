import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.StringBuilder;

//Ui: deals with interactions with the user
//Storage: deals with loading tasks from the file and saving tasks in the file
//Parser: deals with making sense of the user command
//TaskList: contains the task list e.g., it has operations to add/delete tasks in the list


public class Steven {

    private static final ArrayList<Task> toDoList = new ArrayList<>();
    private static final Scanner scanner = new Scanner(System.in);

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
        TaskList taskList = new TaskList();
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
                taskList.printToDoList();
                break;

            case TODO:
                try {
                    taskList.addToDoTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case DEADLINE:
                try {
                    taskList.addDeadlineTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case EVENT:
                try {
                    taskList.addEventTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case MARK:
                try {
                    taskList.markTask(input);
                } catch (InvalidMarkFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case UNMARK:
                try {
                    taskList.unmarkTask(input);
                } catch (InvalidMarkFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case DELETE:
                taskList.deleteTask(input);
                break;

            case UNKNOWN:
                System.out.println("\t?????");
                break;
            }
            ui.printHorizontalLine();
        }
    }
}
