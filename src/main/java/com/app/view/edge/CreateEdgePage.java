package com.app.view.edge;

import com.app.controller.MainController;
import com.app.model.graph.Graph;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;

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
import javafx.util.StringConverter;

/**
 * The creation edge class
 * @version 3.0
 * @since 2.0
 * @author Atahan
 */
public class CreateEdgePage extends BorderPane {

    /**
     * The main controller
     */
    private final MainController controller;

    /**
     * The simulation's graph
     */
    private final Graph graph;

    /**
     * The name's textfield
     */
    private TextField nameField;

    /**
     * The source node
     */
    private final Node presetSource;

    /**
     * Creates the edge adding page
     * @param controller the main controller used for navigation
     * @param graph the selected graph
     */
    public CreateEdgePage(MainController controller, Graph graph) {
        this(controller, graph, null);
    }

    /**
     * Creates the edge adding page
     * @param controller the main controller used for navigation
     * @param graph the selected graph
     * @param presetSource the preselected node
     */
    public CreateEdgePage(MainController controller, Graph graph, Node presetSource) {
        this.controller = controller;
        this.graph = graph;
        this.presetSource = presetSource;
        buildPage();
    }

    /**
     * Builds the page layout
     */
    private void buildPage() {
        Label title = new Label("Create an edge");
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

        ComboBox<LocationState> stateBox = new ComboBox<>();
        stateBox.getItems().addAll(LocationState.values());
        stateBox.setValue(LocationState.OPEN);

        Spinner<Integer> maxAgentsSpinner = new Spinner<>(1, 1000, 20);

        ComboBox<Node> sourceBox = new ComboBox<>();
        ComboBox<Node> destinationBox = new ComboBox<>();
        sourceBox.getItems().addAll(graph.getAllNodes());
        destinationBox.getItems().addAll(graph.getAllNodes());
        sourceBox.setConverter(nodeConverter());
        destinationBox.setConverter(nodeConverter());
        if (presetSource != null) {
            sourceBox.setValue(presetSource);
        }

        TextField distanceField = new TextField();
        distanceField.setPromptText("> 0");

        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);
        addRow(grid, 0, "Name", nameField);
        addRow(grid, 1, "State", stateBox);
        addRow(grid, 2, "Max agents", maxAgentsSpinner);
        addRow(grid, 3, "Source", sourceBox);
        addRow(grid, 4, "Destination", destinationBox);
        addRow(grid, 5, "Distance", distanceField);

        Label errorLabel = new Label();
        Button create = new Button("Create");

        create.getStyleClass().add("primary-button");

        VBox content = new VBox(25, grid, errorLabel, create);
        content.setPadding(new Insets(15));
        content.setAlignment(Pos.CENTER);
        setCenter(content);

        create.setOnAction(e -> {
            Node source = sourceBox.getValue();
            Node destination = destinationBox.getValue();
            if (source == null || destination == null) {
                errorLabel.setText("Please select a source and a destination");
                return;
            }
            if (source.equals(destination)) {
                errorLabel.setText("An edge must connect 2 different nodes");
                return;
            }
            if (Graph.existEdge(graph, source, destination)) {
                errorLabel.setText("This edge already exists");
                return;
            }
            double distance;
            try {
                distance = Double.parseDouble(distanceField.getText().trim());
            } catch (NumberFormatException ex) {
                errorLabel.setText("The distance must be a number");
                return;
            }
            if (distance <= 0) {
                errorLabel.setText("The distance must be > 0");
                return;
            }
            String name = nameField.getText().trim();
            Edge edge;
            if (name.isEmpty()) {
                edge = new Edge(stateBox.getValue(), maxAgentsSpinner.getValue(), source, destination, distance);
            } else {
                edge = new Edge(name, stateBox.getValue(), maxAgentsSpinner.getValue(), source, destination, distance);
            }
            graph.addEdge(edge);
            controller.showGraph();
        });
    }

    /**
     * Builds a converter so the combo boxes display "id. name" instead of the full toString()
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