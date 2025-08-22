/**
 * Represents a task that needs to be done before a specific date or time.
 *
 * @author w3njii
 */
public class Deadline extends Task {
    private final String deadline;

    /**
     * Creates a new deadline task with the given description.
     *
     * @param description a description of the task
     * @param deadline the intended deadline of the task
     */
    public Deadline(String description, String deadline) {
        super(description);
        this.deadline = deadline;
    }

    /**
     * {@inheritDoc}
     *
     * Additionally, adds a "[D]" at the front to represent "deadline" task, and the deadline at the end.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.deadline + ")";
    }
}
