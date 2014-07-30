import com.stewsters.MultiPlanner
import com.stewsters.Planner
import com.stewsters.goap.ActionList
import com.stewsters.path.PathNode
import org.junit.Test

class CombatTestInGroovy {

    @Test
    public void testCombat() {
        Planner combatBrain = new Planner()

        combatBrain.setStartState(
                in_engagement: true,
                is_near: false,
                in_cover: false,
                in_enemy_los: true,
                has_ammo: false,
                has_weapon: true,
                weapon_armed: false,
                weapon_loaded: false
        )

        combatBrain.setGoalState(in_engagement: false)


        def combatActions = new ActionList()
        combatActions.addCondition('track', [is_near: false, weapon_armed: true])
        combatActions.addReaction('track', [is_near: true])

        combatActions.addCondition('unpack_ammo', [has_ammo: false])
        combatActions.addReaction('unpack_ammo', [has_ammo: true])

        combatActions.addCondition('search_for_ammo', [has_ammo: false])
        combatActions.addReaction('search_for_ammo', [has_ammo: true])

        combatActions.addCondition('reload', [has_ammo: true, weapon_loaded: false, in_cover: true])
        combatActions.addReaction('reload', [weapon_loaded: true])

        combatActions.addCondition('arm', [weapon_loaded: true, weapon_armed: false])
        combatActions.addReaction('arm', [weapon_armed: true])

        combatActions.addCondition('shoot', [weapon_loaded: true, weapon_armed: true, is_near: true])
        combatActions.addReaction('shoot', [in_engagement: false])

        combatActions.addCondition('get_cover', [in_cover: false])
        combatActions.addReaction('get_cover', [in_cover: true])

        combatActions.setWeight('unpack_ammo', 3)
        combatActions.setWeight('search_for_ammo', 4)
        combatActions.setWeight('track', 20)


        combatBrain.setActionList(combatActions)


        Planner foodBrain = new Planner();
        foodBrain.setStartState(has_food: false, is_hungry: true)
        foodBrain.setGoalState(is_hungry: false)

        def foodActions = new ActionList()
        foodActions.addCondition('find_food', [has_food: false])
        foodActions.addReaction('find_food', [has_food: true])

        foodActions.addCondition('eat_food', [has_food: true])
        foodActions.addReaction('eat_food', [is_hungry: false])

        foodActions.setWeight('find_food', 20)
        foodActions.setWeight('eat_food', 10)

        foodBrain.setActionList(foodActions)


        Planner healBrain = new Planner()
        healBrain.setStartState(has_bandage: false, is_hurt: true)
        healBrain.setGoalState(is_hurt: false)


        def healActions = new ActionList()
        healActions.addCondition('find_bandage', [has_bandage: false])
        healActions.addReaction('find_bandage', [has_bandage: true])

        healActions.addCondition('apply_bandage', [has_bandage: true])
        healActions.addReaction('apply_bandage', [is_hurt: false])

        healActions.setWeight('find_bandage', 15)

        healBrain.setActionList(healActions)


        MultiPlanner brain = new MultiPlanner()

        brain.addPlanner(combatBrain)
        brain.addPlanner(foodBrain)
        brain.addPlanner(healBrain)

        brain.calculate()

        List<PathNode> path = brain.getPlan()
        path.removeAll({ it.name == "start" })

        def desired = [
                "find_bandage",
                "apply_bandage",

                "find_food",
                "eat_food",

                "get_cover",
                "unpack_ammo",
                "reload",
                "arm",
                "track",
                "shoot"]

        println path

        path.eachWithIndex { PathNode entry, Integer i ->
            println "${i + 1} $entry"
            assert entry.name == (desired[i]);
        }


        println "done"
    }

}