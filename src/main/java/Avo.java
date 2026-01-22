import java.util.Scanner;
import java.util.ArrayList;

public class Avo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();

        System.out.println("Hey there! I'm Avo 🥑");
        System.out.println("What can I do for you?");

        while (true) {
            String userInput = sc.nextLine();

            // EXIT
            if (userInput.equals("bye")) {
                System.out.println("Bye! Avo is going back to sleep 😴");
                break;
            }

            // LIST
            else if (userInput.equals("list")) {
                int index = 1;
                for (String task : tasks) {
                    System.out.println(index + ". " + task);
                    index++;
                }
            }

            // ADD
            else {
                tasks.add(userInput);
                System.out.println("added: " + userInput);
            }
        }

         sc.close();  
    }
}

