package steven.parser;

import steven.command.*;

public class Parser {

    public Command parse(String input) {
        String command = input.split(" ")[0].toLowerCase();
        return switch (command) {
        case "todo" -> new AddToDoTaskCommand(input);
        case "deadline" -> new AddDeadlineTaskCommand(input);
        case "event" -> new AddEventTaskCommand(input);
        case "fixed-duration" -> new AddFixedDurationTaskCommand(input);
        case "mark" -> new MarkTaskCommand(input);
        case "unmark" -> new UnmarkTaskCommand(input);
        case "delete" -> new DeleteTaskCommand(input);
        case "list" -> new ListTasksCommand();
        case "find" -> new FindCommand(input);
        default -> new UnknownCommand();
        };
    }
}

