package calculator.ast;

import calculator.interpreter.Environment;
import calculator.errors.EvaluationError;
import calculator.gui.ImageDrawer;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;
//import misc.exceptions.NotYetImplementedException;

/**
 * All of the public static methods in this class are given the exact same parameters for
 * consistency. You can often ignore some of these parameters when implementing your
 * methods.
 *
 * Some of these methods should be recursive. You may want to consider using public-private
 * pairs in some cases.
 */
public class ExpressionManipulators {
    /**
     * Checks to make sure that the given node is an operation AstNode with the expected
     * name and number of children. Throws an EvaluationError otherwise.
     */
    private static void assertNodeMatches(AstNode node, String expectedName, int expectedNumChildren) {
        if (!node.isOperation()
                && !node.getName().equals(expectedName)
                && node.getChildren().size() != expectedNumChildren) {
            throw new EvaluationError("Node is not valid " + expectedName + " node.");
        }
    }

    /**
     * Accepts an 'toDouble(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'toDouble'.
     * - The 'node' parameter has exactly one child: the AstNode to convert into a double.
     *
     * Postconditions:
     *
     * - Returns a number AstNode containing the computed double.
     *
     * For example, if this method receives the AstNode corresponding to
     * 'toDouble(3 + 4)', this method should return the AstNode corresponding
     * to '7'.
     * 
     * This method is required to handle the following binary operations
     *      +, -, *, /, ^
     *  (addition, subtraction, multiplication, division, and exponentiation, respectively) 
     * and the following unary operations
     *      negate, sin, cos
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if any of the expressions uses an unknown operation.
     */
    public static AstNode handleToDouble(Environment env, AstNode node) {
        // To help you get started, we've implemented this method for you.
        // You should fill in the locations specified by "your code here"
        // in the 'toDoubleHelper' method.
        //
        // If you're not sure why we have a public method calling a private
        // recursive helper method, review your notes from CSE 143 (or the
        // equivalent class you took) about the 'public-private pair' pattern.

        assertNodeMatches(node, "toDouble", 1);
        AstNode exprToConvert = node.getChildren().get(0);
        return new AstNode(toDoubleHelper(env.getVariables(), exprToConvert));
    }

    private static double toDoubleHelper(IDictionary<String, AstNode> variables, AstNode node) {
        // There are three types of nodes, so we have three cases. 
        if (node.isNumber()) {
            return node.getNumericValue();
        } else if (node.isVariable()) {
            String nodeName = node.getName();
            if (!variables.containsKey(nodeName)) {
                throw new EvaluationError("Variable " + nodeName + " is undefined");
            }
            AstNode expreToConvert = variables.get(nodeName);
            return toDoubleHelper(variables, expreToConvert);
        } else {
            // You may assume the expression node has the correct number of children.
            // If you wish to make your code more robust, you can also use the provided
            // "assertNodeMatches" method to verify the input is valid.
            String name = node.getName();
            AstNode e1 = node.getChildren().get(0);
            AstNode e2 = null;
            if (node.getChildren().size() == 2) {
                e2 = node.getChildren().get(1);
            }
            if (name.equals("sin")) {
                return Math.sin(toDoubleHelper(variables, e1));
            } else if (name.equals("cos")) {
                return Math.cos(toDoubleHelper(variables, e1));
            } else if (name.equals("negate")) {
                return -toDoubleHelper(variables, e1);
            } else if (name.equals("+")) {
                return toDoubleHelper(variables, e1) + toDoubleHelper(variables, e2);
            } else if (name.equals("-")) {
                return toDoubleHelper(variables, e1) - toDoubleHelper(variables, e2);
            } else if (name.equals("*")) {
                return toDoubleHelper(variables, e1) * toDoubleHelper(variables, e2);
            } else if (name.equals("/")) {
                return toDoubleHelper(variables, e1) / toDoubleHelper(variables, e2);
            } else if (name.equals("^")) {
                return Math.pow(toDoubleHelper(variables, e1), toDoubleHelper(variables, e2));
            } else {
                throw new EvaluationError("Operator " + name + " is undefined");
            }
        }
    }

