package com.stewsters;

import com.stewsters.goap.GameState;
import com.stewsters.path.AStarPathfinder;
import com.stewsters.goap.ActionList;
import com.stewsters.path.PathNode;

import java.util.HashMap;
import java.util.List;

public class World {

    private GameState startState;
    private GameState goalState;

    private HashMap<String, Boolean> values;
    private ActionList actionList;

    public World(List<String> keys) {
        startState = null;
        goalState = null;
        values = new HashMap<String, Boolean>();
        actionList = null;
    }

    // Returns a copy of the values with selected changes merged in
    private GameState state(HashMap<String, Boolean> updatedMap) {

        GameState gameState = new GameState();
        gameState.state = (HashMap<String, Boolean>) values.clone();
        gameState.state.putAll(updatedMap);

        return gameState;
    }

    /**
     * Sets a start state.  First checks for invalid ones, then modifies the
     * current state for the desired parameters before setting the goal
     */
    public void setStartState(HashMap<String, Boolean> kwargs) {
        startState = state(kwargs);
    }

    public void setGoalState(HashMap<String, Boolean> kwargs) {
        goalState = state(kwargs);
    }

    public void setActionList(ActionList actionList) {
        this.actionList = actionList;
    }

    public List<PathNode> calculate() {
        return AStarPathfinder.aStar(
                startState,
                goalState,
                actionList.conditions,
                actionList.reactions,
                actionList.weights
        );
    }


}