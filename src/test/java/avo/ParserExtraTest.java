package avo;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class ParserExtraTest {
    @Test
    public void parseTodoDescription_empty() {
        Parser parser = new Parser();
        assertEquals("", parser.parseTodoDescription("todo"));
        assertEquals("desc", parser.parseTodoDescription("todo desc"));
    }

    @Test
    public void parseIndex_validAndInvalid() {
        Parser parser = new Parser();
        assertEquals(0, parser.parseIndex("mark 1", "mark "));
        assertThrows(NumberFormatException.class, () -> parser.parseIndex("mark one", "mark "));
    }

    @Test
    public void parseEvent_validAndInvalid() {
        Parser parser = new Parser();
        Parser.EventData data = parser.parseEvent("event party /from 5pm /to 7pm");
        assertNotNull(data);
        assertEquals("party", data.description);
        assertEquals("5pm", data.from);
        assertEquals("7pm", data.to);
        assertNull(parser.parseEvent("event party /from 5pm"));
        assertNull(parser.parseEvent("event /from /to 7pm"));
    }

    @Test
    public void parseOnDate_validAndInvalid() {
        Parser parser = new Parser();
        assertNull(parser.parseOnDate("on"));
        assertNull(parser.parseOnDate("on   "));
        assertEquals(LocalDate.of(2026,2,20), parser.parseOnDate("on 2026-02-20"));
    }

    @Test
    public void parseFindKeyword() {
        Parser parser = new Parser();
        assertEquals("", parser.parseFindKeyword("find"));
        assertEquals("milk", parser.parseFindKeyword("find milk"));
    }
}
