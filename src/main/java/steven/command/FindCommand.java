package steven.command;

import steven.exception.MissingFindKeywordException;
import steven.storage.Storage;
import steven.task.TaskList;

public class FindCommand implements Command {
    private final String input;

    public FindCommand(String input) {
        this.input = input;
    }

    @Override
    public String execute(Storage storage, TaskList tasks) {
        try {
            return tasks.findTasks(input);
        } catch (MissingFindKeywordException e) {
            return e.getMessage();
        }
    }
}
