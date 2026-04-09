package org.example.projectgestaltt.compiler;

import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Convierte el árbol de derivación de ANTLR en una cadena con
 * indentación para mostrarlo en la interfaz.
 */
public class TreePrinter {

    private static final String BRANCH = "├── ";
    private static final String LAST   = "└── ";
    private static final String INDENT = "│   ";
    private static final String SPACE  = "    ";

    /**
     * @param tree    Raíz del árbol de derivación.
     * @param parser  Parser utilizado (necesario para obtener nombres de reglas).
     * @return        Árbol formateado como texto.
     */
    public static String print(ParseTree tree, Parser parser) {
        StringBuilder sb = new StringBuilder();
        printNode(tree, parser, sb, "", true);
        return sb.toString();
    }

    private static void printNode(ParseTree node, Parser parser,
                                  StringBuilder sb, String prefix, boolean isLast) {
        String connector = isLast ? LAST : BRANCH;
        String label     = getLabel(node, parser);

        sb.append(prefix).append(connector).append(label).append("\n");

        String childPrefix = prefix + (isLast ? SPACE : INDENT);

        int childCount = node.getChildCount();
        for (int i = 0; i < childCount; i++) {
            printNode(node.getChild(i), parser, sb, childPrefix, i == childCount - 1);
        }
    }

    private static String getLabel(ParseTree node, Parser parser) {
        if (node instanceof TerminalNode t) {
            String text = t.getText();
            int    type = t.getSymbol().getType();
            String name = (type >= 0 && parser != null)
                    ? parser.getVocabulary().getDisplayName(type)
                    : "EOF";
            return name + ": '" + text + "'";
        }
        // RuleContext → nombre de la regla
        String className = node.getClass().getSimpleName();
        // Eliminar "Context" del final para que sea más legible
        return className.replace("Context", "");
    }
}