package avo.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;

import avo.task.Deadline;
import avo.task.Event;
import avo.task.Task;
import avo.task.Todo;

/**
 * Handles loading tasks from and saving tasks to a file.
 */
public class Storage {

    private final Path filePath;

    /**
     * Creates a storage handler using the given relative file path.
     */
    public Storage(String relativePath) {
        this.filePath = Paths.get(relativePath);
    }

    /**
     * Loads tasks from the file.
     * Returns an empty list if the file does not exist or cannot be read.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();

        if (!Files.exists(filePath)) {
            return tasks; // first run: file doesn't exist yet
        }

        try (BufferedReader br = Files.newBufferedReader(filePath)) {
            String line;
            while ((line = br.readLine()) != null) {
                Task t = parseLine(line);
                if (t != null) {
                    tasks.add(t);
                }
            }
        } catch (IOException e) {
            // start empty if read fails
        }

        return tasks;
    }

    /**
     * Saves all tasks to the file.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            Path parent = filePath.getParent();
            if (parent != null && !Files.exists(parent)) {
                Files.createDirectories(parent); // creates ./data if missing
            }

            try (BufferedWriter bw = Files.newBufferedWriter(filePath)) {
                for (Task t : tasks) {
                    String encoded = encodeTask(t);
                    if (!encoded.isEmpty()) {
                        bw.write(encoded);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            // ignore write errors
        }
    }

    /**
     * Converts a line of text into a Task object.
     * Returns null if the line format is invalid.
     */
    private Task parseLine(String line) {
        // T|1|desc
        // D|0|desc|by
        // E|0|desc|from|to
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) {
            return null;
        }

        String type = parts[0].trim();
        String done = parts[1].trim();
        String desc = parts[2].trim();

        Task t;
        try {
            if (type.equals("T")) {
                t = new Todo(desc);

            } else if (type.equals("D")) {
                if (parts.length < 4) {
                    return null;
                }
                LocalDate by = LocalDate.parse(parts[3].trim());
                t = new Deadline(desc, by);

            } else if (type.equals("E")) {
                if (parts.length < 5) {
                    return null;
                }
                t = new Event(desc, parts[3].trim(), parts[4].trim());

            } else {
                return null;
            }

            if (done.equals("1")) {
                t.markDone();
            }

            return t;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Converts a Task into a text format for storage.
     */
    private String encodeTask(Task t) {
        String done = t.isDone() ? "1" : "0";

        if (t instanceof Todo) {
            return "T|" + done + "|" + t.getDescription();
        }

        if (t instanceof Deadline) {
            Deadline d = (Deadline) t;
            return "D|" + done + "|" + d.getDescription() + "|" + d.getBy();
        }

        if (t instanceof Event) {
            Event e = (Event) t;
            return "E|" + done + "|" + e.getDescription() + "|" + e.getFrom() + "|" + e.getTo();
        }

        return "";
    }
}
