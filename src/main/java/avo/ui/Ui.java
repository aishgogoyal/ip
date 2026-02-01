package avo.ui;

import avo.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles all user interaction and output messages.
 */
public class Ui {

    private final Scanner scanner;

    /**
     * Creates a UI that reads input from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Reads a command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    /**
     * Closes the input scanner.
     */
    public void close() {
        scanner.close();
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        System.out.println("Hey there! I'm Avo 🥑");
        System.out.println("How can I help you today?");
    }

    /**
     * Shows the goodbye message.
     */
    public void showBye() {
        System.out.println("Bye! Avo is going back to sleep 😴");
    }

    /**
     * Displays all tasks in the list.
     */
    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    /**
     * Shows confirmation after adding a task.
     */
    public void showTaskAdded(Task task, int size) {
        System.out.println("✅ Task added!");
        System.out.println("  " + task);
        System.out.println("📌 Now you have " + size + " tasks in the list!");
    }

    /**
     * Shows confirmation after deleting a task.
     */
    public void showTaskDeleted(Task task, int size) {
        System.out.println("🗑️ Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("📌 Now you have " + size + " tasks in the list.");
    }

    /**
     * Shows confirmation after marking a task as done.
     */
    public void showTaskMarked(Task task) {
        System.out.println("🌟 Task marked as done!");
        System.out.println("  " + task);
    }

    /**
     * Shows confirmation after unmarking a task.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("👍 Task marked as not done!");
        System.out.println("  " + task);
    }

    /**
     * Shows an error when a task index is out of range.
     */
    public void showIndexOutOfRange(String command, int size) {
        System.out.println("❗ That task number does not exist.");
        System.out.println("👉 Use: " + command + " <task number> (between 1 and " + size + ")");
    }

    /**
     * Shows an error when a task index is not a number.
     */
    public void showIndexNotNumber(String command) {
        System.out.println("❗ The task number must be a number.");
        System.out.println("👉 Format: " + command + " <task number>");
    }

    /**
     * Shows an error for an empty todo description.
     */
    public void showEmptyTodoError() {
        System.out.println("❗ A todo must have a description.");
        System.out.println("👉 Format: todo <task description>");
    }

    /**
     * Shows an error when a deadline is missing its date.
     */
    public void showDeadlineMissingBy() {
        System.out.println("❗ A deadline must include a time.");
        System.out.println("👉 Format: deadline <task description> /by <yyyy-mm-dd>");
    }

    /**
     * Shows an error for an empty deadline description.
     */
    public void showDeadlineEmptyDescription() {
        System.out.println("❗ The task description cannot be empty.");
        System.out.println("👉 Format: deadline <task description> /by <yyyy-mm-dd>");
    }

    /**
     * Shows an error for an invalid deadline date format.
     */
    public void showDeadlineDateFormatError() {
        System.out.println("❗ That date format looks wrong.");
        System.out.println("👉 Try: deadline <task description> /by <yyyy-mm-dd>  (e.g., 2019-10-15)");
    }

    /**
     * Shows an error when event timing is missing.
     */
    public void showEventMissingFromTo() {
        System.out.println("❗ An event must include a start and end time.");
        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
    }

    /**
     * Shows an error for an empty event description.
     */
    public void showEventEmptyDescription() {
        System.out.println("❗ The event description cannot be empty.");
        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
    }

    /**
     * Shows an error for empty event times.
     */
    public void showEventEmptyTimes() {
        System.out.println("❗ Event start and end times cannot be empty.");
        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
    }

    /**
     * Shows an error when no date is provided for the on command.
     */
    public void showOnMissingDate() {
        System.out.println("📅 Please include a date.");
        System.out.println("👉 Try: on <yyyy-mm-dd>  (e.g., on 2019-10-15)");
    }

    /**
     * Shows the header for tasks on a specific date.
     */
    public void showOnDateHeader(LocalDate date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        System.out.println("📌 Here are tasks on " + date.format(fmt) + ":");
    }

    /**
     * Shows a task that matches the given date.
     */
    public void showOnMatch(int displayIndex, Task task) {
        System.out.println(displayIndex + "." + task);
    }

    /**
     * Shows a message when no tasks match the date.
     */
    public void showNoTasksOnDate() {
        System.out.println("✨ Nothing due that day. You're free! 😎");
    }

    /**
     * Shows an error for an invalid date format.
     */
    public void showOnDateFormatError() {
        System.out.println("❗ That date format looks wrong.");
        System.out.println("👉 Try: on <yyyy-mm-dd>  (e.g., on 2019-10-15)");
    }

    /**
     * Shows an error for an unknown command.
     */
    public void showUnknownCommand() {
        System.out.println("🤔 I don't understand that command.");
        System.out.println("👉 Available commands:");
        System.out.println("   todo <task description>");
        System.out.println("   deadline <task description> /by <yyyy-mm-dd>");
        System.out.println("   event <task description> /from <start> /to <end>");
        System.out.println("   list");
        System.out.println("   mark <task number>");
        System.out.println("   unmark <task number>");
        System.out.println("   delete <task number>");
        System.out.println("   on <yyyy-mm-dd>");
        System.out.println("   bye");
    }

    /**
     * Shows an error when saved tasks cannot be loaded.
     */
    public void showLoadingError() {
        System.out.println("⚠️ I had trouble loading your saved tasks.");
        System.out.println("📭 Starting with an empty task list instead.");
    }
}
