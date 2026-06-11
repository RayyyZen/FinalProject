package com.app.model.agent.path;

import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;

public interface PathFinder {

    public Edge getNextLocation(Node source, Node destination, Graph graph);

}
