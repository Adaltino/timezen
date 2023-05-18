package faculdade.timezen.dao

import faculdade.timezen.planModel.Plan

class PlansDao {

    fun addPlan(plan: Plan) {
        Companion.Plan.add(plan)
    }

    fun buscarTodos() : List<Plan> {
        return Companion.Plan.toList()
    }

    companion object {
        private val Plan = mutableListOf<Plan>()
    }
}