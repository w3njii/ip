public class InvalidTaskFormatException extends StevenException {
    public InvalidTaskFormatException(String taskType) {
        super("\tPls use the correct format for " + taskType + " task");
    }
}
