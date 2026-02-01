package avo;
import java.time.LocalDate;


public class Parser {

    /* ======================
       Command type
       ====================== */

    public CommandType parseCommandType(String userInput) {
        if (userInput.equals("bye")) {
            return CommandType.BYE;
        }
        if (userInput.equals("list")) {
            return CommandType.LIST;
        }
        if (userInput.equals("on") || userInput.startsWith("on ")) {
            return CommandType.ON;
        }
        if (userInput.startsWith("mark ")) {
            return CommandType.MARK;
        }
        if (userInput.startsWith("unmark ")) {
            return CommandType.UNMARK;
        }
        if (userInput.startsWith("delete ")) {
            return CommandType.DELETE;
        }
        if (userInput.startsWith("todo")) {
            return CommandType.TODO;
        }
        if (userInput.startsWith("deadline ")) {
            return CommandType.DEADLINE;
        }
        if (userInput.startsWith("event ")) {
            return CommandType.EVENT;
        }
        if (userInput.startsWith("find ")) {
            return CommandType.FIND;
        }
        return CommandType.UNKNOWN;
    }

    /* ======================
       Index parsing
       ====================== */

    public int parseIndex(String userInput, String prefix) {
        return Integer.parseInt(userInput.substring(prefix.length()).trim()) - 1;
    }

    /* ======================
       Todo parsing
       ====================== */

    public String parseTodoDescription(String userInput) {
        if (userInput.length() <= "todo".length()) {
            return "";
        }
        return userInput.substring("todo".length()).trim();
    }

    /* ======================
       Deadline parsing
       ====================== */

    public DeadlineData parseDeadline(String userInput) {
        String rest = userInput.substring("deadline ".length()).trim();
        String[] parts = rest.split(" /by ", 2);

        if (parts.length < 2) {
            return null;
        }

        String desc = parts[0].trim();
        String byStr = parts[1].trim();

        if (desc.isEmpty() || byStr.isEmpty()) {
            return null;
        }

        LocalDate by = LocalDate.parse(byStr);
        return new DeadlineData(desc, by);
    }

    /* ======================
       Event parsing
       ====================== */

    public EventData parseEvent(String userInput) {
        String rest = userInput.substring("event ".length()).trim();
        String[] firstSplit = rest.split(" /from ", 2);

        if (firstSplit.length < 2) {
            return null;
        }

        String desc = firstSplit[0].trim();
        String[] secondSplit = firstSplit[1].split(" /to ", 2);

        if (secondSplit.length < 2) {
            return null;
        }

        String from = secondSplit[0].trim();
        String to = secondSplit[1].trim();

        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            return null;
        }

        return new EventData(desc, from, to);
    }

    /* ======================
       ON date parsing
       ====================== */

    public LocalDate parseOnDate(String userInput) {
        if (userInput.length() <= "on".length()) {
            return null;
        }

        String dateStr = userInput.substring("on".length()).trim();
        if (dateStr.isEmpty()) {
            return null;
        }

        return LocalDate.parse(dateStr);
    }

    /**
        * Extracts the keyword from a find command.
    */
    public String parseFindKeyword(String userInput) {
        if (userInput.length() <= "find".length()) {
            return "";
        }
        return userInput.substring("find".length()).trim();
    }


    /* ======================
       Helper data classes
       ====================== */

    public static class DeadlineData {
        public final String description;
        public final LocalDate by;

        public DeadlineData(String description, LocalDate by) {
            this.description = description;
            this.by = by;
        }
    }

    public static class EventData {
        public final String description;
        public final String from;
        public final String to;

        public EventData(String description, String from, String to) {
            this.description = description;
            this.from = from;
            this.to = to;
        }
    }
}
