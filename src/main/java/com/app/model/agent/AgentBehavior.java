package com.app.model.agent;

/**
 * The enumeration that contains the different behaviors of an agent
 */
public enum AgentBehavior {

    /**
     * An agent that gives priority to others
     */
    PRIORITYGIVEN(-1),
    
    /**
     * A normal agent
     */
    NORMAL(0),
    
    /**
     * An agent that prioritizes himself over others
     */
    PRIORITYTAKEN(1);

    /**
     * A value related to the agent behavior
     */
    private int value;

    /**
     * The agent behavior constructor that takes as an argument a value
     * @param value A value related to the agent behavior
     */
    private AgentBehavior(int value){
        this.value = value;
    }

    /**
     * Returns the value related to an agent behavior
     * @return the value related to an agent behavior
     */
    public int getValue(){
        return this.value;
    }

}