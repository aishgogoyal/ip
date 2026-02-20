package avo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CommandTypeTest {
    @Test
    public void testEnumValues() {
        assertEquals(CommandType.BYE, CommandType.valueOf("BYE"));
        assertEquals(CommandType.LIST, CommandType.valueOf("LIST"));
        assertEquals(CommandType.MARK, CommandType.valueOf("MARK"));
        assertEquals(CommandType.UNMARK, CommandType.valueOf("UNMARK"));
        assertEquals(CommandType.TODO, CommandType.valueOf("TODO"));
        assertEquals(CommandType.DELETE, CommandType.valueOf("DELETE"));
        assertEquals(CommandType.DEADLINE, CommandType.valueOf("DEADLINE"));
        assertEquals(CommandType.EVENT, CommandType.valueOf("EVENT"));
        assertEquals(CommandType.UNKNOWN, CommandType.valueOf("UNKNOWN"));
        assertEquals(CommandType.ON, CommandType.valueOf("ON"));
        assertEquals(CommandType.FIND, CommandType.valueOf("FIND"));
    }
}
