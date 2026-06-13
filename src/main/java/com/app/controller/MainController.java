package com.app.controller;

import com.app.model.agent.Agent;
import com.app.model.graph.Graph;
import com.app.model.graph.location.*;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.edge.Edge;
import com.app.model.simulation.Simulation;
import com.app.view.agent.AgentDetailsPage;
import com.app.view.agent.AgentListPage;
import com.app.view.agent.CreateAgentPage;
import com.app.view.agent.EscapedAgentsPage;
import com.app.view.edge.CreateEdgePage;
import com.app.view.edge.EdgeDetailsPage;
import com.app.view.file.FilesPage;
import com.app.view.home.HomePage;
import com.app.view.node.CreateNodePage;
import com.app.view.node.NodeDetailsPage;
import com.app.view.simulation.GraphView;
import com.app.view.simulation.SimulationNamePage;

import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

/**
 * The main controller class
 * @version 3.0
 * @since 1.0
 * @author Rayane, Alexis, Atahan, Remis
 */
public class MainController {

    /**
     * The simulation
     */
    private Simulation simulation;

    /**
     * The main pane that is contained in the scene
     */
    private final BorderPane root;

    /**
     * Builds the controller and immediately shows the home page.
     * @param simulation the shared simulation model
     */
    public MainController(Simulation simulation) {
        this.simulation = simulation;
        this.root = new BorderPane();

        this.showHome();
    }

    /**
     * @return the simulation
     */
    public Simulation getSimulation(){
        return this.simulation;
    }

    /**
     * @return the root JavaFX node, to be wired into the main Scene
     */
    public Parent getRoot(){
        return this.root;
    }

    /**
     * Changes the simulation to another one
     * @param simulation The new simulation
     */
    public void setSimulation(Simulation simulation){
        this.simulation = simulation;
    }

    /**
     * Replaces the center of the root pane with the home page
     */
    public void showHome() {
        HomePage page = new HomePage(this);
        root.setCenter(page);
    }

    /**
     * Replaces the center of the root pane with the graph view
     */
    public void showGraph() {
        GraphView page = new GraphView(this, simulation);
        root.setCenter(page);
    }

    /**
     * Shows the details page for a selected node
     * @param node the selected node
     */
    public void showNodeDetails(Node node) {
        NodeDetailsPage page = new NodeDetailsPage(this, node);
        root.setCenter(page);
    }

    /**
     * Shows the details page for a selected edge
     * @param edge the selected edge
     */
    public void showEdgeDetails(Edge edge) {
        EdgeDetailsPage page = new EdgeDetailsPage(this, edge);
        root.setCenter(page);
    }

    /**
     * Shows the page containing all agents
     * @param location the agent's location
     */
    public void showAgents(Location location) {
        AgentListPage page = new AgentListPage(this, location);
        root.setCenter(page);
    }

    /**
     * Shows the details page for a selected agent
     * @param agent the selected agent
     */
    public void showAgentDetails(Agent agent) {
        AgentDetailsPage page = new AgentDetailsPage(this, agent);
        root.setCenter(page);
    }

    /**
     * Create option to add a detail node
     */
    public void showCreateNode() {
        root.setCenter(new CreateNodePage(this, simulation.getGraph()));
    }

    /**
     * Create option to add a detail edge
     * @param source the node source
     */
    public void showCreateEdge(Node source) {
        root.setCenter(new CreateEdgePage(this, simulation.getGraph(), source));
    }

    /**
     * Create option to add a detail edge
     */
    public void showCreateEdge() {
        root.setCenter(new CreateEdgePage(this, simulation.getGraph()));
    }

    /**
     * Create option to add a detail agent
     */
    public void showCreateAgent() {
        root.setCenter(new CreateAgentPage(this, simulation.getGraph()));
    }

    /**
     * Create option to add a detail agent
     * @param node the selected node
     */
    public void showCreateAgent(Node node) {
        root.setCenter(new CreateAgentPage(this, simulation.getGraph(), node));
    }
    
    /**
     * Shows the files page
     */
    public void showFilesPage(){
        root.setCenter(new FilesPage(this, simulation));
    }

    /**
     * Shows the simulation's name page
     */
    public void showSimulationName(){
        root.setCenter(new SimulationNamePage(this, simulation));
    }

    /**
     * Shows the random simulation's name page
     */
    public void showRandomSimulationName(){
        root.setCenter(new SimulationNamePage(this, simulation, Graph.generateRandomGraph()));
    }

    /**
     * Shows the escaped agents page
     */
    public void showEscapedAgents(){
        root.setCenter(new EscapedAgentsPage(this));
    }
}