import com.stewsters.World
import com.stewsters.goap.ActionList
import com.stewsters.path.PathNode
import org.junit.Test

class SleepTestInGroovy {

    @Test
    public void testFood() {
        World world = new World<String>(['hungry', 'has_food', 'in_kitchen', 'tired', 'in_bed'])

        world.setStartState(hungry: true, has_food: false, in_kitchen: false, tired: true, in_bed: false)

        world.setGoalState(tired: false)


        def actions = new ActionList()
        actions.addCondition('eat', [hungry: true, has_food: true, in_kitchen: false])
        actions.addReaction('eat', [hungry: false])
        actions.addCondition('cook', [hungry: true, has_food: false, in_kitchen: true])
        actions.addReaction('cook', [has_food: true])
        actions.addCondition('sleep', [tired: true, in_bed: true])
        actions.addReaction('sleep', [tired: false])
        actions.addCondition('go_to_bed', [in_bed: false, hungry: false])
        actions.addReaction('go_to_bed', [in_bed: true])
        actions.addCondition('go_to_kitchen', [in_kitchen: false])
        actions.addReaction('go_to_kitchen', [in_kitchen: true])
        actions.addCondition('leave_kitchen', [in_kitchen: true])
        actions.addReaction('leave_kitchen', [in_kitchen: false])
        actions.addCondition('order_pizza', [has_food: false, hungry: true])
        actions.addReaction('order_pizza', [has_food: true])

        actions.setWeight('go_to_kitchen', 20)
        actions.setWeight('order_pizza', 1)


        world.setActionList(actions)

        List<PathNode> path = world.calculate()

        def desired = ["start",
                       "order_pizza",
                       "eat",
                       "go_to_bed",
                       "sleep"]

        path.eachWithIndex { def entry, int i ->
            println "${i + 1} $entry"
            assert entry.name == (desired[i]);
        }




        println "done"
    }

}