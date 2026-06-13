package com.app.view.simulation;

import java.util.ArrayList;
import java.util.List;

import com.app.controller.MainController;
import com.app.model.agent.Agent;
import com.app.model.file.SaveLoadManager;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;
import com.app.model.simulation.Simulation;
import com.app.view.node.NodeView;

import javafx.geometry.Pos;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
import javafx.scene.control.Label;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.paint.CycleMethod;

/**
 * The graph view class
 * @version 3.0
 * @since 1.0
 * @author Rémi, Alexis, Rayane
 */
public class GraphView extends BorderPane {

    /**
     * The main controller
     */
    private final MainController controller;

    /**
     * The simulation
     */
    private final Simulation simulation;

    /**
     * The node pane
     */
    private final Pane nodePane;

    /**
     * The node pane width
     */
    private static final double NODEPANE_W = 1350;

    /**
     * The node pane height
     */
    private static final double NODEPANE_H = 850;

    /**
     * Fraction of the half-dimension used for the graph (responsive)
     */
    private static final double LAYOUT_RATIO = 0.80;

    /**
     * The loop initial time period
     */
    private static final double TIME = 0.8;

    /**
     * The add node counter
     */
    private int addNodeCounter = 1;

    /**
     * The add edge counter
     */
    private int addEdgeCounter = 1;

    /**
     * The add agent counter
     */
    private int addAgentCounter = 1;

    /**
     * Updates a loop's speed
     * @param loop A loop
     * @param speed The new speed
     */
    private void updateLoopSpeed(Timeline loop, double speed) {
        loop.stop();
        loop.getKeyFrames().setAll(
            new KeyFrame(Duration.seconds(speed), e -> {
                simulation.move();
                simulation.clearExits();
                relayout();
            })
        );
        loop.play();
    }

    /**
     * Returns the correspondant text of an add button
     * @param element The element that will be added
     * @param counter The number of occurences of the element that will be added
     * @return the correspondant text of an add button
     */
    private String addText(String element, int counter){
        String str = "";
        if(counter > 1){
            str = "s";
        }
        return "Add " + counter + " " + element + str;
    }

