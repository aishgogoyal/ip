package avo.task;

import java.util.ArrayList;

/**
 * Represents a list of tasks and provides basic operations on them.
 */
public class TaskList {

    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list using an existing list of tasks.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Removes and returns the task at the given index.
     */
    public Task remove(int index) {
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns all tasks in the list.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
    * Returns tasks whose description contains the keyword.
     */
    public ArrayList<Task> find(String keyword) {
        ArrayList<Task> matches = new ArrayList<>();

        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                matches.add(task);
            }
        }

        return matches;
    }


    /* ======================
       State updates
       ====================== */

    public void markDone(int index) {
        tasks.get(index).markDone();
    }

    /**
     * Marks the task at the given index as not done.
     */
    public void markNotDone(int index) {
        tasks.get(index).markNotDone();
    }

    /**
     * Checks if the index is within the task list range.
     */
    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    /**
     * Checks if the task list is empty.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
