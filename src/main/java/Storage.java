import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Storage {
    String filePath;
    private final ArrayList<Task> toDoList = new ArrayList<>();

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTasks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : toDoList) {
            stringBuilder.append(task.convertToSaveFormat()).append("\n");
        }
        try {
            FileWriter fw = new FileWriter(this.filePath);
            fw.write(stringBuilder.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Task> fetchTasks() {
        File f = new File(this.filePath);
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
        return toDoList;
    }

    public void loadTask(String task) {
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
}
