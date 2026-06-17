package com.app.model.simulation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.app.model.agent.Agent;
import com.app.model.agent.path.shortestpath.ShortestTimePath;
import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.node.NodeType;
import com.app.model.util.Check;

/**
 * The simulation class that contains a name, a graph and the list of escaped agents.
 * Provides the main simulation loop methods (move, clearExits, optimizeDestinations).
 * @version 3.0
 * @since 2.0
 * @author Rayane
 */
public class Simulation implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The name of the simulation
     */
    private final String name;

    /**
     * The graph that contains the nodes, edges and agents
     */
    private final Graph graph;

    /**
     * The escaped agents
     */
    private List<Agent> escapedAgents;

    /**
     * The maximum number of cleared exit nodes from its agents at once
     */
    private final int MAXEXITCLEAR = 5;

    /**
     * The simulation constructor that takes as arguments its name and graph
     * @param name The name of the simulation
     * @param graph The graph of the simulation
     */
    public Simulation(String name, Graph graph){
        Check.checkNullArgument(name, "The simulation's name is null");
        Check.checkNullArgument(graph, "The simulation's graph is null");

        this.name = name;
        this.graph = graph;
        this.escapedAgents = new ArrayList<>();
    }

    /**
     * The simulation constructor that takes as an argument only its name
     * @param name The name of the simulation
     */
    public Simulation(String name){
        this(name, new Graph());
    }

    /**
     * Returns the name of the simulation
     * @return the name of the simulation
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the graph contained in the simulation
     * @return the graph contained in the simulation
     */
    public Graph getGraph(){
        return this.graph;
    }

    /**
     * Returns the escaped agents
     * @return the escaped agents
     */
    public List<Agent> getEscapedAgents(){
        return this.escapedAgents;
    }

    /**
     * Optimizes the destinations of all the agents
     */
    public void optimizeDestinations(){
        List<Agent> allAgents = this.graph.getAllAgents();

        Iterator<Agent> iterator = allAgents.iterator();

        while(iterator.hasNext()){
            Agent agent = iterator.next();
            Node source = agent.getLocation().getSourceNode();
            Node destination = new ShortestTimePath().getClosestExit(source, this.graph);

            agent.setDestination(destination);
        }
    }

    /**
     * Moves all the agents in all the locations of the graph contained in the simulation according to there destinations
     */
    public void move(){
        List<Agent> allAgents = this.graph.getAllAgents();
        Collections.sort(allAgents);

        Iterator<Agent> iterator = allAgents.iterator();

        while(iterator.hasNext()){
            Agent agent = iterator.next();
            agent.moveToNextLocation(graph);
        }
    }

    /**
     * Clears the exit locations from its agents
     */
    public void clearExits(){
        Iterator<Node> iterator = this.graph.getAllNodes().iterator();

        while(iterator.hasNext()){
            Node node = iterator.next();
            if(node.getType() == NodeType.EXIT){
                List<Agent> agents = new ArrayList<>(node.getAgents());
                Collections.sort(agents);

                Iterator<Agent> it = agents.iterator();

                int counter = 0;
                while(counter < MAXEXITCLEAR && it.hasNext()){
                    Agent agent = it.next();
                    node.removeAgent(agent);
                    this.escapedAgents.add(agent);
                    counter++;
                }
            }
        }
    }

    public Node highDegreeNode(){
        if(this.graph.getAllNodes().isEmpty()){
            return null;
        }

        Node high = this.graph.getAllNodes().get(0);
        int maxDegree = this.graph.getAllAdjEdges(high).size();

        for(Node node : this.graph.getAllNodes()){
            int degree = this.graph.getAllAdjEdges(node).size();
            if(degree > maxDegree){
                maxDegree = degree;
            }
        }

        return high;
    }

    public List<Node> getNodesAdjToHigh(int value){
        Node high = this.highDegreeNode();

        List<Node> nodes = new ArrayList<>();
        Map<Node, Integer> distTo = new HashMap<>();

        Map<Node, Boolean> marked = new HashMap<>();

        distTo.put(high, 0);
        marked.put(high, true);

        while(!nodes.isEmpty()){
            Node node = nodes.getFirst();
            for(Node n : this.graph.getAdjNodes(node)){
                distTo.put(n, distTo.get(node) + 1);
                nodes.addLast(n);
            }
        }

        List<Node> ns = new ArrayList<>();

        for(Map.Entry<Node, Integer> me : distTo.entrySet()){
            if(me.getValue() == value){
                ns.add(me.getKey());
            }
        }

        return ns;
    }
}