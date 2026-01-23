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
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println(tasks.get(i).toListString(i + 1));
                }
                continue;
            }

            if (userInput.startsWith("mark ")) {
                int num = Integer.parseInt(userInput.substring(5).trim());
                int index = num - 1;

                tasks.get(index).markDone();
                System.out.println("Great job! I've marked this task as done:");
                System.out.println(tasks.get(index).toStatusString());
                continue;
            }

            if (userInput.startsWith("unmark ")) {
                int num = Integer.parseInt(userInput.substring(7).trim());
                int index = num - 1;

                tasks.get(index).markNotDone();
                System.out.println("Okie, I've marked this task as not done yet:");
                System.out.println(tasks.get(index).toStatusString());
                continue;
            }

            tasks.add(new Task(userInput));
            System.out.println("added: " + userInput);
        }
         sc.close();
    }
}

