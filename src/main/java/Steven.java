import java.util.Scanner;
import java.util.ArrayList;

public class Steven {

    public static void main(String[] args) {
        final String HORIZONTAL_LINE = "____________________________________________________________";
        final String GREETING = "Hello There! I'm Steven\nHow may I help you?";
        final String GOODBYE = "Bye.";

        ArrayList<String> toDoList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        System.out.println(GREETING);
        System.out.println(HORIZONTAL_LINE);

        while (true) {
            String input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                for (int i = 0; i < toDoList.size(); i++) {
                    System.out.println("\t" + i + 1 + ". " + toDoList.get(i));
                }
                continue;
            }
            toDoList.add(input);
            System.out.println("\t added: " + input);
        }

        scanner.close();
        System.out.println(HORIZONTAL_LINE);
        System.out.println(GOODBYE);
    }
}
