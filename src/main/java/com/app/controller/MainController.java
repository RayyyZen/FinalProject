package com.app.controller;

import com.app.model.exception.AppException;
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

    private Simulation simulation;
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
     * @param location the agent's location
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

    /**
     * Create option to add a detail node.
     */
    public void showCreateNode() {
        root.setCenter(new CreateNodePage(this, simulation.getGraph()));
    }

    /**
     * Create option to add a detail edge.
     * @param source the node source
     */
    public void showCreateEdge(Node source) {
        root.setCenter(new CreateEdgePage(this, simulation.getGraph(), source));
    }

    /**
     * Create option to add a detail edge.
     */
    public void showCreateEdge() {
        root.setCenter(new CreateEdgePage(this, simulation.getGraph()));
    }

    /**
     * Create option to add a detail agent.
     */
    public void showCreateAgent() {
        root.setCenter(new CreateAgentPage(this, simulation.getGraph()));
    }

    /**
     * Create option to add a detail agent.
     * @param node the selected node
     */
    public void showCreateAgent(Node node) {
        root.setCenter(new CreateAgentPage(this, simulation.getGraph(), node));
    }

    /**
     * Delete option for a selected node
     * @param node the selected node
     */
    public void deleteNode(Node node) {
        try {
            simulation.getGraph().removeNode(node);
        } catch (AppException e) {
        }
        showGraph();
    }

    /**
     * Delete option for a selected edge
     * @param edge the selected edge
     */
    public void deleteEdge(Edge edge) {
        try {
            simulation.getGraph().removeEdge(edge);
        } catch (AppException e) {
        }
        showGraph();
    }

    /**
     * Delete option for a selected agent
     * @param agent the selected agent
     */
    public void deleteAgent(Agent agent) {
        Location location = agent.getLocation();
        if (location != null) {
            location.removeAgentById(agent.getId());
            showAgents(location);
        } else {
            showGraph();
        }
    }

    public void showFilesPage(){
        root.setCenter(new FilesPage(this, simulation));
    }

    public void showSimulationName(){
        root.setCenter(new SimulationNamePage(this, simulation));
    }

    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }
}
