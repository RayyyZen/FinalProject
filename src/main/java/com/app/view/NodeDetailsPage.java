package com.app.view;

import com.app.controller.MainController;
import com.app.model.graph.location.node.Node;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Page displaying the details of one selected node.
 */
public class NodeDetailsPage extends BorderPane{
    
    private final MainController controller;
    private final Node node;

    /**
     * Creates the node details page.
     * @param controller the main controller used for navigation
     * @param node the selected node
     */
    public NodeDetailsPage(MainController controller, Node node) {
        this.controller = controller;
        this.node = node;
        buildPage();
    }

    /**
     * Builds the page layout.
     */
    private void buildPage() {
        Label title = new Label("Node details");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button("Back to graph");
        backButton.setOnAction(event -> controller.showGraph());
        backButton.getStyleClass().add("primary-button");

        Button deleteButton = new Button("Delete node");
        deleteButton.setOnAction(e -> {
            controller.getSimulation().getGraph().removeNode(node);
            controller.showGraph();
        });
        deleteButton.getStyleClass().add("primary-button");

        Button showAgentsButton = new Button("See agents");
        showAgentsButton.setOnAction(e -> controller.showAgents(node));
        showAgentsButton.getStyleClass().add("primary-button");

        Button createAgentButton = new Button("Create an agent");
        createAgentButton.setOnAction(e -> controller.showCreateAgent(node));
        createAgentButton.getStyleClass().add("primary-button");

        Button createEdgeButton = new Button("Create an edge");
        createEdgeButton.setOnAction(e -> controller.showCreateEdge(node));
        createEdgeButton.getStyleClass().add("primary-button");

        HBox actionsBox = new HBox(10, createAgentButton, createEdgeButton, showAgentsButton, deleteButton);
        actionsBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(backButton);

        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
        topBar.setCenter(title);
        topBar.setRight(actionsBox);
        topBar.setPadding(new Insets(15));

        setTop(topBar);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);

        addRow(grid, 0, "ID", String.valueOf(node.getId()));
        addRow(grid, 1, "Name", node.getName());
        addRow(grid, 2, "State", String.valueOf(node.getState()));
        addRow(grid, 3, "Type", String.valueOf(node.getType()));
        addRow(grid, 4, "Agents", node.getNumberOfAgents() + " / " + node.getMaxAgents());



        VBox content = new VBox(25);
        content.setPadding(new Insets(25));
        content.getChildren().add(grid);

        setCenter(content);
        content.setAlignment(Pos.CENTER);
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
        nameLabel.setStyle("-fx-font-weight: bold;");

        Label valueLabel = new Label(value);

        grid.add(nameLabel, 0, rowIndex);
        grid.add(valueLabel, 1, rowIndex);
    }
}
