package org.example.projectgestaltt.compiler;

import javafx.scene.control.TreeItem;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TreeBuilder {

    /**
     * Convierte el árbol de ANTLR en TreeItem para JavaFX TreeView
     */
    public static TreeItem<String> build(ParseTree tree, Parser parser) {
        TreeItem<String> item = new TreeItem<>(getLabel(tree, parser));
        item.setExpanded(true);

        for (int i = 0; i < tree.getChildCount(); i++) {
            TreeItem<String> hijo = build(tree.getChild(i), parser);
            item.getChildren().add(hijo);
        }
        return item;
    }

    private static String getLabel(ParseTree node, Parser parser) {
        if (node instanceof TerminalNode t) {
            String text = t.getText();
            int type = t.getSymbol().getType();
            String name = (type >= 0 && parser != null)
                    ? parser.getVocabulary().getDisplayName(type)
                    : "EOF";
            return name + ": '" + text + "'";
        }
        // Nodo de regla — quitar "Context" del nombre
        return node.getClass().getSimpleName().replace("Context", "");
    }
}