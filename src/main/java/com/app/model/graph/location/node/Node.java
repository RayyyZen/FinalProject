package com.app.model.graph.location.node;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.model.agent.Agent;
import com.app.model.agent.AgentState;
import com.app.model.exception.AppException;
import com.app.model.graph.Graph;
import com.app.model.graph.location.*;
import com.app.model.graph.location.edge.Edge;
import com.app.model.util.Check;

/**
 * The node class that contains its informations
 * @version 1.0
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
    private static final int MAXAGENTS = 10;

    /**
     * The node constructor that takes as arguments its name, state, type and the number of agents it can store
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
     * The node constructor that takes as arguments its state, type and the number of agents it can store
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
     * Moves an agent to his next location according to his destination
     * @param agent The agent that will be moved
     */
    public void moveAgentToNextLocation(Graph graph, Agent agent) throws AppException {
        if(!this.getAgents().contains(agent)){
            throw new AppException("The agent does not belong to this edge");
        }

        if(agent.getDestination() != null){

            Edge edge = null;

            if(agent.getState() == AgentState.CRAZY){
                List<Edge> edges = graph.getEdges((Node) agent.getLocation());
                edge = edges.get(ThreadLocalRandom.current().nextInt(0, edges.size()));

            }
            else if(agent.getState() == AgentState.CALM){
                edge = graph.getNextLocation(this, agent.getDestination());
            }
            else{
                List<Edge> all = graph.getEdges((Node) agent.getLocation());
                if(all != null && !all.isEmpty()){
                    Edge ed = all.get(0);
                    int max = ed.getNumberOfAgents();
                    for(Edge e : all){
                        if(e.getNumberOfAgents() > max){
                            ed = e;
                            max = e.getNumberOfAgents();
                        }
                    }
                    edge = ed;
                }

            }

            if(edge != null){
                try{
                    this.removeAgentById(agent.getId());
                    edge.addAgent(agent);
                    agent.setPosition(0);
                } catch(AppException e) {
                    
                }
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