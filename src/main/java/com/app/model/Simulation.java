package com.app.model;

public class Simulation {
    
    private Graph graph;

    public Simulation(){
        this.graph = new Graph();
        this.graph.initializeGraph();
    }

    public Graph getGraph(){
        return this.graph;
    }
}
