package steven;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
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
    Parser parser = new Parser();

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
     * <p>This method prints a greeting, continuously reads user input and calls the getResponse(String input)
     * method with the given user input.</p>
     */
    public void run() {
        Scanner scanner = new Scanner(System.in);
        ui.printGreeting();
        ui.printHorizontalLine();

        while (scanner.hasNext()) {
            String input = scanner.nextLine();
            getResponse(input);
            ui.printHorizontalLine();
        }
        scanner.close();
    }

    /**
     * A helper for the run() method, it is continuously called with the user string input.
     *
     * <p>This method interprets commands using <code>Parser</code>, and performs the corresponding
     * actions such as adding tasks, marking tasks, deleting tasks, or exiting.
     * All changes to tasks are persisted via the <code>Storage</code> class.</p>
     *
     * @param input the input command in a form of a string to the chatbot
     * @return the response of the chatbot to the input
     */
    public String getResponse(String input) {
        PrintStream originalOut = System.out;

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        try {
            Command command = parser.parse(input.split(" ")[0]);

            switch (command) {
            case BYE:
                ui.printGoodbye();
                storage.saveTasks();
                break;

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

            case FIND:
                try {
                    tasks.findTasks(input);
                } catch (StevenException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case UNKNOWN:
                System.out.println("\t?????");
                break;
            }

        } finally {
            // Always restore System.out
            System.setOut(originalOut);
        }

        // Convert captured output to string and return
        return baos.toString();
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
