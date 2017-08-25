import com.stewsters.mk2.Action;
import com.stewsters.mk2.PlannerMk2;
import com.stewsters.mk2.WorldState;
import org.junit.Test;

import java.util.ArrayList;

public class RobotTest {


    @Test
    public void testPlannerOnGame() {

        WorldState startingWorldState = new WorldState();
        startingWorldState.robotHasGear = true;
        int maxCost = 100;

        ArrayList<Action> actions = new ArrayList<>();

        actions.add(new Action(
                "Load Gear",
                (WorldState w) -> {
                    return !w.robotHasGear && !w.atAirship;
                },
                (WorldState w) -> {
                    w.robotHasGear = true;
                    return w;
                },
                10));


        actions.add(new Action(
                "Place Gear",
                (WorldState w) -> {
                    return w.robotHasGear && w.atAirship;
                },
                (WorldState w) -> {
                    w.robotHasGear = false;
                    w.scoredGears++;
                    return w;
                },
                10));

        actions.add(new Action(
                "Go To Airship",
                (WorldState w) -> {
                    return !w.atAirship;
                },
                (WorldState w) -> {
                    w.atAirship = true;
                    return w;
                },
                10));

        actions.add(new Action(
                "Go To Loading",
                (WorldState w) -> {
                    return w.atAirship;
                },
                (WorldState w) -> {
                    w.atAirship = false;
                    return w;
                },
                10));

        PlannerMk2.plan(startingWorldState,
                (WorldState w) -> w.scoredGears,
                actions,
                maxCost
        ).get().stream().forEach(it -> System.out.println(it.getName()));

    }

}
