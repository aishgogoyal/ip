package avo.ui;
import avo.task.Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Ui {

    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void close() {
        scanner.close();
    }

    public void showWelcome() {
        System.out.println("Hey there! I'm Avo 🥑");
        System.out.println("How can I help you today?");
    }

    public void showBye() {
        System.out.println("Bye! Avo is going back to sleep 😴");
    }

    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
    }

    public void showTaskAdded(Task task, int size) {
        System.out.println("✅ Task added!");
        System.out.println("  " + task);
        System.out.println("📌 Now you have " + size + " tasks in the list!");
    }

    public void showTaskDeleted(Task task, int size) {
        System.out.println("🗑️ Noted. I've removed this task:");
        System.out.println("  " + task);
        System.out.println("📌 Now you have " + size + " tasks in the list.");
    }

    public void showTaskMarked(Task task) {
        System.out.println("🌟 Task marked as done!");
        System.out.println("  " + task);
    }

    public void showTaskUnmarked(Task task) {
        System.out.println("👍 Task marked as not done!");
        System.out.println("  " + task);
    }

    public void showIndexOutOfRange(String command, int size) {
        System.out.println("❗ That task number does not exist.");
        System.out.println("👉 Use: " + command + " <task number> (between 1 and " + size + ")");
    }

    public void showIndexNotNumber(String command) {
        System.out.println("❗ The task number must be a number.");
        System.out.println("👉 Format: " + command + " <task number>");
    }

    public void showEmptyTodoError() {
        System.out.println("❗ A todo must have a description.");
        System.out.println("👉 Format: todo <task description>");
    }

    public void showDeadlineMissingBy() {
        System.out.println("❗ A deadline must include a time.");
        System.out.println("👉 Format: deadline <task description> /by <yyyy-mm-dd>");
    }

    public void showDeadlineEmptyDescription() {
        System.out.println("❗ The task description cannot be empty.");
        System.out.println("👉 Format: deadline <task description> /by <yyyy-mm-dd>");
    }

    public void showDeadlineDateFormatError() {
        System.out.println("❗ That date format looks wrong.");
        System.out.println("👉 Try: deadline <task description> /by <yyyy-mm-dd>  (e.g., 2019-10-15)");
    }

    public void showEventMissingFromTo() {
        System.out.println("❗ An event must include a start and end time.");
        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
    }

    public void showEventEmptyDescription() {
        System.out.println("❗ The event description cannot be empty.");
        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
    }

    public void showEventEmptyTimes() {
        System.out.println("❗ Event start and end times cannot be empty.");
        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
    }

    public void showOnMissingDate() {
        System.out.println("📅 Please include a date.");
        System.out.println("👉 Try: on <yyyy-mm-dd>  (e.g., on 2019-10-15)");
    }

    public void showOnDateHeader(LocalDate date) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        System.out.println("📌 Here are tasks on " + date.format(fmt) + ":");
    }

    public void showOnMatch(int displayIndex, Task task) {
        System.out.println(displayIndex + "." + task);
    }

    public void showNoTasksOnDate() {
        System.out.println("✨ Nothing due that day. You're free! 😎");
    }

    public void showOnDateFormatError() {
        System.out.println("❗ That date format looks wrong.");
        System.out.println("👉 Try: on <yyyy-mm-dd>  (e.g., on 2019-10-15)");
    }

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

    public void showLoadingError() {
        System.out.println("⚠️ I had trouble loading your saved tasks.");
        System.out.println("📭 Starting with an empty task list instead.");
    }
}
