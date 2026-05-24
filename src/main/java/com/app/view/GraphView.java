package com.app.view;

import java.util.ArrayList;
import java.util.List;

import com.app.model.Edge;
import com.app.model.Graph;
import com.app.model.LocationState;
import com.app.model.Node;
import com.app.model.NodeType;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

/**
 * Main graphical view of the {@link Graph}.
 * <p>
 * Layout :
 * <ul>
 *   <li>top    : a toolbar with "add" and "remove" buttons</li>
 *   <li>center : a nodePane where every node is drawn on a circle and every
 *       edge is drawn as a line between its endpoints</li>
 * </ul>
 * Every model change triggers a full re-layout so the view always mirrors
 * the model exactly.
 */
public class GraphView extends BorderPane {

    private final Graph graph;
    private final Pane nodePane;

    private static final int DEFAULT_MAX_AGENTS = 10;
    private static final double NODEPANE_W = 1000;
    private static final double NODEPANE_H = 600;
    private static final double LAYOUT_MARGIN = 80;

    /**
     * Builds the view for the given graph and immediately renders the nodes
     * already present in the model.
     * @param graph the graph to display and mutate
     */
    public GraphView(Graph graph) {
        this.graph = graph;

        // Toolbar
        Button addBtn = new Button("Ajouter un nœud");
        Button removeBtn = new Button("Supprimer un nœud");
        HBox toolbar = new HBox(10, addBtn, removeBtn);
        toolbar.setPadding(new Insets(10));
        setTop(toolbar);

        // nodePane
        nodePane = new Pane();
        nodePane.setPrefSize(NODEPANE_W, NODEPANE_H);
        nodePane.setStyle("-fx-background-color: white;");
        setCenter(nodePane);

        // Button actions
        addBtn.setOnAction(e -> {
            Node n = new Node(LocationState.OPEN, NodeType.DESTINATION, DEFAULT_MAX_AGENTS);
            graph.addNode(n);
            relayout();
        });

        removeBtn.setOnAction(e -> {
            List<Node> nodes = graph.getAllNodes();
            if (!nodes.isEmpty()) {
                Node last = nodes.get(nodes.size() - 1);
                graph.removeNodeById(last.getId());
                relayout();
            }
        });

        relayout();
    }

    /**
     * Clears the nodePane, places every node on a circle and draws every edge
     * as a line between its two endpoints. A single node is placed at the center.
     */
    private void relayout() {
        nodePane.getChildren().clear();

        List<Node> nodes = graph.getAllNodes();
        int n = nodes.size();
        if (n == 0) return;

        double cx = NODEPANE_W / 2;
        double cy = NODEPANE_H / 2;

        List<NodeView> views = new ArrayList<>();
        if (n == 1) {
            views.add(new NodeView(nodes.get(0), cx, cy));
        } else {
            double radius = Math.min(cx, cy) - LAYOUT_MARGIN;
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n - Math.PI / 2;
                double x = cx + radius * Math.cos(angle);
                double y = cy + radius * Math.sin(angle);
                views.add(new NodeView(nodes.get(i), x, y));
            }
        }

        for (Edge edge : graph.getAllEdges()) {
            int s = nodes.indexOf(edge.getSource());
            int t = nodes.indexOf(edge.getDestination());
            if (s >= 0 && t >= 0) {
                NodeView a = views.get(s);
                NodeView b = views.get(t);
                nodePane.getChildren().add(new Line(
                        a.getLayoutX(), a.getLayoutY(),
                        b.getLayoutX(), b.getLayoutY()));
            }
        }

        nodePane.getChildren().addAll(views);
    }
}
