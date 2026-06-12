package com.app.model.agent.path.shortestpath;

import com.app.model.graph.location.edge.Edge;
import com.app.model.util.Check;

/**
 * The shortest distance path class that finds the path between two nodes according to the minimum distance between them
 * @version 3.0
 * @since 3.0
 * @author Rayane
 */
public class ShortestDistancePath extends ShortestPath {

    /**
     * Returns the value of an edge according to what will be compared in order to get the optimized path
     * Here the edge value is the distance of the edge
     * @param edge An edge
     * @return the value of an edge according to what will be compared in order to get the optimized path
     */
    @Override
    public double getEdgeValue(Edge edge) {
        Check.checkNullArgument(edge, "The edge is null !");
        
        return edge.getDistance();
    }
    
}