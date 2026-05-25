package com.app.algo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.app.model.*;

public class Dijkstra {
    
    public static Edge nextLocation(Graph graph, Node source, Node destination){
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
                relax(edge, pq, distTo, edgeTo);
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

        return path.get(0);

    }

    public static void relax(Edge edge, PriorityQueue<Map.Entry<Node, Double>> pq, Map<Node, Double> distTo, Map<Node, Edge> edgeTo){
        Node source = edge.getSource();
        Node destination = edge.getDestination();
        double distance = edge.getDistance();

        double distToSource = distTo.get(source);
        double distToDestination = distTo.get(destination);

        if(distToSource + distance < distToDestination){
            distTo.put(destination, distToSource + distance);
            edgeTo.put(destination, edge);
            pq.add(Map.entry(destination, distTo.get(destination)));
        }
    }
}
