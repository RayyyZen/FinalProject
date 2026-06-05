package com.app.model.simulation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
public class Simulation implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Graph graph;

    private final String name;

    /**
     * Builds a new simulation with a freshly initialized graph
     * (see {@link Graph#initializeGraph()}).
     */
    public Simulation(String name){
        this.graph = new Graph();
        this.graph.initializeGraph();
        this.name = name;
    }

    /**
     * @return the graph held by this simulation
     */
    public Graph getGraph(){
        return this.graph;
    }

    /**
     * @return the graph held by this simulation
     */
    public String getName(){
        return this.name;
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

    public void saveInFile() throws Exception {
        File dir = new File("data");
        dir.mkdirs();

        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("1.bin"));
        out.writeObject(this);
        out.close();
    }

    public static Simulation restoreFromFile() throws Exception {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("1.bin"));
        Simulation simulation = (Simulation) in.readObject();
        in.close();
        return simulation;
    }
}