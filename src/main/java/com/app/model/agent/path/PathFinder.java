package com.app.model.agent.path;

import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;

/**
 * The path finder interface
 * @version 3.0
 * @since 3.0
 * @author Rayane
 */
public interface PathFinder {

    /**
     * Returns the next location that should be visited between two nodes from a graph according to a path finder algorithm
     * @param source The source node
     * @param destination The destination node
     * @param graph The graph that contains the source and destination nodes
     * @return the next location that should be visited between two nodes from a graph according to a path finder algorithm
     */
    public Edge getNextLocation(Node source, Node destination, Graph graph);

}