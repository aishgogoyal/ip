import java.util.Scanner;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;

public class Avo {

    private static CommandType getCommandType(String userInput) {
        if (userInput.equals("bye")) {
            return CommandType.BYE;
        }
        if (userInput.equals("list")) {
            return CommandType.LIST;
        }
        if (userInput.startsWith("mark ")) {
            return CommandType.MARK;
        }
        if (userInput.startsWith("unmark ")) {
            return CommandType.UNMARK;
        }
        if (userInput.startsWith("todo")) {
            return CommandType.TODO;
        }
        if (userInput.startsWith("delete ")) {
            return CommandType.DELETE;
        }
        if (userInput.startsWith("deadline ")) {
            return CommandType.DEADLINE;
        }
        if (userInput.startsWith("event ")) {
            return CommandType.EVENT;
        }
        return CommandType.UNKNOWN;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Level 7: load tasks from disk at startup
        Storage storage = new Storage("data/avo.txt");
        ArrayList<Task> tasks = storage.load();

        System.out.println("Hey there! I'm Avo 🥑");
        System.out.println("How can I help you today?");

        while (true) {
            if (!sc.hasNextLine()) {
                break;
            }

            String userInput = sc.nextLine().trim();
            CommandType command = getCommandType(userInput);

            switch (command) {
                case BYE:
                    System.out.println("Bye! Avo is going back to sleep 😴");
                    sc.close();
                    return;

                case LIST:
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + "." + tasks.get(i));
                    }
                    break;

                case MARK:
                    try {
                        int idx = Integer.parseInt(userInput.substring(5).trim()) - 1;

                        if (idx < 0 || idx >= tasks.size()) {
                            System.out.println("❗ That task number does not exist.");
                            System.out.println("👉 Use: mark <task number> (between 1 and " + tasks.size() + ")");
                            break;
                        }

                        tasks.get(idx).markDone();
                        storage.save(tasks); // Level 7: save after list changes

                        System.out.println("🌟 Task marked as done!");
                        System.out.println("  " + tasks.get(idx));
                    } catch (NumberFormatException e) {
                        System.out.println("❗ The task number must be a number.");
                        System.out.println("👉 Format: mark <task number>");
                    } catch (Exception e) {
                        System.out.println("❗ Unable to mark task.");
                        System.out.println("👉 Format: mark <task number>");
                    }
                    break;

                case UNMARK:
                    try {
                        int idx = Integer.parseInt(userInput.substring(7).trim()) - 1;

                        if (idx < 0 || idx >= tasks.size()) {
                            System.out.println("❗ That task number does not exist.");
                            System.out.println("👉 Use: unmark <task number> (between 1 and " + tasks.size() + ")");
                            break;
                        }

                        tasks.get(idx).markNotDone();
                        storage.save(tasks); // Level 7: save after list changes

                        System.out.println("👍 Task marked as not done!");
                        System.out.println("  " + tasks.get(idx));
                    } catch (NumberFormatException e) {
                        System.out.println("❗ The task number must be a number.");
                        System.out.println("👉 Format: unmark <task number>");
                    } catch (Exception e) {
                        System.out.println("❗ Unable to unmark task.");
                        System.out.println("👉 Format: unmark <task number>");
                    }
                    break;

                case TODO: {
                    String desc = "";
                    if (userInput.length() > 4) {
                        desc = userInput.substring(4).trim();
                    }

                    if (desc.isEmpty()) {
                        System.out.println("❗ A todo must have a description.");
                        System.out.println("👉 Format: todo <task description>");
                        break;
                    }

                    Task t = new Todo(desc);
                    tasks.add(t);
                    storage.save(tasks); // Level 7: save after list changes

                    System.out.println("✅ Task added!");
                    System.out.println("  " + t);
                    System.out.println("📌 Now you have " + tasks.size() + " tasks in the list!");
                    break;
                }

                case DELETE:
                    try {
                        int idx = Integer.parseInt(userInput.substring(7).trim()) - 1;

                        if (idx < 0 || idx >= tasks.size()) {
                            System.out.println("❗ That task number does not exist.");
                            System.out.println("👉 Use: delete <task number> (between 1 and " + tasks.size() + ")");
                            break;
                        }

                        Task removed = tasks.remove(idx);
                        storage.save(tasks); // Level 7: save after list changes

                        System.out.println("🗑️ Noted. I've removed this task:");
                        System.out.println("  " + removed);
                        System.out.println("📌 Now you have " + tasks.size() + " tasks in the list.");
                    } catch (NumberFormatException e) {
                        System.out.println("❗ The task number must be a number.");
                        System.out.println("👉 Format: delete <task number>");
                    } catch (Exception e) {
                        System.out.println("❗ Unable to delete task.");
                        System.out.println("👉 Format: delete <task number>");
                    }
                    break;

                case DEADLINE:
                    try {
                        String rest = userInput.substring(9).trim();
                        String[] parts = rest.split(" /by ", 2);

                        if (parts.length < 2) {
                            System.out.println("❗ A deadline must include a time.");
                            System.out.println("👉 Format: deadline <task description> /by <time>");
                            break;
                        }

                        String desc = parts[0].trim();
                        LocalDate by = LocalDate.parse(parts[1].trim());

                        if (desc.isEmpty()) {
                            System.out.println("❗ The task description cannot be empty.");
                            System.out.println("👉 Format: deadline <task description> /by <time>");
                            break;
                        }

                        if (by.isEmpty()) {
                            System.out.println("❗ The deadline time cannot be empty.");
                            System.out.println("👉 Format: deadline <task description> /by <time>");
                            break;
                        }

                        Task t = new Deadline(desc, by);
                        tasks.add(t);
                        storage.save(tasks); // Level 7: save after list changes

                        System.out.println("⏰ Deadline added!");
                        System.out.println("  " + t);
                        System.out.println("📌 Now you have " + tasks.size() + " tasks in the list!");
                    } catch (Exception e) {
                        System.out.println("❗ Unable to create deadline.");
                        System.out.println("👉 Format: deadline <task description> /by <time>");
                    }
                    break;

                case EVENT:
                    try {
                        String rest = userInput.substring(6).trim();
                        String[] firstSplit = rest.split(" /from ", 2);

                        if (firstSplit.length < 2) {
                            System.out.println("❗ An event must include a start time.");
                            System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                            break;
                        }

                        String desc = firstSplit[0].trim();
                        String[] secondSplit = firstSplit[1].split(" /to ", 2);

                        if (secondSplit.length < 2) {
                            System.out.println("❗ An event must include an end time.");
                            System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                            break;
                        }

                        String from = secondSplit[0].trim();
                        String to = secondSplit[1].trim();

                        if (desc.isEmpty()) {
                            System.out.println("❗ The event description cannot be empty.");
                            System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                            break;
                        }

                        if (from.isEmpty() || to.isEmpty()) {
                            System.out.println("❗ Event start and end times cannot be empty.");
                            System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                            break;
                        }

                        Task t = new Event(desc, from, to);
                        tasks.add(t);
                        storage.save(tasks); // Level 7: save after list changes

                        System.out.println("🎉 Event added!");
                        System.out.println("  " + t);
                        System.out.println("📌 Now you have " + tasks.size() + " tasks in the list!");
                    } catch (Exception e) {
                        System.out.println("❗ Unable to create event.");
                        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                    }
                    break;

                case UNKNOWN:
                default:
                    System.out.println("🤔 I don't understand that command.");
                    System.out.println("👉 Available commands:");
                    System.out.println("   todo <task description>");
                    System.out.println("   deadline <task description> /by <time>");
                    System.out.println("   event <task description> /from <start> /to <end>");
                    System.out.println("   list");
                    System.out.println("   mark <task number>");
                    System.out.println("   unmark <task number>");
                    System.out.println("   delete <task number>");
                    System.out.println("   bye");
                    break;
            }
        }

        sc.close();
    }
}

