package org.example.projectgestaltt.compiler;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.List;

public class TreeVisualizer {

    private static final double NODE_H      = 32;
    private static final double LEVEL_GAP   = 55;
    private static final double NODE_GAP    = 10;
    private static final double PADDING     = 30;
    private static final double MIN_W       = 70;

    private static final Color RULE_BG   = Color.web("#3e3a2e");
    private static final Color RULE_FG   = Color.web("#c8c4a8");
    private static final Color TERM_BG   = Color.web("#c8c4a8");
    private static final Color TERM_FG   = Color.web("#2e2a1e");
    private static final Color LINE_COL  = Color.web("#6a6450");
    private static final Color BORDER    = Color.web("#6a6450");

    private static class Node {
        String  label;
        boolean terminal;
        double  x, y, w, subtreeW;
        List<Node> children = new ArrayList<>();

        Node(String label, boolean terminal) {
            this.label    = label;
            this.terminal = terminal;
        }
    }

    public static Pane draw(ParseTree tree, Parser parser) {
        Pane pane = new Pane();
        pane.setStyle("-fx-background-color: #bcb89c;");

        if (tree == null) return pane;

        Node root = build(tree, parser);
        calcWidths(root);
        calcPositions(root, PADDING, PADDING);

        double[] bounds = maxBounds(root);
        pane.setPrefSize(bounds[0] + PADDING, bounds[1] + PADDING);

        drawTree(pane, root);
        return pane;
    }

    private static Node build(ParseTree tree, Parser parser) {
        String label;
        boolean terminal;
        if (tree instanceof TerminalNode t) {
            String name = parser.getVocabulary()
                    .getDisplayName(t.getSymbol().getType());
            label    = name + ": " + t.getText();
            terminal = true;
        } else {
            label    = tree.getClass().getSimpleName()
                    .replace("Context", "");
            terminal = false;
        }
        Node node = new Node(label, terminal);
        for (int i = 0; i < tree.getChildCount(); i++)
            node.children.add(build(tree.getChild(i), parser));
        return node;
    }

    private static void calcWidths(Node n) {
        n.w = Math.max(textWidth(n.label) + 16, MIN_W);
        if (n.children.isEmpty()) {
            n.subtreeW = n.w + NODE_GAP;
            return;
        }
        double total = 0;
        for (Node c : n.children) {
            calcWidths(c);
            total += c.subtreeW;
        }
        n.subtreeW = Math.max(total, n.w + NODE_GAP);
    }

    private static void calcPositions(Node n, double left, double y) {
        n.y = y;
        n.x = left + n.subtreeW / 2.0;
        double cx = left;
        for (Node c : n.children) {
            calcPositions(c, cx, y + NODE_H + LEVEL_GAP);
            cx += c.subtreeW;
        }
    }

    private static void drawTree(Pane pane, Node n) {
        // Líneas a hijos
        for (Node c : n.children) {
            Line line = new Line(n.x, n.y + NODE_H / 2.0,
                    c.x, c.y - NODE_H / 2.0);
            line.setStroke(LINE_COL);
            line.setStrokeWidth(1.5);
            pane.getChildren().add(line);
            drawTree(pane, c);
        }

        // Nodo
        double nx = n.x - n.w / 2.0;
        double ny = n.y - NODE_H / 2.0;

        Rectangle rect = new Rectangle(nx, ny, n.w, NODE_H);
        rect.setFill(n.terminal ? TERM_BG : RULE_BG);
        rect.setStroke(BORDER);
        rect.setStrokeWidth(1);
        rect.setArcWidth(4);
        rect.setArcHeight(4);

        Text text = new Text(n.label);
        text.setFont(Font.font("Courier New", FontWeight.BOLD, 10));
        text.setFill(n.terminal ? TERM_FG : RULE_FG);

        // Centrar texto en nodo
        double tw = text.getBoundsInLocal().getWidth();
        double th = text.getBoundsInLocal().getHeight();
        text.setX(nx + (n.w - tw) / 2.0);
        text.setY(ny + (NODE_H + th) / 2.0 - 3);

        pane.getChildren().addAll(rect, text);
    }

    private static double[] maxBounds(Node n) {
        double mx = n.x + n.w / 2.0;
        double my = n.y + NODE_H / 2.0;
        for (Node c : n.children) {
            double[] b = maxBounds(c);
            mx = Math.max(mx, b[0]);
            my = Math.max(my, b[1]);
        }
        return new double[]{mx, my};
    }

    private static double textWidth(String s) {
        Text t = new Text(s);
        t.setFont(Font.font("Courier New", FontWeight.BOLD, 10));
        return t.getBoundsInLocal().getWidth();
    }
}