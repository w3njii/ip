package steven.parser;

import steven.command.Command;
import steven.command.AddDeadlineTaskCommand;
import steven.command.AddEventTaskCommand;
import steven.command.AddToDoTaskCommand;
import steven.command.MarkTaskCommand;
import steven.command.UnmarkTaskCommand;
import steven.command.DeleteTaskCommand;
import steven.command.ExitCommand;
import steven.command.FindCommand;
import steven.command.ListTasksCommand;
import steven.command.UnknownCommand;
import steven.exception.InvalidMarkFormatException;
import steven.exception.StevenException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Parser {

    public Command parse(String input) {
        String command = input.split(" ")[0].toLowerCase();
        return switch (command) {
        case "todo" -> new AddToDoTaskCommand(input);
        case "deadline" -> new AddDeadlineTaskCommand(input);
        case "event" -> new AddEventTaskCommand(input);
        case "mark" -> new MarkTaskCommand();
        case "unmark" -> new UnmarkTaskCommand();
        case "delete" -> new DeleteTaskCommand();
        case "list" -> new ListTasksCommand();
        case "bye" -> new ExitCommand();
        case "find" -> new FindCommand();
        default -> new UnknownCommand();
        };
    }
}

