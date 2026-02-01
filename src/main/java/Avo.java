import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Avo {

    private static final String DEFAULT_FILE_PATH = "data/avo.txt";
    
    private static final int MARK_PREFIX_LEN = "mark ".length();
    private static final int UNMARK_PREFIX_LEN = "unmark ".length();
    private static final int DELETE_PREFIX_LEN = "delete ".length();
    private static final int TODO_PREFIX_LEN = "todo".length();
    private static final int DEADLINE_PREFIX_LEN = "deadline ".length();
    private static final int EVENT_PREFIX_LEN = "event ".length();
    private static final int ON_PREFIX_LEN = "on".length();

    private final Ui ui;
    private final Storage storage;
    private final ArrayList<Task> tasks;

    public Avo(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);

        ArrayList<Task> loaded;
        try {
            loaded = storage.load();
        } catch (RuntimeException e) {
            ui.showLoadingError();
            loaded = new ArrayList<>();
        }
        this.tasks = loaded;
    }

    private static CommandType getCommandType(String userInput) {
        if (userInput.equals("bye")) {
            return CommandType.BYE;
        }
        if (userInput.equals("list")) {
            return CommandType.LIST;
        }
        if (userInput.equals("on") || userInput.startsWith("on ")) {
            return CommandType.ON;
        }
        if (userInput.startsWith("mark ")) {
            return CommandType.MARK;
        }
        if (userInput.startsWith("unmark ")) {
            return CommandType.UNMARK;
        }
        if (userInput.startsWith("delete ")) {
            return CommandType.DELETE;
        }
        if (userInput.startsWith("todo")) {
            return CommandType.TODO;
        }
        if (userInput.startsWith("deadline ")) {
            return CommandType.DEADLINE;
        }
        if (userInput.startsWith("event ")) {
            return CommandType.EVENT;
        }
        return CommandType.UNKNOWN;
    }

    public void run() {
        ui.showWelcome();

        boolean isExit = false;
        while (!isExit) {
            String userInput = ui.readCommand();
            CommandType command = getCommandType(userInput);

            switch (command) {
            case BYE:
                ui.showBye();
                isExit = true;
                break;

            case LIST:
                ui.showTaskList(tasks);
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

            case UNKNOWN:
            default:
                ui.showUnknownCommand();
                break;
            }
        }

        ui.close();
    }

    private void handleMark(String userInput) {
        try {
            int idx = Integer.parseInt(userInput.substring(MARK_PREFIX_LEN).trim()) - 1;
            if (idx < 0 || idx >= tasks.size()) {
                ui.showIndexOutOfRange("mark", tasks.size());
                return;
            }
            tasks.get(idx).markDone();
            storage.save(tasks);
            ui.showTaskMarked(tasks.get(idx));
        } catch (NumberFormatException e) {
            ui.showIndexNotNumber("mark");
        }
    }

    private void handleUnmark(String userInput) {
        try {
            int idx = Integer.parseInt(userInput.substring(UNMARK_PREFIX_LEN).trim()) - 1;
            if (idx < 0 || idx >= tasks.size()) {
                ui.showIndexOutOfRange("unmark", tasks.size());
                return;
            }
            tasks.get(idx).markNotDone();
            storage.save(tasks);
            ui.showTaskUnmarked(tasks.get(idx));
        } catch (NumberFormatException e) {
            ui.showIndexNotNumber("unmark");
        }
    }

    private void handleDelete(String userInput) {
        try {
            int idx = Integer.parseInt(userInput.substring(DELETE_PREFIX_LEN).trim()) - 1;
            if (idx < 0 || idx >= tasks.size()) {
                ui.showIndexOutOfRange("delete", tasks.size());
                return;
            }
            Task removed = tasks.remove(idx);
            storage.save(tasks);
            ui.showTaskDeleted(removed, tasks.size());
        } catch (NumberFormatException e) {
            ui.showIndexNotNumber("delete");
        }
    }

    private void handleTodo(String userInput) {
        String desc = userInput.length() > TODO_PREFIX_LEN
                ? userInput.substring(TODO_PREFIX_LEN).trim()
                : "";

        if (desc.isEmpty()) {
            ui.showEmptyTodoError();
            return;
        }

        Task t = new Todo(desc);
        tasks.add(t);
        storage.save(tasks);
        ui.showTaskAdded(t, tasks.size());
    }

    private void handleDeadline(String userInput) {
        String rest = userInput.substring(DEADLINE_PREFIX_LEN).trim();
        String[] parts = rest.split(" /by ", 2);

        if (parts.length < 2) {
            ui.showDeadlineMissingBy();
            return;
        }

        String desc = parts[0].trim();
        String byStr = parts[1].trim();

        if (desc.isEmpty()) {
            ui.showDeadlineEmptyDescription();
            return;
        }

        try {
            LocalDate by = LocalDate.parse(byStr);
            Task t = new Deadline(desc, by);
            tasks.add(t);
            storage.save(tasks);
            ui.showTaskAdded(t, tasks.size());
        } catch (DateTimeParseException e) {
            ui.showDeadlineDateFormatError();
        }
    }

    private void handleEvent(String userInput) {
        String rest = userInput.substring(EVENT_PREFIX_LEN).trim();
        String[] first = rest.split(" /from ", 2);

        if (first.length < 2) {
            ui.showEventMissingFromTo();
            return;
        }

        String desc = first[0].trim();
        String[] second = first[1].split(" /to ", 2);

        if (second.length < 2) {
            ui.showEventMissingFromTo();
            return;
        }

        String from = second[0].trim();
        String to = second[1].trim();

        if (desc.isEmpty()) {
            ui.showEventEmptyDescription();
            return;
        }
        if (from.isEmpty() || to.isEmpty()) {
            ui.showEventEmptyTimes();
            return;
        }

        Task t = new Event(desc, from, to);
        tasks.add(t);
        storage.save(tasks);
        ui.showTaskAdded(t, tasks.size());
    }

    private void handleOn(String userInput) {
        String dateStr = userInput.length() > ON_PREFIX_LEN
                ? userInput.substring(ON_PREFIX_LEN).trim()
                : "";

        if (dateStr.isEmpty()) {
            ui.showOnMissingDate();
            return;
        }

        try {
            LocalDate target = LocalDate.parse(dateStr);
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

    public static void main(String[] args) {
        new Avo(DEFAULT_FILE_PATH).run();
    }
}


