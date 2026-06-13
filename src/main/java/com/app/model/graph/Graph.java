package com.app.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import com.app.model.agent.Agent;
import com.app.model.agent.AgentBehavior;
import com.app.model.agent.AgentState;
import com.app.model.agent.path.shortestpath.ShortestTimePath;
import com.app.model.graph.location.Location;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.*;
import com.app.model.util.Check;

/**
 * The graph class that contains nodes, edges and agents
 * @version 3.0
 * @since 1.0
 * @author Rayane
 */
public class Graph implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The unique id of the location
     */
    private final int id;
    
    /**
     * The nodes of the graph
     */
    private List<Node> nodes;

    /**
     * The edges of the graph
     */
    private List<Edge> edges;

    /**
     * The unique id that was given to the last created graph + 1
     */
    private static int lastID = 0;

    /**
     * Minimum number of agents in a location if there is a random graph generation
     */
    private static final int MINAGENTS = 10;

    /**
     * Maximum number of agents in a location if there is a random graph generation
     */
    private static final int MAXAGENTS = 100;

    /**
     * Minimum speed of an agent if there is a random graph generation (in m/s)
     */
    private static final double MINSPEED = 1.0;

    /**
     * Maximum speed of an agent if there is a random graph generation (in m/s)
     */
    private static final double MAXSPEED = 20.0;

    /**
     * Minimum number of nodes in the graph if there is a random graph generation
     */
    private static final int MINNODES = 10;

    /**
     * Maximum number of nodes in the graph if there is a random graph generation
     */
    private static final int MAXNODES = 15;

    /**
     * Minimum number of exit nodes in the graph if there is a random graph generation
     */
    private static final int MINEXITS = 2;

    /**
     * Maximum number of exit nodes in the graph if there is a random graph generation
     */
    private static final int MAXEXITS = 5;

    /**
     * Minimum number of edges in the graph if there is a random graph generation
     */
    private static final int MINEDGES = 20;

    /**
     * Maximum number of nodes in the graph if there is a random graph generation
     */
    private static final int MAXEDGES = 30;

    /**
     * Minimum distance of an edge in the graph if there is a random graph generation (in meters)
     */
    private static final double MINDISTANCE = 100.0;

    /**
     * Maximum distance of an edge in the graph if there is a random graph generation (in meters)
     */
    private static final double MAXDISTANCE = 2000.0;

    /**
     * Checks if an argument of any method of the graph class is null
     * @param obj The argument that will be checked
     * @param attribute The attribute of the graph that is null
     */
    private void checkGraphNullArgument(Object obj, String attribute){
        Check.checkNullArgument(obj, "The " + attribute + " that was going to be added to the graph is null !");
    }

    /**
     * The graph constructor that doesn't take any arguments
     */
    public Graph(){
        this.id = lastID;
        lastID++;

        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    /**
     * Returns true if an edge from source to destination already exists in a graph, or false otherwise
     * @param graph The graph that contains the source and destination nodes
     * @param source The source node
     * @param destination The destination node
     * @return true if an edge from source to destination already exists in a graph, or false otherwise
     */
    public static boolean existEdge(Graph graph, Node source, Node destination){
        Check.checkNullArgument(graph, "The graph in the graph class is null");
        Check.checkNullArgument(source, "The source node in the graph class is null");
        Check.checkNullArgument(destination, "The destination node in the graph class is null");

        for(Edge edge : graph.edges){
            if(edge.getSource().equals(source) && edge.getDestination().equals(destination)){
                return true;
            }
        }

        return false;
    }

    /**
     * Returns a random graph
     * @return a random graph
     */
    public static Graph generateRandomGraph(){
        Graph randomGraph = new Graph();

        List<LocationState> states = List.of(LocationState.CLOSED, LocationState.OPEN);

        //List<NodeType> nodeTypes = List.of(NodeType.DESTINATION, NodeType.EXIT);

        int numberOfNodes = ThreadLocalRandom.current().nextInt(MINNODES, MAXNODES);
        int numberOfExits = ThreadLocalRandom.current().nextInt(MINEXITS, MAXEXITS);

        for(int i = 0; i < numberOfNodes; i++){
            NodeType nodeType = NodeType.DESTINATION;
            if(i < numberOfExits){
                nodeType = NodeType.EXIT;
            }

            LocationState state = states.get(ThreadLocalRandom.current().nextInt(0, states.size()));
            //NodeType nodeType = nodeTypes.get(ThreadLocalRandom.current().nextInt(0, nodeTypes.size()));
            int maxAgents = ThreadLocalRandom.current().nextInt(MINAGENTS, MAXAGENTS + 1);

            Node node = new Node(state, nodeType, maxAgents);

            int numberOfAgents = ThreadLocalRandom.current().nextInt(MINAGENTS, maxAgents + 1);

            for(int j = 0; j < numberOfAgents; j++){
                List<AgentState> agentStates = List.of(AgentState.CALM, AgentState.TIRED, AgentState.PANICKED, AgentState.CRAZY);
                List<AgentBehavior> agentBehaviors = List.of(AgentBehavior.PRIORITYGIVEN, AgentBehavior.NORMAL, AgentBehavior.PRIORITYTAKEN);

                AgentState agentState = agentStates.get(ThreadLocalRandom.current().nextInt(0, agentStates.size()));
                AgentBehavior agentBehavior = agentBehaviors.get(ThreadLocalRandom.current().nextInt(0, agentBehaviors.size()));
                double speed = ThreadLocalRandom.current().nextDouble(MINSPEED, MAXSPEED);

                Node destination = new ShortestTimePath().getClosestExit(node, randomGraph);

                Agent agent = new Agent(speed, agentState, agentBehavior, node);
                agent.setDestination(destination);

                node.addAgent(agent);
            }

            randomGraph.nodes.add(node);
        }

        int numberOfEdges = ThreadLocalRandom.current().nextInt(MINEDGES, MAXEDGES);

        for(int i = 0; i < numberOfEdges; i++){
            LocationState state = states.get(ThreadLocalRandom.current().nextInt(0, states.size()));
            int maxAgents = ThreadLocalRandom.current().nextInt(MINAGENTS, MAXAGENTS);
            double distance = ThreadLocalRandom.current().nextDouble(MINDISTANCE, MAXDISTANCE);

            Node source = randomGraph.nodes.get(ThreadLocalRandom.current().nextInt(0, randomGraph.nodes.size()));
            Node destination = randomGraph.nodes.get(ThreadLocalRandom.current().nextInt(0, randomGraph.nodes.size()));

            if(!source.equals(destination) && !existEdge(randomGraph, source, destination)){
                Edge edge = new Edge(state, maxAgents, source, destination, distance);
                randomGraph.edges.add(edge);
            }
        }

        return randomGraph;
    }

    /**
     * Returns the number of nodes of the graph
     * @return the number of nodes of the graph
     */
    public int getNumberOfNodes(){
        return this.nodes.size();
    }

    /**
     * Returns the number of edges of the graph
     * @return the number of edges of the graph
     */
    public int getNumberOfEdges(){
        return this.edges.size();
    }

    /**
     * Returns all the nodes of the graph
     * @return all the nodes of the graph
     */
    public List<Node> getAllNodes(){
        return this.nodes;
    }

    /**
     * Returns all the edges of the graph
     * @return all the edges of the graph
     */
    public List<Edge> getAllEdges(){
        return this.edges;
    }

    /**
     * Returns all the locations of the graph
     * @return all the locations of the graph
     */
    public List<Location> getAllLocations(){
        List<Location> allLocations = new ArrayList<>();
        
        allLocations.addAll(this.nodes);
        allLocations.addAll(this.edges);

        return allLocations;
    }

    /**
     * Returns all the agents of the graph
     * @return all the agents of the graph
     */
    public List<Agent> getAllAgents(){
        List<Location> allLocations = this.getAllLocations();

        List<Agent> allAgents = new ArrayList<>();

        for(Location location : allLocations){
            allAgents.addAll(location.getAgents());
        }

        return allAgents;
    }

    /**
     * Returns the adjacent nodes of a node in the graph
     * @param node A node from the graph
     * @return the adjacent nodes of a node in the graph
     */
    public List<Node> getAdjNodes(Node node){
        Check.checkNullArgument(node, "The node is null !");

        List<Node> adjNodes = new ArrayList<>();
        
        for(Edge edge : this.edges){
            if(edge.getSource().equals(node)){
                adjNodes.add(edge.getDestination());
            }
        }

        return adjNodes;
    }

    /**
     * Returns the adjacent edges of a node in the graph
     * @param node A node from the graph
     * @param takeAll Indicated if even the opposite directions adjacent edges must be collected
     * @return the adjacent edges of a node in the graph
     */
    public List<Edge> adjEdges(Node node, boolean takeAll){
        Check.checkNullArgument(node, "The node is null !");

        List<Edge> adjEdges = new ArrayList<>();
        
        for(Edge edge : this.edges){
            if(edge.getSource().equals(node) || (takeAll && edge.getDestination().equals(node))){
                adjEdges.add(edge);
            }
        }

        return adjEdges;
    }

    /**
     * Returns the adjacent edges of a node in the graph
     * @param node A node from the graph
     * @return the adjacent edges of a node in the graph
     */
    public List<Edge> getAdjEdges(Node node){
        return this.adjEdges(node, false);
    }

    /**
     * Returns all the adjacent edges of a node in the graph even if its in the opposite direction
     * @param node A node from the graph
     * @return all the adjacent edges of a node in the graph even if its in the opposite direction
     */
    public List<Edge> getAllAdjEdges(Node node){
        return this.adjEdges(node, true);
    }

    /**
     * Adds a new node to the graph
     * @param node The new node that will be added
     */
    public void addNode(Node node){
        checkGraphNullArgument(node, "node");
        this.nodes.add(node);
    }

    /**
     * Adds a new edge to the graph
     * @param edge The new edge that will be added
     */
    public void addEdge(Edge edge){
        checkGraphNullArgument(edge, "edge");
        this.edges.add(edge);
    }

    /**
     * Adds default nodes to the graph
     * @param numberOfNodes The number of added nodes
     */
    public void addDefaultNodes(int numberOfNodes){
        Check.checkNumber(numberOfNodes);

        for(int i = 0; i < numberOfNodes; i++){
            this.nodes.add(new Node());
        }
    }

    /**
     * Adds default edges to the graph
     * @param numberOfEdges The number of added edges
     */
    public void addDefaultEdges(int numberOfEdges){
        Check.checkNumber(numberOfEdges);

        List<Edge> possibleEdges = new ArrayList<>();

        for(Node source : this.nodes){
            for(Node destination : this.nodes){
                if(!source.equals(destination) && !existEdge(this, source, destination)){
                    possibleEdges.add(new Edge(source, destination, (MINDISTANCE + MAXDISTANCE) / 2));
                }
            }
        }

        Collections.shuffle(possibleEdges);

        int counter = 0;

        Iterator<Edge> iterator = possibleEdges.iterator();

        while(iterator.hasNext() && counter < numberOfEdges){
            Edge edge = iterator.next();
            this.edges.add(edge);
            counter++;
        }
    }

    /**
     * Returns the nodes where we can add agents
     * @return the nodes where we can add agents
     */
    private List<Node> getPossibleNodes(){
        List<Node> possibleNodes = new ArrayList<>();

        for(Node node : this.nodes){
            if(node.getNumberOfAgents() < node.getMaxAgents()){
                possibleNodes.add(node);
            }
        }

        return possibleNodes;
    }

    /**
     * Adds default agents to the graph
     * @param numberOfAgents The number of added agents
     */
    public void addDefaultAgents(int numberOfAgents){
        Check.checkNumber(numberOfAgents);

        for(int i = 0; i < numberOfAgents; i++){
            List<Node> possibleNodes = this.getPossibleNodes();
            if(possibleNodes.isEmpty()){
                return;
            }
            int index = ThreadLocalRandom.current().nextInt(0, possibleNodes.size());
            possibleNodes.get(index).addAgent(new Agent());
        }
    }

    /**
     * Removes all agents of a node in the graph
     * @param node The node that will be emptied of its agents 
     */
    public void removeAgentsFromNode(Node node) {
        Check.checkNullArgument(node, "The node is null !");

        if(!this.nodes.contains(node)){
            throw new IllegalStateException("The node you want to change is not contained in the graph !");
        }

        List<Node> adjNodes = this.getAdjNodes(node);
        Iterator<Node> it = adjNodes.iterator();

        while(it.hasNext()){
            Node n = it.next();
            if(!n.valid()){
                it.remove();
            }
        }

        if(adjNodes.isEmpty()){
            node.removeAllAgents();
            return ;
        }

        int numberOfAdjNodes = adjNodes.size();
        int counter = 0;

        List<Agent> removedAgents = new ArrayList<>(node.getAgents());
        Iterator<Agent> iterator = removedAgents.iterator();

        node.removeAllAgents();

        while(counter < numberOfAdjNodes && !removedAgents.isEmpty()){
            Node n = adjNodes.get(counter);
            int numberOfAddedAgents = n.getMaxAgents() - n.getNumberOfAgents();
            int count = 0;

            while(iterator.hasNext()){
                if(counter < numberOfAdjNodes - 1){
                    if(count >= numberOfAddedAgents){
                        break;
                    }
                }
                Agent agent = iterator.next();
                n.forceAddAgent(agent);
                iterator.remove();
                count++;
            }

            counter++;
        }
    }

    /**
     * Removes all agents of an edge in the graph
     * @param node The edge that will be emptied of its agents 
     */
    public void removeAgentsFromEdge(Edge edge) {
        Check.checkNullArgument(edge, "The edge is null !");

        if(!this.edges.contains(edge)){
            throw new IllegalStateException("The edge you want to change is not contained in the graph !");
        }

        List<Agent> removedAgents = new ArrayList<>(edge.getAgents());
        Iterator<Agent> iterator = removedAgents.iterator();

        edge.removeAllAgents();

        Node source = edge.getSource();

        while(iterator.hasNext()){
            Agent agent = iterator.next();
            source.forceAddAgent(agent);
        }
    }

    /**
     * Removes a node from the graph
     * @param node The node that will be removed
     */
    public void removeNode(Node node) {

        Iterator<Edge> iterator = this.getAllAdjEdges(node).iterator();

        while(iterator.hasNext()){
            Edge edge = iterator.next();
            this.removeAgentsFromEdge(edge);
        }

        this.removeAgentsFromNode(node);
        this.nodes.remove(node);

        iterator = this.getAllAdjEdges(node).iterator();

        while(iterator.hasNext()){
            Edge edge = iterator.next();
            this.removeAgentsFromEdge(edge);
            iterator.remove();
        }
    }

    /**
     * Removes an edge from the graph
     * @param edge The edge that will be removed
     */
    public void removeEdge(Edge edge) {
        Check.checkNullArgument(edge, "The edge is null !");

        this.removeAgentsFromEdge(edge);
        this.edges.remove(edge);
    }

    /**
     * Removes all the nodes of the graph
     */
    public void removeAllNodes(){
        this.nodes.clear();
    }

    /**
     * Removes all the edges of the graph
     */
    public void removeAllEdges(){
        this.edges.clear();
    }

    /**
     * Returns a String that contains the graph's attributes
     * @return A String that contains the graph's attributes
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        string.append("Graph : \n");

        for(Node node : this.nodes){
            string.append("-> " + node + "\nEdges : \n");
            
            for(Edge edge : this.getAdjEdges(node)){
                string.append("-> " + edge + "\n");
            }

            string.append("\n-------------\n");
        }

        return string.toString();
    }

    /**
     * Checks if a graph is equal to an object
     * @param obj The object that will be compared to the graph
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Graph)){
            return false;
        }
        
        if(this == obj){
            return true;
        }

        Graph other = (Graph) obj;
        return this.id == other.id;
    }

    /**
     * Returns the hash code of the graph
     * @return the hash code of the graph
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }
}