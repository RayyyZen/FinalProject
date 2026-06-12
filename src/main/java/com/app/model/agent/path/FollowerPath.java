package com.app.model.agent.path;

import java.util.List;

import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;
import com.app.model.util.Check;

/**
 * The follower path class that finds the path between two nodes by following the crowd
 * @version 3.0
 * @since 3.0
 * @author Rayane
 */
public class FollowerPath implements PathFinder {

    /**
     * Returns the next location that should be visited between two nodes from a graph according to an algorithm that follows the crowd
     * @param source The source node
     * @param destination The destination node
     * @param graph The graph that contains the source and destination nodes
     * @return the next location that should be visited between two nodes from a graph according to an algorithm that follows the crowd
     */
    @Override
    public Edge getNextLocation(Node source, Node destination, Graph graph) {
        Check.checkPathArgument(source, destination, graph);

        List<Edge> edges = Edge.getValidEdges(graph.getEdges(source));
        Edge edge = null;

        if(edges != null && !edges.isEmpty()){
            edge = edges.get(0);
            int max = edge.getNumberOfAgents();
            for(Edge e : edges){
                if(e.getNumberOfAgents() > max){
                    edge = e;
                    max = e.getNumberOfAgents();
                }
            }
        }

        return edge;
    }
    
}