    /**
     * Accepts a 'simplify(inner)' AstNode and returns a new node containing the simplified version
     * of the 'inner' AstNode.
     *
     * Preconditions:
     *
     * - The 'node' parameter is an operation AstNode with the name 'simplify'.
     * - The 'node' parameter has exactly one child: the AstNode to simplify
     *
     * Postconditions:
     *
     * - Returns an AstNode containing the simplified inner parameter.
     *
     * For example, if we received the AstNode corresponding to the expression
     * "simplify(3 + 4)", you would return the AstNode corresponding to the
     * number "7".
     *
     * Note: there are many possible simplifications we could implement here,
     * but you are only required to implement a single one: constant folding.
     *
     * That is, whenever you see expressions of the form "NUM + NUM", or
     * "NUM - NUM", or "NUM * NUM", simplify them.
     */
    public static AstNode handleSimplify(Environment env, AstNode node) {
        // Try writing this one on your own!
        // Hint 1: Your code will likely be structured roughly similarly
        //         to your "handleToDouble" method
        // Hint 2: When you're implementing constant folding, you may want
        //         to call your "handleToDouble" method in some way
        // Hint 3: When implementing your private pair, think carefully about
        //         when you should recurse. Do you recurse after simplifying
        //         the current level? Or before?

        assertNodeMatches(node, "simplify", 1);
        AstNode exprToSimplify = node.getChildren().get(0);
        return handleSimplifyHelper(env.getVariables(), exprToSimplify);
    }
    
    private static AstNode handleSimplifyHelper(IDictionary<String, AstNode> variables, AstNode node) {
        if (node.isNumber() || (node.isVariable() && !variables.containsKey(node.getName()))) {
            return node;
        } else if (node.isVariable() && variables.containsKey(node.getName())) {
            return handleSimplifyHelper(variables, variables.get(node.getName()));
        } else {       // is operator
            IList<AstNode> newChildren = new DoubleLinkedList<>();
            AstNode result = new AstNode(node.getName(), newChildren);
            AstNode newLeft = handleSimplifyHelper(variables, node.getChildren().get(0));
            newChildren.add(newLeft);
            if ("+-*/^".contains(node.getName())) {
                AstNode newRight = handleSimplifyHelper(variables, node.getChildren().get(1));
                newChildren.add(newRight);
                if (newLeft.isNumber() && newRight.isNumber() && "+-*".contains(node.getName())) {
                    return new AstNode(toDoubleHelper(variables, result));
                }  
            }
            return result;
        }   
    }

    /**
     * Accepts an Environment variable and a 'plot(exprToPlot, var, varMin, varMax, step)'
     * AstNode and generates the corresponding plot on the ImageDrawer attached to the
     * environment. Returns some arbitrary AstNode.
     *
     * Example 1:
     *
     * >>> plot(3 * x, x, 2, 5, 0.5)
     *
     * This method will receive the AstNode corresponding to 'plot(3 * x, x, 2, 5, 0.5)'.
     * Your 'handlePlot' method is then responsible for plotting the equation
     * "3 * x", varying "x" from 2 to 5 in increments of 0.5.
     *
     * In this case, this means you'll be plotting the following points:
     *
     * [(2, 6), (2.5, 7.5), (3, 9), (3.5, 10.5), (4, 12), (4.5, 13.5), (5, 15)]
     *
     * ---
     *
     * Another example: now, we're plotting the quadratic equation "a^2 + 4a + 4"
     * from -10 to 10 in 0.01 increments. In this case, "a" is our "x" variable.
     *
     * >>> c := 4
     * 4
     * >>> step := 0.01
     * 0.01
     * >>> plot(a^2 + c*a + a, a, -10, 10, step)
     *
     * ---
     *
     * @throws EvaluationError  if any of the expressions contains an undefined variable.
     * @throws EvaluationError  if varMin > varMax
     * @throws EvaluationError  if 'var' was already defined
     * @throws EvaluationError  if 'step' is zero or negative
     */
    public static AstNode plot(Environment env, AstNode node) {
        assertNodeMatches(node, "plot", 5);
        IList<AstNode> children = node.getChildren();
        IDictionary<String, AstNode> variables = env.getVariables();
        
        AstNode exp = children.get(0);
        AstNode var = children.get(1);
        String varName = var.getName();
        
        if (variables.containsKey(varName)) {
            throw new EvaluationError("Varoable " + varName + " is already defined");
        }
        
        double varMin = toDoubleHelper(variables, children.get(2));
        double varMax = toDoubleHelper(variables, children.get(3));
        double step = toDoubleHelper(variables, children.get(4));
        
        if (varMin > varMax) {
            throw new EvaluationError(varMin + " is smaller than " + varMax);
        }
        if (step <= 0) {
            throw new EvaluationError("Step " + step + " is smaller than or equal to zero");
        }
        
        ImageDrawer drawer = env.getImageDrawer();
        IList<Double> xValues = new DoubleLinkedList<>();
        IList<Double> yValues = new DoubleLinkedList<>();
        
        for (double i = varMin; i <= varMax; i += step) {
            variables.put(varName, new AstNode(i));
            double yValue = toDoubleHelper(variables, exp);
            xValues.add(i);
            yValues.add(yValue);
        }
        
        String title = "plot";
        String xAxisLabel = var.getName();
        String yAxisLabel = "output";
        
        drawer.drawScatterPlot(title, xAxisLabel, yAxisLabel, xValues, yValues);
        variables.remove(varName);
        // Note: every single function we add MUST return an
        // AST node that your "simplify" function is capable of handling.
        // However, your "simplify" function doesn't really know what to do
        // with "plot" functions (and what is the "plot" function supposed to
        // evaluate to anyways?) so we'll settle for just returning an
        // arbitrary number.
        //
        // When working on this method, you should uncomment the following line:
        //
        return new AstNode(1);
        //IList<AstNode> child = new DoubleLinkedList<>();
        //child.add(children.get(0));
        //return handleSimplify(env, new AstNode("simplify", child));
    }
    
