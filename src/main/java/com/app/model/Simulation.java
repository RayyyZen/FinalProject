package com.app.model;

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
}
