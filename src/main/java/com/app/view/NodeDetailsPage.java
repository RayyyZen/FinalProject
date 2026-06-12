package com.app.view;

import com.app.controller.MainController;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.node.Node;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * Page displaying the details of one selected node.
 */
public class NodeDetailsPage extends BorderPane{

    private final MainController controller;
    private final Node node;
    private boolean editMode;

    public NodeDetailsPage(MainController controller, Node node) {
        this.controller = controller;
        this.node = node;
        this.editMode = false;
        buildPage();
    }

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

        ComboBox<LocationState> stateBox = new ComboBox<>();
        stateBox.getItems().addAll(LocationState.values());
        stateBox.setValue(node.getState());
        addEditableRow(grid, 2, "State", String.valueOf(node.getState()), stateBox);

        addRow(grid, 3, "Type", String.valueOf(node.getType()));
        addRow(grid, 4, "Agents", node.getNumberOfAgents() + " / " + node.getMaxAgents());

        Button modifyButton = new Button(editMode ? "Save" : "Modify");
        modifyButton.getStyleClass().add("primary-button");
        modifyButton.setOnAction(e -> {
            if (editMode) {
                node.setState(stateBox.getValue());
            }
            editMode = !editMode;
            buildPage();
        });

        VBox content = new VBox(25, grid, modifyButton);
        content.setPadding(new Insets(25));
        content.setAlignment(Pos.CENTER);
        setCenter(content);
    }

    private void addRow(GridPane grid, int row, String label, String value) {
        Label nameLabel = new Label(label + ":");
        nameLabel.setStyle("-fx-font-weight: bold;");
        grid.add(nameLabel, 0, row);
        grid.add(new Label(value), 1, row);
    }

    private void addEditableRow(GridPane grid, int row, String label, String value, javafx.scene.Node field) {
        Label nameLabel = new Label(label + ":");
        nameLabel.setStyle("-fx-font-weight: bold;");
        grid.add(nameLabel, 0, row);
        grid.add(editMode ? field : new Label(value), 1, row);
    }
}
