package steven;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import steven.storage.Storage;
import steven.task.Task;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class StorageTest {

    @Test
    public void fetchTasks_testTasksInputFile_success() {
        ArrayList<Task> tasks = new Storage("src/test/java/steven/test_tasks.txt").fetchTasks();
        assertEquals(3, tasks.size());
    }

    @Test
    void fetchTasks_nonExistentFile_doesNotCrash() {
        ArrayList<Task> tasks = new Storage("").fetchTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    void fetchAndSaveTasks_validInput_success() {
        Storage storage = new Storage("src/test/java/steven/test_file.txt");
        ArrayList<Task> tasks = storage.fetchTasks();
        assertEquals(1, tasks.size());
        tasks.clear();
        storage.saveTasks();
        assertEquals(0, storage.fetchTasks().size());
        storage.loadTask("[T][ ] task");
        storage.loadTask("[E][ ] event (from: 20-10-2093 1532 to: 28-09-2039 1234)");
        storage.saveTasks();
        assertEquals(2, tasks.size());
        tasks.clear();
        storage.saveTasks();
        assertEquals(0, storage.fetchTasks().size());
        storage.loadTask("[D][X] deadline (by: 20-09-2023 1231)");
        storage.saveTasks();
    }
}