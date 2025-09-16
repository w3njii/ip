package steven.storage;

import steven.exception.InvalidDateAndTimeFormatException;
import steven.task.Deadline;
import steven.task.Event;
import steven.task.Task;
import steven.task.ToDo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * The <code>Storage</code> class provides methods to save and load tasks
 * to and from local storage in the form of text files.
 *
 * <p>The tasks are stored in an internal <code>ArrayList</code> that
 * is managed by this class. Methods are provided to fetch tasks from
 * a file, save tasks to a file, and load individual tasks into memory.</p>
 */
public class Storage {
    private final String filePath;

    /**
     * Creates a Storage instance that is able to read from and write to the specified file.
     *
     * @param filePath the filepath of the text file from the root folder
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveToLocal(ArrayList<Task> tasks) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Task task : tasks) {
            stringBuilder.append(task.convertToSaveFormat()).append("\n");
        }
        try {
            FileWriter fw = new FileWriter(this.filePath);
            fw.write(stringBuilder.toString());
            fw.close();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Reads tasks from the specified file and populates the internal list.
     *
     * @return an ArrayList containing tasks read from local storage, returns an empty list if the file is not found
     */
    public ArrayList<Task> fetchTasks() {
        File f = new File(this.filePath);
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            Scanner taskScanner = new Scanner(f);
            while (taskScanner.hasNextLine()) {
                String currentTask = taskScanner.nextLine();
                loadTask(currentTask, tasks);
            }
            taskScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error reading from local: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Parses a task string in its save format and adds the corresponding Task object to the internal list.
     *
     * @param task the text representation of a task in save format
     */
    public void loadTask(String task, ArrayList<Task> tasks) {
        try {
            if (task.startsWith("[T]")) {
                tasks.add(new ToDo(task.substring(7)));
            } else if (task.startsWith("[D]")) {
                int byIndex = task.indexOf(" (by:");
                String description = task.substring(7, byIndex);
                String by = task.substring(byIndex + 6, task.indexOf(")"));
                tasks.add(new Deadline(description, by));
            } else if (task.startsWith("[E]")) {
                int fromIndex = task.indexOf(" (from:");
                int toIndex = task.indexOf(" to:");
                String description = task.substring(7, fromIndex);
                String from = task.substring(fromIndex + 8, toIndex);
                String to = task.substring(toIndex + 5, task.indexOf(")"));
                tasks.add(new Event(description, from, to));
            }
            if (task.startsWith("[X]", 3)) {
                tasks.get(tasks.size() - 1).markAsDone();
            }
        } catch (InvalidDateAndTimeFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
