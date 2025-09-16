package steven.task;

/**
 * Represents a task that takes a fixed amount of time.
 *
 * @author w3njii
 */
public class FixedDuration extends Task {
    private int durationInHours;

    /**
     * Creates a new fixed duration task with the given description and duration in hours.
     * The duration must be an integer.
     *
     * @param description the description of the task
     * @param durationInHours the duration of the task in hours
     */
    public FixedDuration(String description, int durationInHours) {
        super(description);
        this.durationInHours = durationInHours;
    }

    @Override
    public String convertToSaveFormat() {
        return "[FD]" + super.convertToSaveFormat() + " (needs " + durationInHours + " hours)";
    }


    /**
     * {@inheritDoc}
     *
     * Additionally, adds a "[FD]" at the front to represent "fixed duration" task, and the duration at the end.
     */
    @Override
    public String toString() {
        return "[FD]" + super.toString() + " (needs " + durationInHours + " hours)";
    }
}
