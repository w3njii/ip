package steven;

public class InvalidMarkFormatException extends StevenException {
    public InvalidMarkFormatException() {
        super("\tPls use the correct format for marking/unmarking tasks");
    }
}
