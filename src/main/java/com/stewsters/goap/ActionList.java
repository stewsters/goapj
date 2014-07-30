package com.stewsters.goap;

import java.util.HashMap;

/**
 * Created by bloodred on 7/23/14.
 */
public class ActionList {

    public HashMap<String, GameState> conditions = new HashMap<String, GameState>();
    public HashMap<String, GameState> reactions = new HashMap<String, GameState>();
    public HashMap<String, Float> weights = new HashMap<String, Float>();

    public void addCondition(String key, HashMap<String, Boolean> kwargs) {
        if (!weights.containsKey(key)) {
            weights.put(key, 1f);
        }

        if (!conditions.containsKey(key)) {
            GameState gameState = new GameState();
            gameState.state = kwargs;
            conditions.put(key, gameState);

        } else {
            conditions.get(key).state.putAll(kwargs);
        }
    }


    public void addReaction(String key, HashMap<String, Boolean> kwargs) {
        if (!conditions.containsKey(key)) {
            throw new RuntimeException("Trying to add reaction ${key} without matching condition.");
        }

        if (!reactions.containsKey(key)) {
            GameState gameState = new GameState();
            gameState.state = kwargs;
            reactions.put(key, gameState);

        } else {
            reactions.get(key).state.putAll(kwargs);
        }
    }

    public void setWeight(String key, Float value) {

        weights.put(key, value);
    }

}
