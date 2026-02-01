package avo;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.junit.jupiter.api.Test;

public class ParserTest {

    @Test
    public void parseDeadline_validInput_returnsData() {
        Parser parser = new Parser();

        Parser.DeadlineData data = parser.parseDeadline("deadline return book /by 2019-10-15");

        assertNotNull(data);
        assertEquals("return book", data.description);
        assertEquals(LocalDate.of(2019, 10, 15), data.by);
    }

    @Test
    public void parseDeadline_missingBy_returnsNull() {
        Parser parser = new Parser();

        Parser.DeadlineData data = parser.parseDeadline("deadline return book");

        assertNull(data);
    }

    @Test
    public void parseDeadline_invalidDate_throws() {
        Parser parser = new Parser();

        assertThrows(DateTimeParseException.class, () ->
                parser.parseDeadline("deadline return book /by not-a-date")
        );
    }
}
