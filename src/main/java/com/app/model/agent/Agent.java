package com.app.model.agent;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

import com.app.model.agent.path.PathFinder;
import com.app.model.graph.Graph;
import com.app.model.graph.location.Location;
import com.app.model.graph.location.node.Node;
import com.app.model.util.Check;

/**
 * The agent class that contains its informations
 * @version 3.0
 * @since 1.0
 * @author Rayane
 */
public class Agent implements Serializable, Comparable<Agent> {

    /**
     * The serial version UID
     */
    private static final long serialVersionUID = 1L;
    
    /**
     * The unique id of the agent
     */
    private final int id;

    /**
     * The name of the agent
     */
    private String name;

    /**
     * The maximum speed of the agent
     */
    private double speed;

    /**
     * The state of the agent
     */
    private AgentState state;

    /**
     * The behavior of the agent
     */
    private AgentBehavior behavior;

    /**
     * The actual location of the agent
     */
    private Location location;

    /** 
     * The position of the agent
     * 0 for the nodes and a value between 0 and 1 for the edges (0 if he is at the start line of the edge)
    */
    private double position;

    /**
     * The final Location of the agent
     */
    private Node destination;

    /**
     * The unique id that was given to the last created agent + 1
     */
    private static int lastID = 0;

    /**
     * The maximum constant speed (in m/s) that an agent can initially have if the user does not specify it
     */
    private final static double SPEED = 10.0;

    /**
     * The agent constructor that takes as arguments his name, speed, state, behavior, if he is tolerant to congestion and eventually his actual location
     * @param name The name of the agent
     * @param speed The speed of the agent
     * @param state The state of the agent
     * @param behavior The behavior of the agent
     * @param location The actual location of the agent
     */
    public Agent(String name, double speed, AgentState state, AgentBehavior behavior, Location location){
        Check.checkClassNullArgument(name, "agent", "name");
        Check.checkClassNullArgument(state, "agent", "state");
        Check.checkClassNullArgument(behavior, "agent", "behavior");

        if(speed <= 0){
            throw new IllegalArgumentException("The agent's speed must be > 0 !");
        }

        this.id = lastID;
        lastID++;

        this.name = name;
        this.speed = speed;
        this.state = state;
        this.behavior = behavior;
        this.location = location;

        this.position = 0;
        this.destination = null;
    }

    /**
     * The agent constructor that takes as arguments his speed, state, behavior, if he is tolerant to congestion and eventually his actual location
     * @param speed The speed of the agent
     * @param state The state of the agent
     * @param behavior The behavior of the agent
     * @param location The actual location of the agent
     */
    public Agent(double speed, AgentState state, AgentBehavior behavior, Location location){
        this("Agent" + lastID, speed, state, behavior, location);
    }

    /**
     * The agent constructor that does not take any arguments
     */
    public Agent(){
        this("Agent" + lastID, ThreadLocalRandom.current().nextDouble(0.0, SPEED * 100), AgentState.CALM, AgentBehavior.NORMAL, null);
    }

    /**
     * Returns the unique id of the agent
     * @return the unique id of the agent
     */
    public int getId(){
        return this.id;
    }

    /**
     * Returns the name of the agent
     * @return the name of the agent
     */
    public String getName(){
        return this.name;
    }

    /**
     * Returns the speed of the agent
     * @return the speed of the agent
     */
    public double getSpeed(){
        return this.speed;
    }

    /**
     * Returns the state of the agent
     * @return the state of the agent
     */
    public AgentState getState(){
        return this.state;
    }

    /**
     * Returns the behavior of the agent
     * @return the behavior of the agent
     */
    public AgentBehavior getBehavior(){
        return this.behavior;
    }

    /**
     * Returns the current location of the agent
     * @return the current location of the agent
     */
    public Location getLocation(){
        return this.location;
    }

    /**
     * Returns the position of the agent on his current location
     * @return the position of the agent on his current location
     */
    public double getPosition(){
        return this.position;
    }

    /**
     * Returns the destination of the agent
     * @return the destination of the agent
     */
    public Node getDestination(){
        return this.destination;
    }

    /**
     * Changes the speed of the agent
     * @param speed The new speed of the agent
     */
    public void setSpeed(double speed){
        if(speed <= 0){
            throw new IllegalArgumentException("The agent speed must be > 0");
        }
        this.speed = speed;
    }

    /**
     * Changes the state of the agent
     * @param state The new state of the agent
     */
    public void setState(AgentState state){
        Check.checkNullArgument(state, "The agent state is null");
        this.state = state;
    }

    /**
     * Changes the behavior of the agent
     * @param behavior The new behavior of the agent
     */
    public void setBehavior(AgentBehavior behavior){
        Check.checkNullArgument(behavior, "The agent behavior is null");
        this.behavior = behavior;
    }

    /**
     * Changes the location of the agent
     * @param location The new location of the agent (can be null)
     */
    public void setLocation(Location location){
        this.location = location;
    }

    /**
     * Changes the destination of the agent
     * @param destination The new destination of the agent (can be null)
     */
    public void setDestination(Node destination){
        this.destination = destination;
    }

    /**
     * Changes the position of the agent
     * @param position The new position of the agent
     */
    public void setPosition(double position){
        if(position < 0 || position > 1){
            throw new IllegalStateException("The position of an agent in a location is between 0 and 1");
        }
        this.position = position;
    }

    /**
     * Returns the path finder of an agent according to his state
     * @return the path finder of an agent according to his state
     */
    public PathFinder getPathFinder(){
        return this.state.getPathFinder();
    }

    /**
     * Moves an agent to the next location according to his location, destination and state
     * @param graph The graph where the agent is located
     */
    public void moveToNextLocation(Graph graph){
        Check.checkNullArgument(graph, "The graph is null");

        if(this.location != null){
            this.location.moveAgentToNextLocation(graph, this);
        }
    }

    /**
     * Returns a String that contains the agent's attributes
     * @return A String that contains the agent's attributes
     */
    @Override
    public String toString(){
        StringBuilder string = new StringBuilder();

        string.append(this.id + " : " + this.name);
        
        if(this.location != null){
            string.append(" (Location : " + this.location.getName() + " | Destination : ");

            String destinationString = "none)";
            if(this.destination != null){
                destinationString = this.destination.getName() + ")";
            }

            string.append(destinationString);
        }

        return string.toString();
    }

    /**
     * Checks if an agent is equal to an object
     * @param obj The object that will be compared to the agent
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Agent)){
            return false;
        }
        
        if(this == obj){
            return true;
        }

        Agent other = (Agent) obj;
        return this.id == other.id;
    }

    /**
     * Returns the hash code of the agent
     * @return the hash code of the agent
     */
    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }

    /**
     * Returns the value comparison between the agent and an other one
     * @param other An other agent that will be compared
     * @return the value comparison between the agent and an other one
     */
    @Override
    public int compareTo(Agent other) {
        Check.checkNullArgument(other, "The agent is null");

        return other.behavior.getValue() - this.behavior.getValue();
    }
}