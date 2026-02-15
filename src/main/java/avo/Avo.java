package avo;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.IntConsumer;

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
    private boolean hasShownReminders = false;

    private String commandType;

    /**
     * Creates an Avo chatbot using the default file path.
     */
    public Avo() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Creates an Avo chatbot using the given storage file path.
     *
     * @param filePath Relative path to the task storage file.
     */
    public Avo(String filePath) {
        this.ui = new Ui();
        String actualPath = (filePath == null || filePath.trim().isEmpty())
                ? DEFAULT_FILE_PATH
                : filePath;
        this.storage = new Storage(actualPath);
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
     * Generates a response for the user's chat message (GUI calls this).
     *
     * @param input User input.
     * @return Avo's response as a string.
     */
    public String getResponse(String input) {
        String userInput = input.trim();
        CommandType command = parser.parseCommandType(userInput);

        return captureOutput(() -> {
        if (!hasShownReminders) {
            hasShownReminders = true;

            ArrayList<Deadline> reminders = tasks.getUpcomingDeadlines(2);
            if (!reminders.isEmpty()) {
                ui.showReminders(reminders, 2);
            }
        }

        executeCommand(command, userInput);
    });
}

    

    /**
     * Returns the last processed command type (GUI uses this).
     *
     * @return Command type string.
     */
    public String getCommandType() {
        return commandType;
    }

    private String captureOutput(Runnable action) {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream tempOut = new PrintStream(baos);
        System.setOut(tempOut);

        try {
            action.run();
        } finally {
            System.out.flush();
            System.setOut(originalOut);
        }

        return baos.toString().trim();
    }

    /**
     * Runs the chatbot until the user exits.
     */
    public void run() {
        ui.showWelcome();

        ArrayList<Deadline> reminders = tasks.getUpcomingDeadlines(2);
        if (!reminders.isEmpty()) {
        ui.showReminders(reminders, 2);
        }
        

        boolean isExit = false;
        while (!isExit) {
            String userInput = ui.readCommand();
            CommandType command = parser.parseCommandType(userInput);
            isExit = executeCommand(command, userInput);
        }

        ui.close();
    }

    /**
     * Executes a parsed command.
     *
     * @param command The command type.
     * @param userInput The original user input.
     * @return true if the command should exit the app, false otherwise.
     */
    private boolean executeCommand(CommandType command, String userInput) {
        switch (command) {
        case BYE:
            commandType = "Bye";
            ui.showBye();
            return true;

        case LIST:
            commandType = "List";
            ui.showTaskList(tasks.getAll());
            return false;

        case MARK:
            commandType = "Mark";
            handleMark(userInput);
            return false;

        case UNMARK:
            commandType = "Unmark";
            handleUnmark(userInput);
            return false;

        case DELETE:
            commandType = "Delete";
            handleDelete(userInput);
            return false;

        case TODO:
            commandType = "Todo";
            handleTodo(userInput);
            return false;

        case DEADLINE:
            commandType = "Deadline";
            handleDeadline(userInput);
            return false;

        case EVENT:
            commandType = "Event";
            handleEvent(userInput);
            return false;

        case ON:
            commandType = "On";
            handleOn(userInput);
            return false;

        case FIND:
            commandType = "Find";
            handleFind(userInput);
            return false;

        case UNKNOWN:
        default:
            commandType = "Unknown";
            ui.showUnknownCommand();
            return false;
        }
    }

    /**
     * Generic handler for commands that operate on an index without removing tasks
     * (e.g., mark/unmark).
     */
    private void handleIndexCommand(
            String userInput,
            String prefix,
            String actionName,
            IntConsumer action,
            Consumer<Task> successUiAction) {

        try {
            int idx = parser.parseIndex(userInput, prefix);

            if (!tasks.isValidIndex(idx)) {
                ui.showIndexOutOfRange(actionName, tasks.size());
                return;
            }

            action.accept(idx);
            storage.save(tasks.getAll());
            successUiAction.accept(tasks.get(idx));

        } catch (NumberFormatException e) {
            ui.showIndexNotNumber(actionName);
        }
    }

    /**
     * Marks a task as done.
     */
    private void handleMark(String userInput) {
        handleIndexCommand(
                userInput,
                "mark ",
                "mark",
                idx -> tasks.markDone(idx),
                task -> ui.showTaskMarked(task)
        );
    }

    /**
     * Marks a task as not done.
     */
    private void handleUnmark(String userInput) {
        handleIndexCommand(
                userInput,
                "unmark ",
                "unmark",
                idx -> tasks.markNotDone(idx),
                task -> ui.showTaskUnmarked(task)
        );
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
     * Finds and displays tasks that contain the given keyword.
     */
    private void handleFind(String userInput) {
        String keyword = parser.parseFindKeyword(userInput);

        if (keyword.isEmpty()) {
            ui.showUnknownCommand();
            return;
        }

        ArrayList<Task> results = tasks.find(keyword);

        if (results.isEmpty()) {
            ui.showNoFindResults();
        } else {
            ui.showFindResults(results);
        }
    }

    public static void main(String[] args) {
        new Avo(DEFAULT_FILE_PATH).run();
    }
}
