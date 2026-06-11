package com.app.model.agent.path.spf;

import com.app.model.graph.location.edge.Edge;

public class ShortestDistancePath extends ShortestPath {

    @Override
    public double getEdgeValue(Edge edge) {
        return edge.getDistance();
    }
    
}
