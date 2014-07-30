package com.stewsters.path;

import com.stewsters.goap.GameState;

public class PathNode {

    public int id;
    public Integer parentId;

    public String name;

    public GameState gameState;

    public float f;
    public float g;
    public float h;


    public PathNode clone() {
        PathNode pathNode = new PathNode();

        pathNode.id = id;
        pathNode.parentId = parentId;
        pathNode.name = name;
        pathNode.gameState = gameState.clone();
        pathNode.f = f;
        pathNode.g = g;
        pathNode.h = h;

        return pathNode;
    }

    public String toString() {
        return name;
    }


}
