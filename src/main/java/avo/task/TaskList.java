package avo.task;
import java.util.ArrayList;

public class TaskList {

    private final ArrayList<Task> tasks;

    /* ======================
       Constructors
       ====================== */

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /* ======================
       Basic operations
       ====================== */

    public void add(Task task) {
        tasks.add(task);
    }

    public Task remove(int index) {
        return tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }

    /* ======================
       State updates
       ====================== */

    public void markDone(int index) {
        tasks.get(index).markDone();
    }

    public void markNotDone(int index) {
        tasks.get(index).markNotDone();
    }

    /* ======================
       Validation helpers
       ====================== */

    public boolean isValidIndex(int index) {
        return index >= 0 && index < tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }
}
