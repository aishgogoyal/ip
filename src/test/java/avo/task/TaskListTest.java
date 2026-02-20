package avo.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void testFind() {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));
        list.add(new Todo("buy milk"));
        list.add(new Todo("read newspaper"));
        java.util.ArrayList<Task> found = list.find("read");
        assertEquals(2, found.size());
        assertTrue(found.get(0).getDescription().contains("read"));
        assertTrue(found.get(1).getDescription().contains("read"));
    }

    @Test
    public void testIsValidIndexAndIsEmpty() {
        TaskList list = new TaskList();
        assertTrue(list.isEmpty());
        assertFalse(list.isValidIndex(0));
        list.add(new Todo("a"));
        assertFalse(list.isEmpty());
        assertTrue(list.isValidIndex(0));
        assertFalse(list.isValidIndex(1));
    }
}
