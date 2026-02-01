package avo.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline date.
 */
public class Deadline extends Task {

    protected LocalDate by;

    /**
     * Creates a deadline task with a description and due date.
     */
    public Deadline(String description, LocalDate by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the deadline date.
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns the string representation of the deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        return "[D]" + super.toString() + " (by: " + by.format(fmt) + ")";
    }
}


