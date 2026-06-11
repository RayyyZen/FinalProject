package com.app.model.agent.path;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;

public class RandomPath implements PathFinder {

    @Override
    public Edge getNextLocation(Node source, Node destination, Graph graph) {
        List<Edge> edges = Edge.getUnsaturatedEdges(graph.getEdges(source));
        
        if(edges.isEmpty()){
            return null;
        }
        else{
            Edge edge = edges.get(ThreadLocalRandom.current().nextInt(0, edges.size()));
            return edge;
        }
    }
    
}
