package com.app.model.graph.location.node;

import com.app.model.agent.Agent;
import com.app.model.agent.path.PathFinder;
import com.app.model.graph.Graph;
import com.app.model.graph.location.*;
import com.app.model.graph.location.edge.Edge;
import com.app.model.util.Check;

/**
 * The node class that contains its informations
 * @version 3.0
 * @since 1.0
 * @author Rayane
 */
public class Node extends Location {

    /**
     * The type of the node
     */
    private NodeType type;

    /**
     * The unique id that was given to the last created node + 1
     */
    private static int lastID = 0;

    /**
     * The constant maximum number of agents the node can store if it is not specified by the user
     */
    private static final int MAXAGENTS = 50;

    /**
     * The node constructor that takes as arguments its name, state, type and the maximum number of agents it can store
     * @param name The name of the node
     * @param state The state of the node
     * @param type The type of the node
     * @param maxAgents The maximum number of agents the node can store
     */
    public Node(String name, LocationState state, NodeType type, int maxAgents){
        super(lastID, name, state, maxAgents);
        lastID++;

        Check.checkClassNullArgument(type, "node", "type");
        this.type = type;
    }

    /**
     * The node constructor that takes as arguments its state, type and the maximum number of agents it can store
     * @param state The state of the node
     * @param type The type of the node
     * @param maxAgents The maximum number of agents the node can store
     */
    public Node(LocationState state, NodeType type, int maxAgents){
        this("Node" + lastID, state, type, maxAgents);
    }

    /**
     * The node constructor that does not take any arguments
     */
    public Node(){
        this(LocationState.OPEN, NodeType.DESTINATION, MAXAGENTS);
    }

    /**
     * Returns the type of the node
     * @return the type of the node
     */
    public NodeType getType(){
        return this.type;
    }

    /**
     * Returns the source node of a node
     * @return the source node of a node
     */
    public Node getSourceNode(){
        return this;
    }

    /**
     * Moves an agent to his next location according to his destination
     * @param graph The graph where the agent belongs
     * @param agent The agent that will be moved
     */
    @Override
    public void moveAgentToNextLocation(Graph graph, Agent agent) {
        Check.checkMoveAgent(graph, agent, this);

        if(!(agent.getLocation() instanceof Node)){
            throw new IllegalStateException("The agent isn't on a node");
        }

        if(agent.getDestination() != null){
            PathFinder pathFinder = agent.getPathFinder();
            Node source = (Node) agent.getLocation();
            Node destination = agent.getDestination();
            
            Edge edge = pathFinder.getNextLocation(source, destination, graph);

            if(edge != null && edge.valid()){
                this.removeAgent(agent);
                edge.addAgent(agent);
            }
        }
    }

    /**
     * Returns a String that contains the node's attributes
     * @return A String that contains the node's attributes
     */
    @Override
    public String toString(){
        return super.toString() + "\n-> Type : " + this.type + "\n" + super.getAgentsString();
    }

    /**
     * Checks if a node is equal to an object
     * @param obj The object that will be compared to the node
     * @return true if they are equal or false if they aren't
     */
    @Override
    public boolean equals(Object obj){
        return (obj instanceof Node) && super.equals(obj);
    }
}