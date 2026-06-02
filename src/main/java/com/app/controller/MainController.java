package com.app.controller;

import com.app.model.agent.Agent;
import com.app.model.graph.location.*;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.edge.Edge;
import com.app.model.simulation.Simulation;
import com.app.view.*;

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
        HomePage page = new HomePage(this);
        root.setCenter(page);
    }

    /**
     * Replaces the center of the root pane with the graph view.
     */
    public void showGraph() {
        GraphView page = new GraphView(this,simulation);
        root.setCenter(page);
    }

    /**
     * Shows the details page for a selected node.
     * @param node the selected node
     */
    public void showNodeDetails(Node node) {
        NodeDetailsPage page = new NodeDetailsPage(this, node);
        root.setCenter(page);
    }

    /**
     * Shows the details page for a selected edge.
     * @param edge the selected edge
     */
    public void showEdgeDetails(Edge edge) {
        EdgeDetailsPage page = new EdgeDetailsPage(this, edge);
        root.setCenter(page);
    }

    /**
     * @return the root JavaFX node, to be wired into the main Scene
     */
    public Parent getRoot() {
        return root;
    }

    /**
     * Shows the page containing all agents.
     */
    public void showAgents(Location location) {
        AgentListPage page = new AgentListPage(this, location);
        root.setCenter(page);
    }

    /**
     * Shows the details page for a selected agent.
     * @param agent the selected agent
     */
    public void showAgentDetails(Agent agent) {
        AgentDetailsPage page = new AgentDetailsPage(this, agent);
        root.setCenter(page);
    }

    public void showCreateNode() {
        root.setCenter(new CreateNodePage(this, simulation.getGraph()));
    }

    public void showCreateEdge() {
    root.setCenter(new CreateEdgePage(this, simulation.getGraph()));
    }

    public void showCreateAgent(Node node) {
        root.setCenter(new CreateAgentPage(this, simulation.getGraph(), node));
    }
}
