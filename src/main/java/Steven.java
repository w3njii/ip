import java.util.Scanner;

public class Steven {
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.printGreeting();
        ui.printHorizontalLine();

        Parser parser = new Parser();
        Storage storage = new Storage("data/tasklist.txt");
        TaskList taskList = new TaskList(storage.fetchTasks());

        while (true) {
            String input = scanner.nextLine();
            Command command = parser.parse(input);

            switch (command) {
            case BYE:
                scanner.close();
                ui.printGoodbye();
                storage.saveTasks();
                return;

            case LIST:
                taskList.printToDoList();
                break;

            case TODO:
                try {
                    taskList.addToDoTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case DEADLINE:
                try {
                    taskList.addDeadlineTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case EVENT:
                try {
                    taskList.addEventTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case MARK:
                try {
                    taskList.markTask(input);
                } catch (InvalidMarkFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case UNMARK:
                try {
                    taskList.unmarkTask(input);
                } catch (InvalidMarkFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case DELETE:
                taskList.deleteTask(input);
                break;

            case UNKNOWN:
                System.out.println("\t?????");
                break;
            }
            ui.printHorizontalLine();
        }
    }
}
