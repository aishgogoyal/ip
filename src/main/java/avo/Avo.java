package avo;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import avo.storage.Storage;
import avo.task.Deadline;
import avo.task.Event;
import avo.task.Task;
import avo.task.TaskList;
import avo.task.Todo;
import avo.ui.Ui;

/**
 * Main entry point of the Avo chatbot.
 * Handles user commands and coordinates UI, storage, and task management.
 */
public class Avo {

    private static final String DEFAULT_FILE_PATH = "data/avo.txt";

    private final Ui ui;
    private final Storage storage;
    private final TaskList tasks;
    private final Parser parser;

    /**
     * Creates an Avo chatbot using the given storage file path.
     *
     * @param filePath Relative path to the task storage file.
     */
    public Avo(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        this.parser = new Parser();

        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (RuntimeException e) {
            ui.showLoadingError();
            loadedTasks = new TaskList();
        }

        this.tasks = loadedTasks;
    }

    /**
     * Runs the chatbot until the user exits.
     */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            String userInput = ui.readCommand();
            CommandType command = parser.parseCommandType(userInput);

            switch (command) {
            case BYE:
                ui.showBye();
                isExit = true;
                break;

            case LIST:
                ui.showTaskList(tasks.getAll());
                break;

            case MARK:
                handleMark(userInput);
                break;

            case UNMARK:
                handleUnmark(userInput);
                break;

            case DELETE:
                handleDelete(userInput);
                break;

            case TODO:
                handleTodo(userInput);
                break;

            case DEADLINE:
                handleDeadline(userInput);
                break;

            case EVENT:
                handleEvent(userInput);
                break;

            case ON:
                handleOn(userInput);
                break;

            default:
                ui.showUnknownCommand();
                break;
            }
        }

        ui.close();
    }

    /**
     * Marks a task as done.
     */
    private void handleMark(String userInput) {
        try {
            int idx = parser.parseIndex(userInput, "mark ");

            if (!tasks.isValidIndex(idx)) {
                ui.showIndexOutOfRange("mark", tasks.size());
                return;
            }

            tasks.markDone(idx);
            storage.save(tasks.getAll());
            ui.showTaskMarked(tasks.get(idx));

        } catch (NumberFormatException e) {
            ui.showIndexNotNumber("mark");
        }
    }

    /**
     * Marks a task as not done.
     */
    private void handleUnmark(String userInput) {
        try {
            int idx = parser.parseIndex(userInput, "unmark ");

            if (!tasks.isValidIndex(idx)) {
                ui.showIndexOutOfRange("unmark", tasks.size());
                return;
            }

            tasks.markNotDone(idx);
            storage.save(tasks.getAll());
            ui.showTaskUnmarked(tasks.get(idx));

        } catch (NumberFormatException e) {
            ui.showIndexNotNumber("unmark");
        }
    }

    /**
     * Deletes a task from the list.
     */
    private void handleDelete(String userInput) {
        try {
            int idx = parser.parseIndex(userInput, "delete ");

            if (!tasks.isValidIndex(idx)) {
                ui.showIndexOutOfRange("delete", tasks.size());
                return;
            }

            Task removed = tasks.remove(idx);
            storage.save(tasks.getAll());
            ui.showTaskDeleted(removed, tasks.size());

        } catch (NumberFormatException e) {
            ui.showIndexNotNumber("delete");
        }
    }

    /**
     * Adds a todo task.
     */
    private void handleTodo(String userInput) {
        String desc = parser.parseTodoDescription(userInput);

        if (desc.isEmpty()) {
            ui.showEmptyTodoError();
            return;
        }

        Task t = new Todo(desc);
        tasks.add(t);
        storage.save(tasks.getAll());
        ui.showTaskAdded(t, tasks.size());
    }

    /**
     * Adds a deadline task.
     */
    private void handleDeadline(String userInput) {
        try {
            Parser.DeadlineData data = parser.parseDeadline(userInput);

            if (data == null) {
                ui.showDeadlineMissingBy();
                return;
            }

            Task t = new Deadline(data.description, data.by);
            tasks.add(t);
            storage.save(tasks.getAll());
            ui.showTaskAdded(t, tasks.size());

        } catch (DateTimeParseException e) {
            ui.showDeadlineDateFormatError();
        }
    }

    /**
     * Adds an event task.
     */
    private void handleEvent(String userInput) {
        Parser.EventData data = parser.parseEvent(userInput);

        if (data == null) {
            ui.showEventMissingFromTo();
            return;
        }

        Task t = new Event(data.description, data.from, data.to);
        tasks.add(t);
        storage.save(tasks.getAll());
        ui.showTaskAdded(t, tasks.size());
    }

    /**
     * Shows deadlines occurring on a specific date.
     */
    private void handleOn(String userInput) {
        try {
            LocalDate target = parser.parseOnDate(userInput);

            if (target == null) {
                ui.showOnMissingDate();
                return;
            }

            ui.showOnDateHeader(target);

            int count = 0;
            for (int i = 0; i < tasks.size(); i++) {
                Task t = tasks.get(i);
                if (t instanceof Deadline) {
                    Deadline d = (Deadline) t;
                    if (d.getBy().equals(target)) {
                        ui.showOnMatch(i + 1, d);
                        count++;
                    }
                }
            }

            if (count == 0) {
                ui.showNoTasksOnDate();
            }

        } catch (DateTimeParseException e) {
            ui.showOnDateFormatError();
        }
    }

    /**
     * Starts the chatbot application.
     */
    public static void main(String[] args) {
        new Avo(DEFAULT_FILE_PATH).run();
    }
}
