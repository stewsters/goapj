package com.stewsters.goap;

import java.util.HashMap;
import java.util.HashSet;

/**
 * This is the state of the game.
 */
public class GameState {

    public HashMap<String, Boolean> state;


    public GameState clone() {

        GameState gameState = new GameState();

        gameState.state = (HashMap<String, Boolean>) state.clone();
        return gameState;

    }


    // Hamming distance?
    public float distanceToState(GameState other) {

        HashSet scoredKeys = new HashSet();

        float score = 0;

        for (String key : other.state.keySet()) {

            Boolean value = other.state.get(key);

            if (value == null)
                continue;

            if (value != this.state.get(key))
                score++;

            scoredKeys.add(key);

        }

        for (String key : this.state.keySet()) {

            Boolean value = this.state.get(key);

            if (value == null)
                continue;

            if (value != this.state.get(key))
                score++;

            scoredKeys.add(key);

        }
        for (String key : this.state.keySet()) {
            Boolean value = this.state.get(key);

            if (value == null || scoredKeys.contains(key))
                continue;

            if (!value == other.state.get(key)) {
                score++;
            }
        }
        return score;

    }


}
