package com.app.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The node class that contains its informations and the agents located on it
 * @version 5.0
 * @since 1.0
 * @author Rayane
 */
public class Node {
    
    /**
     * The unique id of the node
     */
    private final int id;

    /**
     * The name of the node
     */
    private String name;

    /**
     * The state of the node
     */
    private NodeState state;

    /**
     * The type of the node
     */
    private NodeType type;

    /**
     * The maximum number of agents the node can store
     */
    private int maxAgents;

    /**
     * The constant maximum number of agents the node can store if it is not specified by the user
     */
    private static final int MAXAGENTS = 10;

    /**
     * The agents located on the node
     */
    private List<Agent> agents;

    /**
     * The unique id that was given to the last created node + 1
     */
    private static int lastID = 0;

    /**
     * Checks if an argument is null
     * @param obj The argument that will be checked
     * @param message The message that will be shown if the argument is null
     */
    private void checkNullArgument(Object obj, String message){
        if(message == null){
            throw new IllegalArgumentException("The null argument message is null");
        }
        if(obj == null){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if an argument of any method of the node class is null
     * @param obj The argument that will be checked
     * @param attribute The attribute of the node that is null
     */
    private void checkNodeNullArgument(Object obj, String attribute){
        checkNullArgument(obj, "The " + attribute + " of the node is null !");
    }

    /**
     * The node constructor that takes as arguments its name, state, type and the number of agents it can store
     * @param name The name of the node
     * @param state The state of the node
     * @param type The type of the node
     * @param maxAgents The maximum number of agents the node can store
     */
    public Node(String name, NodeState state, NodeType type, int maxAgents){
        checkNodeNullArgument(name, "name");
        checkNodeNullArgument(state, "state");
        checkNodeNullArgument(type, "type");
        if(maxAgents < 0){
            throw new IllegalArgumentException("The maximum number of agents the node can store must be positive !");
        }

        this.id = lastID;
        lastID++;
        
        this.name = name;
        this.state = state;
        this.type = type;
        this.maxAgents = maxAgents;

        this.agents = new ArrayList<>();
    }

    /**
     * The node constructor that takes as arguments its state, type and the number of agents it can store
     * @param state The state of the node
     * @param type The type of the node
     * @param maxAgents The maximum number of agents the node can store
     */
    public Node(NodeState state, NodeType type, int maxAgents){
        this("Node" + lastID, state, type, maxAgents);
    }

    /**
     * The node constructor that does not take any arguments
     */
    public Node(){
        this(NodeState.OPEN, NodeType.DESTINATION, MAXAGENTS);
    }

    public int getId(){
        return this.id;
    }

    //public void getName()

    /**
     * Returns a String that contains the node's attributes
     * @return A String that contains the node's attributes
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        //string.append(this.id + " : " + this.name + "\n-> State : " + this.state + "\n->Type : " + this.type + "\n-> Agents (" + this.getNumberOfAgents() + " / " + this.maxAgents + ") : \n");
        
        for(Agent agent : this.agents){
            string.append("--> " + agent + "\n");
        }

        return string.toString();
    }

    /**
     * Checks if a node is equal to an object
     * @param obj The object that will be compared to the node
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Node)){
            return false;
        }
        
        if(this == obj){
            return true;
        }

        Node other = (Node) obj;
        return this.id == other.id;
    }

    /**
     * Returns the hash code of the node
     * @return the hash code of the node
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }
}
