package com.app.model.agent.path;

import java.util.Iterator;
import java.util.List;

import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;

public class FollowerPath implements PathFinder {

    @Override
    public Edge getNextLocation(Node source, Node destination, Graph graph) {
        List<Edge> edges = Edge.getUnsaturatedEdges(graph.getEdges(source));
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
