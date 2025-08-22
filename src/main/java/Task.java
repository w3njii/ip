/**
 * Represents a task that can be marked as done or not done.
 *
 * <p>This class provides methods to update and check the
 * completion status of a task. Each task also has a description.</p>
 *
 * @author w3njii
 */
public class Task {
    /**
     * Creates a new task with the given description.
     *
     * @param description a description of the task
     */
    public Task(String description) {
        this.description = description;
    }

    /**
     * Indicates whether the task is completed.
     * Defaults to {@code false}.
     */
    private boolean done = false;

    /**
     * A short description of the task.
     */
    private final String description;

    /**
     * Marks this task as completed.
     */
    public void markAsDone() {
        done = true;
    }

    /**
     * Marks this task as not completed.
     */
    public void markAsNotDone() {
        done = false;
    }

    /**
     * Returns a string representation of this task, showing whether it
     * is completed and its description.
     *
     * <p>The format is:</p>
     * <ul>
     *   <li>{@code [X] description} if the task is done</li>
     *   <li>{@code [ ] description} if the task is not done</li>
     * </ul>
     *
     * @return a string representation of this task
     */
    @Override
    public String toString() {
        return (done ? "[X] " : "[ ] ") + description;
    }
}
