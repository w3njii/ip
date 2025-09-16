package steven.command;

import steven.exception.StevenException;
import steven.storage.Storage;
import steven.task.TaskList;

public class AddDeadlineTaskCommand implements Command {
    private final String input;

    public AddDeadlineTaskCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks) {
        try {
            return tasks.addDeadlineTask(input, storage);
        } catch (StevenException e) {
            return e.getMessage();
        }
    }
}
