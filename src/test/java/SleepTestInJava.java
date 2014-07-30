import com.stewsters.World;
import com.stewsters.goap.ActionList;
import com.stewsters.path.PathNode;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bloodred on 7/29/14.
 */
public class SleepTestInJava {


    @Test
    public void testReloading() {

        World world = new World(Arrays.asList("hungry", "has_food", "in_kitchen", "tired", "in_bed"));

        HashMap startState = new HashMap();
        startState.put("hungry", true);
        startState.put("has_food", false);
        startState.put("in_kitchen", false);
        startState.put("tired", true);
        startState.put("in_bed", false);

        world.setStartState(startState);

        HashMap goalState = new HashMap();
        goalState.put("tired", false);
        world.setGoalState(goalState);


        ActionList actions = new ActionList();
        HashMap eatCondition = new HashMap();
        eatCondition.put("hungry", true);
        eatCondition.put("has_food", true);
        eatCondition.put("in_kitchen", false);
        actions.addCondition("eat", eatCondition);

        HashMap eatReaction = new HashMap();
        eatReaction.put("hungry", false);
        actions.addReaction("eat", eatReaction);


        HashMap cookCondition = new HashMap();
        cookCondition.put("hungry", true);
        cookCondition.put("has_food", false);
        cookCondition.put("in_kitchen", true);
        actions.addCondition("cook", cookCondition);

        HashMap cookReaction = new HashMap();
        cookReaction.put("has_food", true);
        actions.addReaction("cook", cookReaction);


        HashMap sleepCondition = new HashMap();
        sleepCondition.put("tired", true);
        sleepCondition.put("in_bed", true);
        actions.addCondition("sleep", sleepCondition);

        HashMap sleepReaction = new HashMap();
        sleepReaction.put("tired", false);
        actions.addReaction("sleep", sleepReaction);


        // go to bed
        HashMap goToBedCondition = new HashMap();
        goToBedCondition.put("in_bed", false);
        goToBedCondition.put("hungry", false);
        actions.addCondition("go_to_bed", goToBedCondition);

        HashMap goToBedReaction = new HashMap();
        goToBedReaction.put("in_bed", true);
        actions.addReaction("go_to_bed", goToBedReaction);

        // go to kitchen
        HashMap goToKitchenCondition = new HashMap();
        goToKitchenCondition.put("in_kitchen", false);
        actions.addCondition("go_to_kitchen", goToKitchenCondition);

        HashMap goToKitchenReaction = new HashMap();
        goToKitchenReaction.put("in_kitchen", true);
        actions.addReaction("go_to_kitchen", goToKitchenReaction);

        // leave kitchen
        HashMap leaveKitchenCondition = new HashMap();
        leaveKitchenCondition.put("in_kitchen", true);
        actions.addCondition("leave_kitchen", leaveKitchenCondition);

        HashMap leaveKitchenReaction = new HashMap();
        leaveKitchenReaction.put("in_kitchen", false);
        actions.addReaction("leave_kitchen", leaveKitchenReaction);


        // order pizza
        HashMap orderPizzaCondition = new HashMap();
        orderPizzaCondition.put("has_food", false);
        orderPizzaCondition.put("hungry", true);
        actions.addCondition("order_pizza", orderPizzaCondition);

        HashMap orderPizzaReaction = new HashMap();
        orderPizzaReaction.put("has_food", true);
        actions.addReaction("order_pizza", orderPizzaReaction);

        // set Weights
        actions.setWeight("go_to_kitchen", 20f);
        actions.setWeight("order_pizza", 1f);

        world.setActionList(actions);

        List<PathNode> path = world.calculate();

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
