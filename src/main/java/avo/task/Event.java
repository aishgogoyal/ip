package avo.task;

/**
 * Represents a task that happens over a period of time.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Creates an event task with a description, start time, and end time.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the start time of the event.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the end time of the event.
     */
    public String getTo() {
        return to;
    }

    /**
     * Returns the string representation of the event task.
     */
    @Override
    public String toString() {
        return "[E][" + status() + "] " + description
                + " (from: " + from + " to: " + to + ")";
    }
}
