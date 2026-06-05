package com.app.model.agent;

import java.io.Serializable;
import java.util.Objects;

import com.app.model.exception.AppException;
import com.app.model.graph.Graph;
import com.app.model.graph.location.Location;
import com.app.model.graph.location.node.Node;
import com.app.model.util.Check;

public class Agent implements Serializable {

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
     * Indicated if the agent is tolerant to congestion
     */
    private boolean isTolerantToCongestion;

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
     * The unique id that was given to the last created node + 1
     */
    private static int lastID = 0;

    /**
     * The constant speed that an agent initially has if the user does not specify it
     */
    private final static double SPEED = 1000.0; 

    /**
     * The agent constructor that takes as arguments his name, speed, state, behavior, if he is tolerant to congestion and eventually his actual location
     * @param name The name of the agent
     * @param speed The speed of the agent
     * @param state The state of the agent
     * @param behavior The behavior of the agent
     * @param isTolerantToCongestion Indicated if the agent is tolerant to congestion
     * @param location The actual location of the agent
     */
    public Agent(String name, double speed, AgentState state, AgentBehavior behavior, boolean isTolerantToCongestion, Location location){
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
        this.isTolerantToCongestion = isTolerantToCongestion;
        this.location = location;

        this.position = 0;
        this.destination = null;
    }

    /**
     * The agent constructor that does not take any arguments
     */
    public Agent(){
        this("Agent" + lastID, SPEED, AgentState.CALM, AgentBehavior.NORMAL, true, null);
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
     * Returns the value that indicates if the agent is tolerant to congestion or not
     * @return the value that indicates if the agent is tolerant to congestion or not
     */
    public boolean isTolerantToCongestion(){
        return this.isTolerantToCongestion;
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
        this.position = position;
    }

    public void moveToNextLocation(Graph graph){
        if(this.location != null){
            try{
                this.location.moveAgentToNextLocation(graph, this);
            } catch(AppException e) {

            }
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
}