package steven.command;

import steven.storage.Storage;
import steven.task.TaskList;

public class DeleteTaskCommand implements Command {
    private final String input;

    public DeleteTaskCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks) {
        return tasks.deleteTask(input);
    }
}
