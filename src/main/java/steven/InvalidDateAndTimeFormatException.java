package steven;

/**
 * Thrown when the user does not follow the format for date and time when adding a task that requires it.
 */
public class InvalidDateAndTimeFormatException extends StevenException {
  public InvalidDateAndTimeFormatException() {
      super("\tPls use the correct format for date and time, the format is dd-mm-yyyy tttt"
              + "\n\tdd: day\n\tmm: month\n\tyy: year\n\ttttt: time in 24-hour format, do not use colon (:)");
  }
}
