package steven.command;

import steven.exception.InvalidMarkFormatException;
import steven.storage.Storage;
import steven.task.TaskList;

public class MarkTaskCommand implements Command {
    private final String input;

    public MarkTaskCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks) {
        try {
            return tasks.markTask(input);
        } catch (InvalidMarkFormatException e) {
            return e.getMessage();
        }
    }
}
