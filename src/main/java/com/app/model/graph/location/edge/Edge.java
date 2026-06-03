package com.app.model.graph.location.edge;

import com.app.model.agent.Agent;
import com.app.model.exception.AppException;
import com.app.model.graph.Graph;
import com.app.model.graph.location.Location;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.node.Node;
import com.app.model.util.Check;

/**
 * The edge class that contains its informations
 * @version 1.0
 * @since 1.0
 * @author Rayane
 */
public class Edge extends Location {

    /**
     * The source node
     */
    private Node source;

    /**
     * The destination node
     */
    private Node destination;

    /**
     * The distance between the nodes (weight of the edge)
     */
    private double distance;

    /**
     * The unique id that was given to the last created edge + 1
     */
    private static int lastID = 0;

    /**
     * The constant maximum number of agents the edge can store if it is not specified by the user
     */
    private static final int MAXAGENTS = 20;

    private static final int TIME = 1;

    /**
     * The edge constructor that takes as arguments its name, max number of agents it can store, source node, destination node and the distance between both nodes
     * @param name The name of the edge
     * @param state The state of the edge
     * @param maxAgents The maximum number of agents the edge can store
     * @param source The source node
     * @param destination The destination node
     * @param distance The distance between the source and destination nodes
     */
    public Edge(String name, LocationState state, int maxAgents, Node source, Node destination, double distance){
        super(lastID, name, state, maxAgents);
        lastID++;

        Check.checkClassNullArgument(source, "edge", "source");
        Check.checkClassNullArgument(source, "edge", "destination");
        if(source.equals(destination)){
            throw new IllegalArgumentException("An edge must connect 2 different nodes !");
        }
        if(distance <= 0){
            throw new IllegalArgumentException("The distance between 2 nodes must be > 0");
        }

        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }

    /**
     * The edge constructor that takes as arguments its max number of agents it can store, source node, destination node and the distance between both nodes
     * @param state The state of the edge
     * @param maxAgents The maximum number of agents the edge can store
     * @param source The source node
     * @param destination The destination node
     * @param distance The distance between the source and destination nodes
     */
    public Edge(LocationState state, int maxAgents, Node source, Node destination, double distance){
        this("Edge" + lastID, state, maxAgents, source, destination, distance);
    }

    /**
     * The edge constructor that takes as arguments its source node, destination node and the distance between both nodes
     * @param source The source node
     * @param destination The destination node
     * @param distance The distance between the source and destination nodes
     */
    public Edge(Node source, Node destination, double distance){
        this(LocationState.OPEN, MAXAGENTS, source, destination, distance);
    }

    /**
     * Returns the source node of the edge
     * @return the source node of the edge
     */
    public Node getSource(){
        return this.source;
    }

    /**
     * Returns the destination node of the edge
     * @return the destination node of the edge
     */
    public Node getDestination(){
        return this.destination;
    }

    /**
     * Returns the distance between the source and destination nodes
     * @return the distance between the source and destination nodes
     */
    public double getDistance(){
        return this.distance;
    }

    /**
     * Moves an agent to his next location according to his destination
     * @param agent The agent that will be moved
     */
    public void moveAgentToNextLocation(Graph graph, Agent agent) throws AppException {
        if(!this.getAgents().contains(agent)){
            throw new AppException("The agent does not belong to this edge");
        }

        double position = agent.getPosition();
        double speed = agent.getSpeed();
        double distance = this.distance;

        double congestion = this.getNumberOfAgents() / this.getMaxAgents();
        double newSpeed = speed * Math.exp((-1) * congestion);
        double traveledDistance = newSpeed * TIME;

        double newPosition = Math.min(position + ((traveledDistance / 10.0) / distance), 1);
        agent.setPosition(newPosition);

        if(newPosition == 1){
            Node node = this.destination;
            try{
                this.removeAgentById(agent.getId());
                node.addAgent(agent);
                agent.setPosition(0);
                if(node.equals(agent.getDestination())){
                    agent.setDestination(null);
                }
            } catch(AppException e) {

            }
        }
    }

    /**
     * Returns a string that contains the name, id and the direction of a node
     * @param node A certain node from one of the endpoints of the edge
     * @param nodeDirection The direction of the node (Source or Destination)
     * @return a string that contains the name, id and the direction of a node
     */
    private String getNodeString(Node node, String nodeDirection){
        Check.checkNullArgument(node, "The node is null !");
        Check.checkNullArgument(nodeDirection, "The node direction is null !");

        return "-> " + nodeDirection + " : " + node.getId() + ". " + node.getName();
    }

    /**
     * Returns a String that contains the edge's attributes
     * @return A String that contains the edge's attributes
     */
    @Override
    public String toString(){
        return super.toString() + "\n" + this.getNodeString(this.source, "Source") + "\n" + this.getNodeString(destination, "Destination") + "\n" + super.getAgentsString();
    }

    /**
     * Checks if a edge is equal to an object
     * @param obj The object that will be compared to the edge
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        return (obj instanceof Edge) && super.equals(obj);
    }
}