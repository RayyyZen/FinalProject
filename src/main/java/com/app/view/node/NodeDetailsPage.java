package com.app.view.node;

import com.app.controller.MainController;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.node.NodeType;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

/**
 * The node details class
 * @version 3.0
 * @since 1.0
 * @author Atahan, Rémi
 */
public class NodeDetailsPage extends BorderPane{

    /**
     * The main controller
     */
    private final MainController controller;

    /**
     * A specific node from the simulation's graph
     */
    private final Node node;

    /**
     * Indicates if it is in edit mode or not
     */
    private boolean editMode;

    /**
     * The node details class constructor
     * @param controller The main controller
     * @param node A specific node from the graph
     */
    public NodeDetailsPage(MainController controller, Node node) {
        this.controller = controller;
        this.node = node;
        this.editMode = false;
        buildPage();
    }

    /**
     * Buils the node details page
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

        ComboBox<LocationState> stateBox = new ComboBox<>();
        stateBox.getItems().addAll(LocationState.values());
        stateBox.setValue(node.getState());
        addEditableRow(grid, 2, "State", String.valueOf(node.getState()), stateBox);

        ComboBox<NodeType> typeBox = new ComboBox<>();
        typeBox.getItems().addAll(NodeType.values());
        typeBox.setValue(node.getType());
        addEditableRow(grid, 3, "Type", String.valueOf(node.getType()), typeBox);

        addRow(grid, 4, "Agents", node.getNumberOfAgents() + " / " + node.getMaxAgents());
        addRow(grid, 5, "Passed agents", String.valueOf(node.getNumberOfPassedAgents()));
        addRow(grid, 6, "Average speed", String.valueOf(node.getAverageSpeed()));

        Button modifyButton = new Button(editMode ? "Save" : "Modify");
        modifyButton.getStyleClass().add("primary-button");
        modifyButton.setOnAction(e -> {
            if (editMode) {
                node.setState(stateBox.getValue());
                node.setType(typeBox.getValue());
            }
            editMode = !editMode;
            buildPage();
        });

        VBox content = new VBox(25, grid, modifyButton);
        content.setPadding(new Insets(25));
        content.setAlignment(Pos.CENTER);
        setCenter(content);
    }

    /**
     * Adds one information row to the grid
     * @param grid the target grid
     * @param rowIndex the row index
     * @param label the attribute name
     * @param value the attribute value
     */
    private void addRow(GridPane grid, int row, String label, String value) {
        Label nameLabel = new Label(label + ":");
        nameLabel.setStyle("-fx-font-weight: bold;");
        grid.add(nameLabel, 0, row);
        grid.add(new Label(value), 1, row);
    }

    /**
     * Adds one information row to the grid about the editable button
     * @param grid the target grid
     * @param rowIndex the row index
     * @param label the attribute name
     * @param value the attribute value
     * @param field The field
     */
    private void addEditableRow(GridPane grid, int row, String label, String value, javafx.scene.Node field) {
        Label nameLabel = new Label(label + ":");
        nameLabel.setStyle("-fx-font-weight: bold;");
        grid.add(nameLabel, 0, row);
        grid.add(editMode ? field : new Label(value), 1, row);
    }
}
