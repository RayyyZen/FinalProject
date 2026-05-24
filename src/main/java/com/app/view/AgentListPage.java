package com.app.view;

import java.util.ArrayList;
import java.util.List;

import com.app.model.Agent;
import com.app.model.Edge;
import com.app.model.Graph;
import com.app.model.Location;
import com.app.model.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Page displaying all agents as clickable circles.
 */
public class AgentListPage extends BorderPane {
    
    private static final int AGENT_RADIUS = 10;
    private final MainController controller;
    private final Graph graph;

    /**
     * Creates the page displaying all agents.
     * @param controller the main controller used for navigation
     * @param graph the graph containing the agents
     */
    public AgentListPage(MainController controller, Graph graph) {
        this.controller = controller;
        this.graph = graph;
        buildPage();
    }

    /**
     * Builds the page layout.
     */
    private void buildPage() {
        Label title = new Label("Agents list");
        
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showGraph());

        BorderPane topBar = new BorderPane();
        topBar.setLeft(backButton);
        topBar.setCenter(title);
        topBar.setPadding(new Insets(15));

        setTop(topBar);

        List<Agent> agents = getAllAgents();
        if(agents.isEmpty()) {
            Label emptyLabel = new Label("No agents found.");
            emptyLabel.setStyle("-fx-font-size: 16px;");
            setCenter(emptyLabel);
            BorderPane.setAlignment(emptyLabel, Pos.CENTER);
            return;
        }

        FlowPane agentsContainer = new FlowPane();
        agentsContainer.setHgap(20);
        agentsContainer.setVgap(20);
        agentsContainer.setPadding(new Insets(30));
        agentsContainer.setAlignment(Pos.CENTER);

        for (Agent agent : agents) {
            StackPane agentCircle = createAgentCircle(agent);
            agentsContainer.getChildren().add(agentCircle);
        }


    }

    /**
     * Creates one clickable circle for one agent.
     * @param agent the represented agent
     * @return a clickable graphical circle
     */
    private StackPane createAgentCircle(Agent agent) {
        Circle circle = new Circle(AGENT_RADIUS);
        circle.setFill(Color.ORANGE);
        circle.setStroke(Color.DARKORANGE);
        circle.setStrokeWidth(2);

        Text idText = new Text(String.valueOf(agent.getId()));
        idText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        StackPane circlePane = new StackPane(circle, idText);
        circlePane.setCursor(Cursor.HAND);

        Tooltip.install(circlePane, new Tooltip("Agent ID: " + agent.getId()));

        circlePane.setOnMouseClicked(event -> {
            event.consume();
            controller.showAgentDetails(agent);
        });
        return circlePane;
    }

    /**
     * Gets all agents from all graph locations.
     * @return the list of all agents found in the graph
     */
    private List<Agent> getAllAgents() {
        List<Agent> agents = new ArrayList<>();
        for (Node node : graph.getAllNodes()) {
            addAgentsFromLocation(agents,node);
        }
        for (Edge edge : graph.getAllEdges()) {
            addAgentsFromLocation(agents, edge);
        }
        return agents;
    }

    /**
     * Adds agents from a location without duplicates.
     * @param agents the list receiving the agents
     * @param location the location containing agents
     */
    private void addAgentsFromLocation(List<Agent> agents, Location location) {
        for (Agent agent : location.getAgents()) {
            if (!agents.contains(agent)) {
                agents.add(agent);
            }
        }
    }
}
