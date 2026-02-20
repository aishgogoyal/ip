package avo.task;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class DeadlineTest {
    @Test
    public void testDeadlineToString() {
        LocalDate date = LocalDate.of(2026, 2, 20);
        Deadline d = new Deadline("submit report", date);

        assertTrue(d.toString().startsWith("[D]"));
        assertTrue(d.toString().contains("submit report"));
        assertTrue(d.toString().contains("(by: Feb 20 2026)"));

        d.markDone();
        assertTrue(d.isDone());

        assertTrue(d.toString().startsWith("[D]"));
        assertTrue(d.toString().contains("submit report"));
        assertTrue(d.toString().contains("(by: Feb 20 2026)"));
    }


    @Test
    public void testGetBy() {
        LocalDate date = LocalDate.of(2026, 2, 20);
        Deadline d = new Deadline("submit", date);
        assertEquals(date, d.getBy());
    }
}

