package com.app.model.agent.path.spf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.app.model.agent.path.PathFinder;
import com.app.model.graph.Graph;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.node.NodeType;

public abstract class ShortestPath implements PathFinder {

    public abstract double getEdgeValue(Edge edge);
    
    public List<Edge> getPath(Node source, Node destination, Graph graph){
        Map<Node, Double> distTo = new HashMap<>();

        for(Node node : graph.getAllNodes()){
            distTo.put(node, Double.POSITIVE_INFINITY);
        }
        
        distTo.put(source, 0.0);

        Map<Node, Edge> edgeTo = new HashMap<>();

        PriorityQueue<Map.Entry<Node, Double>> pq = new PriorityQueue<>( (a, b) -> Double.compare(a.getValue(), b.getValue()) );

        pq.add(Map.entry(source, 0.0));

        while(!pq.isEmpty()){
            Map.Entry<Node, Double> entry = pq.poll();
            Node node = entry.getKey();
            double distance = entry.getValue();

            if(distTo.get(node) < distance){
                continue;
            }

            for(Edge edge : graph.getEdges(node)){
                if(edge.getNumberOfAgents() < edge.getMaxAgents()){
                    relax(edge, pq, distTo, edgeTo);
                }
            }
        }

        if(!edgeTo.containsKey(destination)){
            return null;
        }

        List<Edge> path = new ArrayList<>();
        Node n = destination;

        while(n != source){
            Edge e = edgeTo.get(n);
            if(e == null){
                return null;
            }
            path.addFirst(e);
            n = e.getSource();
        }

        return path;
    }

    public void relax(Edge edge, PriorityQueue<Map.Entry<Node, Double>> pq, Map<Node, Double> distTo, Map<Node, Edge> edgeTo){
        Node source = edge.getSource();
        Node destination = edge.getDestination();

        double distance = this.getEdgeValue(edge);

        double distToSource = distTo.get(source);
        double distToDestination = distTo.get(destination);

        if(distToSource + distance < distToDestination){
            distTo.put(destination, distToSource + distance);
            edgeTo.put(destination, edge);
            pq.add(Map.entry(destination, distTo.get(destination)));
        }
    }

    public Edge getNextLocation(Node source, Node destination, Graph graph){
        List<Edge> path = this.getPath(source, destination, graph);
        if(path == null || path.isEmpty()){
            return null;
        }
        return path.get(0);
    }

    private double getPathValue(List<Edge> edges){
        if(edges == null || edges.isEmpty()){
            return 0;
        }

        double total = 0;
        for(Edge edge : edges){
            total += this.getEdgeValue(edge);
        }

        return total;
    }

    public Node getClosestExit(Node source, Graph graph){
        List<Node> exits = new ArrayList<>();

        for(Node node : graph.getAllNodes()){
            if(node.getType() == NodeType.EXIT){
                exits.add(node);
            }
        }

        if(exits == null || exits.isEmpty()){
            return null;
        }

        Node closestExit = exits.get(0);
        double min = this.getPathValue(this.getPath(source, closestExit, graph));

        for(Node exit : exits){
            double pathValue = this.getPathValue(this.getPath(source, closestExit, graph));
            if(pathValue < min){
                closestExit = exit;
                min = pathValue;
            }

        }

        return closestExit;
    }

}