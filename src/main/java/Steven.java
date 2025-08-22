import java.util.Scanner;

public class Steven {
    public static void main(String[] args) {
        final String HORIZONTAL_LINE = "____________________________________________________________";
        final String GREETING = "Hello There! I'm Steven\nHow may I help you?";
        final String GOODBYE = "Bye.";

        System.out.println(GREETING);
        System.out.println(HORIZONTAL_LINE);
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println("\t" + input);
            input = scanner.nextLine();
        }

        System.out.println(HORIZONTAL_LINE);
        System.out.println(GOODBYE);
    }
}
