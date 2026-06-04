package com.app.view;

import com.app.controller.MainController;
import com.app.model.agent.Agent;
import com.app.model.agent.AgentBehavior;
import com.app.model.agent.AgentState;
import com.app.model.exception.AppException;
import com.app.model.graph.Graph;
import com.app.model.graph.location.node.Node;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

/**
 * Page used to create a new agent and place it on a selected node.
 */
public class CreateAgentPage extends BorderPane {

    private final MainController controller;
    private final Graph graph;
    private final Node node;

    /**
     * Creates the agents adding page
     * @param controller the main controller used for navigation
     * @param graph the selected graph
     */
    public CreateAgentPage(MainController controller, Graph graph) {
        this(controller, graph, null);
    }

    /**
     * Creates the agents adding page.
     * @param controller the main controller used for navigation
     * @param graph the selected graph
     * @param node the selected node
     */
    public CreateAgentPage(MainController controller, Graph graph, Node node) {
        this.controller = controller;
        this.graph = graph;
        this.node = node;
        buildPage();
    }

    /**
     * Builds the page layout.
     */
    private void buildPage() {
        Label title = new Label("Create an agent on ");
        Button back = new Button("Back");
        back.setOnAction(e -> controller.showGraph());

        back.getStyleClass().add("primary-button");

        BorderPane topBar = new BorderPane();
        topBar.setLeft(back);
        topBar.setCenter(title);
        setTop(topBar);

        topBar.setPadding(new Insets(15, 20, 15, 20));

        this.getStyleClass().add("background");

        ComboBox<Node> startNodeBox = new ComboBox<>();
        startNodeBox.getItems().addAll(graph.getAllNodes());
        startNodeBox.setConverter(nodeConverter());
        if (node != null) {
            startNodeBox.setValue(node);
        }

        TextField nameField = new TextField();

        TextField speedField = new TextField("1000");
        speedField.setPromptText("> 0");

        ComboBox<AgentState> stateBox = new ComboBox<>();
        stateBox.getItems().addAll(AgentState.values());
        stateBox.setValue(AgentState.CALM);

        ComboBox<AgentBehavior> behaviorBox = new ComboBox<>();
        behaviorBox.getItems().addAll(AgentBehavior.values());
        behaviorBox.setValue(AgentBehavior.NORMAL);

        CheckBox tolerantBox = new CheckBox("Tolerant to congestion");
        tolerantBox.setSelected(true);

        ComboBox<Node> destinationBox = new ComboBox<>();
        destinationBox.getItems().addAll(graph.getAllNodes());
        destinationBox.setConverter(nodeConverter());

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);
        addRow(grid, 0, "Start node", startNodeBox);
        addRow(grid, 1, "Name", nameField);
        addRow(grid, 2, "Speed", speedField);
        addRow(grid, 3, "State", stateBox);
        addRow(grid, 4, "Behavior", behaviorBox);
        addRow(grid, 5, "Congestion", tolerantBox);
        addRow(grid, 6, "Destination", destinationBox);

        Label errorLabel = new Label();
        Button create = new Button("Create");

        create.getStyleClass().add("primary-button");

        VBox content = new VBox(25, grid, errorLabel, create);
        content.setPadding(new Insets(15));
        content.setAlignment(Pos.CENTER);
        setCenter(content);

        create.setOnAction(e -> {
            Node startNode = startNodeBox.getValue();
            if (startNode == null) {
                errorLabel.setText("Please select a start node");
                return;
            }
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                errorLabel.setText("The name is required");
                return;
            }
            double speed;
            try {
                speed = Double.parseDouble(speedField.getText().trim());
            } catch (NumberFormatException ex) {
                errorLabel.setText("The speed must be a number");
                return;
            }
            if (speed <= 0) {
                errorLabel.setText("The speed must be > 0");
                return;
            }
            try {
                Agent agent = new Agent(name, speed, stateBox.getValue(), behaviorBox.getValue(), tolerantBox.isSelected(), null);

                Node destination = destinationBox.getValue();
                if (destination != null) {
                    agent.setDestination(destination);
                }
                startNode.addAgent(agent);
                controller.showAgents(startNode);
            } catch (AppException ex) {
                errorLabel.setText(ex.getMessage());
            }
        });
    }

    /**
     * Builds a converter so the destination combo box displays "id. name" instead of the full toString().
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
     * Adds one information row to the grid.
     * @param grid the target grid
     * @param rowIndex the row index
     * @param label the attribute name
     * @param value the attribute value
     */
    private void addRow(GridPane grid, int rowIndex, String label, javafx.scene.Node field) {
        Label nameLabel = new Label(label + " :");
        grid.add(nameLabel, 0, rowIndex);
        grid.add(field, 1, rowIndex);
    }
}