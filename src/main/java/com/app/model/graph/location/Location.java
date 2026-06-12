package com.app.model.graph.location;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.app.model.agent.Agent;
import com.app.model.graph.Graph;
import com.app.model.util.Check;

/**
 * The location class that contains its informations and the agents located on it
 * @version 3.0
 * @since 1.0
 * @author Rayane
 */
public abstract class Location implements Serializable {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The unique id of the location
     */
    private final int id;

    /**
     * The name of the location
     */
    private String name;

    /**
     * The state of the location
     */
    private LocationState state;

    /**
     * The maximum number of agents the location can store
     */
    private int maxAgents;

    /**
     * The agents located on the location
     */
    private List<Agent> agents;

    /**
     * The total number of agents that passed by the location
     */
    private int numberOfPassedAgents;

    /**
     * The average speed of the agents that passed by the location
     */
    private double averageSpeed;

    /**
     * Checks if an argument of any method of the location class is null
     * @param obj The argument that will be checked
     * @param attribute The attribute of the location that is null
     */
    private void checkLocationNullArgument(Object obj, String attribute){
        Check.checkNullArgument(obj, "The " + attribute + " of the location is null !");
    }

    /**
     * The location constructor that takes as arguments its id, name, and the number of agents it can store
     * @param lastID The last id that was given to a location + 1
     * @param name The name of the location
     * @param state The state of the location
     * @param maxAgents The maximum number of agents the location can store
     */
    public Location(int lastID, String name, LocationState state, int maxAgents){
        checkLocationNullArgument(name, "name");
        checkLocationNullArgument(state, "state");
        if(maxAgents < 0){
            throw new IllegalArgumentException("The maximum number of agents the location can store must be positive !");
        }

        this.id = lastID;
        lastID++;
        
        this.name = name;
        this.state = state;
        this.maxAgents = maxAgents;

        this.agents = new ArrayList<>();

        this.numberOfPassedAgents = 0;
        this.averageSpeed = 0.0;
    }

    /**
     * Returns the unique id of the location
     * @return the unique id of the location
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the name of the location
     * @return the name of the location
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the state of the node
     * @return the state of the node
     */
    public LocationState getState(){
        return this.state;
    }

    /**
     * Returns the maximum number of agents the location can store
     * @return the maximum number of agents the location can store
     */
    public int getMaxAgents(){
        return this.maxAgents;
    }

    /**
     * Returns the number of agents stored in the location
     * @return the number of agents stored in the location
     */
    public int getNumberOfAgents(){
        return this.agents.size();
    }

    /**
     * Returns the congestion of the location
     * @return the congestion of the location
     */
    public double getCongestion(){
        return this.getNumberOfAgents() / this.getMaxAgents();
    }

    /**
     * Returns all the agents that are stored in the location
     * @return all the agents that are stored in the location
     */
    public List<Agent> getAgents(){
        return this.agents;
    }

    /**
     * Adds a new agent to the location
     * @param agent The agent that will be added to the location
     * @param forced Indicated wether the agent add must be forced
     */
    private void add(Agent agent, boolean forced) {
        Check.checkNullArgument(agent, "The agent who was being added to the location is null !");

        if(!forced && this.getNumberOfAgents() >= this.maxAgents){
            throw new IllegalStateException("The location is full !");
        }
        
        this.agents.add(agent);
        agent.setLocation(this);
        agent.setPosition(0);

        this.numberOfPassedAgents++;
        this.averageSpeed = ((this.averageSpeed * (this.numberOfPassedAgents - 1)) + agent.getSpeed()) / this.numberOfPassedAgents;
    }

    /**
     * Adds a new agent to the location
     * @param agent The agent that will be added to the location
     */
    public void addAgent(Agent agent) {
        this.add(agent, false);
    }

    /**
     * Forces the add of a new agent to the location
     * @param agent The agent that will be added to the location
     */
    public void forceAddAgent(Agent agent) {
        this.add(agent, true);
    }

    /**
     * Removes an agent from the location
     * @param agent The agent that will be removed from the location
     */
    public void removeAgent(Agent agent){
        this.agents.remove(agent);
        agent.setLocation(null);
    }

    /**
     * Removes all the agents from the location
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
     * Returns true if the location is valid, or false otherwise
     * @return true if the location is valid, or false otherwise
     */
    public boolean valid(){
        return this.getState() == LocationState.OPEN && this.getNumberOfAgents() < this.getMaxAgents();
    }

    /**
     * Moves an agent to his next location according to his destination
     * @param graph The graph where the agent belongs
     * @param agent The agent that will be moved
     */
    public abstract void moveAgentToNextLocation(Graph graph, Agent agent);

    /**
     * Returns a string that contains the agents attributes
     * @return a string that contains the agents attributes
     */
    protected String getAgentsString(){
        StringBuilder string = new StringBuilder();

        string.append("-> Agents (" + this.getNumberOfAgents() + " / " + this.maxAgents + ") : \n");

        for(Agent agent : this.agents){
            string.append("--> " + agent + "\n");
        }

        return string.toString();
    }

    /**
     * Returns a String that contains the location's attributes
     * @return A String that contains the location's attributes
     */
    @Override
    public String toString(){
        return this.id + " : " + this.name + "\n-> State : " + this.state;
    }

    /**
     * Checks if a location is equal to an object
     * @param obj The object that will be compared to the location
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Location)){
            return false;
        }
        
        if(this == obj){
            return true;
        }

        Location other = (Location) obj;
        return this.id == other.id;
    }

    /**
     * Returns the hash code of the location
     * @return the hash code of the location
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }
}