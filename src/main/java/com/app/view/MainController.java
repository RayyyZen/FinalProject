package com.app.view;

import com.app.model.Simulation;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * Top-level UI controller. Owns the root {@link BorderPane} and swaps the
 * page currently displayed in its center (HomePage, GraphView, ...).
 */
public class MainController {

    private final Simulation simulation;
    private final BorderPane root;

    /**
     * Builds the controller and immediately shows the home page.
     * @param simulation the shared simulation model
     */
    public MainController(Simulation simulation) {
        this.simulation = simulation;
        this.root = new BorderPane();

        showHome();
    }

    /**
     * Replaces the center of the root pane with the home page.
     */
    public void showHome() {
        HomePage page = new HomePage(this, simulation);
        root.setCenter(page);
    }

    /**
     * Replaces the center of the root pane with the graph view.
     */
    public void showGraph() {
        GraphView page = new GraphView(simulation.getGraph());
        root.setCenter(page);
    }

    /**
     * public void showNodeDetails(){
        NodedetailsPage page = new NodedetailsPage(node);
        root.setCenter(page);
    }/

    /**
     * @return the root JavaFX node, to be wired into the main Scene
     */
    public Parent getRoot() {
        return root;
    }
}
