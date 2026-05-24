package com.app.view;

import com.app.model.Agent;
import com.app.model.Location;
import com.app.model.Node;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

/**
 * Page displaying the details of one selected agent.
 */
public class AgentDetailsPage extends BorderPane{
    
    private final MainController controller;
    private final Agent agent;

    /**
     * Creates the agent details page.
     * @param controller the main controller used for navigation
     * @param agent the selected agent
     */
    public AgentDetailsPage(MainController controller, Agent agent) {
        this.controller = controller;
        this.agent = agent;
        buildPage();
    }

    /**
     * Builds the page layout.
     */
    private void buildPage() {
        Label title = new Label("Agent details");

        Button backButton = new Button("Back to agents");
        backButton.setOnAction(e -> controller.showAgents((Node) agent.getLocation()));

        BorderPane topBar = new BorderPane();
        topBar.setLeft(backButton);
        topBar.setCenter(title);
        topBar.setPadding(new Insets(15));

        setTop(topBar);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25));
        grid.setHgap(20);
        grid.setVgap(12);

        addRow(grid, 0, "ID", String.valueOf(agent.getId()));
        addRow(grid, 1, "Name", agent.getName());
        addRow(grid, 2, "Speed", String.valueOf(agent.getSpeed()));
        addRow(grid, 3, "State", String.valueOf(agent.getState()));
        addRow(grid, 4, "Behavior", String.valueOf(agent.getBehavior()));
        addRow(grid, 5, "Tolerant to congestion", String.valueOf(agent.isTolerantToCongestion()));
        addRow(grid, 6, "Current location", formatLocation(agent.getLocation()));
        addRow(grid, 7, "Position", String.valueOf(agent.getPosition()));
        addRow(grid, 8, "Destination", formatNode(agent.getDestination()));

        setCenter(grid);
    }

    /**
     * Adds one information row to the grid.
     * @param grid the target grid
     * @param rowIndex the row index
     * @param label the attribute name
     * @param value the attribute value
     */
    private void addRow(GridPane grid, int rowIndex, String label, String value) {
        Label nameLabel = new Label(label + ":");
        
        Label valueLabel = new Label(value);

        grid.add(nameLabel, 0, rowIndex);
        grid.add(valueLabel, 1, rowIndex);
    }

    /**
     * Formats a location safely.
     * @param location the location to format
     * @return the location name or none
     */
    private String formatLocation(Location location) {
        if (location == null) {
            return "none";
        }
        return location.getName();
    }

    /**
     * Formats a node safely.
     * @param node the node to format
     * @return the node name or none
     */
    private String formatNode(Node node) {
        if (node == null) {
            return "none";
        }
        return node.getName();
    }
}
