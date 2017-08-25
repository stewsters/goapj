package com.stewsters.mk2;


public class WorldState implements Comparable<WorldState> {

    public WorldState parentState;
    public Action parentAction;

    public boolean atAirship;
    public boolean robotHasGear;
    public int scoredGears;
    public float cost;


    public WorldState() {

        atAirship = true;
        robotHasGear = false;
        scoredGears = 0;
        cost = 0;
        parentState = null;
    }

    public WorldState(WorldState old) {
        atAirship = old.atAirship;
        robotHasGear = old.robotHasGear;
        scoredGears = old.scoredGears;
        cost = old.cost;
        parentState = old;
    }

    @Override
    public int compareTo(WorldState o) {
        return Float.compare(cost, o.cost);
    }
}
