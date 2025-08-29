package steven;

public class Parser {

    public Command parse(String input) {
        return switch (input.toLowerCase()) {
            case "todo" -> Command.TODO;
            case "deadline" -> Command.DEADLINE;
            case "event" -> Command.EVENT;
            case "mark" -> Command.MARK;
            case "unmark" -> Command.UNMARK;
            case "delete" -> Command.DELETE;
            case "list" -> Command.LIST;
            case "bye" -> Command.BYE;
            default -> Command.UNKNOWN;
        };
    }
}
