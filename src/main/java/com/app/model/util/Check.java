package com.app.model.util;

import com.app.model.agent.Agent;
import com.app.model.graph.Graph;
import com.app.model.graph.location.Location;
import com.app.model.graph.location.node.Node;

public class Check {
    
    /**
     * Checks if an argument is null
     * @param obj The argument that will be checked
     * @param message The message that will be shown if the argument is null
     */
    public static void checkNullArgument(Object obj, String message){
        if(message == null){
            throw new IllegalArgumentException("The null argument message is null");
        }
        if(obj == null){
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Checks if an argument of any method of a class is null
     * @param obj The argument that will be checked
     * @param className The class name
     * @param attribute The attribute of the class that is null
     */
    public static void checkClassNullArgument(Object obj, String className, String attribute){
        checkNullArgument(obj, "The " + attribute + " of the " + className + " is null !");
    }

    /**
     * Checks if a source node or a destination node or a graph is null
     * @param source The source node
     * @param destination The destination node
     * @param graph The graph that contains the source and destination nodes
     */
    public static void checkPathArgument(Node source, Node destination, Graph graph){
        checkNullArgument(source, "The source node is null");
        checkNullArgument(destination, "The destination node is null");
        checkNullArgument(graph, "The graph node is null");
    }

    /**
     * Checks if the arguments of an agent movement method are valid
     * @param graph The graph where the agent belongs
     * @param agent The agent that belongs to the graph
     * @param location The location of the agent
     */
    public static void checkMoveAgent(Graph graph, Agent agent, Location location){
        Check.checkNullArgument(graph, "The graph is null");
        Check.checkNullArgument(agent, "The agent is null");
        Check.checkNullArgument(location, "The location is null");

        if(!location.getAgents().contains(agent)){
            throw new IllegalStateException("The agent does not belong to this location");
        }
    }

    /**
     * Checks if a number is >=1
     * @param number The number that will be checked
     */
    public static void checkNumber(int number){
        if(number < 1){
            throw new IllegalArgumentException("The number of added elements must be >= 1");
        }
    }
}