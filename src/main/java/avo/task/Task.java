package avo.task;

/**
 * Represents a generic task with a description and completion status.
 */
public class Task {

    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markNotDone() {
        isDone = false;
    }

    /**
     * Returns the task status symbol.
     */
    public String status() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Checks if the task is done.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Returns the string representation of the task.
     */
    @Override
    public String toString() {
        return "[ ] " + description;
    }
}

