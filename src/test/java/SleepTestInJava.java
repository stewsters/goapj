import com.stewsters.Planner;
import com.stewsters.goap.ActionList;
import com.stewsters.path.PathNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SleepTestInJava {


    @Test
    public void testReloading() {

        Planner planner = new Planner();

        HashMap<String, Boolean> startState = new HashMap<String, Boolean>();
        startState.put("hungry", true);
        startState.put("has_food", false);
        startState.put("in_kitchen", false);
        startState.put("tired", true);
        startState.put("in_bed", false);

        planner.setStartState(startState);

        HashMap<String, Boolean> goalState = new HashMap<String, Boolean>();
        goalState.put("tired", false);
        planner.setGoalState(goalState);


        ActionList actions = new ActionList();
        HashMap<String, Boolean> eatCondition = new HashMap<String, Boolean>();
        eatCondition.put("hungry", true);
        eatCondition.put("has_food", true);
        eatCondition.put("in_kitchen", false);
        actions.addCondition("eat", eatCondition);

        HashMap<String, Boolean> eatReaction = new HashMap<String, Boolean>();
        eatReaction.put("hungry", false);
        actions.addReaction("eat", eatReaction);


        HashMap<String, Boolean> cookCondition = new HashMap<String, Boolean>();
        cookCondition.put("hungry", true);
        cookCondition.put("has_food", false);
        cookCondition.put("in_kitchen", true);
        actions.addCondition("cook", cookCondition);

        HashMap<String, Boolean> cookReaction = new HashMap<String, Boolean>();
        cookReaction.put("has_food", true);
        actions.addReaction("cook", cookReaction);


        HashMap<String, Boolean> sleepCondition = new HashMap<String, Boolean>();
        sleepCondition.put("tired", true);
        sleepCondition.put("in_bed", true);
        actions.addCondition("sleep", sleepCondition);

        HashMap<String, Boolean> sleepReaction = new HashMap<String, Boolean>();
        sleepReaction.put("tired", false);
        actions.addReaction("sleep", sleepReaction);


        // go to bed
        HashMap<String, Boolean> goToBedCondition = new HashMap<String, Boolean>();
        goToBedCondition.put("in_bed", false);
        goToBedCondition.put("hungry", false);
        actions.addCondition("go_to_bed", goToBedCondition);

        HashMap<String, Boolean> goToBedReaction = new HashMap<String, Boolean>();
        goToBedReaction.put("in_bed", true);
        actions.addReaction("go_to_bed", goToBedReaction);

        // go to kitchen
        HashMap<String, Boolean> goToKitchenCondition = new HashMap<String, Boolean>();
        goToKitchenCondition.put("in_kitchen", false);
        actions.addCondition("go_to_kitchen", goToKitchenCondition);

        HashMap<String, Boolean> goToKitchenReaction = new HashMap<String, Boolean>();
        goToKitchenReaction.put("in_kitchen", true);
        actions.addReaction("go_to_kitchen", goToKitchenReaction);

        // leave kitchen
        HashMap<String, Boolean> leaveKitchenCondition = new HashMap<String, Boolean>();
        leaveKitchenCondition.put("in_kitchen", true);
        actions.addCondition("leave_kitchen", leaveKitchenCondition);

        HashMap<String, Boolean> leaveKitchenReaction = new HashMap<String, Boolean>();
        leaveKitchenReaction.put("in_kitchen", false);
        actions.addReaction("leave_kitchen", leaveKitchenReaction);


        // order pizza
        HashMap<String, Boolean> orderPizzaCondition = new HashMap<String, Boolean>();
        orderPizzaCondition.put("has_food", false);
        orderPizzaCondition.put("hungry", true);
        actions.addCondition("order_pizza", orderPizzaCondition);

        HashMap<String, Boolean> orderPizzaReaction = new HashMap<String, Boolean>();
        orderPizzaReaction.put("has_food", true);
        actions.addReaction("order_pizza", orderPizzaReaction);

        // set Weights
        actions.setWeight("go_to_kitchen", 20f);
        actions.setWeight("order_pizza", 1f);

        planner.setActionList(actions);

        List<PathNode> path = planner.calculate();

        List<String> desired = Arrays.asList("start",
                "order_pizza",
                "eat",
                "go_to_bed",
                "sleep");

        int i = 0;
        for (PathNode pathNode : path) {

            System.out.println(pathNode);
            assert pathNode.name.equals(desired.get(i));
            i++;
        }
    }
}
