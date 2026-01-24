import java.util.Scanner;
import java.util.ArrayList;

public class Avo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hey there! I'm Avo 🥑");
        System.out.println("How can I help you today?");

        while (true) {
            if (!sc.hasNextLine()) {
                break;
            }

            String userInput = sc.nextLine().trim();

            // bye
            if (userInput.equals("bye")) {
                System.out.println("Bye! Avo is going back to sleep 😴");
                break;
            }

            // list
            if (userInput.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                continue;
            }

            // mark
            if (userInput.startsWith("mark ")) {
                try {
                    int idx = Integer.parseInt(userInput.substring(5).trim()) - 1;

                    if (idx < 0 || idx >= tasks.size()) {
                        System.out.println("❗ That task number does not exist.");
                        System.out.println("👉 Use: mark <task number> (between 1 and " + tasks.size() + ")");
                        continue;
                    }

                    tasks.get(idx).markDone();
                    System.out.println("🌟 Task marked as done!");
                    System.out.println("  " + tasks.get(idx));
                } catch (NumberFormatException e) {
                    System.out.println("❗ The task number must be a number.");
                    System.out.println("👉 Format: mark <task number>");
                } catch (Exception e) {
                    System.out.println("❗ Unable to mark task.");
                    System.out.println("👉 Format: mark <task number>");
                }
                continue;
            }

            // unmark
            if (userInput.startsWith("unmark ")) {
                try {
                    int idx = Integer.parseInt(userInput.substring(7).trim()) - 1;

                    if (idx < 0 || idx >= tasks.size()) {
                        System.out.println("❗ That task number does not exist.");
                        System.out.println("👉 Use: unmark <task number> (between 1 and " + tasks.size() + ")");
                        continue;
                    }

                    tasks.get(idx).markNotDone();
                    System.out.println("👍 Task marked as not done!");
                    System.out.println("  " + tasks.get(idx));
                } catch (NumberFormatException e) {
                    System.out.println("❗ The task number must be a number.");
                    System.out.println("👉 Format: unmark <task number>");
                } catch (Exception e) {
                    System.out.println("❗ Unable to unmark task.");
                    System.out.println("👉 Format: unmark <task number>");
                }
                continue;
            }

            // todo
            if (userInput.startsWith("todo")) {
                String desc = "";
                if (userInput.length() > 4) {
                    desc = userInput.substring(4).trim();
                }

                if (desc.isEmpty()) {
                    System.out.println("❗ A todo must have a description.");
                    System.out.println("👉 Format: todo <task description>");
                    continue;
                }

                Task t = new Todo(desc);
                tasks.add(t);
                System.out.println("✅ Task added!");
                System.out.println("  " + t);
                System.out.println("📌 Now you have " + tasks.size() + " tasks in the list!");
                continue;
            }

            // deadline
            if (userInput.startsWith("deadline ")) {
                try {
                    String rest = userInput.substring(9).trim();
                    String[] parts = rest.split(" /by ", 2);

                    if (parts.length < 2) {
                        System.out.println("❗ A deadline must include a time.");
                        System.out.println("👉 Format: deadline <task description> /by <time>");
                        continue;
                    }

                    String desc = parts[0].trim();
                    String by = parts[1].trim();

                    if (desc.isEmpty()) {
                        System.out.println("❗ The task description cannot be empty.");
                        System.out.println("👉 Format: deadline <task description> /by <time>");
                        continue;
                    }

                    if (by.isEmpty()) {
                        System.out.println("❗ The deadline time cannot be empty.");
                        System.out.println("👉 Format: deadline <task description> /by <time>");
                        continue;
                    }

                    Task t = new Deadline(desc, by);
                    tasks.add(t);
                    System.out.println("⏰ Deadline added!");
                    System.out.println("  " + t);
                    System.out.println("📌 Now you have " + tasks.size() + " tasks in the list!");
                } catch (Exception e) {
                    System.out.println("❗ Unable to create deadline.");
                    System.out.println("👉 Format: deadline <task description> /by <time>");
                }
                continue;
            }

            // event
            if (userInput.startsWith("event ")) {
                try {
                    String rest = userInput.substring(6).trim();
                    String[] firstSplit = rest.split(" /from ", 2);

                    if (firstSplit.length < 2) {
                        System.out.println("❗ An event must include a start time.");
                        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                        continue;
                    }

                    String desc = firstSplit[0].trim();

                    String[] secondSplit = firstSplit[1].split(" /to ", 2);

                    if (secondSplit.length < 2) {
                        System.out.println("❗ An event must include an end time.");
                        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                        continue;
                    }

                    String from = secondSplit[0].trim();
                    String to = secondSplit[1].trim();

                    if (desc.isEmpty()) {
                        System.out.println("❗ The event description cannot be empty.");
                        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                        continue;
                    }

                    if (from.isEmpty() || to.isEmpty()) {
                        System.out.println("❗ Event start and end times cannot be empty.");
                        System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                        continue;
                    }

                    Task t = new Event(desc, from, to);
                    tasks.add(t);
                    System.out.println("🎉 Event added!");
                    System.out.println("  " + t);
                    System.out.println("📌 Now you have " + tasks.size() + " tasks in the list!");
                } catch (Exception e) {
                    System.out.println("❗ Unable to create event.");
                    System.out.println("👉 Format: event <task description> /from <start> /to <end>");
                }
                continue;
            }

            // unknown command
            System.out.println("🤔 I don't understand that command.");
            System.out.println("👉 Available commands:");
            System.out.println("   todo <task description>");
            System.out.println("   deadline <task description> /by <time>");
            System.out.println("   event <task description> /from <start> /to <end>");
            System.out.println("   list");
            System.out.println("   mark <task number>");
            System.out.println("   unmark <task number>");
            System.out.println("   bye");
        }

        sc.close();
    }
}



