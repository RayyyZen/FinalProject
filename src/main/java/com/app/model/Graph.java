package com.app.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Graph {
    
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

        int numberOfEdges = 7;
        double minDistance = 1.0;
        double maxDistance = 20.0;

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

    public void removeNodeById(int id){
        Iterator<Node> iterator = this.nodes.iterator();

        while(iterator.hasNext()){
            Node node = iterator.next();

            if(node.getId() == id){
                iterator.remove();
            }
        }
    }

    public void removeAllNodes(){
        this.nodes.clear();
    }

    public void addEdge(Edge edge){
        checkGraphNullArgument(edge, "edge");
        this.edges.add(edge);
    }

    public void removeEdgeById(int id){
        Iterator<Edge> iterator = this.edges.iterator();

        while(iterator.hasNext()){
            Edge edge = iterator.next();

            if(edge.getId() == id){
                iterator.remove();
            }
        }
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

    public int getNumberOfNodes(){
        return this.nodes.size();
    }

    public int getNumberOfEdges(){
        return this.edges.size();
    }

    /**
     * Returns a String that contains the graph's attributes
     * @return A String that contains the graph's attributes
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        int numberOfNodes = this.getNumberOfNodes();
        int numberOfEdges = this.getNumberOfEdges();

        string.append("\nGraph (" + numberOfNodes + " Nodes | " + numberOfEdges + " Edges) : \n\n");

        string.append("-> Nodes (" + numberOfNodes + ") : \n\n");
        
        for(Node node : this.nodes){
            string.append("--> " + node + "\n");
        }

        string.append("-> Edges (" + numberOfEdges + ") : \n\n");
        
        for(Edge edge : this.edges){
            string.append("--> " + edge + "\n");
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
