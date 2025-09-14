package steven.task;

import steven.exception.*;

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
    private final ArrayList<Task> toDoList;

    /**
     * Creates a {@code TaskList} with the given list of tasks.
     *
     * @param toDoList the list of tasks to initialize with
     */
    public TaskList(ArrayList<Task> toDoList) {
        this.toDoList = toDoList;
    }

    /**
     * Adds a new {@link ToDo} task to the list.
     *
     * @param input the raw user input containing the task description
     * @throws StevenException if the description is empty
     */
    public String addToDoTask(String input) throws StevenException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        Task currentTask = new ToDo(description);
        toDoList.add(currentTask);
        return "\tOK, I've added this task: " + currentTask + "\n\tNow there are "
                + toDoList.size() + " tasks in your list: ";
    }

    /**
     * Adds a new {@link Deadline} task to the list.
     *
     * @param input the raw user input containing the description and deadline
     * @return
     * @throws StevenException if the format is invalid, description is empty,
     *                         or deadline is missing
     */
    public String addDeadlineTask(String input) throws StevenException {
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
        toDoList.add(currentTask);
        return "\tOK, I've added this task: " + currentTask + "\tNow there are "
                + toDoList.size() + " tasks in your list: ";
    }

    /**
     * Adds a new {@link Event} task to the list.
     *
     * @param input the raw user input containing the description, start, and end times
     * @return
     * @throws StevenException if the format is invalid, description is empty,
     *                         or start/end times are missing
     */
    public String addEventTask(String input) throws StevenException {
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
        toDoList.add(currentTask);
        return "\tOK, I've added this task: " + currentTask + "\tNow there are "
                + toDoList.size() + " tasks in your list: ";
    }

    /**
     * Deletes a task from the list.
     *
     * @param input the raw user input specifying the task index to delete
     * @return
     */
    public String deleteTask(String input) {
        if (!input.startsWith("delete ")) {
            return "Invalid input";
        }
        try {
            int number = Integer.parseInt(input.substring(7));
            if (number < 1 || number > toDoList.size()) {
                return "\tYou only have " + toDoList.size() + " tasks in your list";
            }
            Task deletedTask = toDoList.remove(number - 1);
            return "\tOK, DELETE THIS ONE ALR:\n\t" + deletedTask + "\n\tNow ur list got "
                    + toDoList.size() + " task";
        } catch (NumberFormatException e) {
            return "Invalid input";
        }
    }

    /**
     * Marks a task in the list as done.
     *
     * @param input the raw user input specifying the task index
     * @return
     * @throws InvalidMarkFormatException if the input is not in the correct format
     */
    public String markTask(String input) throws InvalidMarkFormatException {
        if (!input.startsWith("mark ")) {
            throw new InvalidMarkFormatException();
        }
        try {
            int number = Integer.parseInt(input.substring(5));
            if (number > toDoList.size() || number < 1) {
                return "\tYou only have " + toDoList.size() + " tasks in your list";
            }
            toDoList.get(number - 1).markAsDone();
            return "\tOK, I will mark this task as done \n\t" + toDoList.get(number - 1);
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
    public String unmarkTask(String input) throws InvalidMarkFormatException {
        if (!input.startsWith("unmark ")) {
            throw new InvalidMarkFormatException();
        }
        try {
            int number = Integer.parseInt(input.substring(7));
            if (number > toDoList.size() || number < 1) {
                return "\tYou only have " + toDoList.size() + " tasks in your list";
            }
            toDoList.get(number - 1).markAsNotDone();
            return "\tOK, I will mark this task as not done \n\t" + toDoList.get(number - 1);
        } catch (NumberFormatException e) {
            throw new InvalidMarkFormatException();
        }
    }

    /**
     * Prints all tasks currently in the list.
     */
    public void printToDoList() {
        System.out.println("\tHere are the tasks in your list: ");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println("\t\t" + (i + 1) + ". " + toDoList.get(i).toString());
        }
    }

    public void findTasks(String keyword) throws MissingFindKeywordException {
        if (keyword.split(" ").length < 2) {
            throw new MissingFindKeywordException();
        }
        System.out.println("Here are the matching tasks in your list: ");
        int i = 1;
        for (Task task : toDoList) {
            if (task.toString().contains(keyword)) {
                System.out.println("\t" + i + ". " + task);
                i++;
            }
        }
    }
}
