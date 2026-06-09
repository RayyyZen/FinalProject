package com.app.view;

import java.util.ArrayList;
import java.util.List;

import com.app.controller.MainController;
import com.app.file.SaveLoadManager;
import com.app.model.agent.Agent;
import com.app.model.exception.AppException;
import com.app.model.graph.Graph;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.node.NodeType;
import com.app.model.simulation.Simulation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

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

    private final MainController controller;
    private final Simulation simulation;
    private final Pane nodePane;

    private static final int DEFAULT_MAX_AGENTS = 10;
    private static final double NODEPANE_W = 1350;
    private static final double NODEPANE_H = 850;
    private static final double LAYOUT_MARGIN = 80;

    /**
     * Builds the view for the given graph and immediately renders the nodes
     * already present in the model.
     * @param graph the graph to display and mutate
     */
    public GraphView(MainController controller, Simulation simulation) {
        this.controller = controller;
        this.simulation = simulation;

        // Toolbar
        Button addBtn = new Button("Add a node");
        Button removeBtn = new Button("Delete a node");
        Button nextBtn = new Button("Next");
        Button addNode = new Button("Create a node");
        Button addEdge = new Button("Create an edge");
        Button addAgent = new Button("Create an agent");
        Button playBtn = new Button("Play");
        Button stopBtn = new Button("Stop");



        Button save = new Button("Save");

        save.getStyleClass().add("tool-button");

        save.setOnAction(e -> {
            try{
                SaveLoadManager.saveInFile(simulation);
            } catch(Exception ex) {

            }
        });

        Button exit = new Button("Exit");

        exit.getStyleClass().add("tool-button");

        exit.setOnAction(e -> {
            controller.setSimulation(null);
            controller.showHome();
        });



        Timeline loop = new Timeline(new KeyFrame(Duration.seconds(1), e -> { simulation.move(); relayout(); }));
        loop.setCycleCount(Timeline.INDEFINITE);
        playBtn.setOnAction(e -> loop.play());
        stopBtn.setOnAction(e -> loop.stop());

        addBtn.getStyleClass().add("tool-button");
        removeBtn.getStyleClass().add("tool-button");
        addNode.getStyleClass().add("tool-button");
        addEdge.getStyleClass().add("tool-button");
        addAgent.getStyleClass().add("tool-button");

        playBtn.getStyleClass().add("primary-button");
        stopBtn.getStyleClass().add("primary-button");
        nextBtn.getStyleClass().add("primary-button");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox toolbar = new HBox(10, addBtn, removeBtn, addNode, addEdge, addAgent, save, exit, spacer, playBtn, stopBtn, nextBtn);
        toolbar.setPadding(new Insets(15, 20, 15, 20));
        setTop(toolbar);

        // nodePane
        nodePane = new Pane();
        nodePane.setPrefSize(NODEPANE_W, NODEPANE_H);
        this.getStyleClass().add("background");
        setCenter(nodePane);

        nodePane.widthProperty().addListener((o, ov, nv) -> relayout());
        nodePane.heightProperty().addListener((o, ov, nv) -> relayout());

        // Button actions
        addBtn.setOnAction(e -> {
            Node n = new Node(LocationState.OPEN, NodeType.DESTINATION, DEFAULT_MAX_AGENTS);
            simulation.getGraph().addNode(n);
            relayout();
        });

        removeBtn.setOnAction(e -> {
            List<Node> nodes = simulation.getGraph().getAllNodes();
            if (!nodes.isEmpty()) {
                Node last = nodes.get(nodes.size() - 1);
                try {
                    simulation.getGraph().removeNode(last);
                } catch (AppException e1) {
                }
                relayout();
            }
        });

        nextBtn.setOnAction(e -> {
            simulation.move();
            relayout();
        });

        addNode.setOnAction(e -> controller.showCreateNode());

        addEdge.setOnAction(e -> controller.showCreateEdge());

        addAgent.setOnAction(e -> controller.showCreateAgent());

        relayout();
    }

    /**
     * Clears the nodePane, places every node on a circle and draws every edge
     * as a line between its two endpoints. A single node is placed at the center.
     */
    private void relayout() {
        nodePane.getChildren().clear();

        List<Node> nodes = simulation.getGraph().getAllNodes();
        int n = nodes.size();
        if (n == 0) return;

        double w = nodePane.getWidth()  > 0 ? nodePane.getWidth()  : NODEPANE_W;
        double h = nodePane.getHeight() > 0 ? nodePane.getHeight() : NODEPANE_H;
        double cx = w / 2;
        double cy = h / 2;

        List<NodeView> views = new ArrayList<>();
        if (n == 1) {
            views.add(new NodeView(nodes.get(0), cx, cy, controller));
        } else {
            double radius = Math.min(cx, cy) - LAYOUT_MARGIN;
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n - Math.PI / 2;
                double x = cx + radius * Math.cos(angle);
                double y = cy + radius * Math.sin(angle);
                views.add(new NodeView(nodes.get(i), x, y, controller));
            }
        }

        List<javafx.scene.Node> agentVisuals = new ArrayList<>();

        for (Edge edge : simulation.getGraph().getAllEdges()) {
            int s = nodes.indexOf(edge.getSource());
            int t = nodes.indexOf(edge.getDestination());
            if (s >= 0 && t >= 0) {
                NodeView a = views.get(s);
                NodeView b = views.get(t);

                Line edgeLine = new Line(
                        a.getLayoutX(), a.getLayoutY(),
                        b.getLayoutX(), b.getLayoutY()
                );

                edgeLine.setOnMouseClicked(event -> {
                    event.consume();
                   controller.showEdgeDetails(edge);
                });

                edgeLine.getStyleClass().addAll("edge", "click");

                double x1 = a.getLayoutX(), y1 = a.getLayoutY();
                double x2 = b.getLayoutX(), y2 = b.getLayoutY();

                double angle = Math.atan2(y2 - y1, x2 - x1);
                double radius = 25;

                Polygon arrow = new Polygon(0, 0, -12, -6, -12, 6);
                arrow.setFill(Color.GREY);
                arrow.getTransforms().add(new Rotate(Math.toDegrees(angle), 0, 0));
                arrow.setTranslateX(x2 - radius * Math.cos(angle));
                arrow.setTranslateY(y2 - radius * Math.sin(angle));

                arrow.setOnMouseClicked(event -> {
                    event.consume();
                   controller.showEdgeDetails(edge);
                });
                Text edgeLabel = new Text(String.valueOf(edge.getNumberOfAgents()));
                edgeLabel.setFill(Color.BLACK);
                edgeLabel.setX((x1 + x2) / 2 + 5);
                edgeLabel.setY((y1 + y2) / 2 - 5);

                nodePane.getChildren().addAll(edgeLine, arrow, edgeLabel);

                for (Agent agent : edge.getAgents()) {
                    
                    double progress = agent.getPosition();
                    double agentX = x1 + progress * (x2 - x1);
                    double agentY = y1 + progress * (y2 - y1);

                    Circle agentCircle = new Circle(10, Color.ORANGE);
                    agentCircle.setStroke(Color.BLACK);

                    Text agentLabel = new Text(String.valueOf(agent.getId()));

                    
                    StackPane agentVisual = new StackPane(agentCircle, agentLabel);
                    agentVisual.setLayoutX(agentX - 10);
                    agentVisual.setLayoutY(agentY - 10);

                    agentVisuals.add(agentVisual);
                }
            }


        }

        nodePane.getChildren().addAll(views);
        nodePane.getChildren().addAll(agentVisuals);
    }
}
