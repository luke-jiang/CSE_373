package calculator.interpreter;

import calculator.ast.AstNode;
import datastructures.concrete.DoubleLinkedList;
import datastructures.interfaces.IList;

public class Interpreter {
    public AstNode evaluate(Environment env, AstNode node) {
        if (node.isNumber()) {
            return node;
        } else if (node.isVariable()) {
            return node;
        } else if (node.isOperation()) {
            String nodeName = node.getName();
            if (env.getSpecialFunctions().containsKey(nodeName)) {
                // Special functions take complete control
                return env.getSpecialFunctions().get(nodeName).manipulate(env, node);
            } else {
                // Regular, custom functions are executed normally:
                // we first execute the children before handing control back
                // to the regular function (if one exists)
                IList<AstNode> children = new DoubleLinkedList<>();
                for (AstNode oldChild : node.getChildren()) {
                    children.add(evaluate(env, oldChild));
                }

                AstNode output = new AstNode(node.getName(), children);
                if (env.getCustomFunctions().containsKey(nodeName)) {
                    output = env.getCustomFunctions().get(nodeName).manipulate(env, output);
                }
                return output;
            }
        } else {
            throw new AssertionError();
        }
    }
}
