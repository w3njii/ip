/**
 * Represents a task that does not have a date or time attached to it.
 *
 * @author w3njii
 */
public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }

    /**
     * {@inheritDoc}
     *
     * Additionally, adds a "[T]" at the front to represent "to-do" task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
