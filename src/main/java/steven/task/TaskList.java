package steven.task;

import steven.exception.EmptyDescriptionException;
import steven.exception.InvalidMarkFormatException;
import steven.exception.InvalidTaskFormatException;
import steven.exception.MissingDeadlineException;
import steven.exception.MissingFindKeywordException;
import steven.exception.MissingStartAndEndTimeException;
import steven.exception.MissingDurationException;
import steven.exception.StevenException;
import steven.storage.Storage;

import java.util.ArrayList;

/**
 * Represents a list of tasks that can be modified by user commands.
 * <p>
 * Provides methods to add, delete, mark, unmark, and print tasks.
 * It maintains an internal {@link ArrayList} of {@link Task} objects
 * that reflects the current state of the task list.
 * </p>
 */
public class TaskList {
    private final ArrayList<Task> taskList;

    /**
     * Creates a {@code TaskList} with the given list of tasks.
     *
     * @param taskList the list of tasks to initialize with
     */
    public TaskList(ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Adds a new {@link ToDo} task to the list.
     *
     * @param input the raw user input containing the task description
     * @throws StevenException if the description is empty
     */
    public String addToDoTask(String input, Storage storage) throws StevenException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        Task currentTask = new ToDo(description);
        taskList.add(currentTask);
        storage.saveToLocal(taskList);
        return "\tOK, I've added this task: " + currentTask + "\n\tNow there are "
                + taskList.size() + " tasks in your list: ";
    }

    /**
     * Adds a new {@link Deadline} task to the list.
     *
     * @param input the raw user input containing the description and deadline
     * @throws StevenException if the format is invalid, description is empty,
     *                         or deadline is missing
     */
    public String addDeadlineTask(String input, Storage storage) throws StevenException {
        String descriptionAndDeadline = input.substring(8).stripLeading();
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
        taskList.add(currentTask);
        storage.saveToLocal(taskList);
        return "\tOK, I've added this task: " + currentTask + "\n\tNow there are "
                + taskList.size() + " tasks in your list: ";
    }

    /**
     * Adds a new {@link Event} task to the list.
     *
     * @param input the raw user input containing the description, start, and end times
     * @throws StevenException if the format is invalid, description is empty,
     *                         or start/end times are missing
     */
    public String addEventTask(String input, Storage storage) throws StevenException {
        String descriptionAndTime = input.substring(5).stripLeading();
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
        if (from.isBlank() || to.isBlank()) {
            throw new MissingStartAndEndTimeException();
        }
        Task currentTask = new Event(description, from, to);
        taskList.add(currentTask);
        storage.saveToLocal(taskList);
        return "\tOK, I've added this task: " + currentTask + "\n\tNow there are "
                + taskList.size() + " tasks in your list: ";
    }

    public String addFixedDurationTask(String input, Storage storage) throws StevenException {
        String descriptionAndDuration = input.substring(14).stripLeading();
        if (descriptionAndDuration.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        int durationIndex = descriptionAndDuration.indexOf(" /duration ");
        if (durationIndex == -1) {
            throw new InvalidTaskFormatException("fixed duration");
        }
        String description = descriptionAndDuration.substring(0, durationIndex);
        String duration = descriptionAndDuration.substring(durationIndex + 11);
        if (duration.trim().isEmpty()) {
            throw new MissingDurationException();
        }
        Task currentTask = new FixedDuration(description, Integer.parseInt(duration));
        taskList.add(currentTask);
        storage.saveToLocal(taskList);
        return "\tOK, I've added this task: " + currentTask + "\n\tNow there are "
                + taskList.size() + " tasks in your list: ";
    }

    /**
     * Deletes a task from the list.
     *
     * @param input the raw user input specifying the task index to delete
     */
    public String deleteTask(String input, Storage storage) {
        if (!input.startsWith("delete ")) {
            return "Invalid input";
        }
        try {
            int number = Integer.parseInt(input.substring(7));
            if (number < 1 || number > taskList.size()) {
                return "\tYou only have " + taskList.size() + " tasks in your list";
            }
            Task deletedTask = taskList.remove(number - 1);
            storage.saveToLocal(taskList);
            return "\tOK, DELETE THIS ONE ALR:\n\t" + deletedTask + "\n\tNow ur list got "
                    + taskList.size() + " task";
        } catch (NumberFormatException e) {
            return "Invalid input";
        }
    }

    /**
     * Marks a task in the list as done.
     *
     * @param input the raw user input specifying the task index
     * @throws InvalidMarkFormatException if the input is not in the correct format
     */
    public String markTask(String input, Storage storage) throws InvalidMarkFormatException {
        if (!input.startsWith("mark ")) {
            throw new InvalidMarkFormatException();
        }
        try {
            int number = Integer.parseInt(input.substring(5));
            if (number > taskList.size() || number < 1) {
                return "\tYou only have " + taskList.size() + " tasks in your list";
            }
            taskList.get(number - 1).markAsDone();
            storage.saveToLocal(taskList);
            return "\tOK, I will mark this task as done \n\t" + taskList.get(number - 1);
        } catch (NumberFormatException e) {
            throw new InvalidMarkFormatException();
        }
    }

    /**
     * Marks a task in the list as not done.
     *
     * @param input the raw user input specifying the task index
     * @throws InvalidMarkFormatException if the input is not in the correct format
     */
    public String unmarkTask(String input, Storage storage) throws InvalidMarkFormatException {
        if (!input.startsWith("unmark ")) {
            throw new InvalidMarkFormatException();
        }
        try {
            int number = Integer.parseInt(input.substring(7));
            if (number > taskList.size() || number < 1) {
                return "\tYou only have " + taskList.size() + " tasks in your list";
            }
            taskList.get(number - 1).markAsNotDone();
            storage.saveToLocal(taskList);
            return "\tOK, I will mark this task as not done \n\t" + taskList.get(number - 1);
        } catch (NumberFormatException e) {
            throw new InvalidMarkFormatException();
        }
    }

    /**
     * Prints all tasks currently in the list.
     */
    public String getToDoListString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\tHere are the tasks in your list: ");
        for (int i = 0; i < taskList.size(); i++) {
            sb.append("\n\t\t")
                    .append(i + 1)
                    .append(". ")
                    .append(taskList.get(i).toString());
        }
        return sb.toString();
    }


    public String findTasks(String input) throws MissingFindKeywordException {
        String[] parts = input.trim().split("\\s+", 2);

        if (parts.length < 2 || parts[1].isBlank()) {
            throw new MissingFindKeywordException();
        }

        String keyword = parts[1];

        StringBuilder sb = new StringBuilder();
        sb.append("\tHere are the matching tasks in your list:\n");

        int i = 1;
        for (Task task : taskList) {
            if (task.toString().contains(keyword)) {
                sb.append("\t").append(i).append(". ").append(task).append("\n");
                i++;
            }
        }

        if (i == 1) {
            return "\tNo matching tasks found";
        }

        return sb.toString();
    }
}
