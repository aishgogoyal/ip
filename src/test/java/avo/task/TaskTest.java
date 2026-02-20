package avo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {
    @Test
    public void testTaskCreationAndStatus() {
        Task t = new Task("read book");
        assertEquals("read book", t.getDescription());
        assertFalse(t.isDone());
        assertEquals(" ", t.status());
        assertEquals("[ ] read book", t.toString());
    }

    @Test
    public void testMarkDoneAndNotDone() {
        Task t = new Task("read book");
        t.markDone();
        assertTrue(t.isDone());
        assertEquals("X", t.status());
        t.markNotDone();
        assertFalse(t.isDone());
        assertEquals(" ", t.status());
    }
}