    public static AstNode handleDerive(Environment env, AstNode node) {
        assertNodeMatches(node, "Derive", 1);
        AstNode expr = handleSimplifyHelper(env.getVariables(), node.getChildren().get(0));
        return handleSimplifyHelper(env.getVariables(), handleDeriveHelper(env.getVariables(), expr));
    }
    
    private static AstNode handleDeriveHelper(IDictionary<String, AstNode> variables, AstNode node) {
        if (node.isNumber()) {
            return new AstNode(0);
        } else if (node.isVariable()) {
            return new AstNode(1);
        } else {
            if ("+-".contains(node.getName())) {
                IList<AstNode> newChildren = new DoubleLinkedList<>();
                newChildren.add(handleDeriveHelper(variables, node.getChildren().get(0)));
                newChildren.add(handleDeriveHelper(variables, node.getChildren().get(1)));
                return new AstNode(node.getName(), newChildren);
            }
            if (node.getName().equals("^")) {
                AstNode left = node.getChildren().get(0);
                AstNode right = node.getChildren().get(1);
                IList<AstNode> newChildren = new DoubleLinkedList<>();
                if ((left.isNumber() && right.isVariable())) {
                    newChildren.add(left);
                    IList<AstNode> newChildren2 = new DoubleLinkedList<>();
                    newChildren2.add(right);
                    newChildren2.add(new AstNode(left.getNumericValue() - 1));
                    newChildren.add(new AstNode("^", newChildren2));
                } else if (left.isVariable() && right.isNumber()) {   
                    newChildren.add(right);
                    IList<AstNode> newChildren2 = new DoubleLinkedList<>();
                    newChildren2.add(left);
                    newChildren2.add(new AstNode(right.getNumericValue() - 1));
                    newChildren.add(new AstNode("^", newChildren2));
                }
                return new AstNode("*", newChildren);
            }
            if (node.getName().equals("*")) {
                AstNode newLeft = node.getChildren().get(0);
                AstNode newRight = node.getChildren().get(1);
                if (node.getChildren().get(0).isVariable()) {
                    newLeft = new AstNode(1);
                }
                if (node.getChildren().get(0).isOperation()) {
                    newLeft = handleDeriveHelper(variables, node.getChildren().get(0));
                }
                if (node.getChildren().get(1).isVariable()) {
                    newRight = new AstNode(1);
                }
                if (node.getChildren().get(1).isOperation()) {
                    newRight = handleDeriveHelper(variables, node.getChildren().get(1));
                }
                IList<AstNode> newChildren = new DoubleLinkedList<>();
                newChildren.add(newLeft);
                newChildren.add(newRight);
                return new AstNode("*", newChildren);
            }
            return node;
        }
    }
    
}
