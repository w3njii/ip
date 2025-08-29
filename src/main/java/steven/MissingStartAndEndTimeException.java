package steven;

public class MissingStartAndEndTimeException extends StevenException {
    public MissingStartAndEndTimeException() {
        super("\tYour event from when to when????");
    }
}
