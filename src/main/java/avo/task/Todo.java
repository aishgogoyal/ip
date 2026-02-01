package avo.task;

/**
 * Represents a todo task without any date or time.
 */
public class Todo extends Task {

    /**
     * Creates a todo task with the given description.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns the string representation of the todo task.
     */
    @Override
    public String toString() {
        return "[T][" + status() + "] " + description;
    }
}

