package steven.command;

import steven.exception.StevenException;
import steven.storage.Storage;
import steven.task.TaskList;

public class AddEventTaskCommand implements Command {
    private final String input;

    public AddEventTaskCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks) {
        try {
            return tasks.addEventTask(input, storage);
        } catch (StevenException e) {
            return e.getMessage();
        }
    }
}
