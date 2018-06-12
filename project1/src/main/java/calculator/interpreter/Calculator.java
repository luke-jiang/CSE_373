package calculator.interpreter;

import calculator.ast.AstManipulator;
import calculator.ast.AstNode;
import calculator.ast.BuiltinManipulators;
import calculator.ast.ControlFlowManipulators;
import calculator.ast.ExpressionManipulators;
import calculator.gui.ImageDrawer;
import calculator.parser.Parser;
import datastructures.concrete.DoubleLinkedList;
import datastructures.concrete.dictionaries.ArrayDictionary;
import datastructures.interfaces.IDictionary;
import datastructures.interfaces.IList;

import java.util.Iterator;

public class Calculator {
    // Components used by the calculator
    private Parser parser;
    private Interpreter interpreter;

    // State
    private IDictionary<String, AstNode> variables;
    private ImageDrawer imageDrawer;

    // Internal data
    private IDictionary<String, AstManipulator> customFunctions;
    private IDictionary<String, AstManipulator> specialFunctions;
    private IDictionary<String, Integer> precedenceMap;

    private static final int STRONGEST_PRECEDENCE = 0;
    private static final int WEAKEST_PRECEDENCE = Integer.MAX_VALUE;

    public Calculator() {
        this(null);
    }

    public Calculator(ImageDrawer imageDrawer) {
        this.parser = new Parser();
        this.interpreter = new Interpreter();

        this.variables = new ArrayDictionary<>();
        this.imageDrawer = imageDrawer;

        this.customFunctions = new ArrayDictionary<>();
        this.specialFunctions = new ArrayDictionary<>();
        this.precedenceMap = new ArrayDictionary<>();

        // Your functions
        this.customFunctions.put("simplify", ExpressionManipulators::handleSimplify);
        this.customFunctions.put("toDouble", ExpressionManipulators::handleToDouble);
        this.customFunctions.put("plot", ExpressionManipulators::plot);
        this.customFunctions.put("derive", ExpressionManipulators::handleDerive);

        // Internal functions (that need to manipulate control flow or the environment somehow)
        this.specialFunctions.put("block", BuiltinManipulators::handleBlock);
        this.specialFunctions.put("assign", BuiltinManipulators::handleAssign);
        this.specialFunctions.put("quit", BuiltinManipulators::handleQuit);
        this.specialFunctions.put("exit", BuiltinManipulators::handleQuit);
        this.specialFunctions.put("clear", BuiltinManipulators::handleClear);

        // Code you may implement for extra credit
        this.specialFunctions.put("randomlyPick", ControlFlowManipulators::handleRandomlyPick);
        this.specialFunctions.put("if", ControlFlowManipulators::handleIf);
        this.specialFunctions.put("repeat", ControlFlowManipulators::handleRepeat);

        this.precedenceMap.put("^", 1);
        this.precedenceMap.put("negate", 2);
        this.precedenceMap.put("*", 3);
        this.precedenceMap.put("/", 3);
        this.precedenceMap.put("+", 4);
        this.precedenceMap.put("-", 4);
    }

    public void setImageDrawer(ImageDrawer imageDrawer) {
        this.imageDrawer = imageDrawer;
    }

    public String evaluate(String input) {
        if (input.trim().equals("")) {
            return "";
        }
        Environment env = this.prepareEnvironment();
        AstNode ast = this.parser.parse(input + "\n");
        AstNode normalizedAst = injectSimplify(env, ast);
        AstNode output = this.interpreter.evaluate(env, normalizedAst);
        return this.convertToString(output);
    }

    private Environment prepareEnvironment() {
        return new Environment(
                this.variables,
                this.imageDrawer,
                this.customFunctions,
                this.specialFunctions,
                this.interpreter);
    }

    private static AstNode injectSimplify(Environment env, AstNode node) {
        return wrapSimplifyFunc(injectSimplifyHelper(env, node));
    }

    private static AstNode injectSimplifyHelper(Environment env, AstNode node) {
        if (node.isNumber()) {
            return node;
        } else if (node.isVariable()) {
            return node;
        } else {
            IList<AstNode> newChildren = new DoubleLinkedList<>();
            for (AstNode oldChild : node.getChildren()) {
                newChildren.add(injectSimplifyHelper(env, oldChild));
            }

            if (env.getSpecialFunctions().containsKey(node.getName())) {
                for (int i = 0; i < newChildren.size(); i++) {
                    newChildren.set(i, wrapSimplifyFunc(newChildren.get(i)));
                }

                return wrapSimplifyFunc(new AstNode(node.getName(), newChildren));
            } else {
                return new AstNode(node.getName(), newChildren);
            }
        }
    }

    private static AstNode wrapSimplifyFunc(AstNode inner) {
        if (inner.isOperation() && inner.getName().equals("simplify")) {
            return inner;
        } else {
            IList<AstNode> children = new DoubleLinkedList<>();
            children.add(inner);
            return new AstNode("simplify", children);
        }
    }

    /**
     * This method returns a String representation of any valid AstNode.
     *
     * It doesn't really need to be public, but it's handy for debugging.
     */
    public String convertToString(AstNode node) {
        return this.convertToString(node, WEAKEST_PRECEDENCE);
    }

    private String convertToString(AstNode node, int parentPrecedenceLevel) {
        if (node.isNumber()) {
            double val = node.getNumericValue();
            if (val == (long) val) {
                return String.format("%d", (long) val);
            } else {
                return String.format("%s", val);
            }
        } else if (node.isVariable()) {
            return node.getName();
        } else {
            String name = node.getName();

            boolean hasPrecedence = this.precedenceMap.containsKey(name);
            int currPrecedenceLevel = hasPrecedence ? this.precedenceMap.get(name) : STRONGEST_PRECEDENCE;
            int childPrecedenceLevel = hasPrecedence ? currPrecedenceLevel : WEAKEST_PRECEDENCE;

            IList<String> children = new DoubleLinkedList<>();
            for (AstNode child : node.getChildren()) {
                children.add(this.convertToString(child, childPrecedenceLevel));
            }

            String out;
            if ("-+*/^".contains(name)) {
                out = this.join(" " + name + " ", children);
            } else if ("negate".equals(name)) {
                out = "-" + children.get(0);
            } else {
                out = name + "(" + this.join(", ", children) + ")";
            }

            if (currPrecedenceLevel > parentPrecedenceLevel) {
                out = "(" + out + ")";
            }

            return out;
        }
    }

    private String join(String connector, IList<String> items) {
        String out = "";
        if (!items.isEmpty()) {
            Iterator<String> iter = items.iterator();
            out = iter.next();
            while (iter.hasNext()) {
                out += connector + iter.next();
            }
        }
        return out;
    }
}
