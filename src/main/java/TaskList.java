import java.util.ArrayList;

public class TaskList {
    private static final ArrayList<Task> toDoList = new ArrayList<>();

    public void addToDoTask(String input) throws StevenException {
        String description = input.substring(4).trim();
        if (description.isEmpty()) {
            throw new EmptyDescriptionException();
        }
        Task currentTask = new ToDo(description);
        toDoList.add(currentTask);
        System.out.println("\tOK, I've added this task: " + currentTask);
        System.out.println("\tNow there are " + toDoList.size() + " tasks in your list: ");
    }

    public void addDeadlineTask(String input) throws StevenException {
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

    public void addEventTask(String input) throws StevenException {
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

    public void deleteTask(String input) {
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

    public void markTask(String input) throws InvalidMarkFormatException {
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

    public void unmarkTask(String input) throws InvalidMarkFormatException {
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
    public void printToDoList() {
        System.out.println("\tHere are the tasks in your list: ");
        for (int i = 0; i < toDoList.size(); i++) {
            System.out.println("\t\t" + (i + 1) + ". " + toDoList.get(i).toString());
        }
    }
}
