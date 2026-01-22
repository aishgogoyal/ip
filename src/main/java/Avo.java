import java.util.Scanner;

public class Avo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Hey there! I'm Avo 🥑");
        System.out.println("What can I do for you?");

        while(true){
             String userInput = sc.nextLine();
             if(userInput.equals("bye")){
                System.out.println("Bye! Avo is going back to sleep 😴");
                break;
            }
            System.out.println(userInput);

        }
        sc.close();

    }
}

