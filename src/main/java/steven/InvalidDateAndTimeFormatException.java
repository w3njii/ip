package steven;

public class InvalidDateAndTimeFormatException extends StevenException {

    public InvalidDateAndTimeFormatException() {
        super("\tPlease use the correct format for date and time. The format is dd-mm-yyyy tttt.\n\t"
                + "dd: day\n\t"
                + "mm: month\n\t"
                + "yyyy: year\n\t"
                + "tttt: time in 24-hour format, do not use colon (:).");
    }
}
