package com.app.model;

import com.app.model.simulation.Simulation;

public class Main {
    
    public static void main(String[] args){
        Simulation simulation = new Simulation();

        System.out.println(simulation.getGraph());

        try{
            //simulation.getGraph().removeEdge(simulation.getGraph().getAllEdges().get(0));
        } catch(Exception e) {
            return;
        }

        simulation.move();

        System.out.println(simulation.getGraph());

        //System.out.println("\n\n" + Dijkstra.nextLocation(simulation.getGraph(), simulation.getGraph().getAllNodes().get(0), simulation.getGraph().getAllNodes().get(1)) + "\n\n");


    }
}
