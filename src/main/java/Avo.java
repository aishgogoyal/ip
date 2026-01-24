import java.util.Scanner;
import java.util.ArrayList;

public class Avo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hey there! I'm Avo 🥑");
        System.out.println("What can I do for you?");

        while (true) {
            String userInput = sc.nextLine();

            if (userInput.equals("bye")) {
                System.out.println("Bye! Avo is going back to sleep 😴");
                break;
            }

            if (userInput.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                continue;
            }

            if (userInput.startsWith("mark ")) {
                int idx = Integer.parseInt(userInput.substring(5).trim()) - 1;
                tasks.get(idx).markDone();
                System.out.println("good job! I've marked this task as done:");
                System.out.println("  " + tasks.get(idx));
                continue;
            }

            if (userInput.startsWith("unmark ")) {
                int idx = Integer.parseInt(userInput.substring(7).trim()) - 1;
                tasks.get(idx).markNotDone();
                System.out.println("okie, I've marked this task as not done yet:");
                System.out.println("  " + tasks.get(idx));
                continue;
            }

            if (userInput.startsWith("todo ")) {
                String desc = userInput.substring(5).trim();
                Task t = new Todo(desc);
                tasks.add(t);
                System.out.println("got it. I've added this task:");
                System.out.println("  " + t);
                System.out.println("now you have " + tasks.size() + " tasks in the list. you got this!");
                continue;
            }

            if (userInput.startsWith("deadline ")) {
                String rest = userInput.substring(9).trim();
                String[] parts = rest.split(" /by ", 2);
                String desc = parts[0].trim();
                String by = parts[1].trim();

                Task t = new Deadline(desc, by);
                tasks.add(t);
                System.out.println("perfect! I've added this task:");
                System.out.println("  " + t);
                System.out.println("now you have " + tasks.size() + " tasks in the list.");
                continue;
            }

            if (userInput.startsWith("event ")) {
                String rest = userInput.substring(6).trim();
                String[] firstSplit = rest.split(" /from ", 2);
                String desc = firstSplit[0].trim();

                String[] secondSplit = firstSplit[1].split(" /to ", 2);
                String from = secondSplit[0].trim();
                String to = secondSplit[1].trim();

                Task t = new Event(desc, from, to);
                tasks.add(t);
                System.out.println("alright, I've added this task:");
                System.out.println("  " + t);
                System.out.println("now you have " + tasks.size() + " tasks in the list.");
                continue;
            }

            System.out.println("oops, sorry I don't understand that yet.");
        }
        sc.close();
    }
}


