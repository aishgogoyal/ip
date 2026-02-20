package avo.task;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

public class TaskListExtraTest {
    @Test
    public void testFind() {
        TaskList list = new TaskList();
        list.add(new Todo("read book"));
        list.add(new Todo("buy milk"));
        list.add(new Todo("read newspaper"));
        ArrayList<Task> found = list.find("read");
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
