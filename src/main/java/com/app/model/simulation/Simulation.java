package com.app.model.simulation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.app.model.agent.Agent;
import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;

/**
 * Top-level model object holding the state of the running simulation.
 * For now it only owns the {@link Graph}; agents and ticks will be added later.
 */
public class Simulation {

    private final Graph graph;

    /**
     * Builds a new simulation with a freshly initialized graph
     * (see {@link Graph#initializeGraph()}).
     */
    public Simulation(){
        this.graph = new Graph();
        this.graph.initializeGraph();
    }

    /**
     * @return the graph held by this simulation
     */
    public Graph getGraph(){
        return this.graph;
    }

    public void move(){
        List<Node> nodes = this.graph.getAllNodes();
        List<Edge> edges = this.graph.getAllEdges();

        List<Agent> allAgents = new ArrayList<>();

        for(Node node : nodes){
            for(Agent agent : node.getAgents()){
                allAgents.add(agent);
            }
        }

        for(Edge edge : edges){
            for(Agent agent : edge.getAgents()){
                allAgents.add(agent);
            }
        }

        Iterator<Agent> iterator = allAgents.iterator();

        while(iterator.hasNext()){
            Agent agent = iterator.next();
            agent.moveToNextLocation(graph);
        }
    }
}
