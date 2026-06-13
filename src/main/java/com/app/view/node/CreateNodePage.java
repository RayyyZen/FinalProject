package com.app.view.node;

import com.app.controller.MainController;
import com.app.model.graph.Graph;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.node.NodeType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

/**
 * The node creation class
 * @version 3.0
 * @since 2.0
 * @author Atahan
 */
public class CreateNodePage extends BorderPane {

    /**
     * The main controller
     */
    private final MainController controller;

    /**
     * The graph of the simulation
     */
    private final Graph graph;

    /**
     * The name's field
     */
    private TextField nameField;

    /**
     * The node's state field
     */
    private ComboBox<LocationState> stateBox;

    /**
     * The node's type field
     */
    private ComboBox<NodeType> typeBox;

    /**
     * The max agents field
     */
    private Spinner<Integer> maxAgentsSpinner;

    /**
     * Creates the node adding page
     * @param controller the main controller used for navigation
     * @param graph the selected graph
     */
    public CreateNodePage(MainController controller, Graph graph) {
        this.controller = controller;
        this.graph = graph;
        buildPage();
    }

    /**
     * Builds the page layout
     */
    private void buildPage() {
        Label title = new Label("Create a node");
        Button back = new Button("Back");
        back.setOnAction(e -> controller.showGraph());

        back.getStyleClass().add("primary-button");

        BorderPane topBar = new BorderPane();
        topBar.setLeft(back);
        topBar.setCenter(title);
        setTop(topBar);

        topBar.setPadding(new Insets(15, 20, 15, 20));

        this.getStyleClass().add("background");

        nameField = new TextField();

        stateBox = new ComboBox<>();
        stateBox.getItems().addAll(LocationState.values());
        stateBox.setValue(LocationState.OPEN);

        typeBox = new ComboBox<>();
        typeBox.getItems().addAll(NodeType.values());
        typeBox.setValue(NodeType.DESTINATION);

        maxAgentsSpinner = new Spinner<>(1, 1000, 10);

        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);
        addRow(grid, 0, "Name", nameField);
        addRow(grid, 1, "State", stateBox);
        addRow(grid, 2, "Type", typeBox);
        addRow(grid, 3, "Capacity", maxAgentsSpinner);

        Label errorLabel = new Label();
        Button create = new Button("Create");

        create.getStyleClass().add("primary-button");

        VBox content = new VBox(25, grid, errorLabel, create);
        content.setPadding(new Insets(15));
        content.setAlignment(Pos.CENTER);
        setCenter(content);

        create.setOnAction(e -> createNode());
    }

    /**
     * Adds one information row to the grid
     * @param grid the target grid
     * @param rowIndex the row index
     * @param label the attribute name
     * @param value the attribute value
     */
    private void addRow(GridPane grid, int rowIndex, String label, javafx.scene.Node field) {
        Label l = new Label(label + " :");
        grid.add(l, 0, rowIndex);
        grid.add(field, 1, rowIndex);
    }

    /**
     * Reads the form, creates the node and adds it to the graph
     */
    private void createNode() {
        String name = nameField.getText().trim();
        Node node;
        if (name.isEmpty()) {
            node = new Node(stateBox.getValue(), typeBox.getValue(), maxAgentsSpinner.getValue());
        } else {
            node = new Node(name, stateBox.getValue(), typeBox.getValue(), maxAgentsSpinner.getValue());
        }
        graph.addNode(node);
        controller.showGraph();
    }
}