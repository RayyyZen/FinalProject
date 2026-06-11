package com.app.model.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.model.agent.Agent;
import com.app.model.exception.AppException;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.*;
import com.app.model.util.Check;

public class Graph implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * The nodes of the graph
     */
    private List<Node> nodes;

    /**
     * The edges of the graph
     */
    private List<Edge> edges;

    /**
     * Checks if an argument of any method of the graph class is null
     * @param obj The argument that will be checked
     * @param attribute The attribute of the graph that is null
     */
    private void checkGraphNullArgument(Object obj, String attribute){
        Check.checkNullArgument(obj, "The " + attribute + " that was going to be added to the graph is null !");
    }

    public Graph(){
        this.nodes = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public boolean existEdge(Node source, Node destination){
        Check.checkNullArgument(source, "The source node in the graph class is null");
        Check.checkNullArgument(destination, "The destination node in the graph class is null");

        for(Edge edge : this.edges){
            if(edge.getSource().equals(source) && edge.getDestination().equals(destination)){
                return true;
            }
        }

        return false;
    }

    public void initializeGraph(){
        List<LocationState> states = List.of(LocationState.CLOSED, LocationState.OPEN);
        List<NodeType> types = List.of(NodeType.DESTINATION, NodeType.EXIT);

        int minNumberOfAgents = 10;
        int maxNumberOfAgents = 100;

        int numberOfNodes = 10;

        for(int i = 0; i < numberOfNodes; i++){
            LocationState state = states.get(ThreadLocalRandom.current().nextInt(0, states.size()));
            NodeType type = types.get(ThreadLocalRandom.current().nextInt(0, types.size()));
            int maxAgents = ThreadLocalRandom.current().nextInt(minNumberOfAgents, maxNumberOfAgents);

            Node node = new Node(state, type, maxAgents);
            this.nodes.add(node);
        }

        int numberOfEdges = 21;
        double minDistance = 1.0;
        double maxDistance = 2000.0;

        for(int i = 0; i < numberOfEdges; i++){
            LocationState state = states.get(ThreadLocalRandom.current().nextInt(0, states.size()));
            int maxAgents = ThreadLocalRandom.current().nextInt(minNumberOfAgents, maxNumberOfAgents);
            double distance = ThreadLocalRandom.current().nextDouble(minDistance, maxDistance);

            Node source = this.nodes.get(ThreadLocalRandom.current().nextInt(0, this.nodes.size()));
            Node destination = this.nodes.get(ThreadLocalRandom.current().nextInt(0, this.nodes.size()));

            if(!source.equals(destination) && !this.existEdge(source, destination)){
                Edge edge = new Edge(state, maxAgents, source, destination, distance);
                this.edges.add(edge);
            }
        }

        int numberOfAgents = 10;

        for(int i = 0; i < numberOfAgents; i++){
            Node node = this.nodes.get(ThreadLocalRandom.current().nextInt(0, this.nodes.size()));
            Node destination = this.nodes.get(ThreadLocalRandom.current().nextInt(0, this.nodes.size()));

            if(!node.equals(destination)){
                Agent agent = new Agent();
                agent.setDestination(destination);
                try{
                    node.addAgent(agent);
                }catch(AppException e){
                        
                }
            }
        }

    }

    public List<Node> getAllNodes(){
        return this.nodes;
    }

    public List<Edge> getAllEdges(){
        return this.edges;
    }

    public void addNode(Node node){
        checkGraphNullArgument(node, "node");
        this.nodes.add(node);
    }

    public void removeNode(Node node) throws AppException {

        Iterator<Edge> iterator = this.getAllEdges(node).iterator();

        while(iterator.hasNext()){
            Edge edge = iterator.next();
            this.removeAgentsFromEdge(edge);
        }
        this.removeAgentsFromNode(node);
        this.nodes.remove(node);

        iterator = this.getAllEdges(node).iterator();

        while(iterator.hasNext()){
            Edge edge = iterator.next();
            this.removeAgentsFromEdge(edge);
        }
    }

    public void removeAllNodes(){
        this.nodes.clear();
    }

    public void addEdge(Edge edge){
        checkGraphNullArgument(edge, "edge");
        this.edges.add(edge);
    }

    public void removeEdge(Edge edge) throws AppException {
        Check.checkNullArgument(edge, "The edge is null !");

        this.removeAgentsFromEdge(edge);
        this.edges.remove(edge);
    }

    public void removeAllEdges(){
        this.edges.clear();
    }

    public List<Node> getAdj(Node node){
        Check.checkNullArgument(node, "The node is null !");

        List<Node> adj = new ArrayList<>();
        
        for(Edge edge : this.edges){
            if(edge.getSource().equals(node)){
                adj.add(edge.getDestination());
            }
        }

        return adj;
    }

    public List<Edge> getEdges(Node node){
        Check.checkNullArgument(node, "The node is null !");

        List<Edge> edges = new ArrayList<>();
        
        for(Edge edge : this.edges){
            if(edge.getSource().equals(node)){
                edges.add(edge);
            }
        }

        return edges;
    }

    public List<Edge> getAllEdges(Node node){
        Check.checkNullArgument(node, "The node is null !");

        List<Edge> edges = new ArrayList<>();
        
        for(Edge edge : this.edges){
            if(edge.getSource().equals(node) || edge.getDestination().equals(node)){
                edges.add(edge);
            }
        }

        return edges;
    }

    public int getNumberOfNodes(){
        return this.nodes.size();
    }

    public int getNumberOfEdges(){
        return this.edges.size();
    }

    public void removeAgentsFromNode(Node node) throws AppException {
        Check.checkNullArgument(node, "The node is null !");
        if(!this.nodes.contains(node)){
            throw new AppException("The node you want to change is not contained in the graph !");
        }

        List<Node> adj = this.getAdj(node);

        if(adj.isEmpty()){
            node.removeAllAgents();
            return ;
        }

        int size = adj.size();
        int counter = 0;

        List<Agent> agents = new ArrayList<>(node.getAgents());
        Iterator<Agent> iterator = agents.iterator();

        node.removeAllAgents();

        while(counter < size && !agents.isEmpty()){
            Node n = adj.get(counter);
            int numberOfAddedAgents = n.getMaxAgents() - n.getNumberOfAgents();
            int count = 0;

            while(iterator.hasNext()){
                if(counter < size - 1){
                    if(count >= numberOfAddedAgents){
                        break;
                    }
                }
                Agent agent = iterator.next();
                n.forceAddAgent(agent);
                iterator.remove();
                count++;
            }
        }
    }

    public void removeAgentsFromEdge(Edge edge) throws AppException {
        Check.checkNullArgument(edge, "The edge is null !");
        if(!this.edges.contains(edge)){
            throw new AppException("The edge you want to change is not contained in the graph !");
        }

        List<Agent> agents = new ArrayList<>(edge.getAgents());
        Iterator<Agent> iterator = agents.iterator();

        edge.removeAllAgents();

        Node source = edge.getSource();

        while(iterator.hasNext()){
            Agent agent = iterator.next();
            source.addAgent(agent);
        }
    }

    public Edge getNextLocation(Node source, Node destination){
        //return Dijkstra.nextLocation(this, source, destination);
        return null;
    }

    /**
     * Returns a String that contains the graph's attributes
     * @return A String that contains the graph's attributes
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        for(Node node : this.nodes){
            for(Agent agent : node.getAgents()){
                string.append("-> " + agent + "\n");
            }
        }

        /*
        string.append("Graph : \n");

        for(Node node : this.nodes){
            string.append("-> " + node + "\nEdges : \n");
            
            for(Edge edge : this.getEdges(node)){
                string.append("-> " + edge + "\n");
            }

            string.append("\n-------------\n");
        }
        */

        return string.toString();
    }
}
