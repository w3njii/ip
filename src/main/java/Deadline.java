import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that needs to be done before a specific date or time.
 *
 * @author w3njii
 */
public class Deadline extends Task {
    private final LocalDateTime deadline;

    /**
     * Creates a new deadline task with the given description and deadline.
     * <p>
     * The deadline must be provided in the format:
     * <pre>
     * dd-mm-yyyy tttt
     * </pre>
     * where:
     * <ul>
     *   <li><code>dd</code> — day</li>
     *   <li><code>mm</code> — month</li>
     *   <li><code>yyyy</code> — year</li>
     *   <li><code>tttt</code> — time in 24-hour format</li>
     * </ul>
     *
     * @param description the description of the task
     * @param deadline    the deadline string in the specified format
     */

    public Deadline(String description, String deadline) {
        super(description);
        int day = Integer.parseInt(deadline.substring(0, 2));
        int month = Integer.parseInt(deadline.substring(3, 5));
        int year = Integer.parseInt(deadline.substring(6, 10));
        int hour = Integer.parseInt(deadline.substring(11, 13));
        int minute = Integer.parseInt(deadline.substring(13, 15));
        this.deadline = LocalDateTime.of(year, month, day, hour, minute);
    }

    /**
     * {@inheritDoc}
     *
     * Additionally, adds a "[D]" at the front to represent "deadline" task, and the deadline at the end.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: "
                + this.deadline.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm")) + ")";
    }
}
