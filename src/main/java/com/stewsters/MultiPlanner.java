package com.stewsters;

import com.stewsters.path.PathNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MultiPlanner {

    private ArrayList<Planner> planners;
    private ArrayList<List<PathNode>> plans;

    public MultiPlanner() {

        planners = new ArrayList<Planner>();
        plans = new ArrayList<List<PathNode>>();

    }

    public void addPlanner(Planner planner) {
        planners.add(planner);
    }

    public void calculate() {
        plans = new ArrayList<List<PathNode>>();

        for (Planner planner : planners) {
            plans.add(planner.calculate());
        }

    }

    public ArrayList<PathNode> getPlan() {

        HashMap<Float, List<PathNode>> tempPlans = new HashMap<Float, List<PathNode>>();

        for (List<PathNode> plan : plans) {
            Float planCost = 0f;
            for (PathNode action : plan) {
                planCost += action.g;
            }

            if (tempPlans.containsKey(planCost)) {
                tempPlans.get(planCost).addAll(plan);
            } else {
                tempPlans.put(planCost, plan);
            }
            //TODO: this will fail if 2 plans have the same cost?
        }


        List<Float> sortedPlanCostss = new ArrayList<Float>();
        sortedPlanCostss.addAll(tempPlans.keySet());
        Collections.sort(sortedPlanCostss);


        ArrayList<PathNode> finalPlan = new ArrayList<PathNode>();
        for (Float planCost : sortedPlanCostss) {
            finalPlan.addAll(tempPlans.get(planCost));
        }
        return finalPlan;
    }

}
