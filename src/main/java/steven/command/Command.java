package steven.command;

import steven.exception.StevenException;
import steven.storage.Storage;
import steven.task.TaskList;

/**
 * Represents the different commands that can be given by the user.
 * Each command corresponds to an action supported by the chatbot.
 */
public interface Command {
    String execute(Storage storage, TaskList tasks) throws StevenException;
}
