package avo.task;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TaskListTest {

    @Test
    public void addAndRemove_taskCountUpdatesCorrectly() {
        TaskList list = new TaskList();

        list.add(new Todo("eat"));
        list.add(new Todo("sleep"));

        assertEquals(2, list.size());

        Task removed = list.remove(0);
        assertEquals("[T][ ] eat", removed.toString());
        assertEquals(1, list.size());
        assertEquals("[T][ ] sleep", list.get(0).toString());
    }

    @Test
    public void markDone_changesTaskStatus() {
        TaskList list = new TaskList();
        list.add(new Todo("read"));

        list.markDone(0);

        assertTrue(list.get(0).isDone());
        assertEquals("[T][X] read", list.get(0).toString());
    }

    @Test
    public void isValidIndex_outOfRange_returnsFalse() {
        TaskList list = new TaskList();
        list.add(new Todo("one"));

        assertFalse(list.isValidIndex(-1));
        assertFalse(list.isValidIndex(1));
        assertTrue(list.isValidIndex(0));
    }
}
