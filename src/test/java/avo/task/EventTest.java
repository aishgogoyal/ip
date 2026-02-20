package avo.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EventTest {
    @Test
    public void testEventToString() {
        Event e = new Event("meeting", "2pm", "3pm");
        assertEquals("[E][ ] meeting (from: 2pm to: 3pm)", e.toString());
        e.markDone();
        assertEquals("[E][X] meeting (from: 2pm to: 3pm)", e.toString());
    }
    @Test
    public void testGetFromAndTo() {
        Event e = new Event("party", "evening", "night");
        assertEquals("evening", e.getFrom());
        assertEquals("night", e.getTo());
    }
}
