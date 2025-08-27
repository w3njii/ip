import java.util.Scanner;

public class Steven {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Steven(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.fetchTasks());
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.printGreeting();
        ui.printHorizontalLine();

        Parser parser = new Parser();

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
                tasks.printToDoList();
                break;

            case TODO:
                try {
                    tasks.addToDoTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case DEADLINE:
                try {
                    tasks.addDeadlineTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case EVENT:
                try {
                    tasks.addEventTask(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case MARK:
                try {
                    tasks.markTask(input);
                } catch (InvalidMarkFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case UNMARK:
                try {
                    tasks.unmarkTask(input);
                } catch (InvalidMarkFormatException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case DELETE:
                tasks.deleteTask(input);
                break;

            case UNKNOWN:
                System.out.println("\t?????");
                break;
            }
            ui.printHorizontalLine();
        }
    }
    public static void main(String[] args) {
        new Steven("data/tasklist.txt").run();
    }
}
