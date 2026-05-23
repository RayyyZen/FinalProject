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
     * Checks if an argument of any method of the graph class is null
     * @param obj The argument that will be checked
     * @param attribute The attribute of the graph that is null
     */
    private void checkGraphNullArgument(Object obj, String attribute){
        Check.checkNullArgument(obj, "The " + attribute + " that was going to be added to the graph is null !");
    }

    public Graph(){
        this.nodes = new ArrayList<>();
    }

    public void initializeGraph(){
        List<NodeState> states = List.of(NodeState.CLOSED, NodeState.OPEN);
        List<NodeType> types = List.of(NodeType.DESTINATION, NodeType.EXIT);
        int maxNumberOfAgents = 100;

        int numberOfNodes = 10;

        for(int i = 0; i < numberOfNodes; i++){
            NodeState state = states.get(ThreadLocalRandom.current().nextInt(0, states.size()));
            NodeType type = types.get(ThreadLocalRandom.current().nextInt(0, types.size()));
            int maxAgents = ThreadLocalRandom.current().nextInt(10, maxNumberOfAgents);

            Node node = new Node(state, type, maxAgents);
            this.nodes.add(node);
        }

    }

    public List<Node> getAllNodes(){
        return this.nodes;
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

    /**
     * Returns a String that contains the graph's attributes
     * @return A String that contains the graph's attributes
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        string.append("Graph nodes : \n");
        
        for(Node node : this.nodes){
            string.append("-> " + node + "\n");
        }

        return string.toString();
    }
}
