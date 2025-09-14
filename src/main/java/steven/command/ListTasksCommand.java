package steven.command;

import steven.storage.Storage;
import steven.task.TaskList;

public class ListTasksCommand implements Command {
    @Override
    public String execute(Storage storage, TaskList tasks) {
        return tasks.getToDoListString();
    }
}
