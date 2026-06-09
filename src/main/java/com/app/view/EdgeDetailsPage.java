package com.app.view;

import com.app.controller.MainController;
import com.app.model.graph.location.edge.Edge;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;

public class EdgeDetailsPage extends BorderPane {
    
    private final MainController controller;
    private final Edge edge;

    /**
     * Creates the edge details page.
     * @param controller the main controller used for navigation
     * @param edge the selected edge
     */
    public EdgeDetailsPage(MainController controller, Edge edge) {
        this.controller = controller;
        this.edge = edge;
        buildPage();
    }

    /**
     * Builds the page layout.
     */
    private void buildPage() {
        Label title = new Label("Edge details");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button backButton = new Button("Back to graph");
        backButton.setOnAction(event -> controller.showGraph());
        backButton.getStyleClass().add("primary-button");

        Button deleteButton = new Button("Delete edge");
        deleteButton.setOnAction(e -> controller.deleteEdge(edge));
        deleteButton.getStyleClass().add("primary-button");

        Button showAgentsButton = new Button("See agents");
        showAgentsButton.setOnAction(e -> controller.showAgents(edge));
        showAgentsButton.getStyleClass().add("primary-button");

        HBox toolBar = new HBox(10, backButton, deleteButton, showAgentsButton);
        toolBar.setPadding(new Insets(15, 20, 15, 20));
        setTop(toolBar);

        BorderPane topBar = new BorderPane();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setLeft(backButton);
        BorderPane.setAlignment(backButton, Pos.CENTER_LEFT);
        topBar.setCenter(title);

        HBox actionsBox = new HBox(10, showAgentsButton, deleteButton);
        actionsBox.setAlignment(Pos.CENTER_RIGHT);
        topBar.setRight(actionsBox);

        setTop(topBar);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);


        addRow(grid, 0, "ID", String.valueOf(edge.getId()));
        addRow(grid, 1, "Name", edge.getName());
        addRow(grid, 2, "State", String.valueOf(edge.getState()));
        addRow(grid, 3, "Source", edge.getSource().getName());
        addRow(grid, 4, "Destination", edge.getDestination().getName());
        addRow(grid, 5, "Distance", String.valueOf(edge.getDistance()));
        addRow(grid, 6, "Agents", edge.getNumberOfAgents() + " / " + edge.getMaxAgents());

        VBox content = new VBox(25);
        content.setPadding(new Insets(25));
        content.getChildren().add(grid);
        content.setAlignment(Pos.CENTER);
        setCenter(content);
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
