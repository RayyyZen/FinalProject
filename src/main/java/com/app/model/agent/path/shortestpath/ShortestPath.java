package com.app.model.agent.path.shortestpath;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import com.app.model.agent.path.PathFinder;
import com.app.model.graph.Graph;
import com.app.model.graph.location.LocationState;
import com.app.model.graph.location.edge.Edge;
import com.app.model.graph.location.node.Node;
import com.app.model.graph.location.node.NodeType;
import com.app.model.util.Check;

/**
 * The shortest path class that finds the path between two nodes according to the minimum edge value
 * @version 3.0
 * @since 3.0
 * @author Rayane
 */
public abstract class ShortestPath implements PathFinder {

    /**
     * The shortest path class
     */
    public ShortestPath(){}

    /**
     * Returns the value of an edge according to what will be compared in order to get the optimized path
     * @param edge An edge
     * @return the value of an edge according to what will be compared in order to get the optimized path
     */
    public abstract double getEdgeValue(Edge edge);
    
    /**
     * Returns the shortest path between two nodes from a graph
     * @param source The source node
     * @param destination The destination node
     * @param graph The graph that contains the source and destination nodes
     * @return the shortest path between two nodes from a graph
     */
    private List<Edge> getPath(Node source, Node destination, Graph graph){
        Check.checkPathArgument(source, destination, graph);

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

            for(Edge edge : graph.getAdjEdges(node)){
                if(edge.valid()){
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

    /**
     * Relaxes an edge from the graph
     * @param edge An edge from the graph
     * @param pq The priority queue that contains the nodes that will be processed and there distance from the source node
     * @param distTo Contains the distances between each node of the graph and the source one
     * @param edgeTo Contains the edge where each node come from in order to form a path
     */
    private void relax(Edge edge, PriorityQueue<Map.Entry<Node, Double>> pq, Map<Node, Double> distTo, Map<Node, Edge> edgeTo){
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

    /**
     * Returns the next location that should be visited between two nodes from a graph according to the shortest path algorithm
     * @param source The source node
     * @param destination The destination node
     * @param graph The graph that contains the source and destination nodes
     * @return the next location that should be visited between two nodes from a graph according to the shortest path algorithm
     */
    public Edge getNextLocation(Node source, Node destination, Graph graph){
        Check.checkPathArgument(source, destination, graph);

        List<Edge> path = this.getPath(source, destination, graph);
        if(path == null || path.isEmpty()){
            return null;
        }
        return path.get(0);
    }

    /**
     * Returns the total value of a path
     * @param path A path that contains a list of edges
     * @return the total value of a path
     */
    private double getPathValue(List<Edge> path){
        if(path == null || path.isEmpty()){
            return 0;
        }

        double total = 0;
        for(Edge edge : path){
            total += this.getEdgeValue(edge);
        }

        return total;
    }

    /**
     * Returns the closest exit from a source node in the graph
     * @param source A source node from a graph
     * @param graph A graph that contains the source node
     * @return the closest exit from a source node in the graph
     */
    public Node getClosestExit(Node source, Graph graph){
        Check.checkNullArgument(source, "The source node is null");
        Check.checkNullArgument(source, "The graph is null");

        List<Node> exits = new ArrayList<>();

        for(Node node : graph.getAllNodes()){
            if(node.getType() == NodeType.EXIT && node.getState() == LocationState.OPEN){
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