package calculator.errors;

/**
 * Represents any kind of error that may be triggered while evaluating
 * an AstNode in any way.
 */
public class EvaluationError extends RuntimeException {
    public EvaluationError(String message) {
        super(message);
    }

    public EvaluationError(String message, Throwable cause) {
        super(message, cause);
    }

    public EvaluationError(Throwable cause) {
        super(cause);
    }
}
