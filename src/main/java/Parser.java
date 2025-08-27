public class Parser {

    public Command parse(String input) {
        return Command.convert(input.split(" ")[0]);
    }
}
