public enum Command {
    BYE, LIST, TODO, DEADLINE, EVENT, MARK, UNMARK, DELETE, UNKNOWN;

    public static Command convert(String input) {
        return switch (input.toLowerCase()) {
            case "todo" -> TODO;
            case "deadline" -> DEADLINE;
            case "event" -> EVENT;
            case "mark" -> MARK;
            case "unmark" -> UNMARK;
            case "delete" -> DELETE;
            case "list" -> LIST;
            case "bye" -> BYE;
            default -> UNKNOWN;
        };
    }
}
