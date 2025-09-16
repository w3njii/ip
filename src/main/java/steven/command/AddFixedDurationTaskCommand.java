package steven.command;

import steven.exception.StevenException;
import steven.storage.Storage;
import steven.task.TaskList;

public class AddFixedDurationTaskCommand implements Command {
    private final String input;

    public AddFixedDurationTaskCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks) {
        try {
            return tasks.addFixedDurationTask(input, storage);
        } catch (StevenException e) {
            return e.getMessage();
        }
    }
}
