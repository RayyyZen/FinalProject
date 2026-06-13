package com.app.model.agent;

import com.app.model.agent.path.*;
import com.app.model.agent.path.shortestpath.*;
import com.app.model.util.Check;

/**
 * The enumeration that contains the different states of an agent
 * @version 3.0
 * @since 1.0
 * @author Rayane
 */
public enum AgentState {

    /**
     * Calm agent
     */
    CALM(new ShortestTimePath()),
    
    /**
     * Tired agent
     */
    TIRED(new ShortestDistancePath()),
    
    /**
     * Panicked agent
     */
    PANICKED(new FollowerPath()),
    
    /**
     * Crazy agent
     */
    CRAZY(new RandomPath());

    /**
     * A path finder according to the agent state
     */
    private PathFinder pathFinder;

    /**
     * The agent state constructor that takes as an argument a path finder
     * @param pathFinder A pathFinder
     */
    private AgentState(PathFinder pathFinder){
        Check.checkNullArgument(pathFinder, "The path finder is null");

        this.pathFinder = pathFinder;
    }

    /**
     * Returns the path finder according to an agent state
     * @return the path finder according to an agent state
     */
    public PathFinder getPathFinder(){
        return this.pathFinder;
    }
}