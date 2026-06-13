package com.app.view.agent;

import com.app.controller.MainController;
import com.app.model.agent.Agent;
import com.app.model.agent.AgentBehavior;
import com.app.model.agent.AgentState;
import com.app.model.graph.location.Location;
import com.app.model.graph.location.node.Node;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * The agent details class
 * @version 3.0
 * @since 1.0
 * @author Atahan, Rémi
 */
public class AgentDetailsPage extends BorderPane{

    /**
     * The main controller
     */
    private final MainController controller;

    /**
     * A specific agent
     */
    private final Agent agent;

    /**
     * Indicates if it is the editing mode
     */
    private boolean editMode;

    /**
     * The agent details contructor
     * @param controller The main controller
     * @param agent A specific agent from the graph
     */
    public AgentDetailsPage(MainController controller, Agent agent) {
        this.controller = controller;
        this.agent = agent;
        this.editMode = false;
        buildPage();
    }

    /**
     * Builds the agent details page
     */
    private void buildPage() {
        Label title = new Label("Agent details");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button("Back to agents");
        backButton.setOnAction(e -> {
            if (agent.getLocation() != null) controller.showAgents(agent.getLocation());
            else controller.showGraph();
        });
        backButton.getStyleClass().add("primary-button");

        Button deleteButton = new Button("Delete agent");
        deleteButton.getStyleClass().add("primary-button");
        deleteButton.setOnAction(e -> {
            Location location = agent.getLocation();
            if (location != null) {
                location.removeAgent(agent);
                controller.showAgents(location);
            } else {
                controller.showGraph();
            }
        });

        HBox actionsBox = new HBox(10, deleteButton);
        actionsBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topBar = new BorderPane();
        topBar.setLeft(backButton);
        topBar.setCenter(title);
        topBar.setRight(actionsBox);
        topBar.setPadding(new Insets(15));
        setTop(topBar);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(25));
        grid.setHgap(20);
        grid.setVgap(12);
        grid.setAlignment(Pos.CENTER);

        addRow(grid, 0, "ID", String.valueOf(agent.getId()));
        addRow(grid, 1, "Name", agent.getName());

        TextField speedField = new TextField(String.valueOf(agent.getSpeed()));
        addEditableRow(grid, 2, "Speed", String.valueOf(agent.getSpeed()), speedField);

        ComboBox<AgentState> stateBox = new ComboBox<>();
        stateBox.getItems().addAll(AgentState.values());
        stateBox.setValue(agent.getState());
        addEditableRow(grid, 3, "State", String.valueOf(agent.getState()), stateBox);

        ComboBox<AgentBehavior> behaviorBox = new ComboBox<>();
        behaviorBox.getItems().addAll(AgentBehavior.values());
        behaviorBox.setValue(agent.getBehavior());
        addEditableRow(grid, 4, "Behavior", String.valueOf(agent.getBehavior()), behaviorBox);

        addRow(grid, 5, "Current location", formatLocation(agent.getLocation()));
        addRow(grid, 6, "Position", String.valueOf(agent.getPosition()));

        ComboBox<Node> destinationBox = new ComboBox<>();
        if (controller.getSimulation() != null) {
            destinationBox.getItems().addAll(controller.getSimulation().getGraph().getAllNodes());
            destinationBox.setConverter(nodeConverter());
        }
        destinationBox.setValue(agent.getDestination());
        addEditableRow(grid, 7, "Destination", formatNode(agent.getDestination()), destinationBox);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button modifyButton = new Button(editMode ? "Save" : "Modify");
        modifyButton.getStyleClass().add("primary-button");
        modifyButton.setOnAction(e -> {
            if (editMode) {
                double speed;
                try {
                    speed = Double.parseDouble(speedField.getText().trim());
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Speed must be a number");
                    return;
                }
                if (speed <= 0) {
                    errorLabel.setText("Speed must be > 0");
                    return;
                }
                agent.setSpeed(speed);
                agent.setState(stateBox.getValue());
                agent.setBehavior(behaviorBox.getValue());
                agent.setDestination(destinationBox.getValue());
            }
            editMode = !editMode;
            buildPage();
        });

        VBox content = new VBox(20, grid, errorLabel, modifyButton);
        content.setAlignment(Pos.CENTER);
        content.setPadding(new Insets(15));
        setCenter(content);
    }

    /**
     * Builds a converter so the destination combo box displays "id. name" instead of the full toString()
     * @return a converter that turns a node into its "id. name" label
     */
    private StringConverter<Node> nodeConverter() {
        return new StringConverter<Node>() {
            @Override
            public String toString(Node n) {
                if (n == null) {
                    return "";
                }
                return n.getId() + ". " + n.getName();
            }

            @Override
            public Node fromString(String t) {
                return null;
            }
        };
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
     * @param row the row index
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

    /**
     * Returns a location's string format
     * @param location A location
     * @return a location's sting format
     */
    private String formatLocation(Location location) {
        return location == null ? "none" : location.getName();
    }

    /**
     * Returns a node's string format
     * @param location A node
     * @return a node's sting format
     */
    private String formatNode(Node node) {
        return node == null ? "none" : node.getName();
    }
}