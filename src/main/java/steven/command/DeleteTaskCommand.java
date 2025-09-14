package steven.command;

import steven.storage.Storage;
import steven.task.TaskList;

public class DeleteTaskCommand implements Command {
    @Override
    public String execute(Storage storage, TaskList tasks) {
        return "";
    }
}
