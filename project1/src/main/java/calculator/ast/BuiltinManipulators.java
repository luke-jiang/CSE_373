package calculator.ast;

import calculator.errors.EvaluationError;
import calculator.errors.QuitError;
import calculator.gui.ImageDrawer;
import calculator.interpreter.Environment;
import calculator.interpreter.Interpreter;
import datastructures.interfaces.IList;

public class BuiltinManipulators {
    /**
     * This function is responsible for handling the `quit()` operation node.
     *
     * It makes the calculator program end when invoked.
     */
    public static AstNode handleQuit(Environment env, AstNode wrapper) {
        // This exception is caught and handled within one of the calculator-related classes.
        throw new QuitError();
    }

    /**
     * This function is responsible for handling the `block(a, b, c, ..., z)` node.
     *
     * It invokes child a, then child b, then child c and so forth until all children
     * are evaluated.
     */
    public static AstNode handleBlock(Environment env, AstNode wrapper) {
        // We check to make sure the signature is ok -- this is strictly optional.
        assertSignatureOk("block", wrapper);

        // Next, we construct a dummy return value node.
        AstNode out = new AstNode(1);

        // We then ask the interpreter to evaluate each children, one by one.
        for (AstNode child : wrapper.getChildren()) {
            out = env.getInterpreter().evaluate(env, child);
        }

        // Then return value is same as last item in block,
        // or is equal to 1 if the block, for whatever reason, is empty
        return out;
    }

    /**
     *
     * @param env
     * @param wrapper
     * @return
     */
    public static AstNode handleAssign(Environment env, AstNode wrapper) {
        // Same thing: optional sanity check
        assertSignatureOk("assign", 2, wrapper);

        // Store the interpreter and children to help tidy up code
        Interpreter interp = env.getInterpreter();
        IList<AstNode> children = wrapper.getChildren();

        // Parse children
        AstNode var = children.get(0).getChildren().get(0);  // Unnest 'simplify'
        AstNode expr = interp.evaluate(env, children.get(1));

        // Some sanity checking
        if (!var.isVariable()) {
            throw new EvaluationError(String.format(
                    "LHS of assignment must be a variable. Encountered %s instead.",
                    var.isNumber() ? var.getNumericValue() : var.getName()));
        }

        // Record and return result
        env.getVariables().put(var.getName(), expr);
        return expr;
    }

    public static AstNode handleClear(Environment env, AstNode wrapper) {
        assertSignatureOk("clear", wrapper);

        ImageDrawer drawer = env.getImageDrawer();
        drawer.getGraphics().clearRect(0, 0, drawer.getWidth(), drawer.getHeight());

        return wrapper;
    }

    private static void assertSignatureOk(String name, int numChildren, AstNode node) {
        boolean ok = node.isOperation()
                && node.getName().equals(name)
                && node.getChildren().size() == numChildren;
        if (!ok) {
            String msg = String.format(
                    "Input ('%s' w/ %d) does not match expected ('%s' w/ %d)",
                    node.getName(),
                    node.getChildren().size(),
                    name,
                    numChildren);

            throw new EvaluationError(msg);
        }
    }

    private static void assertSignatureOk(String name, AstNode node) {
        boolean ok = node.isOperation() && node.getName().equals(name);
        if (!ok) {
            String msg = String.format(
                    "Input ('%s') does not match expected ('%s')",
                    node.getName(),
                    name);

            throw new EvaluationError(msg);
        }
    }
}

