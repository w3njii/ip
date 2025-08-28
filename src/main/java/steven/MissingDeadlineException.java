package steven;

public class MissingDeadlineException extends StevenException {
    public MissingDeadlineException() {
        super("\tWhr is your deadline???");
    }
}
