package com.app.view.node;

import com.app.controller.MainController;
import com.app.model.graph.location.node.Node;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    public NodeView(Node node, double x, double y, double labelAngle, MainController controller) {
        this.node = node;

        boolean empty = node.getNumberOfAgents() == 0;

        Circle circle = new Circle(25, empty ? Color.BLACK : getCongestionColor(node));

        circle.getStyleClass().addAll("node", "click");

        setOnMouseClicked(event -> {
            event.consume();
            controller.showNodeDetails(node);
        });

        Text label = new Text(node.getName());
        label.setFill(empty ? Color.WHITE : Color.BLACK);
        label.setX(-label.getLayoutBounds().getWidth() / 2);
        label.setY(5);

        Text capacity = new Text(node.getNumberOfAgents() + " / " + node.getMaxAgents());
        capacity.setFill(Color.BLACK);
        capacity.setFont(Font.font("System", FontWeight.BOLD, 14));

        double offset = 50;
        capacity.setX(Math.cos(labelAngle) * offset - capacity.getLayoutBounds().getWidth() / 2);
        capacity.setY(Math.sin(labelAngle) * offset + 5);

        getChildren().addAll(circle, label, capacity);

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
     * white when empty, orange when half full, red when full.
     */
    private Color getCongestionColor(Node node) {
        int max = node.getMaxAgents();
        if (max <= 0) {
            return Color.WHITE;
        }
        double ratio = (double) node.getNumberOfAgents() / max;
        if (ratio > 1) ratio = 1;

        if (ratio < 0.5) {
            return Color.WHITE.interpolate(Color.ORANGE, ratio * 2);
        } else {
            return Color.ORANGE.interpolate(Color.RED, (ratio - 0.5) * 2);
        }
    }
}
