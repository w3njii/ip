package steven;

import java.util.Scanner;

/**
 * The main class of the Steven chatbot.
 *
 * <p>This class handles user interaction via the command line, manages the
 * task list, and coordinates saving and loading tasks from local storage.
 * It interprets user commands and delegates operations to the {@link TaskList}
 * and {@link Storage} classes.</p>
 */
public class Steven {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a new Steven chatbot instance.
     *
     * @param filePath the path to the file where tasks will be stored
     */
    public Steven(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.fetchTasks());
    }

    /**
     * Starts the command-line interface of the application.
     *
     * <p>This method prints a greeting, continuously reads user input,
     * interprets commands using <code>Parser</code>, and performs the corresponding
     * actions such as adding tasks, marking tasks, deleting tasks, or exiting.
     * All changes to tasks are persisted via the <code>Storage</code> class.</p>
     */
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

    /**
     * The entry point of the application.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        new Steven("data/tasklist.txt").run();
    }
}
