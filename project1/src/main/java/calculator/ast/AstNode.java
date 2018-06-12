package calculator.ast;

import calculator.errors.EvaluationError;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

/**
 * Represents a single node in an abstract syntax tree (AST).
 *
 * See spec for more details on what an AST is.
 */
public class AstNode {
    private String name;
    private IList<AstNode> children;
    private ExprType type;

    /**
     * Creates a leaf node representing a single number.
     */
    public AstNode(double number) {
        this("" + number, new DoubleLinkedList<>(), ExprType.NUMBER);
    }

    /**
     * Creates a leaf node representing a variable.
     */
    public AstNode(String name) {
        this(name, new DoubleLinkedList<>(), ExprType.VARIABLE);
    }

    /**
     * Creates a node representing some operation.
     *
     * An "operation" is any kind of node that represents a function call or
     * combines one or more AST nodes.
     *
     * For example, the expression "3 + 2" can be represented as the '+'
     * operation with two children:
     *
     *     new AstNode("+", [new AstNode(3.0), new AstNode(2.0)])
     *
     * As another example, the expression "sin(x)" can be represented as the
     * 'sin' operation with one child:
     *
     *     new AstNode("sin", [new AstNode("x")])
     *
     * Note that the list of children may be empty: this represents calling a
     * function with no arguments.
     */
    public AstNode(String name, IList<AstNode> children) {
        this(name, children, ExprType.OPERATION);
    }

    private AstNode(String name, IList<AstNode> children, ExprType type) {
        this.name = name;
        this.children = children;
        this.type = type;
    }

    /**
     * Returns 'true' if this node represents a number, and 'false' otherwise.
     */
    public boolean isNumber() {
        return this.type == ExprType.NUMBER;
    }

    /**
     * Returns 'true' if this node represents a variable, and 'false' otherwise.
     */
    public boolean isVariable() {
        return this.type == ExprType.VARIABLE;
    }

    /**
     * Returns 'true' if this node represents an operation or function call,
     * and 'false' otherwise.
     */
    public boolean isOperation() {
        return this.type == ExprType.OPERATION;
    }

    /**
     * Returns the variable or operation name.
     *
     * @throws EvaluationError  if this node is a number
     */
    public String getName() {
        if (this.isNumber()) {
            throw new EvaluationError("Attempted to call 'getName()' on a number AstNode");
        }
        return this.name;
    }

    /**
     * Returns the numeric value of this node.
     *
     * @throws EvaluationError  if this node does not represent a number
     */
    public double getNumericValue() {
        if (!this.isNumber()) {
            throw new EvaluationError("Attempted to call 'getNumericValue()' on a variable or operation AstNode");
        }
        return Double.parseDouble(this.name);
    }

    /**
     * Returns this node's children.
     *
     * If this node represents a number or variable, the returned list
     * is guaranteed to be empty.
     */
    public IList<AstNode> getChildren() {
        return this.children;
    }

    private enum ExprType {
        NUMBER,
        VARIABLE,
        OPERATION,
    }
}
