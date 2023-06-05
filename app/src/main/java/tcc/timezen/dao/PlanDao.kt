package tcc.timezen.dao

import tcc.timezen.model.Plan

class PlanDao {

    fun addPlan(plan: Plan) {
        Plan.add(plan)
    }

    fun listAll() : List<Plan> {
        return Plan.toList()
    }

    fun getPlan(i: Int) = Plan.get(i)

    companion object {
        private val Plan = mutableListOf<Plan>()
    }
}