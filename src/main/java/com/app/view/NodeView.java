package com.app.view;

import com.app.controller.MainController;
import com.app.model.graph.location.node.Node;

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
    public NodeView(Node node, double x, double y, MainController controller) {
        this.node = node;

        Circle circle = new Circle(25, getCongestionColor(node));

        circle.getStyleClass().addAll("node", "click");

        setOnMouseClicked(event -> {
            event.consume();
            controller.showNodeDetails(node);
        });

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

    /**
     * Returns a color based on how full the node is :
     * green when almost empty, orange when half full, red when almost full.
     */
    private Color getCongestionColor(Node node) {
        int max = node.getMaxAgents();
        if (max <= 0) {
            return Color.LIGHTGREEN;
        }
        double ratio = (double) node.getNumberOfAgents() / max;

        if (ratio < 0.5) {
            return Color.LIGHTGREEN;
        } else if (ratio < 0.8) {
            return Color.ORANGE;
        } else {
            return Color.RED;
        }
    }
}
