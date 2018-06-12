package calculator.errors;

/**
 * An exception thrown by the parser indicating that the string
 * it's parsing appears to be incomplete.
 *
 * You should ignore this file.
 */
public class IncompleteInputError extends ParseError {
    public IncompleteInputError() {
        super("Provided input is incomplete");
    }

    public IncompleteInputError(String message) {
        super(message);
    }

    public IncompleteInputError(String message, Throwable cause) {
        super(message, cause);
    }

    public IncompleteInputError(Throwable cause) {
        super(cause);
    }
}
