package com.app.model.graph.location;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import com.app.model.agent.Agent;
import com.app.model.exception.AppException;
import com.app.model.graph.Graph;
import com.app.model.util.Check;

/**
 * The location class that contains its informations and the agents located on it
 * @version 1.0
 * @since 1.0
 * @author Rayane
 */
public abstract class Location {
    
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
     * Returns all the agents that are stored in the location
     * @return all the agents that are stored in the location
     */
    public List<Agent> getAgents(){
        return this.agents;
    }

    /**
     * Returns an agent from the location by his id
     * @param id The id of the agent that will be eventually returned
     * @return an agent from the location by his id
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
     * Adds a new agent to the location
     * @param agent The agent that will be added to the location
     * @throws AppException In case the location has reached its maximum number of stored agents
     */
    public void addAgent(Agent agent) throws AppException {
        Check.checkNullArgument(agent, "The agent who was being added to the location is null !");

        if(this.getNumberOfAgents() >= this.maxAgents){
            throw new AppException("The location is full !");
        }
        
        this.agents.add(agent);
        agent.setLocation(this);
    }

    /**
     * Forces the add of a new agent to the location
     * @param agent The agent that will be added to the location
     */
    public void forceAddAgent(Agent agent) {
        Check.checkNullArgument(agent, "The agent who was being added to the location is null !");
        
        this.agents.add(agent);
        agent.setLocation(this);
    }

    /**
     * Removes an agent from the location by his id
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
     * Moves an agent to his next location according to his destination
     * @param agent The agent that will be moved
     */
    public abstract void moveAgentToNextLocation(Graph graph, Agent agent) throws AppException;

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