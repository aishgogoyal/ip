package avo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TodoTest {
    @Test
    public void testTodoToString() {
        Todo todo = new Todo("buy milk");
        assertEquals("[T][ ] buy milk", todo.toString());
        todo.markDone();
        assertEquals("[T][X] buy milk", todo.toString());
    }
}
