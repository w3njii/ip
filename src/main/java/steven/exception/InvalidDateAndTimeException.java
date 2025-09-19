package steven.exception;

/**
 * Thrown when a user attempts to add a task that involves a date and time but provides an invalid date or time.
 * e.g. 12-99-2023 1234 is invalid because the "99"th month does not exist.
 */
public class InvalidDateAndTimeException extends StevenException {
    public InvalidDateAndTimeException() {
        super("\tpls use a valid date or time");
    }
}
