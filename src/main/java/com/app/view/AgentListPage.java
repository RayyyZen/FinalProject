package com.app.view;

import java.util.List;

import com.app.model.Agent;
import com.app.model.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    
    private static final int AGENT_RADIUS = 20;
    private final MainController controller;
    private final Node node;

    /**
     * Creates the page displaying all agents.
     * @param controller the main controller used for navigation
     * @param graph the graph containing the agents
     */
    public AgentListPage(MainController controller, Node node) {
        this.controller = controller;
        this.node = node;
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

        List<Agent> agents = node.getAgents();
        if(agents.isEmpty()) {
            Label emptyLabel = new Label("No agents found.");
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

        setCenter(agentsContainer);
    }

    /**
     * Creates one clickable circle for one agent.
     * @param agent the represented agent
     * @return a clickable graphical circle
     */
    private StackPane createAgentCircle(Agent agent) {
        Circle circle = new Circle(AGENT_RADIUS);
        circle.setFill(Color.ORANGE);
        circle.setStroke(Color.BLACK);

        Text idText = new Text(String.valueOf(agent.getId()));

        StackPane circlePane = new StackPane(circle, idText);
        circlePane.setCursor(Cursor.HAND);

        circlePane.setOnMouseClicked(e -> {
            e.consume();
            controller.showAgentDetails(agent);
        });
        return circlePane;
    }
}
