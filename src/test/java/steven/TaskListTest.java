package steven;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import steven.exception.StevenException;
import steven.task.Task;
import steven.task.TaskList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TaskListTest {

    @Test
    public void addToDoTask_emptyDescription_emptyDescriptionExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addToDoTask("todo ");
            fail();
        } catch (StevenException e) {
            assertEquals("\tWhr is your description????", e.getMessage());
        }
    }

    @Test
    public void addDeadlineTask_emptyDescription_emptyDescriptionExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addDeadlineTask("deadline  ");
            fail();
        } catch (StevenException e) {
            assertEquals("\tWhr is your description????", e.getMessage());
        }
    }

    @Test
    public void addDeadlineTask_invalidFormat_invalidFormatExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addDeadlineTask("deadline task ");
            fail();
        } catch (StevenException e) {
            assertEquals("\tPls use the correct format for deadline task", e.getMessage());
        }
    }

    @Test
    public void addDeadlineTask_emptyDeadline_missingDeadlineExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addDeadlineTask("deadline task /by ");
            fail();
        } catch (StevenException e) {
            assertEquals("\tWhr is your deadline???", e.getMessage());
        }
    }

    @Test
    public void addDeadlineTask_validInput_taskAddedSuccessfully() throws StevenException {
        ArrayList<Task> list = new ArrayList<>();
        TaskList taskList = new TaskList(list);
        taskList.addDeadlineTask("deadline return book /by 20-09-2023 1234");
        assertEquals(1, list.size());
        assertEquals("[D][ ] return book (by: 20 Sept 2023 12:34)",
                list.get(0).toString());
    }

    @Test
    public void addEventTask_emptyDescription_emptyDescriptionExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addEventTask("event  ");
            fail();
        } catch (StevenException e) {
            assertEquals("\tWhr is your description????", e.getMessage());
        }
    }

    @Test
    public void addEventTask_invalidFormat_invalidFormatExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addEventTask("event party");
            fail();
        } catch (StevenException e) {
            assertEquals("\tPls use the correct format for event task", e.getMessage());
        }
    }

    @Test
    public void addEventTask_missingFromOrTo_invalidFormatExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addEventTask("event party /from 5pm");
            fail();
        } catch (StevenException e) {
            assertEquals("\tPls use the correct format for event task", e.getMessage());
        }
    }

    @Test
    public void addEventTask_emptyStartAndEndTime_missingStartAndEndTimeExceptionThrown() {
        try {
            new TaskList(new ArrayList<>()).addEventTask("event party /from  /to  ");
            fail();
        } catch (StevenException e) {
            assertEquals("\tYour event from when to when????", e.getMessage());
        }
    }

    @Test
    public void addEventTask_validInput_taskAddedSuccessfully() throws StevenException {
        ArrayList<Task> list = new ArrayList<>();
        TaskList taskList = new TaskList(list);
        taskList.addEventTask("event some event /from 16-09-2025 0000 /to 17-09-2025 0000");
        assertEquals(1, list.size());
        assertEquals("[E][ ] some event (from: 16 Sept 2025 00:00 to: 17 Sept 2025 00:00)",
                list.get(0).toString());
    }
}
