package com.stewsters.path;

import com.stewsters.goap.GameState;

import java.util.HashMap;


public class Path {


    private final HashMap<String, GameState> actions;
    public final HashMap<String, GameState> reactions;
    public final HashMap<String, Float> weights;

    public final HashMap<String, PathNode> actionNodes;

    public HashMap<Integer, PathNode> nodes;

    public int nodeId;

    public GameState state;

    public HashMap<Integer, PathNode> olist;
    public HashMap<Integer, PathNode> clist;


    public Path(GameState goalState, HashMap<String, GameState> actions, HashMap<String, GameState> reactions, HashMap<String, Float> weights) {

        nodes = new HashMap<Integer, PathNode>();
        nodeId = 0;
        this.state = goalState;
        this.actions = actions;
        this.reactions = reactions;
        this.weights = weights;

        actionNodes = new HashMap<String, PathNode>();

        olist = new HashMap<Integer, PathNode>();
        clist = new HashMap<Integer, PathNode>();
    }

}