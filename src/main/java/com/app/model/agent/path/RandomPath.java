package com.app.model.agent.path;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;
import com.app.model.util.Check;

/**
 * The follower path class that finds the path between two nodes by following random destinations
 * @version 3.0
 * @since 3.0
 * @author Rayane
 */
public class RandomPath implements PathFinder {

    /**
     * The random path class
     */
    public RandomPath(){}

    /**
     * Returns the next location that should be visited between two nodes from a graph by following random destinations
     * @param source The source node
     * @param destination The destination node
     * @param graph The graph that contains the source and destination nodes
     * @return the next location that should be visited between two nodes from a graph by following random destinations
     */
    @Override
    public Edge getNextLocation(Node source, Node destination, Graph graph) {
        Check.checkPathArgument(source, destination, graph);

        List<Edge> edges = Edge.getValidEdges(graph.getAdjEdges(source));
        
        if(edges.isEmpty()){
            return null;
        }
        else{
            Edge edge = edges.get(ThreadLocalRandom.current().nextInt(0, edges.size()));
            return edge;
        }
    }
    
}