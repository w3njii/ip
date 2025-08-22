/**
 * Represents a task that start at a specific date/time and ends at a specific date/time.
 *
 * @author w3njii
 */
public class Event extends Task {
    private final String startTime;
    private final String endTime;

    /**
     * Creates a new event task with the given description.
     *
     * @param description a description of the task
     * @param startTime the time that the event starts
     * @param endTime the time that the event ends
     */
    public Event(String description, String startTime, String endTime) {
        super(description);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * {@inheritDoc}
     *
     * Additionally, adds a "[E]" at the front to represent "event" task, and the start and end time at the end.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.startTime + " to: " + this.endTime + ")";
    }
}
