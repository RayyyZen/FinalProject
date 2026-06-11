package com.app.model.agent.path.spf;

import com.app.model.graph.location.edge.Edge;

public class ShortestTimePath extends ShortestPath {

    @Override
    public double getEdgeValue(Edge edge) {
        double congestion = edge.getNumberOfAgents() / edge.getMaxAgents();
        return edge.getDistance() / Math.exp((-1) * congestion);
    }
    
}
