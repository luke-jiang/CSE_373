package calculator.ast;

import calculator.interpreter.Environment;
import calculator.interpreter.Interpreter;
//import datastructures.concrete.DoubleLinkedList;
//import datastructures.interfaces.IList;

/**
 * Note: this file is meant for the extra credit portion of this assignment
 * focused around adding a programming language to our calculator.
 *
 * If you choose to work on this extra credit, feel free to add additional
 * control flow handlers beyond the two listed here. Be sure to register
 * each new function inside the 'Calculator' class -- see line 59.
 */
public class ControlFlowManipulators {
    /**
     * Handles AST nodes corresponding to "randomlyPick(body1, body2)"
     *
     * Preconditions:
     *
     * - Receives an operation node with the name "randomlyPick" and two (arbitrary) children
     *
     * Postcondition:
     *
     * - This method will randomly decide to evaluate and return the result of either body1 or
     *   body2 with 50% probability. If body1 is interpreted, body2 is ignored completely and vice versa.
     */
    public static AstNode handleRandomlyPick(Environment env, AstNode wrapper) {
        AstNode body1 = wrapper.getChildren().get(0);
        AstNode body2 = wrapper.getChildren().get(1);

        Interpreter interp = env.getInterpreter();
        if (Math.random() < 0.5) {
            // Note: when implementing this method, we do NOT want to
            // manually recurse down either child: we instead want the calculator
            // to take back full control and evaluate whatever the body1 or body2
            // AST nodes might be. To do so, we use the 'Interpreter' object
            // available to us within the environment.
            return interp.evaluate(env, body1);
        } else {
            return interp.evaluate(env, body2);
        }
    }

    /**
     * Handles AST nodes corresponding to "if(cond, body, else)"
     *
     * Preconditions:
     *
     * - Receives an operation node with the name "if" and three children
     *
     * Postcondition:
     *
     * - If 'cond' evaluates to any non-zero number, interpret the "body" AST node and ignore the
     *   "else" node completely.
     * - Otherwise, evaluate the "else" node.
     * - In either case, return the result of whatever AST node you ended up interpreting.
     */
    public static AstNode handleIf(Environment env, AstNode wrapper) {
        Interpreter interp = env.getInterpreter();
        AstNode cond = wrapper.getChildren().get(0);
        AstNode condResult = interp.evaluate(env, cond);
        if (condResult.isNumber() && condResult.getNumericValue() != 0) {
            return interp.evaluate(env, wrapper.getChildren().get(1));
        } else {
            return interp.evaluate(env, wrapper.getChildren().get(2));
        }
    }

    /**
     * Handles AST nodes corresponding to "repeat(times, body)"
     *
     * Preconditions:
     *
     * - Receives an operation node with the name "repeat" and two children
     * - The 'times' AST node is assumed to be some arbitrary AST node that,
     *   when interpreted, will also produce an integer result.
     *
     * Postcondition:
     *
     * - Repeatedly evaluates the given body the specified number of times.
     * - Returns the result of interpreting 'body' for the final time.
     */
    public static AstNode handleRepeat(Environment env, AstNode wrapper) {
        Interpreter interp = env.getInterpreter();
        AstNode amount = wrapper.getChildren().get(0);
        int iteration = (int) interp.evaluate(env, amount).getNumericValue();
        AstNode body = wrapper.getChildren().get(1);
        AstNode out = new AstNode(1);
        while (iteration > 0) {
            out = env.getInterpreter().evaluate(env, body);
            iteration--;
        }
        return out;
    }
    /*
    public static AstNode handleWhile(Environment env, AstNode wrapper) {
        Interpreter interp = env.getInterpreter();
        AstNode condition = wrapper.getChildren().get(0);
        AstNode body = wrapper.getChildren().get(1);
        int conditionValue = (int) interp.evaluate(env, condition).getNumericValue();
        if (conditionValue == 0) {
            return new AstNode(0);
        } else {
            return handleWhileHelper(env, condition, body);
        }
    }
    

    private static AstNode handleWhileHelper(Environment env, AstNode condition, AstNode body) {
        Interpreter interp = env.getInterpreter();
        AstNode answer = interp.evaluate(env, body);
        
    }
    */
}
