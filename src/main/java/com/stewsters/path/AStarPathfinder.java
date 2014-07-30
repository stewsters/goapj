package com.stewsters.path;

import com.stewsters.goap.GameState;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class AStarPathfinder {


    private static boolean conditionsAreMet(GameState state1, GameState state2) {
        for (String key : state2.state.keySet()) {
            if (state2.state.get(key) == null) {
                continue;
            }
            if (state1.state.get(key) != state2.state.get(key)) {
                return false;
            }
        }
        return true;
    }


    private static boolean nodeInList(PathNode node, HashMap<Integer, PathNode> nodeList) {
        for (PathNode nextNode : nodeList.values()) {
            if (node.gameState == nextNode.gameState && node.name == nextNode.name)
                return true;
        }
        return false;
    }

    private static PathNode createNode(Path path, GameState state, String name) {

        path.nodeId++;

        PathNode newNode = new PathNode();

        newNode.id = path.nodeId;
        newNode.parentId = null;
        newNode.name = name;
        newNode.gameState = state;
        newNode.f = 0;
        newNode.g = 0;
        newNode.h = 0;

        path.nodes.put(path.nodeId, newNode);

        return path.nodes.get(path.nodeId);
    }

    public static List<PathNode> aStar(GameState startState,
                                       GameState goalState,
                                       HashMap<String, GameState> conditions,
                                       HashMap<String, GameState> reactions,
                                       HashMap<String, Float> weights) {

        Path path = new Path(goalState, conditions, reactions, weights);

        PathNode startNode = createNode(path, startState, "start");
        startNode.g = 0;
        startNode.h = startState.distanceToState(goalState);
        startNode.f = startNode.g + startNode.h;
        path.olist.put(startNode.id, startNode);

        for (String condition : conditions.keySet()) {
            path.actionNodes.put(condition, createNode(path, conditions.get(condition), "action"));
        }
        return walkPath(path);
    }

    private static List<PathNode> walkPath(Path path) {

        PathNode node = null;

        while (path.olist.size() > 0) {


            // Find lowest node

            int lowestNodeId = -1;
            float lowestF = Float.MAX_VALUE;

            for (PathNode nextNode : path.olist.values()) {
                if (lowestNodeId < 0 || nextNode.f < lowestF) {
                    lowestNodeId = nextNode.id;
                    lowestF = nextNode.f;
                }
            }

            if (lowestNodeId >= 0) {
                node = path.nodes.get(lowestNodeId);
            } else {
                return new ArrayList();
            }

            // Remove the lowest rank
            path.olist.remove(node.id);

            // If it matches the goal. we are done
            if (conditionsAreMet(node.gameState, path.state)) {

                ArrayList rtnPath = new ArrayList(); // [[name:"goal", state:path['goal']]]

                //here we have the final path, we need to iterate back to the beginning state
                while (node.parentId != null) {
                    rtnPath.add(node);
                    node = path.nodes.get(node.parentId);
                }

                rtnPath.add(node);
                Collections.reverse(rtnPath);

                return rtnPath;
            }

            //Add it to closed
            path.clist.put(node.id, node);

            //Find neighbor
            List<PathNode> neighbors = new ArrayList<PathNode>();

            for (String actionName : path.actionNodes.keySet()) {

                if (!conditionsAreMet(node.gameState, path.actionNodes.get(actionName).gameState)) {
                    continue;
                }

                path.nodeId++;

                PathNode cNode = node.clone();
                cNode.gameState = node.gameState.clone();
                cNode.id = path.nodeId;
                cNode.name = actionName;

                for (String key : path.reactions.get(actionName).state.keySet()) {
                    Boolean value = path.reactions.get(actionName).state.get(key);
                    if (value == null)
                        continue;
                    if (cNode.gameState.state.get(key) == null) {
                        continue;
                    }
                    cNode.gameState.state.put(key, value);
                }
                path.nodes.put(cNode.id, cNode);
                neighbors.add(cNode);
            }

            for (PathNode nextNode : neighbors) {
                Float gCost = node.g + path.weights.get(nextNode.name);// weight_table[nextNode.name]
                boolean inOlist = nodeInList(nextNode, path.olist);
                boolean inClist = nodeInList(nextNode, path.clist);

                if (inOlist && gCost < nextNode.g) {
                    path.olist.remove(nextNode.id);
                }

                if (inClist && gCost < nextNode.g) {
                    path.clist.remove(nextNode.id);
                }

                if (!inOlist && !inClist) {
                    nextNode.g = gCost;
                    nextNode.h = nextNode.gameState.distanceToState(path.state);
                    nextNode.f = nextNode.g + nextNode.h;
                    nextNode.parentId = node.id;

                    path.olist.put(nextNode.id, nextNode);
                }
            }
        }

        return new ArrayList<PathNode>();
    }
}