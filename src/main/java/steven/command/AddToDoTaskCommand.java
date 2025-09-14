package steven.command;

import steven.exception.StevenException;
import steven.storage.Storage;
import steven.task.TaskList;

public class AddToDoTaskCommand implements Command {
    private final String input;

    public AddToDoTaskCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks) {
        try {
            return tasks.addToDoTask(input);
        } catch (StevenException e) {
            return e.getMessage();
        }
    }
}
