package calculator.ast;

import calculator.interpreter.Environment;

/**
 * An interface representing any kind of function that might
 * "manipulate" or "modify" an AstNode.
 *
 * You can ignore this file.
 */
public interface AstManipulator {
    AstNode manipulate(Environment env, AstNode node);
}
