package calculator.errors;

/**
 * An error thrown by the parser when it encounters a syntax error of
 * any kind.
 *
 * You should ignore this file.
 */
public class ParseError extends EvaluationError {
    public ParseError(String message) {
        super(message);
    }

    public ParseError(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseError(Throwable cause) {
        super(cause);
    }
}
