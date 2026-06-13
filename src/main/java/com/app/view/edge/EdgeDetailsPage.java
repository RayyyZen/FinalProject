package com.app.view.edge;

import com.app.controller.MainController;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.edge.Edge;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class EdgeDetailsPage extends BorderPane {

    private final MainController controller;
    private final Edge edge;
    private boolean editMode;

    public EdgeDetailsPage(MainController controller, Edge edge) {
        this.controller = controller;
        this.edge = edge;
        this.editMode = false;
        buildPage();
    }

    private void buildPage() {
        Label title = new Label("Edge details");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button("Back to graph");
        backButton.setOnAction(event -> controller.showGraph());
        backButton.getStyleClass().add("primary-button");

        Button deleteButton = new Button("Delete edge");
        deleteButton.setOnAction(e -> {
            controller.getSimulation().getGraph().removeEdge(edge);
            controller.showGraph();
        });
        deleteButton.getStyleClass().add("primary-button");

        Button showAgentsButton = new Button("See agents");
        showAgentsButton.setOnAction(e -> controller.showAgents(edge));
        showAgentsButton.getStyleClass().add("primary-button");

        HBox actionsBox = new HBox(10, showAgentsButton, deleteButton);
        actionsBox.setAlignment(Pos.CENTER_RIGHT);

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
        topBar.setCenter(title);
        topBar.setRight(actionsBox);
        setTop(topBar);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(12);

        addRow(grid, 0, "ID", String.valueOf(edge.getId()));
        addRow(grid, 1, "Name", edge.getName());

        ComboBox<LocationState> stateBox = new ComboBox<>();
        stateBox.getItems().addAll(LocationState.values());
        stateBox.setValue(edge.getState());
        addEditableRow(grid, 2, "State", String.valueOf(edge.getState()), stateBox);

        addRow(grid, 3, "Source", edge.getSource().getName());
        addRow(grid, 4, "Destination", edge.getDestination().getName());
        addRow(grid, 5, "Distance", String.valueOf(edge.getDistance()));
        addRow(grid, 6, "Agents", edge.getNumberOfAgents() + " / " + edge.getMaxAgents());

        Button modifyButton = new Button(editMode ? "Save" : "Modify");
        modifyButton.getStyleClass().add("primary-button");
        modifyButton.setOnAction(e -> {
            if (editMode) {
                edge.setState(stateBox.getValue());
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
