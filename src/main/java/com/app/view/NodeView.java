package com.app.view;

import com.app.model.Node;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Graphical representation of a {@link Node}: a colored circle with the node
 * name as a centered label. Keeps a reference to its underlying model node so
 * the view layer can look up which node was clicked.
 */
public class NodeView extends Group {

    private final Node node;

    /**
     * Builds a new node view at the given position.
     * @param node the model node this view represents
     * @param x    the x-coordinate within the parent pane
     * @param y    the y-coordinate within the parent pane
     */
    public NodeView(Node node, double x, double y) {
        this.node = node;

        Circle circle = new Circle(20, Color.LIGHTBLUE);
        circle.setStroke(Color.BLACK);

        Text label = new Text(node.getName());
        label.setX(-label.getLayoutBounds().getWidth() / 2);
        label.setY(5);

        getChildren().addAll(circle, label);

        setLayoutX(x);
        setLayoutY(y);
    }

    /**
     * @return the model node this view represents
     */
    public Node getNode() {
        return node;
    }
}