    /**
     * The graph view constructor
     * Builds the view for the given graph and immediately renders the nodes
     * @param controller The main controller
     * @param simulation The simulation
     */
    public GraphView(MainController controller, Simulation simulation) {
        this.controller = controller;
        this.simulation = simulation;

        // Toolbar
        Button addNodeBtn = new Button("Add 1 node");
        Button addEdgeBtn = new Button("Add 1 edge");
        Button addAgentBtn = new Button("Add 1 agent");
        Button nextBtn = new Button("Next");
        Button addNode = new Button("Create a node");
        Button addEdge = new Button("Create an edge");
        Button addAgent = new Button("Create an agent");
        Button playBtn = new Button("Play");
        Button stopBtn = new Button("Stop");

        Button plusNodeBtn = new Button("+");
        plusNodeBtn.getStyleClass().add("primary-button");
        plusNodeBtn.setOnAction(e -> {
            addNodeCounter++;
            addNodeBtn.setText(this.addText("node", addNodeCounter));
        });

        Button minusNodeBtn = new Button("-");
        minusNodeBtn.getStyleClass().add("primary-button");
        minusNodeBtn.setOnAction(e -> {
            if(addNodeCounter > 1){
                addNodeCounter--;
                addNodeBtn.setText(this.addText("node", addNodeCounter));
            }
        });

        Button plusEdgeBtn = new Button("+");
        plusEdgeBtn.getStyleClass().add("primary-button");
        plusEdgeBtn.setOnAction(e -> {
            addEdgeCounter++;
            addEdgeBtn.setText(this.addText("edge", addEdgeCounter));
        });

        Button minusEdgeBtn = new Button("-");
        minusEdgeBtn.getStyleClass().add("primary-button");
        minusEdgeBtn.setOnAction(e -> {
            if(addEdgeCounter > 1){
                addEdgeCounter--;
                addEdgeBtn.setText(this.addText("edge", addEdgeCounter));
            }
        });

        Button plusAgentBtn = new Button("+");
        plusAgentBtn.getStyleClass().add("primary-button");
        plusAgentBtn.setOnAction(e -> {
            addAgentCounter++;
            addAgentBtn.setText(this.addText("agent", addAgentCounter));
        });

        Button minusAgentBtn = new Button("-");
        minusAgentBtn.getStyleClass().add("primary-button");
        minusAgentBtn.setOnAction(e -> {
            if(addAgentCounter > 1){
                addAgentCounter--;
                addAgentBtn.setText(this.addText("agent", addAgentCounter));
            }
        });
        




        Button escapedBtn = new Button("Agents escaped");
        escapedBtn.getStyleClass().add("primary-button");
        escapedBtn.setOnAction(e -> controller.showEscapedAgents());

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


        Timeline loop = new Timeline(new KeyFrame(Duration.seconds(TIME), e -> { simulation.move(); simulation.clearExits(); relayout(); }));
        loop.setCycleCount(Timeline.INDEFINITE);
        playBtn.setOnAction(e -> loop.play());
        stopBtn.setOnAction(e -> loop.stop());

        addNodeBtn.getStyleClass().add("tool-button");
        addEdgeBtn.getStyleClass().add("tool-button");
        addAgentBtn.getStyleClass().add("tool-button");
        addNode.getStyleClass().add("tool-button");
        addEdge.getStyleClass().add("tool-button");
        addAgent.getStyleClass().add("tool-button");

        playBtn.getStyleClass().add("primary-button");
        stopBtn.getStyleClass().add("primary-button");
        nextBtn.getStyleClass().add("primary-button");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox toolBar = new HBox(10, addNodeBtn, plusNodeBtn, minusNodeBtn, addEdgeBtn, plusEdgeBtn, minusEdgeBtn, addAgentBtn, plusAgentBtn, minusAgentBtn, addNode, addEdge, addAgent, spacer, save, exit);
        toolBar.setPadding(new Insets(15, 20, 15, 20));
        setTop(toolBar);



        Button mult1 = new Button("x1");
        mult1.setOnAction(e -> updateLoopSpeed(loop, TIME));

        Button mult2 = new Button("x2");
        mult2.setOnAction(e -> updateLoopSpeed(loop, TIME / 2));

        Button mult3 = new Button("x4");
        mult3.setOnAction(e -> updateLoopSpeed(loop, TIME / 4));

        Button mult4 = new Button("x8");
        mult4.setOnAction(e -> updateLoopSpeed(loop, TIME / 8));

        mult1.getStyleClass().add("primary-button");
        mult2.getStyleClass().add("primary-button");
        mult3.getStyleClass().add("primary-button");
        mult4.getStyleClass().add("primary-button");

        VBox rightBar = new VBox(10, mult1, mult2, mult3, mult4);
        rightBar.setPadding(new Insets(15, 20, 15, 20));
        setRight(rightBar);
        rightBar.setAlignment(Pos.CENTER);

        Label legendTitle = new Label("Congestion level:");

        Rectangle gradientBar = new Rectangle(200, 15);
        gradientBar.setArcWidth(10);
        gradientBar.setArcHeight(10);
        gradientBar.setStroke(Color.BLACK);

        Stop[] stops = new Stop[] {
                new Stop(0.0, Color.WHITE),
                new Stop(0.5, Color.ORANGE),
                new Stop(1.0, Color.RED)
        };


        LinearGradient linearGradient = new LinearGradient(
                0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        gradientBar.setFill(linearGradient);

        Label minLabel = new Label("0%");

        Label maxLabel = new Label("100%");

        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);

        HBox labelsBox = new HBox(minLabel, spacer1, maxLabel);
        labelsBox.setMaxWidth(200);

        Label exitLegend = new Label("Exit nodes:");

        Circle exitNodes = new Circle(25, Color.WHITE);
        exitNodes.getStyleClass().add("exit");

        VBox legendContent = new VBox(5, legendTitle, gradientBar, labelsBox, exitLegend, exitNodes);
        legendContent.setPadding(new Insets(10, 0, 20, 0));

        VBox leftBar = new VBox(20);
        leftBar.setPadding(new Insets(15, 20, 15, 20));
        setLeft(leftBar);
        leftBar.setAlignment(Pos.CENTER);
        leftBar.getChildren().add(legendContent);

        Button optimizeDestinations = new Button("Optimize destinations");
        optimizeDestinations.getStyleClass().add("primary-button");
        optimizeDestinations.setOnAction(e -> {
            simulation.optimizeDestinations();
            relayout();
        });

        HBox bottomBar = new HBox(10, playBtn, stopBtn, nextBtn);
        VBox bottomBox = new VBox(10, escapedBtn ,optimizeDestinations, bottomBar);
        bottomBox.setPadding(new Insets(15, 20, 40, 20));
        setBottom(bottomBox);
        bottomBox.setAlignment(Pos.CENTER);
        bottomBar.setAlignment(Pos.CENTER);



        // nodePane
        nodePane = new Pane();
        nodePane.setPrefSize(NODEPANE_W, NODEPANE_H);
        this.getStyleClass().add("background");
        setCenter(nodePane);

        nodePane.widthProperty().addListener((o, ov, nv) -> relayout());
        nodePane.heightProperty().addListener((o, ov, nv) -> relayout());

        // Button actions
        addNodeBtn.setOnAction(e -> {
            simulation.getGraph().addDefaultNodes(addNodeCounter);
            relayout();
        });

        addEdgeBtn.setOnAction(e -> {
            simulation.getGraph().addDefaultEdges(addEdgeCounter);
            relayout();
        });

        addAgentBtn.setOnAction(e -> {
            simulation.getGraph().addDefaultAgents(addAgentCounter);
            relayout();
        });

        nextBtn.setOnAction(e -> {
            simulation.move();
            simulation.clearExits();
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
            views.add(new NodeView(nodes.get(0), cx, cy, Math.PI / 2, controller));
        } else {
            double radius = Math.min(cx, cy) * LAYOUT_RATIO;
            for (int i = 0; i < n; i++) {
                double angle = 2 * Math.PI * i / n - Math.PI / 2;
                double x = cx + radius * Math.cos(angle);
                double y = cy + radius * Math.sin(angle);
                views.add(new NodeView(nodes.get(i), x, y, angle, controller));
            }
        }

        List<javafx.scene.Node> agentVisuals = new ArrayList<>();

        for (Edge edge : simulation.getGraph().getAllEdges()) {
            int s = nodes.indexOf(edge.getSource());
            int t = nodes.indexOf(edge.getDestination());
            if (s >= 0 && t >= 0) {
                NodeView a = views.get(s);
                NodeView b = views.get(t);

                int c = 0;
                int value = -3;
                for (Edge e : simulation.getGraph().getAllEdges()){
                    if(e.equals(edge)){
                        value *= -1;
                    }
                    if(e.getSource().equals(edge.getDestination()) && e.getDestination().equals(edge.getSource())){
                        c = value;
                        break;
                    }
                }


                Line edgeLine = new Line(
                        a.getLayoutX() + c, a.getLayoutY() + c,
                        b.getLayoutX() + c, b.getLayoutY() + c
                );

                edgeLine.setOnMouseClicked(event -> {
                    event.consume();
                   controller.showEdgeDetails(edge);
                });

                edgeLine.getStyleClass().addAll("edge", "click");

                double x1 = a.getLayoutX() + c, y1 = a.getLayoutY() + c;
                double x2 = b.getLayoutX() + c, y2 = b.getLayoutY() + c;

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

                nodePane.getChildren().addAll(edgeLine, arrow);

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

                    agentVisual.setOnMouseClicked(e -> controller.showAgentDetails(agent));

                    agentVisuals.add(agentVisual);
                }
            }


        }

        nodePane.getChildren().addAll(views);
        nodePane.getChildren().addAll(agentVisuals);
    }

}