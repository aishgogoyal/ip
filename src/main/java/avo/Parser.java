package avo;

import java.time.LocalDate;

/**
 * Parses user input into command types and structured command data.
 */
public class Parser {

    /**
     * Identifies the command type from user input.
     */
    public CommandType parseCommandType(String userInput) {
        assert userInput != null : "userInput should not be null";

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

    /**
     * Parses a 1-based index from a command and converts it to 0-based.
     */
    public int parseIndex(String userInput, String prefix) {
        assert userInput != null : "userInput should not be null";
        assert prefix != null : "prefix should not be null";
        assert userInput.startsWith(prefix)
                : "userInput should start with the given prefix";

        String indexStr = userInput.substring(prefix.length()).trim();
        assert !indexStr.isEmpty() : "Index string should not be empty";

        int index = Integer.parseInt(indexStr) - 1;
        assert index >= 0 : "Parsed index should be non-negative";

        return index;
    }

    /**
     * Extracts the description from a todo command.
     */
    public String parseTodoDescription(String userInput) {
        assert userInput != null : "userInput should not be null";
        assert userInput.startsWith("todo")
                : "parseTodoDescription should only be called for todo commands";

        if (userInput.length() <= "todo".length()) {
            return "";
        }
        return userInput.substring("todo".length()).trim();
    }

    /**
     * Parses a deadline command.
     * Returns null if required parts are missing.
     */
    public DeadlineData parseDeadline(String userInput) {
        assert userInput != null : "userInput should not be null";
        assert userInput.startsWith("deadline ")
                : "parseDeadline should only be called for deadline commands";

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
        assert by != null : "Parsed deadline date should not be null";

        return new DeadlineData(desc, by);
    }

    /**
     * Parses an event command.
     * Returns null if required parts are missing.
     */
    public EventData parseEvent(String userInput) {
        assert userInput != null : "userInput should not be null";
        assert userInput.startsWith("event ")
                : "parseEvent should only be called for event commands";

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

    /**
     * Parses the date from an "on" command.
     */
    public LocalDate parseOnDate(String userInput) {
        assert userInput != null : "userInput should not be null";
        assert userInput.startsWith("on")
                : "parseOnDate should only be called for on commands";

        if (userInput.length() <= "on".length()) {
            return null;
        }

        String dateStr = userInput.substring("on".length()).trim();
        if (dateStr.isEmpty()) {
            return null;
        }

        LocalDate date = LocalDate.parse(dateStr);
        assert date != null : "Parsed date should not be null";

        return date;
    }

    /**
     * Extracts the keyword from a find command.
     */
    public String parseFindKeyword(String userInput) {
        assert userInput != null : "userInput should not be null";
        assert userInput.startsWith("find")
                : "parseFindKeyword should only be called for find commands";

        if (userInput.length() <= "find".length()) {
            return "";
        }
        return userInput.substring("find".length()).trim();
    }


    /* ======================
       Helper data classes
       ====================== */

    /**
     * Holds parsed deadline data.
     */
    public static class DeadlineData {
        public final String description;
        public final LocalDate by;

        /**
         * Creates a {@code DeadlineData} object.
         *
         * @param description Task description.
         * @param by Deadline date.
         */
        public DeadlineData(String description, LocalDate by) {
            this.description = description;
            this.by = by;
        }
    }

    /**
     * Holds parsed event data.
     */
    public static class EventData {
        public final String description;
        public final String from;
        public final String to;

        /**
         * Creates a {@code EventData} object.
         *
         * @param description Event description.
         * @param from Start time.
         * @param to End time.
         */
        public EventData(String description, String from, String to) {
            this.description = description;
            this.from = from;
            this.to = to;
        }
    }


}
