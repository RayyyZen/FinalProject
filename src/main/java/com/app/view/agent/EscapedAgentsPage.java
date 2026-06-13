package com.app.view.agent;

import java.util.List;

import com.app.controller.MainController;
import com.app.model.agent.Agent;

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
 * The escaped agents class
 * @version 3.0
 * @since 3.0
 * @author Rémi
 */
public class EscapedAgentsPage extends BorderPane {

    /**
     * The agent's circle radius
     */
    private static final int AGENT_RADIUS = 20;

    /**
     * The main controller
     */
    private final MainController controller;

    /**
     * Creates the escaped agents page
     * @param controller the main controller used for navigation
     */
    public EscapedAgentsPage(MainController controller) {
        this.controller = controller;
        buildPage();
    }

    /**
     * Builds the page layout
     */
    private void buildPage() {
        Label title = new Label("Escaped agents");

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> controller.showGraph());
        backButton.getStyleClass().add("primary-button");

        BorderPane topBar = new BorderPane();
        topBar.setLeft(backButton);
        topBar.setCenter(title);
        topBar.setPadding(new Insets(15));

        setTop(topBar);

        List<Agent> agents = controller.getSimulation().getEscapedAgents();

        if (agents == null || agents.isEmpty()) {
            Label emptyLabel = new Label("No escaped agents yet.");
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
     * Creates one clickable circle for one agent
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
