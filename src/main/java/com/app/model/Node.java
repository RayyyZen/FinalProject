package com.app.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * The node class that contains its informations and the agents located on it
 * @version 1.0
 * @since 1.0
 * @author Rayane
 */
public class Node implements Location {
    
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
     * Checks if an argument of any method of the node class is null
     * @param obj The argument that will be checked
     * @param attribute The attribute of the node that is null
     */
    private void checkNodeNullArgument(Object obj, String attribute){
        Check.checkNullArgument(obj, "The " + attribute + " of the node is null !");
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

    /**
     * Returns the unique id of the node
     * @return the unique id of the node
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the name of the node
     * @return the name of the node
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the state of the node
     * @return the state of the node
     */
    public NodeState getState(){
        return this.state;
    }

    /**
     * Returns the type of the node
     * @return the type of the node
     */
    public NodeType getType(){
        return this.type;
    }

    /**
     * Returns the maximum number of agents the node can store
     * @return the maximum number of agents the node can store
     */
    public int getMaxAgents(){
        return this.maxAgents;
    }

    /**
     * Returns the number of agents stored in the node
     * @return the number of agents stored in the node
     */
    public int getNumberOfAgents(){
        return this.agents.size();
    }

    /**
     * Returns all the agents that are stored in the node
     * @return all the agents that are stored in the node
     */
    public List<Agent> getAgents(){
        return this.agents;
    }

    /**
     * Returns an agent from the node by his id
     * @param id The id of the agent that will be eventually returned
     * @return an agent from the node by his id
     */
    public Agent getAgentById(int id){
        for(Agent agent : this.agents){
            if(agent.getId() == id){
                return agent;
            }
        }
        return null;
    }

    /**
     * Removes an agent from the node by his id
     * @param id The id of the agent that will be removed
     */
    public void removeAgentById(int id){
        Iterator<Agent> iterator = this.agents.iterator();

        while(iterator.hasNext()){
            Agent agent = iterator.next();
            
            if(agent.getId() == id){
                iterator.remove();
                agent.setLocation(null);
                return;
            }
        }
    }

    /**
     * Removes all the agents from the node
     */
    public void removeAllAgents(){
        Iterator<Agent> iterator = this.agents.iterator();

        while(iterator.hasNext()){
            Agent agent = iterator.next();
            iterator.remove();
            agent.setLocation(null);
        }
    }

    /**
     * Adds a new agent to the node
     * @param agent The agent that will be added to the node
     * @throws AppException In case the node has reached its maximum number of stored agents
     */
    public void addAgent(Agent agent) throws AppException {
        Check.checkNullArgument(agent, "The agent who was being added to the node is null !");

        if(this.getNumberOfAgents() >= this.maxAgents){
            throw new AppException("The node is full !");
        }
        
        this.agents.add(agent);
        agent.setLocation(this);
    }

    /**
     * Returns a String that contains the node's attributes
     * @return A String that contains the node's attributes
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        string.append(this.id + " : " + this.name + "\n-> State : " + this.state + "\n-> Type : " + this.type + "\n-> Agents (" + this.getNumberOfAgents() + " / " + this.maxAgents + ") : \n");
        
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
